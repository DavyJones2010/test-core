package edu.xmu.test.javase.monitor;

import java.lang.management.ClassLoadingMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryManagerMXBean;
import java.util.List;

import javax.management.MBeanInfo;
import javax.management.MBeanServer;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

/**
 * <a href="http://wangxuliangboy.iteye.com/blog/347189">iteye blog</a><br>
 * <a href="http://www.ibm.com/developerworks/cn/java/j-mxbeans">ibm blog</a>
 * 
 * @author davyjones
 *
 */
public class MXBeanClient {
	public static void main(String[] args) throws Exception {
		// local MXBean sample
		// 1. 通过工厂方法获取MXBean
		ClassLoadingMXBean classLoadingMXBean = ManagementFactory.getClassLoadingMXBean();
		long totalLoadedClassCount = classLoadingMXBean.getTotalLoadedClassCount();
		int loadedClassCount = classLoadingMXBean.getLoadedClassCount();
		long unloadedClassCount = classLoadingMXBean.getUnloadedClassCount();
		System.out.println(totalLoadedClassCount);
		System.out.println(loadedClassCount);
		System.out.println(unloadedClassCount);

		List<MemoryManagerMXBean> memoryManagerMXBeans = ManagementFactory.getMemoryManagerMXBeans();
		System.out.println(memoryManagerMXBeans);

		MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
		System.out.println(memoryMXBean.getHeapMemoryUsage() + ", " + memoryMXBean.getNonHeapMemoryUsage());

		// 2. 通过平台MBeanServer方法获得
		MBeanServer server = ManagementFactory.getPlatformMBeanServer();
		// info只是描述信息,没有存具体的值信息
		MBeanInfo info = server.getMBeanInfo(new ObjectName("java.lang:type=Memory")); // name可以从jconsole->MBean里查到
		System.out.println(info.getAttributes()[0].getName()); // HeapMemoryUsege
		System.out.println(info.getAttributes()[1].getName()); // NonHeapMemoryUsage
		System.out.println(info.getAttributes()[2].getName()); // Verbose
		System.out.println(server.getAttribute(new ObjectName("java.lang:type=Memory"), "Verbose"));

		// remote MXBean sample
		// 1. 首先需要用关键的命令行选项启动远程虚拟机, 启动参数如下:
		// -Dcom.sun.management.jmxremote.port=1234
		// -Dcom.sun.management.jmxremote.authenticate=false
		// -Dcom.sun.management.jmxremote.ssl=false

		// 2. 获得相关 MBean 服务器连接的引用
		JMXServiceURL address = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:1234/jmxrmi");
		JMXConnector connector = JMXConnectorFactory.connect(address);
		MBeanServerConnection mbs = connector.getMBeanServerConnection();

		ObjectName srvThrdName = new ObjectName(ManagementFactory.THREAD_MXBEAN_NAME);
		// Get the current thread count for the JVM
		int threadCount = ((Integer) mbs.getAttribute(srvThrdName, "ThreadCount")).intValue();
		System.out.println(" Thread Count = " + threadCount);
	}
}
