package edu.xmu.test.scjp.ch06;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import com.google.common.collect.Lists;

public class SerTest {
	@Test(expected = java.io.NotSerializableException.class)
	public void serTest() throws FileNotFoundException, IOException {
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(
					"src/test/resources/b.ser"));
			oos.writeObject(new B(Lists.newArrayList(new A(), new A())));
			oos.close();
		} finally {
			IOUtils.closeQuietly(oos);
		}
	}

	static class A {
	}

	static class B implements Serializable {
		List<A> values;

		public B(List<A> values) {
			this.values = values;
		}
	}
}
