package com.tornadomicroservice.filter.common;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.tornadomicroservice.filter.exceptions.MethodNotFoundException;
import com.tornadomicroservice.filter.exceptions.ParameterCountMismatchException;


public class Filter {
	@Autowired
    private ApplicationContext _appContext;
	
	private HashMap<String, Action> _3cases = new HashMap<String, Action>();
	private Class<?> injectedClass;

	public Filter() {
	}

	public Filter(Class<?> clazz) {
		setInjectedClass(clazz);
	}

	public void addCaseWithParamsAndClass(String caseParam, String methodName, Class<?> clazz, Class<?>... classes) {
		try {
			methodValidation(clazz, methodName);
		} catch (MethodNotFoundException err) {
			err.printStackTrace();
			System.exit(1);
		}
		_3cases.put(caseParam, new Action(clazz, methodName, classes));
	}
	
	private void addCaseWithParams(String caseParam, String methodName, Class<?>... classes) {
		try {
			methodValidation(getInjectedClass(), methodName);
		} catch (MethodNotFoundException err) {
			err.printStackTrace();
			System.exit(1);
		}
		_3cases.put(caseParam, new Action(getInjectedClass(), methodName, classes));
	}
	
	public Object filter(String caseParam, Object... params) {
		try {
			Object obj = _appContext.getBean(_3cases.get(caseParam).getClazz());
			Method method = obj.getClass().getMethod(_3cases.get(caseParam).getMethodName(), _3cases.get(caseParam).getClasses());
			Parameter[] declareds = method.getParameters();
			Object[] varParams = new Object[method.getParameterCount()];
			for (int i = 0; i < method.getParameterCount(); i++) {
				switch (declareds[i].getType().getSimpleName()) {
				case "short":
					varParams[i] = Short.parseShort(params[i].toString());
					break;
				case "int":
				case "Integer":
					varParams[i] = Integer.parseInt(params[i].toString());
					break;
				case "long":
				case "Long":
					varParams[i] = Long.parseLong(params[i].toString());
					break;
				case "float":
				case "Float":
					varParams[i] = Float.parseFloat(params[i].toString());
					break;
				case "double":
				case "Double":
					varParams[i] = Double.parseDouble(params[i].toString());
					break;
				case "char":
					varParams[i] = (Character) params[i].toString().charAt(0);
					break;
				default:
					varParams[i] = params[i];
				}
			}
			if (params.length == 0 || (params.length == 1 && params[0].toString().toLowerCase().equals("null"))) {
				return method.getReturnType().cast(method.invoke(obj));
			} else {
				return method.getReturnType().cast(method.invoke(obj, varParams));
			}
		} catch ( NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	
	public Object chainFilter(Object ...searchMethods) {
		List<Object> result = new ArrayList<>();
		for(int i = 0; i < searchMethods.length; i++) {
			String searchMethod = searchMethods[i].toString().split("\\(")[0];
			String params = searchMethods[i].toString().replaceAll(".*\\(|\\).*", "");
				System.out.println("Method is: " + searchMethod);
				System.out.println("Parameter is: " + params);
				result.add(this.filter(searchMethod, params));
		}
		return result.stream().distinct().collect(Collectors.toList());
	}
	public void constraintValidation(Method method, Object... params)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException {
		if (method.getParameterCount() == params.length || (method.getParameterCount() == 0)) {
		} else {
			throw new ParameterCountMismatchException();
		}
	}
	
	public void methodValidation(Class<?> clazz, String methodName) {
		Method[] methods = clazz.getDeclaredMethods();
		boolean flag = false;
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				flag = true;
			}
		}
		if (!flag) {
			throw new MethodNotFoundException();
		}

	}

	/**
	 * @return the injectedClass
	 */
	public Class<?> getInjectedClass() {
		return injectedClass;
	}

	/**
	 * @param injectedClass the injectedClass to set
	 */
	public void setInjectedClass(Class<?> injectedClass) {
		this.injectedClass = injectedClass;
	}
	
	public void initCases() {
		Method[] methods = this.getInjectedClass().getDeclaredMethods();
		for(int i = 0; i < methods.length ; i++) {
			this.addCaseWithParams(methods[i].getName(),methods[i].getName(), methods[i].getParameterTypes()); 
		}
	}
}