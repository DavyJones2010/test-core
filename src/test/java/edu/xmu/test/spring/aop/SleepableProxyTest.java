package edu.xmu.test.spring.aop;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/aop/human.xml")
public class SleepableProxyTest {
	@Resource(name = "humanProxy")
	Sleepable sleeper;
	@Resource(name = "personProxy")
	Sleepable sleeper2;

	@Test
	public void proxyTest() {
		sleeper.sleep();
		sleeper2.sleep();
	}
}
