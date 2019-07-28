package com.smartrequest.data.network;

import android.support.annotation.Keep;

@Keep
public class ServerError extends Exception {

	enum Type {

		TOKEN_NOT_VALID,
		REFRESH_TOKEN_NOT_VALID,
		UNKNOWN;

		public static Type fromString(String type) {
			switch (type) {
				case "TOKEN_NOT_VALID":
					return TOKEN_NOT_VALID;
				case "REFRESH_TOKEN_NOT_VALID":
					return REFRESH_TOKEN_NOT_VALID;
				default:
					return UNKNOWN;
			}
		}

	}

	private String type;

	private String message;

	private String debugMessage;

	//region ==================== Getters ====================

	@Override
	public String getMessage() {
		return message;
	}

	public String getDebugMessage() {
		return debugMessage;
	}

	public Type getType() {
		return Type.fromString(this.type);
	}

	//endregion
}
