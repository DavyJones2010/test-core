package edu.xmu.test.javase.jvm;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

public class URLClassLoaderSample {
	public static void main(String[] args) throws Exception {
		URLStreamHandler dummy = null;
		/*
		 * 1) URL ending with "/" represents it is a directory
		 * 2) URL ending with no "/" represents it is a jar package, class loader will decompress jar and load class
		 * 3) URL for file system must starts with "file:///" instead of "file://"
		 */
		URL url = new URL(null, "file:///C:/YangKunlun/my_own_class_path/", dummy);
		URL url2 = new URL(null, "file:///C:/YangKunlun/my_own_class_path/junit-4.11.jar", dummy);
		URL url3 = new URL(null, "file:///C:/YangKunlun/my_own_class_path/hamcrest-core-1.3.jar", dummy);
		try (URLClassLoader loader = new URLClassLoader(new URL[] { url, url2, url3 }) {
			public String toString() {
				return "MyClassLoader";
			};
		}) {
			/*
			 * It turnes out MySampleClass is loaded by AppClassLoader instead of MyClassLoader <br>
			 * Because URLClass follows the parent delegation model <br>
			 */
			Class<?> clazz = loader.loadClass("MySampleClass");
			Method m = clazz.getMethod("main", new Class[] { String[].class });
			m.invoke(null, new Object[] { null });
			System.out.println("MySampleClass is loaded by: " + clazz.getClassLoader());

			/*
			 * We have to make sure the scope of junit is test in order to suppress junit loaded by system class loader <br>
			 * It turnes out org.junit.Assert is loaded by MyClassLoader <br>
			 * We have to make sure hamcrest-core-1.3.jar is in our own classloader's classpath because junit depends on hamcrest and thus hamcrest will be loaded by MyClassLoader
			 * <br>
			 */
			clazz = loader.loadClass("org.junit.Assert");
			m = clazz.getMethod("assertTrue", new Class[] { boolean.class });
			m.invoke(null, true);
			System.out.println("Thread: " + Thread.currentThread() + " org.junit.Assert is loaded by: " + clazz.getClassLoader());

			/*
			 * We can set class loader for a specific thread. 
			 * And we can get this class loader in the specific thread.
			 */
			Thread t = new Thread(new MyRunnable());
			t.setContextClassLoader(loader);
			t.start();
		}
	}

	static class MyRunnable implements Runnable {
		@Override
		public void run() {
			ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
			System.out.println("CurrentClassLoader: " + contextClassLoader);
			try {
				Class<?> clazz = contextClassLoader.loadClass("org.junit.Assert");
				System.out.println("Thread: " + Thread.currentThread() + " org.junit.Assert is loaded by: " + clazz.getClassLoader());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}
