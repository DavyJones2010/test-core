package edu.xmu.test.spring.aop;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/aop/human.xml")
public class SleepableProxyTest {
	@Resource(name = "human")
	Human human;
	@Resource(name = "person")
	Person person;

	@Test
	public void proxhumanyTest() {
		//sleeper = ApplicationContext
		//ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext(
		//	"classpath:spring/aop/human.xml");
		//Sleepable human = (Sleepable) classPathXmlApplicationContext.getBean("human");
		human.sleep();
		person.sleep();
		//sleeper.sleep();
		//sleeper2.sleep();
		//human.sleepDeep();
	}
}
