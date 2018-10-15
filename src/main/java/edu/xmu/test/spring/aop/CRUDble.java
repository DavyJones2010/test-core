package edu.xmu.test.spring.aop;

public interface CRUDble {
	void insert(Object obj);

	Object get(Integer id);

	void delete(Object obj);

	void update(Integer id, Object obj);
}
