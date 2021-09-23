package com.data.data.hmly.service.lvxbang.util;

import com.qq.connect.QQConnect;
import com.qq.connect.QQConnectException;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.utils.QQConnectConfig;
import com.qq.connect.utils.http.PostParameter;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Sane on 16/3/12.
 */
public class QQOauthUtil extends QQConnect {
    private String[] extractionAuthCodeFromUrl(String url) throws QQConnectException {
        if (url == null) {
            throw new QQConnectException("you pass a null String object");
        } else {
            Matcher m = Pattern.compile("code=(\\w+)&state=(\\w+)&?").matcher(url);
            String authCode = "";
            String state = "";
            if (m.find()) {
                authCode = m.group(1);
                state = m.group(2);
            }

            return new String[]{authCode, state};
        }
    }

    public AccessToken getAccessTokenByRequest(ServletRequest request) throws QQConnectException {
        String queryString = ((HttpServletRequest) request).getQueryString();
        if (queryString == null) {
            return new AccessToken();
        } else {
//            String state = (String) ((HttpServletRequest) request).getSession().getAttribute("qq_connect_state");
            String state = request.getParameter("state");
            if (state != null && !"".equals(state)) {
                String[] authCodeAndState = this.extractionAuthCodeFromUrl(queryString);
                String returnState = authCodeAndState[1];
                String returnAuthCode = authCodeAndState[0];
                AccessToken accessTokenObj = null;
                accessTokenObj = getAccessTokenObj(request, state, returnState, returnAuthCode);

                return accessTokenObj;
            } else {
                return new AccessToken();
            }
        }
    }

    private AccessToken getAccessTokenObj(ServletRequest request, String state, String returnState, String returnAuthCode) throws QQConnectException {
        AccessToken accessTokenObj;
        if (!"".equals(returnState) && !"".equals(returnAuthCode)) {
            accessTokenObj = getAccessToken(request, state, returnState, returnAuthCode);
        } else {
            accessTokenObj = new AccessToken();
        }
        return accessTokenObj;
    }

    private AccessToken getAccessToken(ServletRequest request, String state, String returnState, String returnAuthCode) throws QQConnectException {
        AccessToken accessTokenObj;
        if (!state.equals(returnState)) {
            accessTokenObj = new AccessToken();
        } else {
            String redirectUri = RedirectUriUtil.getRedirectUri(request, QQConnectConfig.getValue("redirect_URI"));
            accessTokenObj = new AccessToken(this.client.post(QQConnectConfig.getValue("accessTokenURL"), new PostParameter[]{new PostParameter("client_id", QQConnectConfig.getValue("app_ID")), new PostParameter("client_secret", QQConnectConfig.getValue("app_KEY")), new PostParameter("grant_type", "authorization_code"), new PostParameter("code", returnAuthCode), new PostParameter("redirect_uri", redirectUri)}, false));
        }
        return accessTokenObj;
    }
}
