package br.com.dfs.parse;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.InvocationTargetException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.dfs.exception.HasSuperClassException;
import br.com.dfs.exception.NotHasAttributes;
import br.com.dfs.exception.NotHasValidAttributeType;
import br.com.dfs.utils.ExtractData;

public final class ParseFile<T> {

	protected static final Logger LOGGER = Logger.getLogger(ParseFile.class.getName());

	private ParseType parseType;

	private Class<T> clazz;

	public ParseFile(ParseType type) {
		this.parseType = type;
	}

	public OutputStream parse(T object) throws HasSuperClassException,
			NotHasAttributes, NotHasValidAttributeType, FileNotFoundException,
			IOException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		return this.parse(Arrays.asList(object));
	}

	public OutputStream parse(List<T> objects) throws HasSuperClassException,
			NotHasAttributes, NotHasValidAttributeType, FileNotFoundException,
			IOException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		if (objects == null || objects.isEmpty()) {
			LOGGER.warning("A lista de entidades não pode ser nula ou vazia.");
			throw new InvalidParameterException("A lista de entidades não pode ser nula ou vazia.");
		}

		setTypeClass(objects.get(0).getClass());

		return createStream(extractContent(objects));
	}

	@SuppressWarnings("unchecked")
	private void setTypeClass(Class<?> clazz) {
		this.clazz = (Class<T>) clazz;
	}

	private OutputStream createStream(String content)throws FileNotFoundException, IOException {
		if (parseType.getExtensionFile() == null || parseType.getExtensionFile().isEmpty()) {
			LOGGER.log(Level.WARNING, "", new InvalidParameterException("A extenção do arquivo não foi informada"));
			throw new InvalidParameterException("A extenção do arquivo não foi informada");
		}
		String file = clazz.getCanonicalName() + "."+ parseType.getExtensionFile();

		LOGGER.info("Iniciando processo de criação do arquivo  " + file);

		OutputStream stream = new FileOutputStream(file);
		OutputStreamWriter streamWriter = new OutputStreamWriter(stream);
		BufferedWriter bufferedWriter = new BufferedWriter(streamWriter);
		bufferedWriter.write(content);
		bufferedWriter.close();

		return stream;
	}

	private String extractContent(List<T> objects)
			throws HasSuperClassException, NotHasAttributes,
			NotHasValidAttributeType, IllegalAccessException,
			InvocationTargetException, FileNotFoundException, IOException {

		LOGGER.info("Iniciando processo de extração de conteúdo para a entidade "
				+ clazz.getName());

		ExtractData extractInfo = new ExtractData(clazz);

		String[] headerFields = extractInfo.getDeclaredFields();
		List<String[]> values = new ArrayList<String[]>();
		for (Object obj : objects) {
			if (obj == null)
				throw new InvalidParameterException(
						"A entidade não pode ser nula");
			values.add(extractInfo.getValues(obj));
		}
		return parseType.createContent(headerFields, values);
	}

}
