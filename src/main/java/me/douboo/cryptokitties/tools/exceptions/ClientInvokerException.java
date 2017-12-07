package me.douboo.cryptokitties.tools.exceptions;

/**
 * 客户端请求异常
 * 
 * @author luheng
 * @version v01.00.00 $Revision$
 * @date 2016年5月12日
 * @time 下午3:20:06
 */
public class ClientInvokerException extends BaseUtilException {

	private static final long serialVersionUID = -2099663546472519958L;

	public ClientInvokerException() {
		super();
	}

	public ClientInvokerException(String message) {
		super(message);
	}

	public ClientInvokerException(Throwable cause) {
		super(cause);
	}

}
