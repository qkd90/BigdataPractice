package com.data.data.hmly.action.goods;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.goods.CategoryTypeService;
import com.data.data.hmly.service.goods.entity.CategoryType;
import com.data.data.hmly.service.goods.entity.enums.CategoryTypeStatus;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.PinyinUtil;
import com.zuipin.util.StringUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2016/4/19.
 */
public class CategoryTypeAction extends FrameBaseAction {

    private CategoryType categoryType = new CategoryType();

    @Resource
    private CategoryTypeService categoryTypeService;

    public Result categoryTypeList() {
        Map<String, Object> result = new HashMap<String, Object>();
        List<CategoryType> categoryTypeList = categoryTypeService.getCategoryTypeList(categoryType, null);
        if (categoryTypeList.isEmpty()) {
            result.put("success", false);
            result.put("msg", "暂无可用分类信息!");
            result.put("data", categoryTypeList);
        }
        result.put("success", true);
        result.put("msg", "");
        result.put("data", categoryTypeList);
        return json(JSONObject.fromObject(result));
    }

    public Result comboxTypeList() {
        List<CategoryType> categoryTypeList = categoryTypeService.getCategoryTypeList(categoryType, null);
        return json(JSONArray.fromObject(categoryTypeList));
    }

    public Result addCategoryType() {
        Map<String, Object> result = new HashMap<String, Object>();
        if (getParameter("typeDes") != null) {
            String typeDes = getParameter("typeDes").toString();
            if (!StringUtils.hasText(typeDes)) {
                result.put("success", false);
                result.put("msg", "顶级分类名称不可为空!");
            } else {
                String type = PinyinUtil.hanziToPinyin(typeDes, "");
                CategoryType categoryType = new CategoryType();
                categoryType.setType(type);
                categoryType.setTypeDes(typeDes);
                categoryType.setStatus(CategoryTypeStatus.SHOW);
                categoryType.setCreateTime(new Date());
                categoryType.setUpdateTime(new Date());
                categoryTypeService.save(categoryType);
                result.put("success", true);
                result.put("msg", "添加顶级分类成功!");
                result.put("data", categoryType);
            }
        } else {
            result.put("success", false);
            result.put("msg", "顶级分类名称不可为空!");
        }
        return json(JSONObject.fromObject(result));
    }

    public CategoryType getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(CategoryType categoryType) {
        this.categoryType = categoryType;
    }
}
