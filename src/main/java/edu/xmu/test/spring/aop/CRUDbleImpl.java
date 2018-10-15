package edu.xmu.test.spring.aop;

import org.springframework.stereotype.Component;

@Component("crudbleImpl")
public class CRUDbleImpl implements CRUDble {

	@Override
	public void insert(Object obj) {
		System.out.println("insert: " + obj);
	}

	@Override
	public Object get(Integer id) {
		System.out.println("get " + id);
		return new Object();
	}

	@Override
	public void delete(Object obj) {
		System.out.println("delete " + obj);
	}

	@Override
	public void update(Integer id, Object obj) {
		System.out.println("update id: " + id);
	}

}
