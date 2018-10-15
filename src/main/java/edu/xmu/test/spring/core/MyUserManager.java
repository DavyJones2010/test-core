package edu.xmu.test.spring.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Created by davywalker on 16/10/31.
 */
public class MyUserManager {
    // @Resource与Autowired都可以将FactoryBean自动装配到property上
    // @Resource(name = "myUser")
    @Autowired
    @Qualifier("myUser")
    User user;

    public String getUserName() {
        return user.getUsername();
    }

    public User getUser() {
        return user;
    }
}
