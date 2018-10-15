package edu.xmu.test.j2ee.osgi;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class HelloWorldActivator implements BundleActivator {

	@Override
	public void start(BundleContext context) throws Exception {
		System.out.println("Hello world");
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		System.out.println("Goodbye world");
	}

}
