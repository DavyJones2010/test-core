package edu.xmu.test.spring.aop;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/aop/crud.xml")
public class CRUDbleProxyTest {
	@Resource(name = "crudbleImpl")
	CRUDble instance;

	@Test
	public void proxyTest() {
		instance.insert("Hallo");
		instance.get("Hallo".hashCode());
	}
}
