package com.data.data.hmly.action.labels;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.enums.LabelStatus;
import com.data.data.hmly.enums.TargetType;
import com.data.data.hmly.service.LabelItemService;
import com.data.data.hmly.service.LabelService;
import com.data.data.hmly.service.entity.Label;
import com.data.data.hmly.service.entity.LabelItem;
import com.data.data.hmly.service.vo.LabelsVo;
import com.framework.hibernate.util.Page;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.JsonFilter;
import com.zuipin.util.PropertiesManager;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class LabelsAction  extends FrameBaseAction {
	private String json;
	protected Map<String, Object> result = new HashMap<String, Object>();
	@Resource
	private LabelService labelService;
    @Resource
    private PropertiesManager propertiesManager;
    @Resource
    private LabelItemService labelItemService;

    private Integer			page				= 1;
    private Integer			rows				= 10;

    private String fgDomain;

	private TargetType targetType;

	public Result checkLabels(){
		String idStr = (String) getParameter("id");
		Label label = new Label();
		if (StringUtils.isNotBlank(idStr)) {
			label = labelService.load(Long.parseLong(idStr));
			result = labelService.checkLabels(label);
		}
		simpleResult(result, true, "");
		return jsonResult(result);
	}

    /**
     * 删除
     * @return
     */
	public Result delLabels(){
		String idStr = (String) getParameter("id");
		Label label = null;
		if (StringUtils.isNotBlank(idStr)) {
			label = labelService.load(Long.parseLong(idStr));
			Long parentId = labelService.delLabel(label);
            if (parentId != null) {
                result.put("parentId", parentId);
            }
		}
		simpleResult(result, true, "");
		return jsonResult(result);
	}

    /**
     * 添加、编辑保存
     * @return
     */
	public Result saveLabel() {
        String idStr = (String) getParameter("id");
		String parentId = (String) getParameter("parentId");
		String name = (String) getParameter("name");
        String alias = (String) getParameter("alias");
        String sortStr = (String) getParameter("sort");
		String status = (String) getParameter("status");

		Label label = new Label();
		if (StringUtils.isNotBlank(idStr)) {    // 编辑
			label = labelService.load(Long.parseLong(idStr));
		} else {    // 新增
//            label.setCreateTime(new Date());
            label.setSysUnit(getCompanyUnit());
            label.setUser(getLoginUser());
            if (StringUtils.isNotBlank(parentId)) { // 设置父节点
                label.setParent(labelService.load(Long.parseLong(parentId)));
            }
        }

		label.setName(name);
        label.setAlias(alias);
        if (StringUtils.isNotBlank(status)) {
            label.setStatus(LabelStatus.valueOf(status));
        }
        if (StringUtils.isNotBlank(sortStr) && com.zuipin.util.StringUtils.isNumber(sortStr)) {
            label.setSort(Integer.parseInt(sortStr));
        }

        labelService.saveLabel(label);
        result.put("parentId", parentId);
        simpleResult(result, true, "");
		return jsonResult(result);
	}

	/**
	 * 标签列表数据
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public Result labelsTreegrid() throws UnsupportedEncodingException{

		List<LabelsVo> labelList = null;

//		String parentId = (String) getParameter("id");
		String name = (String) getParameter("name");
		String status = (String) getParameter("status");

		Label labelParent = new Label();
//		if(StringUtils.isNotBlank(parentId)){
//			labelParent = labelService.load(Long.parseLong(parentId));
//		}
		if(StringUtils.isNotBlank(name)){
			name = new String(name.getBytes("ISO-8859-1"),"UTF-8");
			labelParent.setName(name);
		}

		if("USE".equals(status)){
			labelParent.setStatus(LabelStatus.USE);
		}
		if("IDLE".equals(status)){
			labelParent.setStatus(LabelStatus.IDLE);
		}

		Page pageInfo = new Page(page,rows);
		labelList = labelService.getTreLabels(pageInfo,labelParent);

		result.put("rows", labelList);
		result.put("total", pageInfo.getTotalCount());

		return json(JSONObject.fromObject(result, JsonFilter.getFilterConfig()));
	}

	public Result manage() {
        fgDomain = propertiesManager.getString("FG_DOMAIN");
		return dispatch();
	}




    /**
     * 查询标签列表
     */
	public Result listLabels() {
        String parentId = (String) getParameter("parentId");
        String status = (String) getParameter("status");
        String targetTypeStr = (String) getParameter("targetType");
        String targetIdStr = (String) getParameter("targetId");
        String keyword = (String) getParameter("keyword");
        String qryWay = (String) getParameter("qryWay");

        Label label = new Label();
        if (StringUtils.isNotBlank(qryWay)) {
            label.setParentId(null);    // 查询所有
            label.setName(keyword);
        } else if (StringUtils.isNotBlank(parentId)) {
            label.setParentId(Long.valueOf(parentId));
        } else {
            label.setParentId(-1L);
        }
		if (StringUtils.isNotBlank(status)) {
			label.setStatus(LabelStatus.valueOf(status));
		}
        Long targetId = null;
        if (StringUtils.isNotBlank(targetIdStr)) {
            targetId = Long.valueOf(targetIdStr);
        }
        List<LabelsVo> labels = labelService.listLabels(label, targetType, targetId);
        return json(JSONArray.fromObject(labels, JsonFilter.getFilterConfig()));
    }


	/**
	 * 查询标签列表
	 */
	public Result listNoExistsLabels() {
		String parentId = (String) getParameter("parentId");
		String status = (String) getParameter("status");
		String targetTypeStr = (String) getParameter("targetType");
		String targetIdStr = (String) getParameter("targetId");
		String keyword = (String) getParameter("keyword");
		String qryWay = (String) getParameter("qryWay");

		Label label = new Label();
		if (StringUtils.isNotBlank(qryWay)) {
			label.setParentId(null);    // 查询所有
			label.setName(keyword);
		} else if (StringUtils.isNotBlank(parentId)) {
			label.setParentId(Long.valueOf(parentId));
		} else {
			label.setParentId(-1L);
		}
		if (StringUtils.isNotBlank(status)) {
			label.setStatus(LabelStatus.valueOf(status));
		}
		Long targetId = null;
		if (StringUtils.isNotBlank(targetIdStr)) {
			targetId = Long.valueOf(targetIdStr);
		}
		List<LabelsVo> labels = labelService.listNoExistsLabels(label, targetType, targetId);
		return json(JSONArray.fromObject(labels, JsonFilter.getFilterConfig()));
	}

	/**
	 * 查询已关联标签列表
	 */
	public Result listBindLabels() {
        String targetTypeStr = (String) getParameter("targetType");
        String targetIdStr = (String) getParameter("targetId");
//        String keyword = (String) getParameter("keyword");

        TargetType targetType = null;
        Long targetId = null;
        if (StringUtils.isNotBlank(targetTypeStr)) {
            targetType = TargetType.valueOf(targetTypeStr);
        }
        if (StringUtils.isNotBlank(targetTypeStr)) {
            targetId = Long.valueOf(targetIdStr);
        }
        List<LabelsVo> labels = labelService.listBindLabels(targetType, targetId);
        return json(JSONArray.fromObject(labels, JsonFilter.getFilterConfig()));
	}

    /**
     * 查询产品标签
     */
    public Result listProductLabel() {
        String targetTypeStr = (String) getParameter("targetType");
        String labelIdStr = (String) getParameter("labelId");
		String cityStr = (String) getParameter("city");
        List<LabelItem> labels = labelItemService.findProductLabel(Long.valueOf(labelIdStr), TargetType.valueOf(targetTypeStr), cityStr);
        return json(JSONArray.fromObject(labels, JsonFilter.getFilterConfig()));
    }

	/**
     * 标签名称模糊查询
	 * @return
	 */
	public Result listLabelByKey() {
		String name = (String) getParameter("name");
		List<Label> labels = labelService.listLabelByKey(name);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (Label l : labels) {
            Map<String, Object> m = new HashMap<String, Object>();
            m.put("id", l.getId());
            m.put("name", l.getName());
            list.add(m);
        }

		JsonConfig config = JsonFilter.getIncludeConfig();
		JSONArray json = JSONArray.fromObject(list, config);
		return json(json);
	}

	/**
	 * 移动标签
	 * @return
	 */
	public Result moveLabel() {
		String toIdStr = (String) getParameter("toId");
		String moveIdsStr = (String) getParameter("moveIds");

        if (StringUtils.isNotBlank(moveIdsStr)) {
            String[] moveIdsArray = moveIdsStr.split(",");
            result = labelService.doMoveLabelHandle(toIdStr, moveIdsArray);
        } else {
            simpleResult(result, false, "请选择要移动的标签");
        }
		return jsonResult(result);
	}


    public String getJson() {
		return json;
	}
	public void setJson(String json) {
		this.json = json;
	}
	public Map<String, Object> getResult() {
		return result;
	}
	public void setResult(Map<String, Object> result) {
		this.result = result;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

    public String getFgDomain() {
        return fgDomain;
    }

    public void setFgDomain(String fgDomain) {
        this.fgDomain = fgDomain;
    }

	public TargetType getTargetType() {
		return targetType;
	}

	public void setTargetType(TargetType targetType) {
		this.targetType = targetType;
	}
}
