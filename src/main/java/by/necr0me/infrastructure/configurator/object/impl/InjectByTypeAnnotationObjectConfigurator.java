package by.necr0me.infrastructure.configurator.object.impl;

import by.necr0me.infrastructure.annotation.InjectByType;
import by.necr0me.infrastructure.configurator.object.ObjectConfigurator;
import by.necr0me.infrastructure.context.ApplicationContext;
import lombok.SneakyThrows;

import java.lang.reflect.Field;

public class InjectByTypeAnnotationObjectConfigurator implements ObjectConfigurator {
    @SneakyThrows
    @Override
    public void configure(Object t, ApplicationContext context) {
        for(Field field : t.getClass().getDeclaredFields()) {
            if(field.isAnnotationPresent(InjectByType.class)) {
                field.setAccessible(true);
                Object object = context.getObject(field.getType());
                field.set(t, object);
            }
        }
    }
}
