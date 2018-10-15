package edu.xmu.test.hibernate.service;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

public class UserWithNativeIDInterceptor extends EmptyInterceptor {
	private static final Logger logger = Logger.getLogger(UserWithNativeIDInterceptor.class);

	@Override
	public boolean onLoad(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
		List<Object> stateList = Arrays.asList(state);
		List<String> propertyNameList = Arrays.asList(propertyNames);
		List<Type> typeList = Arrays.asList(types);
		logger.info(String.format("Start onLoad, entity: [%s], id: [%s], state: [%s], propertyNames: [%s], types: [%s]", entity, id, stateList, propertyNameList, typeList));
		return false;
	}

	@Override
	public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
		List<Object> stateList = Arrays.asList(state);
		List<String> propertyNameList = Arrays.asList(propertyNames);
		List<Type> typeList = Arrays.asList(types);
		logger.info(String.format("Start onSave, entity: [%s], id: [%s], state: [%s], propertyNames: [%s], types: [%s]", entity, id, stateList, propertyNameList, typeList));
		return false;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void preFlush(Iterator entities) {
		logger.info(String.format("Start preFlush, entities: [%s]", entities));
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void postFlush(Iterator entities) {
		logger.info(String.format("Start postFlush, entities: [%s]", entities));
	}

	@Override
	public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {
		List<Object> currStateList = Arrays.asList(currentState);
		List<Object> prevStateList = Arrays.asList(previousState);
		List<String> propertyNameList = Arrays.asList(propertyNames);
		List<Type> typeList = Arrays.asList(types);
		logger.info(String.format("Start onFlushDirty, entity: [%s], id: [%s], currState: [%s], prevState:[%s], propertyNames: [%s], types: [%s]", entity, id, currStateList,
				prevStateList, propertyNameList, typeList));
		return false;
	}

	@Override
	public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
		List<Object> stateList = Arrays.asList(state);
		List<String> propertyNameList = Arrays.asList(propertyNames);
		List<Type> typeList = Arrays.asList(types);
		logger.info(String.format("Start onDelete, entity: [%s], id: [%s], state: [%s], propertyNames: [%s], types: [%s]", entity, id, stateList, propertyNameList, typeList));
	}

}
