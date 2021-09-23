package com.data.data.hmly.action.yihaiyou.response;

import com.data.data.hmly.service.wechat.entity.WechatDataNews;

/**
 * Created by huangpeijie on 2016-05-20,0020.
 */
public class CouponRuleResponse {
    private String title;
    private String content;

    public CouponRuleResponse() {
    }

    public CouponRuleResponse(WechatDataNews news) {
        this.title = news.getTitle();
        this.content = news.getContent();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
