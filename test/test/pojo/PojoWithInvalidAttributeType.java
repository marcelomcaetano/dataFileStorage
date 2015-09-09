package test.pojo;

import java.io.Serializable;

public class PojoWithInvalidAttributeType implements Serializable{

	private static final long serialVersionUID = -7231962125608503944L;
	
	private String[] nomes;

	public String[] getNomes() {
		return nomes;
	}

	public void setNomes(String[] nomes) {
		this.nomes = nomes;
	}
	
}
