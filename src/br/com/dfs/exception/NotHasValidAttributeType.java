package br.com.dfs.exception;

public class NotHasValidAttributeType extends Exception {
	
	private static final long serialVersionUID = 5527804457848870750L;

	private String msg;

	public NotHasValidAttributeType(String msg) {
		super(msg);
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}
	
}
