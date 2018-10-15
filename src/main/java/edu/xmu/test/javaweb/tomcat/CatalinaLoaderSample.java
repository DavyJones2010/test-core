package edu.xmu.test.javaweb.tomcat;

import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.google.common.collect.Maps;

public class CatalinaLoaderSample {

	static interface Reloader {
		boolean modified();
	}

	static class WebappClassLoader extends URLClassLoader implements Reloader {
		Map<String, Long> loadedClasses = Maps.newConcurrentMap();
		String classPath = "";

		public WebappClassLoader(String classPath) throws Exception {
			super(new URL[] { new URL(classPath) });
			this.classPath = classPath;
		}

		/**
		 * will cause a full scan of loaded classes
		 */
		@Override
		public boolean modified() {
			// fully scan all loaded classes and check if there is any change
			// for (Entry<String, Long> entry : loadedClasses.entrySet()) {
			// String className = entry.getKey();
			// long prevTimemark = entry.getValue();
			// long timemark = new File(className).lastModified();
			// if (timemark > prevTimemark) {
			// entry.setValue(timemark);
			// return true;
			// }
			// }
			return true;
		}
	}

	static interface Loader {
		ClassLoader getClassLoader();

		boolean getReloadable();

		void setReloadable(boolean reloadable);

	}

	static class WebappLoader implements Loader, Runnable {
		boolean reloadable = true;
		int checkInterval = 10;
		String loaderClass = "edu.xmu.test.javaweb.tomcat.CatalinaLoaderSample$WebappClassLoader";

		Context context;
		WebappClassLoader classLoader;
		ClassLoader parentClassLoader;

		WebappLoader() throws Exception {
			this.classLoader = createClassLoader();
		}

		WebappClassLoader createClassLoader() throws Exception {
			Class<?> clazz = Class.forName(loaderClass);
			WebappClassLoader classLoader = null;
			if (null == parentClassLoader) {
				classLoader = (WebappClassLoader) clazz.newInstance();
			} else {
				Class<?>[] argTypes = { ClassLoader.class };
				Object[] args = { parentClassLoader };
				Constructor<?> c = clazz.getConstructor(argTypes);
				classLoader = (WebappClassLoader) c.newInstance(args);
			}
			return classLoader;
		}

		public String getLoaderClass() {
			return loaderClass;
		}

		public void setLoaderClass(String loaderClass) {
			this.loaderClass = loaderClass;
		}

		@Override
		public ClassLoader getClassLoader() {
			return classLoader;
		}

		@Override
		public boolean getReloadable() {
			return reloadable;
		}

		@Override
		public void setReloadable(boolean reloadable) {
			this.reloadable = reloadable;
		}

		@Override
		public void run() {
			try {
				TimeUnit.SECONDS.sleep(checkInterval);
				if (classLoader.modified()) {
					new Thread(() -> {
						// context.reload();
						}).start();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		public Context getContext() {
			return context;
		}

		public void setContext(Context context) {
			this.context = context;
		}

	}

	static class Context {
		Loader loader;

		public Loader getLoader() {
			return loader;
		}

		public void setLoader(Loader loader) {
			this.loader = loader;
		}

	}

}
