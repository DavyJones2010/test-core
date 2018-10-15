package edu.xmu.test.javase.jvm;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * An interesting question about class loader read from {@link <a href="http://stackoverflow.com/questions/21962631/findloadedclass-returns-null">findLoadedClass() returns null</a>}
 */
public class ClassLoaderSample2 {

	/**
	 * The class loader does not care about which one gets the bytes of the class, but rather which one calls defineClass() <br>
	 * The one that actually calls defineClass(classToBeLoaded) is set as the classLoader of classToBeLoaded <br>
	 * The findLoadedClass will also link current classLoader with classToBeFound <br>
	 * If a class is loaded by system class loader, then OurCustomizedClassLoader.findLoadedClass(classToBeFound) returns null unless it calls
	 * getSystemClassLoader.findLoadedClass(classToBeFound) <br>
	 */
	@SuppressWarnings({ "unused", "resource" })
	public static void main(String[] args) throws Exception {
		System.out.println("main: " + new SimpleClassLoader().foo().getClassLoader());
		System.out.println("main: " + new SimpleClassLoader().foo2().getClassLoader());
		ClassLoadedByResolve placeholder = new ClassLoadedByResolve();
		System.out.println("main: " + new SimpleClassLoader().foo3().getClassLoader());

		Class<?> clazz = new SimpleClassLoader().loadClass("edu.xmu.test.javase.jvm.ClassLoaderSample2$ClassLoadedByReflect");
		System.out.println("main: " + clazz.getClassLoader());

		// Class.forName will call loadClass() internally, if we put Class.forName insied loadClass(), java.lang.ClassCircularityError will be thrown
		Class<?> reflectClazz = Class.forName("edu.xmu.test.javase.jvm.ClassLoaderSample2$ClassLoadedByReflect", true, clazz.getClassLoader());
		System.out.println("reflectClazz.classLoader: " + reflectClazz.getClassLoader());
	}

	public static class SimpleClassLoader extends URLClassLoader {
		public SimpleClassLoader() throws Exception {
			super(new URL[] { new URL("file:///C:/Users/ky67981.NAM/git/test-repo/test-core/target/classes/") });
		}

		Class<?> foo() throws Exception {
			Class<?> clazz = null;
			clazz = loadClass("edu.xmu.test.javase.jvm.ClassLoaderSample2$ClassLoadedByReflect");
			// class edu.xmu.test.javase.jvm.ClassLoaderSample2$ClassLoadedByReflect
			System.out.println("foo1: " + clazz);

			/*
			 * Returns "SimpleClassLoader", because "ClassLoadedByReflect" is loaded by SimpleClassLoader <br>
			 * And we are invoking findLoadedClass using SimpleClassLoader, that matches exactly
			 */
			System.out.println("foo1: " + findLoadedClass("edu.xmu.test.javase.jvm.ClassLoaderSample2$ClassLoadedByReflect").getClassLoader());

			return clazz;
		}

		Class<?> foo2() throws Exception {
			Class<?> clazz = null;
			clazz = Class.forName("edu.xmu.test.javase.jvm.ClassLoaderSample2$ClassLoadedByReflect");
			// class edu.xmu.test.javase.jvm.ClassLoaderSample2$ClassLoadedByReflect
			System.out.println("foo2: " + clazz);

			/*
			 * Returns null, because this foo2() is called with class loader of SystemClassLoader, and therefore Class.forName() is using current class loader <br>
			 * And thus "ClassLoadedByReflect" is loaded by SystemClassLoader <br>
			 * We are invoking findLoadedClass under the context of SimpleClassLoader <br>
			 */
			System.out.println("foo2: " + findLoadedClass("edu.xmu.test.javase.jvm.ClassLoaderSample2$ClassLoadedByReflect"));
			return clazz;
		}

		Class<?> foo3() throws Exception {
			Class<?> clazz = null;
			clazz = loadClass("edu.xmu.test.javase.jvm.ClassLoaderSample2$ClassLoadedByResolve");
			System.out.println(clazz);

			System.out.println(findLoadedClass("edu.xmu.test.javase.jvm.ClassLoaderSample2$ClassLoadedByResolve"));
			return clazz;
		}

		@Override
		public Class<?> loadClass(String name) throws ClassNotFoundException {
			Class<?> clazz = null;
			try {
				clazz = getSystemClassLoader().getParent().loadClass(name);
				if (null == clazz) {
					clazz = super.findClass(name);
				}
			} catch (ClassNotFoundException e) {
				clazz = super.findClass(name);
			}
			return clazz;
			// return getParent().loadClass(name);
		}
	}

	public static class ClassLoadedByReflect {
	}

	public static class ClassLoadedByResolve {
	}
}
