package com.hjr.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MyCommandLineRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        for (String arg : args) {
            System.out.print(arg + " ");
        }
        System.out.println("MyCommandLineRunner -- run");
    }
}
