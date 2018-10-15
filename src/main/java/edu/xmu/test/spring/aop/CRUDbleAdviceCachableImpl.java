package edu.xmu.test.spring.aop;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.google.common.collect.ForwardingMap;

@Component("crudbleAdviceCachableImpl")
@Aspect
public class CRUDbleAdviceCachableImpl {

	private static class LoggableMap<K, V> extends ForwardingMap<K, V> {
		Map<K, V> instance = new ConcurrentHashMap<>();

		@Override
		protected Map<K, V> delegate() {
			return instance;
		}

		@Override
		public V put(K key, V value) {
			System.out.println("Putting: " + value);
			return super.put(key, value);
		}

		@Override
		public V get(Object key) {
			System.out.println("Getting " + key);
			return super.get(key);
		}

		@Override
		public V remove(Object object) {
			System.out.println("Removing " + object);
			return super.remove(object);
		}
	}

	Map<Integer, Object> objCache = new LoggableMap<>();

	@Pointcut("execution(* edu.xmu.test.spring.aop.CRUDble.*(..) )")
	public void cacheableMethod() {
	}

	@Around(value = "cacheableMethod()")
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		System.out.println("Start doAround");
		Object returnObj = null;
		if (pjp.getSignature().getName().startsWith("get")) {
			if (objCache.containsKey((Integer) pjp.getArgs()[0])) {
				returnObj = objCache.get(pjp.getArgs()[0].hashCode());
			} else {
				returnObj = pjp.proceed();
				objCache.put((Integer) pjp.getArgs()[0], returnObj);
			}
		} else if (pjp.getSignature().getName().startsWith("insert")) {
			returnObj = pjp.proceed();
			objCache.put(pjp.getArgs()[0].hashCode(), pjp.getArgs()[0]);
		} else if (pjp.getSignature().getName().startsWith("delete")) {
			returnObj = pjp.proceed();
			objCache.remove(pjp.getArgs()[0].hashCode());
		} else if (pjp.getSignature().getName().startsWith("update")) {
			returnObj = pjp.proceed();
			objCache.put((Integer) pjp.getArgs()[0], pjp.getArgs()[1]);
		}
		System.out.println("Finished doAround");
		return returnObj;
	}
}
