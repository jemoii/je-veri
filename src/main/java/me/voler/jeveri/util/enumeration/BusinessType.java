package me.voler.jeveri.util.enumeration;

public enum BusinessType {
	REGISTER(1, "注册"), LOGIN(2, "登录"), RESET_PASSWORD(3, "重置密码");

	private int typeCode;
	private String typeMsg;

	private BusinessType(int typeCode, String typeMsg) {
		this.typeCode = typeCode;
		this.typeMsg = typeMsg;
	}

	public final int getTypeCode() {
		return typeCode;
	}

	public final String getTypeMsg() {
		return typeMsg;
	}

	public static final String getTypeMsg(int typeCode) {
		for (BusinessType businessType : BusinessType.values()) {
			if (businessType.typeCode == typeCode) {
				return businessType.typeMsg;
			}
		}
		return String.valueOf(typeCode);
	}

	public static final boolean validateCode(int typeCode) {
		for (BusinessType businessType : BusinessType.values()) {
			if (businessType.typeCode == typeCode) {
				return true;
			}
		}
		return false;
	}
}
