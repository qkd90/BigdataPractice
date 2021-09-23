package com.data.data.hmly.service.lvxbang.util;

import javax.servlet.ServletRequest;

/**
 * Created by Sane on 16/3/12.
 */
public class RedirectUriUtil {
    public static String getRedirectUri(ServletRequest request, String uri) {
        if (!uri.startsWith("http")) {
            String domain = request.getScheme() + "://" + request.getServerName();
            return domain + uri;
        }
        return uri;
    }
}
