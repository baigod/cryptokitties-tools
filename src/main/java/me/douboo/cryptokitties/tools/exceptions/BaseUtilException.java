package me.douboo.cryptokitties.tools.exceptions;

public abstract class BaseUtilException extends RuntimeException {

	private static final long serialVersionUID = -4310524148478577970L;

	public BaseUtilException() {
		super();
	}

	public BaseUtilException(Throwable cause) {
		super(cause);
	}

	public BaseUtilException(String mesage) {
		super(mesage);
	}
}
