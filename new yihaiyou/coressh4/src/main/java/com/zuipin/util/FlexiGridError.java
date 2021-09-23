/**
 * @(#)FlexiGridError.java 2011-5-18
 * 
 * Copyright 2000-2011 by Yinhoo Corporation.
 *
 * All rights reserved.
 * 
 */
package com.zuipin.util;

/**
 * @author Jolly
 * @date 2011-5-18
 * @version $Revision$
 */
public class FlexiGridError {
    private String errorCode;
    private String errorMessage;

    /**
     * @param errorCode
     *            the errorCode to set
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * @return the errorCode
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * @param errorMessage
     *            the errorMessage to set
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * @return the errorMessage
     */
    public String getErrorMessage() {
        return errorMessage;
    }
}
