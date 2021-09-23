package com.data.data.hmly.action.yhypc;

import com.data.data.hmly.service.goods.CategoryService;
import com.data.data.hmly.service.goods.CategoryTypeService;
import com.data.data.hmly.service.goods.entity.Category;
import com.data.data.hmly.service.goods.entity.CategoryType;
import com.data.data.hmly.service.wechat.WechatDataImgTextService;
import com.data.data.hmly.service.wechat.entity.WechatDataNews;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.PinyinUtil;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2017/3/7.
 */
public class AboutWebAction extends YhyAction {

    private Map<String, Object> result = new HashMap<String, Object>();

    @Resource
    private CategoryTypeService categoryTypeService;
    @Resource
    private CategoryService categoryService;
    @Resource
    private WechatDataImgTextService wechatDataImgTextService;

    public Result index() {
        // 查找分类
        CategoryType categoryType = categoryTypeService.getByType("article");
        // 查找分类
        Category category = categoryService.getByTypeAndName(categoryType, "web端帮助资源");
        List<WechatDataNews> wechatDataNewses = wechatDataImgTextService.findByCategory(category);
        for (WechatDataNews wechatDataNews : wechatDataNewses) {
            result.put(PinyinUtil.hanziToPinyin(wechatDataNews.getTitle(), ""), wechatDataNews);
        }
        return dispatch();
    }

    public Map<String, Object> getResult() {
        return result;
    }

    public void setResult(Map<String, Object> result) {
        this.result = result;
    }
}
