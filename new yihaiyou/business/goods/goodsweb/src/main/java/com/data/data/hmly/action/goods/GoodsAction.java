package com.data.data.hmly.action.goods;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.action.goods.vo.CategoryTreeVo;
import com.data.data.hmly.service.entity.SysSite;
import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.goods.CategoryTypeService;
import com.data.data.hmly.service.goods.CategoryService;
import com.data.data.hmly.service.goods.entity.Category;
import com.data.data.hmly.service.goods.entity.CategoryType;
import com.data.data.hmly.service.goods.entity.enums.CategoryStatus;
import com.framework.struts.AjaxCheck;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.JsonFilter;
import com.zuipin.util.PropertiesManager;
import com.zuipin.util.StringUtils;
import com.zuipin.util.UUIDUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by vacuity on 15/10/14.
 */

public class GoodsAction extends FrameBaseAction {

    Logger logger = Logger.getLogger(GoodsAction.class);
    @Resource
    private PropertiesManager propertiesManager;

    @Resource
    private CategoryService categoryService;
    @Resource
    private CategoryTypeService categoryTypeService;



    private Category category = new Category();

    private Long id;

    private long cateId;

    private File img;

    public Result goodslist() {
        return dispatch();
    }

    public Result addcategory() {
        return dispatch();
    }

    public Result editcategory() {
        Long id = Long.parseLong(getParameter("id").toString());
        category = categoryService.findById(id);
        return dispatch();
    }

    public Result getCategoryMap() {
        Long id = Long.parseLong(getParameter("id").toString());
        category = categoryService.findById(id);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", category.getId());
        map.put("typeName", category.getType().getType());
        map.put("categoryType", category.getType().getType());
        map.put("categoryname", category.getName());
        map.put("parentcategory", category.getParentId());
        map.put("sortorder", category.getSortOrder());
        map.put("status", category.getStatus());
        return jsonResult(map);
    }

    public Result getCategoryData() {
        SysUnit sysUnit = null;
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig(new Class[]{}, new String[]{});
        String typeIdStr = getRequest().getParameter("typeId");
        if (StringUtils.hasText(typeIdStr)) {
            Long type = Long.parseLong(typeIdStr);
            List<Category> data = categoryService.getDataByType(type, sysUnit);
            return datagrid(data, jsonConfig);
        }
        return null;
    }

    public Result getComboCatgoryData() {
        // 分类获取权限划分至站点
        SysSite sysSite = getSite();
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig(new Class[]{}, new String[]{});
        String typeStr = getRequest().getParameter("type");
        if (StringUtils.hasText(typeStr)) {
            List<Category> parentCategoryList = categoryService.getDataByType(typeStr, sysSite);
            List<CategoryTreeVo> rootData = new ArrayList<>();
            for (Category parentCategory : parentCategoryList) {
                if (CategoryStatus.HIDE.equals(parentCategory.getStatus())) {
                    continue;
                }
                List<Category> childCategoryList = parentCategory.getChildren();
                List<CategoryTreeVo> childCategoryTreeVos = new ArrayList<CategoryTreeVo>();
                CategoryTreeVo rootCategoryTreeVo = new CategoryTreeVo();
                rootCategoryTreeVo.setId(parentCategory.getId());
                rootCategoryTreeVo.setText(parentCategory.getName());
                if (childCategoryList != null) {
                    for (Category childCategory : childCategoryList) {
                        if (CategoryStatus.HIDE.equals(childCategory.getStatus())) {
                            continue;
                        }
                        CategoryTreeVo childCategoryTreeVo = new CategoryTreeVo();
                        childCategoryTreeVo.setId(childCategory.getId());
                        childCategoryTreeVo.setText(childCategory.getName());
                        childCategoryTreeVos.add(childCategoryTreeVo);
                    }
                    rootCategoryTreeVo.setChildren(childCategoryTreeVos);
                }
                rootData.add(rootCategoryTreeVo);
            }
            return json(JSONArray.fromObject(rootData, jsonConfig));
        }
        return null;
    }

