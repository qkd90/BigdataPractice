package com.data.data.hmly.action.lvxbang;

import com.data.data.hmly.service.wechat.WechatDataImgTextService;
import com.data.data.hmly.service.wechat.entity.WechatDataNews;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.JsonFilter;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Jonathan.Guo
 */
public class HelpWebAction extends LxbAction {

    @Resource
    private WechatDataImgTextService wechatDataImgTextService;

    public Integer page;
    public String keyword;

    public Result index() {
        return dispatch();
    }

    public Result dataListBykeyword() {
        List<WechatDataNews> list = wechatDataImgTextService.findListBykeyword(keyword, null);
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig();
        if (list.isEmpty()) {
            return null;
        }
        return json(JSONObject.fromObject(list.get(0), jsonConfig));
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
