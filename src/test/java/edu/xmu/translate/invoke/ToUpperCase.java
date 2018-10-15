package edu.xmu.translate.invoke;

import java.lang.invoke.CallSite;
import java.lang.invoke.ConstantCallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.MethodType;

public class ToUpperCase {
	public static CallSite bootstrap(Lookup lookup, String name,
			MethodType type, String value) throws Exception {
		MethodHandle mh = lookup.findVirtual(String.class, "toUpperCase",
				MethodType.methodType(String.class)).bindTo(value);
		return new ConstantCallSite(mh);
	}
}
