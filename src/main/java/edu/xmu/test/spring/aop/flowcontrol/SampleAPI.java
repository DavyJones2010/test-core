package edu.xmu.test.spring.aop.flowcontrol;

public interface SampleAPI {
	public void aService(String id);

	public void bService(String id);

	public void cService(int id);

	public void dService(Long id);

	public void eService(Object obj);
}
