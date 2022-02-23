package edu.xmu.test.javax.spi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyImpl implements MyInterface {
    @Autowired
    MyService myService;

    @Override
    public void sayHello() {
        myService.doSomething();
    }
}
