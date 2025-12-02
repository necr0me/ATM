package by.necr0me;


import by.necr0me.infrastructure.context.ApplicationContext;

import java.util.Map;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = Application.run("by.necr0me", Map.of());
    }
}