package com.tornadomicroservice.filter.common;

import org.springframework.beans.factory.annotation.Autowired;

public class Action {
	@Autowired
	private Class<?> clazz;
	private String methodName;
	private Object[] args;
	private Class<?>[] classes;
	
	public Action(Class<?> clazz, String methodName) {
		setClazz(clazz);
		setMethodName(methodName);
	}
	
	public Action(Class<?> clazz, String methodName, Class<?>... classes) {
		setClazz(clazz);
		setMethodName(methodName);
		setClasses(classes);
	}
	
	public Action(Class<?> clazz, String methodName, Object[] args) {
		setClazz(clazz);
		setMethodName(methodName);
		setArgs(args);
	}
	
	public Action(Class<?> clazz, String methodName, Object[] args, Class<?>[] classes) {
		setClazz(clazz);
		setMethodName(methodName);
		setArgs(args);
		setClasses(classes);
	}

	/**
	 * @return the clazz
	 */
	public Class<?> getClazz() {
		return clazz;
	}



	/**
	 * @param clazz the clazz to set
	 */
	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}



	/**
	 * @return the methodName
	 */
	public String getMethodName() {
		return methodName;
	}

	/**
	 * @param methodName the methodName to set
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}


	/**
	 * @return the args
	 */
	public Object[] getArgs() {
		return args;
	}


	/**
	 * @param args the args to set
	 */
	public void setArgs(Object[] args) {
		this.args = args;
	}

	/**
	 * @return the classes
	 */
	public Class<?>[] getClasses() {
		return classes;
	}

	/**
	 * @param classes the classes to set
	 */
	public void setClasses(Class<?>[] classes) {
		this.classes = classes;
	}
}

