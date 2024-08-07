package org.example;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.example.config.AppConfig;

public class Application {

    public static void main(String[] args) {
        new AnnotationConfigApplicationContext(AppConfig.class);
    }
}
