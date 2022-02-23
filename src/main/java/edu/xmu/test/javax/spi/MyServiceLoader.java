package edu.xmu.test.javax.spi;

import java.util.ServiceLoader;

/**
 * @see <a href="https://davyjones2010.github.io/2022-02-23-java-minor-bugs/#java-spi%E6%9C%BA%E5%88%B6%E5%88%9B%E5%BB%BA%E7%9A%84%E5%AE%9E%E4%BE%8B%E6%97%A0%E6%B3%95%E8%A2%ABspringcontext%E7%AE%A1%E7%90%86%E9%97%AE%E9%A2%98">Java SPI机制创建的实例无法被SpringContext管理问题</a>
 */
public class MyServiceLoader {
    public static void main(String[] args) {
        ServiceLoader<MyInterface> interfaces = ServiceLoader.load(MyInterface.class);
        for (MyInterface anInterface : interfaces) {
            anInterface.sayHello();
        }
    }
}
