package by.necr0me.infrastructure.configurator.object.impl;

import by.necr0me.infrastructure.annotation.InjectProperty;
import by.necr0me.infrastructure.configurator.object.ObjectConfigurator;
import by.necr0me.infrastructure.context.ApplicationContext;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class ValueAnnotationObjectConfigurator implements ObjectConfigurator {
    private Map<String, String> propertiesMap;

    public ValueAnnotationObjectConfigurator() {
        try {
            String path = ClassLoader.getSystemClassLoader().getResource("application.properties").getPath(); // TODO: what if props file absent?
            Stream<String> lines = new BufferedReader(new FileReader(path)).lines();
            propertiesMap = lines.map(line -> line.split("=")).collect(toMap(arr -> arr[0], arr -> arr[1]));
        } catch (NullPointerException | FileNotFoundException ignored) {}
    }

    @Override
    @SneakyThrows // TODO: get rid of
    public void configure(Object object, ApplicationContext context) {
        Class<?> implClass = object.getClass();
        for (Field field : implClass.getDeclaredFields()) { // TODO: resolve inheritance
            InjectProperty annotation = field.getAnnotation(InjectProperty.class);

            if(annotation != null) {
                String value = annotation.propertyName().isEmpty() ? propertiesMap.get(field.getName()) : propertiesMap.get(annotation.propertyName());
                field.setAccessible(true);
                field.set(object, value);
            }
        }
    }
}
