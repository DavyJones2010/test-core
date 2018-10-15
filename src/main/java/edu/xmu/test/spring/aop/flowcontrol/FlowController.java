package edu.xmu.test.spring.aop.flowcontrol;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;

import com.google.common.collect.Maps;

/**
 * 利用spring AOP来实现流量控制<br>
 * 监听每个*Service接口,采集10s内的调用量,如果超过阈值,则直接返回<br>
 * 
 * @author davyjones
 *
 */
public class FlowController implements MethodInterceptor {
	public static final Logger flowControlLogger = Logger.getLogger("flowControlLogger");
	public static final Logger flowControlErrorLogger = Logger.getLogger("flowControlErrorLogger");

	public static final int defaultMethodFlowCount = 100;
	Map<String, Long> methodFlowCount = Maps.newConcurrentMap();
	AppConfig appConfig = new AppConfig();

	public void init() {
		Timer timer = new Timer();
		RefreshTask refreshTask = new RefreshTask();
		timer.schedule(refreshTask, 0L, TimeUnit.SECONDS.toMillis(10));
		appConfig.init();
	}

	public class RefreshTask extends TimerTask {
		@Override
		public void run() {
			methodFlowCount.clear();
			flowControlLogger.info("methodFlowCount cleared");
		}
	}

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		long maxCount = defaultMethodFlowCount;
		String methodName = invocation.getMethod().getName();
		if (appConfig.methodFlowConfig.containsKey(methodName)) {
			maxCount = appConfig.methodFlowConfig.get(methodName);
		}
		if (methodFlowCount.containsKey(methodName)) {
			long currentCount = methodFlowCount.get(methodName);
			currentCount++;
			methodFlowCount.put(methodName, currentCount);
			if (currentCount >= maxCount) {
				flowControlErrorLogger.error("method " + methodName + " flow controlled. currentCount=" + currentCount
						+ " maxCount=" + maxCount);
				return null;
			} else {
				return invocation.proceed();
			}
		} else {
			methodFlowCount.put(methodName, 1L);
			return invocation.proceed();
		}
	}

}
