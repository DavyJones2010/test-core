package edu.xmu.test.javase.jvm;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import javax.servlet.http.HttpServlet;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.junit.rules.ErrorCollector;

public class ClassLoaderSample {
	/**
	 * ExtensionClassLoader: {@link sun.misc.Launcher$ExtClassLoader} <br/>
	 * ApplicationClassLoader: {@link sun.misc.Launcher$AppClassLoader} <br/>
	 * sun.misc.Launcher$ExtClassLoader extends java.net.URLClassLoader extends java.lang.ClassLoader <br/>
	 * 
	 */
	@SuppressWarnings("restriction")
	public static void main(String[] args) throws Exception {
		URL[] urls = sun.misc.Launcher.getBootstrapClassPath().getURLs();
		// print all bootstrap classloader's target url
		for (int i = 0; i < urls.length; i++) {
			System.out.println(urls[i].toExternalForm());
		}

		// ExtensionClassLoader, its parent is BootstrapClassLoader, and it is set as "null" because bootstrap classloader is not a real instance of ClassLoader
		ClassLoader extensionClassLoader = ClassLoader.getSystemClassLoader().getParent();
		// output is null
		System.out.println(extensionClassLoader.getClass().getName());
		System.out.println(extensionClassLoader.getParent());

		// SystemClassLoader, its parent is ExtensionClassLoader
		ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
		// output is "sun.misc.Launcher$ExtClassLoader@{hashcode}"
		System.out.println(systemClassLoader.getClass().getName());
		System.out.println(systemClassLoader.getParent().toString());

		// output is null, means that System.class is loaded by bootstrap class loader
		System.out.println(System.class.getName() + " is loaded by: " + System.class.getClassLoader());
		System.out.println(String.class.getName() + " is loaded by: " + String.class.getClassLoader());

		// output is sun.misc.Launcher$ExtClassLoader, means that JarFileSystemProvider.class is loaded by extension class loader
		System.out.println(com.sun.nio.zipfs.JarFileSystemProvider.class.getName() + " is loaded by: " + com.sun.nio.zipfs.JarFileSystemProvider.class.getClassLoader());

		// output is sun.misc.Launcher$AppClassLoader, means that StringUtils.class is loaded by application class loader
		System.out.println(HttpServlet.class.getName() + " is loaded by: " + HttpServlet.class.getClassLoader());
		System.out.println(StringUtils.class.getName() + " is loaded by: " + StringUtils.class.getClassLoader());

		System.out.println("systemClassLoader: " + ClassLoader.getSystemClassLoader());

		MyOwnClassLoaderWithoutDelegation myOwnClassLoader = new MyOwnClassLoaderWithoutDelegation();
		myOwnClassLoader.setBaseUrl("file:///C:/YangKunlun/my_own_class_path/");
		myOwnClassLoader.addURL("junit-4.11.jar");
		myOwnClassLoader.addURL("/");

		System.out.println("MyOwnClassLoaderWithoutDelegation.parent: " + myOwnClassLoader.getParent());

		Class<?> clazz = null;
		clazz = myOwnClassLoader.loadClass("java.lang.String");
		System.out.println("java.lang.String is loaded by: " + clazz.getClassLoader());

		clazz = myOwnClassLoader.loadClass("edu.xmu.test.javase.jvm.ClassLoaderSample$MyClassA");
		System.out.println("edu.xmu.test.javase.jvm.ClassLoaderSample$MyClassA is loaded by: " + clazz.getClassLoader());

		Class<?> assertClazz = Class.forName("org.junit.Assert");
		System.out.println("org.junit.Assert is loaded by: " + assertClazz.getClassLoader());

		assertClazz = myOwnClassLoader.loadClass("org.junit.Assert");
		System.out.println("org.junit.Assert is loaded by: " + assertClazz.getClassLoader());
		assertClazz.getMethod("assertTrue", new Class[] { boolean.class }).invoke(null, new Object[] { true });

		ErrorCollector ec = new ErrorCollector();
		System.out.println("ec is loaded by: " + ec.getClass().getClassLoader());

		// ec = (ErrorCollector) myOwnClassLoader.loadClass("org.junit.rules.ErrorCollector").newInstance();

		clazz = myOwnClassLoader.loadClass("MySampleClass");
		System.out.println("MySampleClass is loaded by: " + clazz.getClassLoader());

		Object instance = clazz.newInstance();
		clazz.getMethod("hallo", new Class[] {}).invoke(instance, null);

		MyOwnClassLoaderWithoutDelegation myOwnClassLoader2 = new MyOwnClassLoaderWithoutDelegation();
		myOwnClassLoader2.setBaseUrl("file:///C:/YangKunlun/my_own_class_path/");
		myOwnClassLoader2.addURL("junit-4.11.jar");
		myOwnClassLoader2.addURL("/");

		assertClazz = myOwnClassLoader2.loadClass("org.junit.Assert");
		assertClazz = myOwnClassLoader2.loadClass("org.junit.Assert");
		System.out.println("org.junit.Assert is loaded by: " + assertClazz.getClassLoader());
	}

	/**
	 * A customized ClassLoader that breaks Parent Delegation rules
	 */
	static class MyOwnClassLoaderWithoutDelegation extends URLClassLoader {

		String baseUrl;
		final static Logger logger = Logger.getLogger(MyOwnClassLoaderWithoutDelegation.class);

		@Override
		protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
			Class<?> clazz = null;
			try {
				clazz = findLoadedClass(name);
				if (null == clazz) {
					clazz = findClass(name);
					logger.info("Class: " + name + " hasn't been loaded before, it is loaded by MyOwnClassLoaderWithoutDelegation...");
				} else {
					logger.info("Class: " + name + " has already been loaded before, returning...");
				}
			} catch (ClassNotFoundException e) {
				clazz = getParent().loadClass(name);
				logger.info("Class: " + name + " cannot be loaded with MyOwnClassLoaderWithoutDelegation, delegrating to parent loader...");
			}
			return clazz;
		}

		public void setBaseUrl(String baseUrl) {
			this.baseUrl = baseUrl;
		}

		public void addURL(String url) {
			URL uurl = null;
			try {
				uurl = new URL(baseUrl + url);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			super.addURL(uurl);
		}

		public MyOwnClassLoaderWithoutDelegation() {
			super(new URL[0]);
		}

		@Override
		public String toString() {
			return "MyOwnClassLoader [baseUrl=" + baseUrl + "]" + ", hashCode: " + hashCode();
		}

	}

	static class MyClassA {
	}
}
