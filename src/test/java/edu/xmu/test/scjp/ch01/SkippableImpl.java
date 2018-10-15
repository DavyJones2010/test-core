package edu.xmu.test.scjp.ch01;

public class SkippableImpl implements Skippable {

	@Override
	public void skip() {
		SkippableImpl impl = new SkippableImpl2();
		impl.skip();
	}
	public static void main(String[] args) {
		SkippableImpl impl = new SkippableImpl();
		impl.skip();
	}
}

class SkippableImpl2 extends SkippableImpl {
	public enum Days {
		MON, TUE, WED
	};
	@Override
	public void skip() {
		Short myGold = 7;
		for (Days d : Days.values());
		System.out.println(Days.values()[2]);
	}
}
