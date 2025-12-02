package by.necr0me.infrastructure.context;

import by.necr0me.infrastructure.annotation.Singleton;
import by.necr0me.infrastructure.config.Config;
import by.necr0me.infrastructure.factory.ObjectFactory;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ApplicationContext {
    @Setter
    private ObjectFactory factory;
    private Map<Class, Object> cache = new ConcurrentHashMap<>();
    @Getter
    private Config config;

    public ApplicationContext(Config config) {
        this.config = config;
    }

    public <T> T getObject(Class<T> type) {
        if(cache.containsKey(type)) {
            return (T) cache.get(type);
        }

        Class<? extends T> implClass = type;

        if(type.isInterface()) {
            implClass = config.getImplClass(type);
        }
        T t = factory.createObject(implClass);

        if(implClass.isAnnotationPresent(Singleton.class)) {
            cache.put(type, t);
        }

        return t;
    }

    public void initSingletons() {
        Set<Class<?>> singletons = config.getScanner().getTypesAnnotatedWith(Singleton.class);
        for(Class<?> clazz : singletons) {
            if(!clazz.getAnnotation(Singleton.class).isLazy()) {
                Class<?>[] interfaces = clazz.getInterfaces();
                if(interfaces.length == 0) {
                    cache.put(clazz, factory.createObject(clazz));
                } else if(interfaces.length == 1) {
                    cache.put(clazz, factory.createObject(clazz));
                }
            }
        }
    }
}
