package com.data.data.hmly.service.lvxbang.util;

import weibo4j.Weibo;
import weibo4j.model.PostParameter;
import weibo4j.model.WeiboException;
import weibo4j.util.WeiboConfig;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Sane on 16/3/12.
 */
public class WeiboAccessTokenUtil extends Weibo {
    public weibo4j.http.AccessToken getAccessTokenByCode(String code, HttpServletRequest request) throws WeiboException {
        String redirectUri = RedirectUriUtil.getRedirectUri(request, WeiboConfig.getValue("redirect_URI").trim());
        return new weibo4j.http.AccessToken(this.client.post(WeiboConfig.getValue("accessTokenURL"), new PostParameter[]{new PostParameter("client_id", WeiboConfig.getValue("client_ID")), new PostParameter("client_secret", WeiboConfig.getValue("client_SERCRET")), new PostParameter("grant_type", "authorization_code"), new PostParameter("code", code), new PostParameter("redirect_uri", redirectUri)}, false));
    }
}
