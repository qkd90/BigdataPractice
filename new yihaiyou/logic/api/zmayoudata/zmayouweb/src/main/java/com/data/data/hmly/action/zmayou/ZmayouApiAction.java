package com.data.data.hmly.action.zmayou;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.zmyproduct.ZmyTicketService;
import com.framework.struts.NotNeedLogin;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.StringUtils;
import org.springframework.util.Base64Utils;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dy on 2016/5/9.
 */
public class ZmayouApiAction extends FrameBaseAction {

    Map<String, Object> map = new HashMap<String, Object>();

    @Resource
    private ZmyTicketService zmyTicketService;


    /**
     * 功能描述：同步芝麻游胡里山门票
     * @return
     */
    @NotNeedLogin
    public Result syncProduct() {
        String resultData = (String) getParameter("data");
        String returnFlag = "false";
        resultData = "W3siZGlzdHJpYnV0b3JJZCI6NjEsImlzVGVhbSI6MSwibWFya2V0UHJpY2UiOjAuMDEsIm1heE51bSI6NCwibWluTnVtIjoyLCJuYW1lIjoi5rWL6K+VLS3lkIzlvKAiLCJwcm9kdWN0SWQiOjQ1MywicHVyY2hhc2VQcmljZSI6MC4wMSwicmVhbG5hbWUiOjAsInJlZnVuZEZhY3RvcmFnZSI6MSwicmVmdW5kVGltZUxhZyI6MCwicmVmdW5kVHlwZSI6MSwic3RhdGUiOjIsInR5cGUiOjEsInZhbGlkVHlwZSI6MSwidmFsaWRpdHkiOjd9LHsiZGlzdHJpYnV0b3JJZCI6NjEsImlzVGVhbSI6MSwibWFya2V0UHJpY2UiOjAuMDEsIm1heE51bSI6NCwibWluTnVtIjoyLCJuYW1lIjoiU1hMIiwicHJvZHVjdElkIjo0NDYsInB1cmNoYXNlUHJpY2UiOjAuMDEsInJlYWxuYW1lIjowLCJyZWZ1bmRGYWN0b3JhZ2UiOjEsInJlZnVuZFRpbWVMYWciOjAsInJlZnVuZFR5cGUiOjEsInN0YXRlIjoyLCJ0eXBlIjoxLCJ2YWxpZFR5cGUiOjEsInZhbGlkaXR5Ijo3fSx7ImRpc3RyaWJ1dG9ySWQiOjYxLCJpc1RlYW0iOjAsIm1hcmtldFByaWNlIjowLjAxLCJtYXhOdW0iOjEsIm1pbk51bSI6MSwibmFtZSI6IuiDoemHjOWxseelqC3miJDkurrnpagiLCJwcm9kdWN0SWQiOjQ1NCwicHVyY2hhc2VQcmljZSI6MC4wMSwicmVhbG5hbWUiOjAsInJlZnVuZEZhY3RvcmFnZSI6MSwicmVmdW5kVGltZUxhZyI6MCwicmVmdW5kVHlwZSI6MSwic3RhdGUiOjIsInR5cGUiOjEsInZhbGlkVHlwZSI6MSwidmFsaWRpdHkiOjd9XQ==";
        byte[] bt = null;
        if (!StringUtils.isNotBlank(resultData)) {
            returnFlag = "false";
            simpleResult(map, false, "数据同步失败！");
        }
        bt = Base64Utils.decodeFromString(resultData);
        try {
            if (bt != null) {
                resultData = new String(bt, "UTF-8");

                /*
                JSONObject jsonObject = JSONObject.fromObject(resultData);
                Object object = jsonObject.get("data");
                resultData = object.toString();
                bt = null;
                bt = Base64Utils.decodeFromString(resultData);
                resultData = new String(bt, "UTF-8");
                map = zmyTicketService.doSynProduct(resultData);
                */

                map.put("success", false);
                if ((boolean) map.get("success")) {
                    returnFlag = "true";
                    simpleResult(map, true, "数据同步成功！");
                } else {
                    returnFlag = "false";
                    simpleResult(map, false, "数据同步失败！");
                }
            } else {
                returnFlag = "false";
                simpleResult(map, false, "数据为空！");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return text(returnFlag);
    }

}


