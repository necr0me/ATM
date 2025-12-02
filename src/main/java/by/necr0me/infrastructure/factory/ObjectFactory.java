package by.necr0me.infrastructure.factory;

import by.necr0me.infrastructure.annotation.PostConstruct;
import by.necr0me.infrastructure.configurator.object.ObjectConfigurator;
import by.necr0me.infrastructure.configurator.proxy.ProxyConfigurator;
import by.necr0me.infrastructure.context.ApplicationContext;
import lombok.SneakyThrows;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ObjectFactory {
    private final ApplicationContext context;
    private final List<ObjectConfigurator> objectConfigurators = new ArrayList<>();
    private final List<ProxyConfigurator> proxyConfigurators = new ArrayList<>();

    @SneakyThrows
    public ObjectFactory(ApplicationContext context) {
        this.context = context;
        scanObjectConfigurators(context);
        scanProxyConfigurators(context);
    }

    private void scanProxyConfigurators(ApplicationContext context) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        for (Class<? extends ProxyConfigurator> configurator : context.getConfig().getScanner().getSubTypesOf(ProxyConfigurator.class)) {
            proxyConfigurators.add(configurator.getDeclaredConstructor().newInstance());
        }
    }

    private void scanObjectConfigurators(ApplicationContext context) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        for (Class<? extends ObjectConfigurator> configurator : context.getConfig().getScanner().getSubTypesOf(ObjectConfigurator.class)) {
            objectConfigurators.add(configurator.getDeclaredConstructor().newInstance());
        }
    }

    @SneakyThrows
    public <T> T createObject(Class<T> implClass) {
        T t = create(implClass);

        configure(t);

        invokePostConstruct(implClass, t);

        t = wrapWithProxy(implClass, t);

        return t;
    }

    private <T> T wrapWithProxy(Class<T> implClass, T t) {
        for (ProxyConfigurator configurator : proxyConfigurators) {
            t = (T) configurator.replaceWithProxyIfNeeded(t, implClass);
        }
        return t;
    }

    private <T> void invokePostConstruct(Class<T> implClass, T t) throws IllegalAccessException, InvocationTargetException {
        for (Method method : implClass.getMethods() ) {
            if(method.isAnnotationPresent(PostConstruct.class)) {
                method.invoke(t);
            }
        }
    }

    private <T> void configure(T t) {
        objectConfigurators.forEach(configurator -> configurator.configure(t, context));
    }

    private <T> T create(Class<T> implClass) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        return implClass.getDeclaredConstructor().newInstance();
    }

}
