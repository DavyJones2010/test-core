package edu.xmu.test.spring.core;

import org.springframework.beans.factory.FactoryBean;

/**
 * Created by davywalker on 16/10/31.
 */
public class MyUserFactoryBean implements FactoryBean<User> {
    @Override
    public User getObject() throws Exception {
        return new User("davywalker", "helloworld");
    }

    @Override
    public Class<?> getObjectType() {
        return User.class;
    }

    @Override
    public boolean isSingleton() {
        // 如果被设置为isSingleton == true, 则spring的context会只调用一次getObject()方法, 将生成的bean缓存到context里.
        // 如果被设置为isSingleton == false, 则spring的context在每次需要拿到bean的时候, 都会调用该FactoryBean的getObject方法, 生成bean.
        return true;
    }
}
