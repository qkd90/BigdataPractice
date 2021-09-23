package com.data.data.hmly.action.yihaiyou;

import com.data.data.hmly.service.MemberService;
import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.order.util.ShenzhouUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opensymphony.xwork2.Result;
import com.zuipin.action.JxmallAction;
import com.zuipin.util.PropertiesManager;
import com.zuipin.util.SpringContextHolder;
import com.zuipin.util.StringUtils;
import org.apache.commons.io.FileUtils;

import javax.annotation.Resource;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.Map;

/**
 * Created by huangpeijie on 2016-09-08,0008.
 */
public class BaseAction extends JxmallAction {
    @Resource
    protected PropertiesManager propertiesManager;
    @Resource
    protected MemberService memberService;

    protected final ObjectMapper mapper = new ObjectMapper();

    public Member getLoginUser() {
        Member user = (Member) getSession().getAttribute(UserConstants.CURRENT_LOGIN_USER);
        if (user != null) {
            return user;
        } else {
            return null;
        }
    }

    public void refreshToken(Member user) {
        Map<String, Object> result = ShenzhouUtil.refreshToken(user.getShenzhouRefreshToken());
        if (!(Boolean) result.get("success")) {
            user.setShenzhouAccessToken(null);
            user.setShenzhouRefreshToken(null);
            user.setShenzhouTokenDate(null);
        } else {
            user.setShenzhouAccessToken(result.get("accessToken").toString());
            user.setShenzhouRefreshToken(result.get("refreshToken").toString());
            user.setShenzhouTokenDate((Date) result.get("tokenDate"));
        }
        memberService.update(user);
        getSession().setAttribute(UserConstants.CURRENT_LOGIN_USER, user);
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

    public Result redirectUrlJson(Member user) {
        result.put("success", false);
        if (StringUtils.isNotBlank(user.getTelephone())) {
            result.put("redirectUrl", ShenzhouUtil.shenzhouAuthorize + "&mobile=" + user.getTelephone());
        } else {
            result.put("redirectUrl", ShenzhouUtil.shenzhouAuthorize);
        }
        return jsonResult(result);
    }

}
