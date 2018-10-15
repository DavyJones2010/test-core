package edu.xmu.test.javase.monitor;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;

/**
 * MBean其实也是JavaBean的一种，但是MBean往往代表的是JMX中的一种可以被管理的资源。MBean会通过接口定义，给出这些资源的一些特定操作：
 * <li>属性的读和写操作
 * <li>可以被执行的操作
 * <li>关于自己的描述信息
 * 
 * http://blog.csdn.net/expleeve/article/details/37502501
 * 
 * @author davyjones
 */
public class MBeanSample {
	public static void main(String[] args) throws Exception {
		MBeanServer server = ManagementFactory.getPlatformMBeanServer();
		ObjectName mbeanName = new ObjectName("edu.xmu.test.javase.monitor:type=Hello");
		Hello mbean = new Hello("ClarenceAu", 23, "davywalker@gmail.com");
		server.registerMBean(mbean, mbeanName);
		System.out.println("Wait ...");
		Thread.sleep(Long.MAX_VALUE);
	}
}
