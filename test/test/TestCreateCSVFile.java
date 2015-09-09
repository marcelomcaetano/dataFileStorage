package test;

import static org.junit.Assert.assertTrue;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import test.parse.CSV;
import test.pojo.Pojo;
import test.pojo.PojoWithInvalidAttributeType;
import test.pojo.PojoWithSuperClass;
import test.pojo.PojoWithoutAttributes;
import br.com.dfs.exception.HasSuperClassException;
import br.com.dfs.exception.NotHasAttributes;
import br.com.dfs.exception.NotHasValidAttributeType;
import br.com.dfs.parse.ParseFile;

public class TestCreateCSVFile {

	private Pojo entity;

	private List<Pojo> entityList;

	@Before
	public void init() {
		entity = new Pojo();
		entity.setId(1);
		entity.setName("Marcelo");

		entityList = new ArrayList<Pojo>();
		for (int i = 0; i < 20; i++) {
			Pojo entity = new Pojo();
			entity.setId(i);
			entity.setName("Nome " + i);
			entityList.add(entity);

		}
	}

	@Test
	public void createCSVFile() throws Exception {
		ParseFile<Pojo> parseFile = new ParseFile<Pojo>(new CSV());
		assertTrue(parseFile.parse(entity) instanceof OutputStream);
	}

	@Test
	public void createCSVFileWithManyPojo() throws Exception {
		ParseFile<Pojo> parseFile = new ParseFile<Pojo>(new CSV());
		assertTrue(parseFile.parse(entityList) instanceof OutputStream);
	}

	@Test(expected = HasSuperClassException.class)
	public void expectedHasSuperClassException() throws Exception {
		new ParseFile<PojoWithSuperClass>(new CSV()).parse(new PojoWithSuperClass());
	}
	
	@Test(expected = NotHasAttributes.class)
	public void expectedNotHasAttributes() throws Exception {
		new ParseFile<PojoWithoutAttributes>(new CSV()).parse(new PojoWithoutAttributes());
	}

	@Test(expected = NotHasValidAttributeType.class)
	public void expectedNotHasValidAttributeType() throws Exception {
		new ParseFile<PojoWithInvalidAttributeType>(new CSV()).parse(new PojoWithInvalidAttributeType());
	}
	
}
