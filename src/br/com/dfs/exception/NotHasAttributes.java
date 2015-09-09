package br.com.dfs.exception;

public class NotHasAttributes extends Exception {

	private static final long serialVersionUID = 8553848887207083846L;

	private String msg;

	public NotHasAttributes(String msg) {
		super(msg);
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}
}