    public Result getCategoryRootData() {
        String type = getRequest().getParameter("type");
        return getParentCategory(type);
    }

    public Result getParentCategory(String type) {
        SysUnit sysUnit = getCompanyUnit();
        List<Category> root = categoryService.getRootServiceByType(type, sysUnit);
        List<Category> result = new ArrayList<Category>();
        Category linecategory = new Category();
        linecategory.setId(0L);
        linecategory.setName("一级分类");
        result.add(linecategory);
        result.addAll(root);
        List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
        for (Category category : result) {
            if (cateId != 0 && category.getId() == cateId) {
                continue;
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", category.getId());
            map.put("name", category.getName());
            maps.add(map);
        }

        return json(JSONArray.fromObject(maps));
    }

    public Result delCategory() {
        Map<String, Object> result = new HashMap<String, Object>();
        if (id != null && id > 0) {
            Category category = categoryService.findById(id);
            category.setStatus(CategoryStatus.DEL);
            categoryService.update(category);
            result.put("success", true);
            result.put("msg", "删除成功!");
        } else {
            result.put("success", false);
            result.put("msg", "操作失败!分类ID不可为空");
        }
        return json(JSONObject.fromObject(result));
    }

    @AjaxCheck
    public Result getForm() {
        Map<String, Object> result = new HashMap<String, Object>();
        String imgPath = propertiesManager.getString("IMG_DIR");
        Category newCategory = new Category();
        if (getParameter("id") != null) {
            newCategory = categoryService.findById(Long.parseLong(getParameter("id").toString()));
        }
        String relativePath = "/banner/" + UUIDUtil.getUUID();
        if (img != null) {
            String filePath = imgPath + relativePath;
            // 转存文件
            try {
                FileUtils.copyFile(img, new File(filePath));
                newCategory.setCategoryImgUrl(relativePath);
            } catch (IOException e) {
                // 文件存储失败
                logger.error("文件存储失败", e);
                result.put("success", false);
                result.put("msg", "分类图片保存失败!");
                return json(JSONObject.fromObject(result));
            }
        }
        if (getParameter("categoryname") != null) {
            newCategory.setName(getParameter("categoryname").toString());
        }
        String parent = getParameter("parentcategory").toString();
        newCategory.setParentId(Long.parseLong(parent));
        newCategory.setSortOrder(Integer.parseInt(getParameter("sortorder").toString()));
        if (getParameter("status") != null) {
            String status = getParameter("status").toString();
            newCategory.setStatus(CategoryStatus.valueOf(status));
        }
        if (getParameter("categoryType") != null) {
            String type = getParameter("categoryType").toString();
            CategoryType categoryType = categoryTypeService.getByType(type);
            newCategory.setType(categoryType);
        }
        User user = getLoginUser();
        newCategory.setUser(user);
        SysUnit sysUnit = getCompanyUnit();
        newCategory.setSysUnit(sysUnit);
        newCategory.setAddTime(new Date());
        if (getParameter("id") != null) {
            categoryService.saveOrUpdate(newCategory);
        } else {
            if (checkCount(newCategory.getType().getType())) {
                categoryService.saveOrUpdate(newCategory);
            } else {
                result.put("success", false);
                result.put("msg", "添加失败!(该分类的数量已经超过50个!)");
                return json(JSONObject.fromObject(result));
            }
        }
        result.put("success", true);
        result.put("msg", "操作成功");
        result.put("title", newCategory.getType().getTypeDes());
        return json(JSONObject.fromObject(result));
    }

    public boolean checkCount(String type) {
        SysUnit sysUnit = getCompanyUnit();
        if (categoryService.countByType(type, sysUnit) < 50) {
            return true;
        } else {
            return false;
        }
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public File getImg() {
        return img;
    }

    public void setImg(File img) {
        this.img = img;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCateId() {
        return cateId;
    }

    public void setCateId(long cateId) {
        this.cateId = cateId;
    }
}



