package br.com.dfs.exception;

public class HasSuperClassException extends Exception {

	private static final long serialVersionUID = -3850959610528371660L;

	private String msg;

	public HasSuperClassException(String msg) {
		super(msg);
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}
	
}
