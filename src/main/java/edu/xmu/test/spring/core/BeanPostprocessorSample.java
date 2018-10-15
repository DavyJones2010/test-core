package edu.xmu.test.spring.core;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * {@link <a href="http://forum.spring.io/forum/spring-projects/container/23635-lazy-init-beanpostprocessor-working-fine">BeanPostProcessor lazy-init</a>} <br>
 * It is important to know that a BeanFactory treats
 * bean post-processors slightly differently than an ApplicationContext. An ApplicationContext will
 * automatically detect any beans
 * which are defined in the configuration metadata which is supplied to it that implement the BeanPostProcessor interface, and register them as post-processors, to be then called
 * appropriately by the container on bean creation. Nothing else needs to be done other than deploying the post-processors in a similar fashion to any other bean. *
 */
public class BeanPostprocessorSample {
	public static void main(String[] args) {
        //ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/core/spring-core.xml");
        //String bean = (String) context.getBean("myName");
        //System.out.println(bean);
        //
        //User myUser = (User)context.getBean("myUser");
        //System.out.println(myUser);
        //myUser = (User)context.getBean("myUser");
        //System.out.println(myUser);
        //
        //MyUserManager myUserManager = (MyUserManager)context.getBean("myUserManager");
        //System.out.println(myUserManager.getUser());
        //context.close();
    }
}
