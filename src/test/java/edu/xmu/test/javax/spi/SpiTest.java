package edu.xmu.test.javax.spi;

import org.junit.Test;

import java.util.ServiceLoader;

/**
 * Created by davywalker on 17/2/28.
 * <p>
 * java spi的具体约定如下 ：
 * 当服务的提供者，提供了服务接口的一种实现之后，在jar包的META-INF/services/目录里同时创建一个以服务接口命名的文件。该文件里就是实现该服务接口的具体实现类。而当外部程序装配这个模块的时候，就能通过该jar包META-INF/services/里的配置文件找到具体的实现类名，并装载实例化，完成模块的注入。
 * 基于这样一个约定就能很好的找到服务接口的实现类，而不需要再代码里制定。
 * jdk提供服务实现查找的一个工具类：java.util.ServiceLoader
 * </p>
 */
public class SpiTest {

    @Test
    public void test() throws Exception {
        ServiceLoader<Command> load = ServiceLoader.load(Command.class);
        for (Command command : load) {
            command.execute();
        }

    }
}
