package com.data.data.hmly.service.user.vo;


import com.data.data.hmly.enums.ThirdPartyUserType;

/**
 * @author Jonathan.Guo
 */
public class OpenLoginInfo {

    public String openId;
    public ThirdPartyUserType type;
    public String nickName;
    public String headPath;
    public String unionId;

    public OpenLoginInfo(String openId, ThirdPartyUserType type, String nickName, String headPath, String unionId) {
        this.openId = openId;
        this.type = type;
        this.nickName = nickName;
        this.headPath = headPath;
        this.unionId = unionId;
    }

    public OpenLoginInfo() {

    }
}
