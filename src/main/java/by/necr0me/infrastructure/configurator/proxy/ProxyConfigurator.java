package by.necr0me.infrastructure.configurator.proxy;

public interface ProxyConfigurator {
    Object replaceWithProxyIfNeeded(Object t, Class implClass);
}
