package me.voler.jeveri.util.enumeration;

public enum SendError {

	/**
	 * 发送成功
	 */
	NONE_ERROR(0),

	/**
	 * 系统错误，出现内部Bug
	 */
	SYSTEM_ERROR(-1),

	/**
	 * 请求签名错误
	 */
	SIGNATURE_ERROR(-2),

	/**
	 * 请求参数错误
	 */
	PARAM_ERROR(-2),

	/**
	 * 发送模板抛出错误，包括网络错误等
	 */
	TEMPLATE_ERROR(-2);

	private int errCode;

	public final int getErrCode() {
		return errCode;
	}

	private SendError(int errCode) {
		this.errCode = errCode;
	}

}
