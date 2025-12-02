package by.necr0me;

import by.necr0me.infrastructure.context.ApplicationContext;
import by.necr0me.infrastructure.config.impl.JavaConfig;
import by.necr0me.infrastructure.factory.ObjectFactory;

import java.util.Map;

public class Application {
    public static ApplicationContext run(String packageToScan, Map<Class, Class> ifc2implClass) {
        JavaConfig config = new JavaConfig(packageToScan, ifc2implClass);
        ApplicationContext context = new ApplicationContext(config);
        ObjectFactory objectFactory = new ObjectFactory(context);
        context.setFactory(objectFactory);
        context.initSingletons();

        return context;
    }
}
