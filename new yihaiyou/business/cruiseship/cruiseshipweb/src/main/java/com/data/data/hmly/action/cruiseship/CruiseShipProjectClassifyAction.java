package com.data.data.hmly.action.cruiseship;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.cruiseship.CruiseShipProjectClassifyService;
import com.data.data.hmly.service.cruiseship.entity.CruiseShipProjectClassify;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.JsonFilter;
import com.zuipin.util.StringUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by Think on 2017/6/16.
 */

public class CruiseShipProjectClassifyAction extends FrameBaseAction {

    @Resource
    private CruiseShipProjectClassifyService cruiseShipProjectClassifyService;

    private CruiseShipProjectClassify cruiseShipProjectClassify = new CruiseShipProjectClassify();
    private Long projectId;
    private String imgPaths;
    private Long parentId;
    private Long id;
    private String keyword;

    public Result list() {
        List<CruiseShipProjectClassify> cruiseShipProjectClassifyList = new ArrayList<CruiseShipProjectClassify>();
        cruiseShipProjectClassifyList = cruiseShipProjectClassifyService.listById(parentId,keyword);

        for (CruiseShipProjectClassify classify : cruiseShipProjectClassifyList) {
            classify.setParentId(classify.getCruiseShipProjectClassify().getId());
        }
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig("cruiseShipProjectClassify");
        return datagrid(cruiseShipProjectClassifyList, cruiseShipProjectClassifyList.size(), jsonConfig);
    }

    public Result classifyNameList(){
        List<CruiseShipProjectClassify> cruiseShipProjectClassifyList = new ArrayList<CruiseShipProjectClassify>();
        cruiseShipProjectClassifyList = cruiseShipProjectClassifyService.queryClassifyNameById(parentId);
        for (CruiseShipProjectClassify classify : cruiseShipProjectClassifyList) {
            classify.setParentId(classify.getCruiseShipProjectClassify().getId());
        }
        return json(JSONArray.fromObject(cruiseShipProjectClassifyList, JsonFilter.getIncludeConfig())) ;
    }

    public Result save() {
        Map<String, Object> result = new HashMap<String, Object>();
        if (cruiseShipProjectClassify.getId() != null) {
            // 更新项目信息
            CruiseShipProjectClassify sourceCruiseShipProjectClassify = cruiseShipProjectClassifyService.get(cruiseShipProjectClassify.getId());
            sourceCruiseShipProjectClassify.setClassifyName(cruiseShipProjectClassify.getClassifyName());
            sourceCruiseShipProjectClassify.setShowStatus(true);
            sourceCruiseShipProjectClassify.setSort(cruiseShipProjectClassify.getSort());
            sourceCruiseShipProjectClassify.setCoverImage(cruiseShipProjectClassify.getCoverImage());
            sourceCruiseShipProjectClassify.setCruiseShipProjectClassify(cruiseShipProjectClassify.getCruiseShipProjectClassify());
            if (StringUtils.hasText(imgPaths)) {
                sourceCruiseShipProjectClassify.setCoverImage(imgPaths);
            }
            sourceCruiseShipProjectClassify.setUpdateTime(new Date());
            cruiseShipProjectClassifyService.update(sourceCruiseShipProjectClassify);
            result.put("success", true);
            result.put("msg", "更新项目信息成功!");
        } else {
            // 新增项目信息
            cruiseShipProjectClassify.setShowStatus(true);
            cruiseShipProjectClassify.setCreateTime(new Date());
            if (StringUtils.hasText(imgPaths)) {
                cruiseShipProjectClassify.setCoverImage(imgPaths);
            }
            cruiseShipProjectClassifyService.save(cruiseShipProjectClassify);
            result.put("success", true);
            result.put("msg", "保存项目信息成功!");
        }
        return json(JSONObject.fromObject(result));
    }

   /* public Result saveClassifyName(){

        Map<String, Object> result = new HashMap<String, Object>();
        if (cruiseShipProjectClassify.getId() != null) {

        }

        return null;
    }*/

    public Result delProject() {

        Map<String, Object> result = new HashMap<String, Object>();
        if (projectId != null) {
            CruiseShipProjectClassify sourceCruiseShipProjectClassify = cruiseShipProjectClassifyService.get(projectId);
            sourceCruiseShipProjectClassify.setShowStatus(false);
            cruiseShipProjectClassifyService.update(sourceCruiseShipProjectClassify);
            result.put("success", true);
            result.put("msg", "删除成功!");
        }
           /* result.put("success", true);
            result.put("msg", "删除成功!");
        } else {
            result.put("success", false);
            result.put("msg", "删除失败, ID不能为空!");
        }*/
       return json(JSONObject.fromObject(result));
    }

    public CruiseShipProjectClassify getCruiseShipProjectClassify() {
        return cruiseShipProjectClassify;
    }

    public void setCruiseShipProjectClassify(CruiseShipProjectClassify cruiseShipProjectClassify) {
        this.cruiseShipProjectClassify = cruiseShipProjectClassify;
    }

    public CruiseShipProjectClassifyService getCruiseShipProjectClassifyService() {
        return cruiseShipProjectClassifyService;
    }

    public void setCruiseShipProjectClassifyService(CruiseShipProjectClassifyService cruiseShipProjectClassifyService) {
        this.cruiseShipProjectClassifyService = cruiseShipProjectClassifyService;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getImgPaths() {
        return imgPaths;
    }

    public void setImgPaths(String imgPaths) {
        this.imgPaths = imgPaths;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
