package edu.xmu.test.j2ee.ws;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService
public interface IMyService {
	@WebResult(name = "addResult")
	int add(@WebParam(name = "a") int a, @WebParam(name = "b") int b);

	@WebResult(name = "subtractResult")
	int subtract(@WebParam(name = "a") int a, @WebParam(name = "b") int b);
}
