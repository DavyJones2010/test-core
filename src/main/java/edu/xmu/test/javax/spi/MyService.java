package edu.xmu.test.javax.spi;

import org.springframework.stereotype.Component;

@Component
public class MyService {
    public void doSomething() {
        System.out.println("hello");
    }
}
