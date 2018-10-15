package edu.xmu.test.spring.aop.flowcontrol;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/aop/flowcontrol.xml")
public class FlowControlTest {

	@Resource(name = "sampleApiService")
	SampleAPI sampleApi;

	@Test
	public void flowControlTest() throws InterruptedException {
		while (true) {
			sampleApi.aService("123");
			sampleApi.bService("123");
			sampleApi.cService(123);
			sampleApi.dService(123L);
			sampleApi.eService(new Object());
			Thread.sleep(90L);
		}
	}
}
