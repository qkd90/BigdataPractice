package com.data.data.hmly.service.user.exception;

/**
 * @author Jonathan.Guo
 */
public class ThirdPartyUserException extends RuntimeException {

    public static final int SUCCESS = 0;
    public static final int ERROR_EXIST = 1;
    public static final int ERROR_LACK_OF_INFO = 2;
    public static final int ERROR_NO_INFO = 3;
    public static final int ERROR_FAILED = -1;

    private int errorCode;
    private String description;

    public ThirdPartyUserException(int errorCode, String description) {
        this.errorCode = errorCode;
        this.description = description;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
