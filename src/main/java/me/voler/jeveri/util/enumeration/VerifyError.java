package me.voler.jeveri.util.enumeration;

public enum VerifyError {

	/**
	 * 验证成功
	 */
	NONE_ERROR(0),

	/**
	 * 系统错误，出现内部Bug
	 */
	SYSTEM_ERROR(-1),

	/**
	 * 超时验证，缓存失效
	 */
	TIMEOUT_ERROR(-2),

	/**
	 * 验证信息不一致
	 */
	NOT_EQUAL_ERROR(-3);

	private int errCode;

	public final int getErrCode() {
		return errCode;
	}

	VerifyError(int errCode) {
		this.errCode = errCode;
	}

}
