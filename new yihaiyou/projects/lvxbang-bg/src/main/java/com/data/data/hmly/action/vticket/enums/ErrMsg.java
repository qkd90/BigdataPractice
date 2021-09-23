package com.data.data.hmly.action.vticket.enums;

/**
 * Created by caiys on 2016/1/27.
 */
public enum ErrMsg {
    inputAccount("请输入用户名"),
    inputPassword("请输入密码"),
    accountNotExisted("用户不存在"),
    accountLocked("用户被锁定或者被删除"),
    accountInfoErr("用户名或者密码错误"),
    accountException("账号异常");

    private String desc;

    ErrMsg(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
