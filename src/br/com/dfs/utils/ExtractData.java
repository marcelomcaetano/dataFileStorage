package br.com.dfs.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Logger;

import br.com.dfs.exception.HasSuperClassException;
import br.com.dfs.exception.NotHasAttributes;
import br.com.dfs.exception.NotHasValidAttributeType;

public class ExtractData {

	protected static final Logger LOGGER = Logger.getLogger(ExtractData.class.getName());

	public static final String STRING_GET = "get";

	private Class<?> clazz;

	private String[] headerFields;

	public ExtractData(Class<?> clazz) throws HasSuperClassException,
			NotHasAttributes, NotHasValidAttributeType {
		ValidateClass.validate(clazz);
		this.clazz = clazz;
	}

	public String[] getDeclaredFields() {
		headerFields = new String[numberOfFields(clazz)];
		int pos = 0;
		for (Field field : clazz.getDeclaredFields()) {
			if (!ValidateClass.isSerialVersionUIDName(field)) {
				headerFields[pos] = field.getName();
				pos++;
			}
		}
		return headerFields;
	}

	private int numberOfFields(Class<?> clazz) {
		int count = 0;
		for (Field field : clazz.getDeclaredFields()) {
			if (!ValidateClass.isSerialVersionUIDName(field)) {
				count++;
			}
		}
		return count;
	}

	public String[] getValues(Object obj) throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		Class<?> clazz = obj.getClass();
		String[] values = new String[headerFields.length];
		int pos = 0;
		for (String name : headerFields) {
			for (Method method : clazz.getMethods()) {
				if (name != null && isGetter(method)
						&& method.getName().equals(createGetName(name))) {
					values[pos++] = (method.invoke(obj) != null) ? method
							.invoke(obj).toString() : "";
				}
			}
		}
		return values;
	}

	private String createGetName(String name) {
		return STRING_GET + Character.toString(name.charAt(0)).toUpperCase()
				+ name.substring(1);
	}

	private boolean isGetter(Method method) {
		return method.getName().startsWith(STRING_GET)
				&& method.getReturnType() != void.class
				&& method.getParameterTypes().length == 0;
	}

}
