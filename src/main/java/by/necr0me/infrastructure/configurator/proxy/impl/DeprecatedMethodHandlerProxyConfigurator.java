package by.necr0me.infrastructure.configurator.proxy.impl;

import by.necr0me.infrastructure.annotation.Deprecated;
import by.necr0me.infrastructure.configurator.proxy.ProxyConfigurator;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;

public class DeprecatedMethodHandlerProxyConfigurator implements ProxyConfigurator {
    private List<Method> deprecatedMethods;

    @Override
    public Object replaceWithProxyIfNeeded(Object t, Class implClass) {
        deprecatedMethods = Arrays.stream(implClass.getMethods()).filter(m -> m.isAnnotationPresent(Deprecated.class)).map(this::getInterfaceMethod).toList();

        if(!deprecatedMethods.isEmpty()) {
            if(implClass.getInterfaces().length == 0) {
                return Enhancer.create(implClass, (InvocationHandler) (proxy, method, args) -> getInvocationHandlerLogic(method, args, t));
            } else {
                return Proxy.newProxyInstance(implClass.getClassLoader(), implClass.getInterfaces(), (proxy, method, args) -> getInvocationHandlerLogic(method, args, t));
            }
        } else {
            return t;
        }
    }

    private Method getInterfaceMethod(Method method) {
        for(Class<?> clazz : method.getDeclaringClass().getInterfaces()) {
            try {
                return clazz.getMethod(method.getName(), method.getParameterTypes());
            } catch (NoSuchMethodException ignored) {}
        }

        return method;
    }

    private Object getInvocationHandlerLogic(Method method, Object[] args, Object t) throws InvocationTargetException, IllegalAccessException {
        if(deprecatedMethods.contains(method)) {
            System.out.println("WARNING: Method " + method.toGenericString() + " is deprecated");
        }
        return method.invoke(t, args);
    }
}
