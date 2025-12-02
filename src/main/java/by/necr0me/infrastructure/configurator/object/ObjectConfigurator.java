package by.necr0me.infrastructure.configurator.object;

import by.necr0me.infrastructure.context.ApplicationContext;

public interface ObjectConfigurator {
    void configure(Object object, ApplicationContext context);
}
