package edu.xmu.test.j2ee.ws;

import javax.jws.WebService;

@WebService(endpointInterface = "edu.xmu.test.j2ee.ws.IMyService", targetNamespace = "edu.xmu.ws", serviceName = "MyService")
public class MyServiceImpl implements IMyService {

	@Override
	public int add(int a, int b) {
		int sum = a + b;
		System.out.println(a + "+" + b + "=" + sum);
		return sum;
	}

	@Override
	public int subtract(int a, int b) {
		int res = a - b;
		System.out.println(a + "-" + b + "=" + res);
		return res;
	}

}
