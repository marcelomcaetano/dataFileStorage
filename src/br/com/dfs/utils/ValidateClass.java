package br.com.dfs.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.dfs.exception.HasSuperClassException;
import br.com.dfs.exception.NotHasAttributes;
import br.com.dfs.exception.NotHasValidAttributeType;

public final class ValidateClass {

	protected static final Logger LOGGER = Logger.getLogger(ValidateClass.class.getName());

	public static final String SERIAL_VERSION_UID_NAME = "serialVersionUID";

	public static Set<Class<?>> getAcceptedTypes() {
		Set<Class<?>> ret = new HashSet<Class<?>>();
		ret.add(Boolean.class);
		ret.add(Byte.class);
		ret.add(Character.class);
		ret.add(Float.class);
		ret.add(Integer.class);
		ret.add(Long.class);
		ret.add(Short.class);
		ret.add(Double.class);
		ret.add(String.class);
		return ret;
	}

	public static void validate(Class<?> clazz) throws HasSuperClassException,
			NotHasAttributes, NotHasValidAttributeType {
		LOGGER.info("Iniciando processo de validação para a classe: "
				+ clazz.getName());
		hasSuperClass(clazz);
		hasFields(clazz);
		hasOnlyValidAttributes(clazz);
	}

	private static void hasSuperClass(Class<?> clazz)
			throws HasSuperClassException {
		LOGGER.info("Validando se classe extende outra classe.");
		if (clazz.getSuperclass() != Object.class) {
			LOGGER.log(Level.WARNING, "LOG", new HasSuperClassException(
					"A classe não pode herdar outras classes."));
			throw new HasSuperClassException(
					"A classe não pode herdar outras classes.");
		}
	}

	private static void hasFields(Class<?> clazz) throws NotHasAttributes {
		LOGGER.info("Validando se classe contém campos.");
		if (clazz.getDeclaredFields().length == 0 || (clazz.getDeclaredFields().length == 1
				&& isSerialVersionUIDName(clazz.getDeclaredFields()[0]))) {
			LOGGER.log(Level.WARNING, "LOG", new HasSuperClassException(
					"A classe " + clazz.getName() + " não contém atributos."));
			throw new NotHasAttributes("A classe " + clazz.getName()
					+ " não contém atributos.");
		}
	}

	private static void hasOnlyValidAttributes(Class<?> clazz) throws NotHasValidAttributeType {
		LOGGER.info("Validando se classe ccontém campos válidos.");
		for (Field field : clazz.getDeclaredFields()) {
			if (!field.getType().isPrimitive()
					&& !getAcceptedTypes().contains(field.getType())) {
				String msg = "A classe "+clazz.getName()+" deve ter somente atributos de tipos primitivos, seu respectivo Wrapper, ou String. \n";
				msg += field.getType() + " não é permitido.";
				LOGGER.log(Level.WARNING, "LOG", new NotHasValidAttributeType(
						msg));
				throw new NotHasValidAttributeType(msg);
			}
		}

	}

	protected static boolean isSerialVersionUIDName(Field field) {
		if (field.getName().equals(SERIAL_VERSION_UID_NAME)) {
			if (Modifier.isStatic(field.getModifiers())
					&& Modifier.isFinal(field.getModifiers())) {
				return true;
			}
		}
		return false;
	}

}
