package com.data.data.hmly.action.lvxbang;

import com.data.data.hmly.action.lvxbang.response.AdviceResponse;
import com.data.data.hmly.service.comment.AdviceService;
import com.data.data.hmly.service.comment.entity.Advice;
import com.data.data.hmly.service.entity.Member;
import com.framework.hibernate.util.Page;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.opensymphony.xwork2.Result;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jonathan.Guo
 */
public class AdviceWebAction extends LxbAction {

    @Resource
    private AdviceService adviceService;

    public int pageNo;
    public int pageSize;
    public String content;

    public Result getAdvice() {
        Page page = new Page(pageNo, pageSize);
        List<Advice> list = adviceService.list(new Advice(), page, "createTime", "desc");
        List<AdviceResponse> responses = Lists.newArrayList(Lists.transform(list, new Function<Advice, AdviceResponse>() {
            @Override
            public AdviceResponse apply(Advice advice) {
                return new AdviceResponse(advice);
            }
        }));
        return json(JSONArray.fromObject(responses));
    }

    public Result countAdvice() {
        Long count = adviceService.count(new Advice());
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        result.put("count", count);
        return json(JSONObject.fromObject(result));
    }

    public Result save() {
        Advice advice = new Advice();
        Member user = getLoginUser(false);
        advice.setUser(user);
        advice.setContent(content);
        advice.setAccept(false);
        advice.setReply(false);
        advice.setUpdateTime(new Date());
        adviceService.save(advice);
        return text("success");
    }
}
