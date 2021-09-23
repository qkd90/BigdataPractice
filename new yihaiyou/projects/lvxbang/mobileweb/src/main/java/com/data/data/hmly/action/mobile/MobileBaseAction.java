package com.data.data.hmly.action.mobile;

import com.data.data.hmly.service.entity.Member;
import com.zuipin.action.JxmallAction;
import com.zuipin.util.PropertiesManager;
import com.zuipin.util.SpringContextHolder;
import org.apache.commons.io.FileUtils;

import javax.security.auth.login.LoginException;
import javax.servlet.http.Cookie;
import java.io.*;

/**
 * Created by huangpeijie on 2016-05-18,0018.
 */
public class MobileBaseAction extends JxmallAction {

    public Member getLoginUser() throws LoginException {
        return getLoginUser(false);
    }

    public Member getLoginUser(boolean strict) throws LoginException {
        Member user = (Member) getSession().getAttribute(UserConstants.CURRENT_LOGIN_USER);
        if (user != null) {
            return user;
        }
        if (!strict) {
            return null;
        }
        throw new LoginException();
    }

    public void addCookie(String name, String value) {
        Cookie cookie = getCookie(name);
        if (cookie != null) {
            cookie.setValue(value);
        } else {
            cookie = new Cookie(name, value);
            cookie.setPath("/");
        }
        getResponse().addCookie(cookie);
    }

    public String getJsonDate(String fileAttr, Integer validDay) {
        PropertiesManager propertiesManager = SpringContextHolder.getBean("propertiesManager");
        String filePath = propertiesManager.getString("JSON_DIR") + fileAttr;
        String json = "";
        try {
            File file = new File(filePath);
            if (file.exists() && validDayFile(file, validDay)) {
                json = FileUtils.readFileToString(file, "utf-8");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    public void setJsonDate(String fileAttr, String json) {
        PropertiesManager propertiesManager = SpringContextHolder.getBean("propertiesManager");
        String filePath = propertiesManager.getString("JSON_DIR") + fileAttr;
        try {
            File file = new File(filePath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Boolean validDayFile(File file, Integer validDay) {
        long validDayLong = validDay * 24 * 60 * 60 * 1000l;
        long passTime = System.currentTimeMillis() - file.lastModified();
        return passTime > validDayLong ? false : true;
    }
}