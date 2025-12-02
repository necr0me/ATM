package by.necr0me.infrastructure.configurator.proxy.impl;

import by.necr0me.infrastructure.annotation.Deprecated;
import by.necr0me.infrastructure.configurator.proxy.ProxyConfigurator;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DeprecatedClassHandlerProxyConfigurator implements ProxyConfigurator {
    @Override
    public Object replaceWithProxyIfNeeded(Object t, Class implClass) {
        if(implClass.isAnnotationPresent(Deprecated.class)) {
            if(implClass.getInterfaces().length == 0) {
                return Enhancer.create(implClass, (InvocationHandler) (proxy, method, args) -> getInvocationHandlerLogic(method, args, t, implClass));
            } else {
                return Proxy.newProxyInstance(implClass.getClassLoader(), implClass.getInterfaces(), (proxy, method, args) -> getInvocationHandlerLogic(method, args, t, implClass));
            }
        } else {
            return t;
        }
    }

    private Object getInvocationHandlerLogic(Method method, Object[] args, Object t, Class implClass) throws InvocationTargetException, IllegalAccessException {
        System.out.println("WARNING: Class " + implClass.getName() + " is deprecated");
        return method.invoke(t, args);
    }
}