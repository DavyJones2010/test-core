package edu.xmu.test.javase.jvm;

public class ClassLoaderVisibilitySample {
	/**
	 * Class loaded by parent class loader is visible to all its child class loader <br>
	 * But class loaded by child is not visible to its parent loader <br>
	 */
	public static void main(String[] args) throws Exception {
		System.out.println(Class.forName("edu.xmu.test.javase.jvm.ClassLoaderVisibilitySample", true, ClassLoaderVisibilitySample.class.getClassLoader()));
		// ClassNotFoundException expected
		System.out.println(Class.forName("edu.xmu.test.javase.jvm.ClassLoaderVisibilitySample", true, ClassLoaderVisibilitySample.class.getClassLoader().getParent()));
	}
}
