package edu.xmu.test.javase.util;

import java.io.PrintStream;

public class SysoutUtil {
	static class SystemLogHandler extends PrintStream {
		protected PrintStream out = null;
		static boolean shouldIntercept = false;

		public SystemLogHandler(PrintStream out) {
			super(out);
			this.out = out;
		}

		public static void startCapture() {
			shouldIntercept = true;
		}

		@Override
		public void println(String x) {
			if (shouldIntercept) {
			} else {
				out.println(x);
			}
		}
	}

	public static void main(String[] args) {
		SystemLogHandler systemLogHandler = new SystemLogHandler(System.out);

		System.setOut(systemLogHandler);
		System.setErr(systemLogHandler);

		System.out.println("Hello world");

	}
}
