package com.data.data.hmly.service.nctripticket.pojo;

/**
 * Created by caiys on 2016/1/26.
 */
public class CtripResultStatusVO {
    private Boolean isSuccess;		//	是否成功
    private String errorCode;		//	错误编号
    private String customerErrorMessage;		//	用户友好的错误信息
    private String errorMessage	;	//	错误信息

    // 非正常返回错误字段
    private String errCode;
    private String errMsg;

    public Boolean getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getCustomerErrorMessage() {
        return customerErrorMessage;
    }

    public void setCustomerErrorMessage(String customerErrorMessage) {
        this.customerErrorMessage = customerErrorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
