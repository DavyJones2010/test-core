package edu.xmu.test.spring.core;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PropertyPlaceholderConfigurerSample {

	static class DummySystem {
		private String sysName;
		private String username;
		private String password;

		public void setSysName(String sysName) {
			this.sysName = sysName;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		@Override
		public String toString() {
			return "DummySystem [sysName=" + sysName + ", username=" + username + ", password=" + password + "]";
		}

	}

	public static void main(String[] args) {
		//System.setProperty("env", "dev");
		//ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/core/spring-core.xml");
		//DummySystem system = (DummySystem) context.getBean("system");
		//// properties would be replaced with value defined in spring/core/system.properties file
		//System.out.println(system);
		//context.close();
	}
}
