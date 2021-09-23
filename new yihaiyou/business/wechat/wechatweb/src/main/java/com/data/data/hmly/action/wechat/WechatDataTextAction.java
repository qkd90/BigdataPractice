package com.data.data.hmly.action.wechat;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.wechat.WechatDataTextService;
import com.data.data.hmly.service.wechat.entity.WechatDataText;
import com.framework.struts.AjaxCheck;
import com.gson.inf.MsgTypes;
import com.opensymphony.xwork2.Result;

/**
 * Created by vacuity on 15/11/20.
 */
public class WechatDataTextAction extends FrameBaseAction {
    private static final long serialVersionUID = -617072372295001263L;
    @Resource
    private WechatDataTextService textService;
    private Map<String, Object> map = new HashMap<String, Object>();
    private WechatDataText wechatDataText = new WechatDataText();


    @AjaxCheck
    public Result saveText() {

//		String title = (String) getParameter("title");

        if (wechatDataText != null) {
            String content = wechatDataText.getContent();
            if (StringUtils.isNotBlank(content)) {
                content = content.replaceAll("<br />\r\n", "\n");
                content = content.replaceAll("<br />", "");
            }
            wechatDataText.setContent(content);
            textService.saveOrUpdate(wechatDataText, getLoginUser(), getCompanyUnit(), MsgTypes.text);
            simpleResult(map, true, "添加成功！");
        } else {
            simpleResult(map, false, "添加失败！");
        }

        return jsonResult(map);
    }


    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public WechatDataText getWechatDataText() {
        return wechatDataText;
    }

    public void setWechatDataText(WechatDataText wechatDataText) {
        this.wechatDataText = wechatDataText;
    }
}
