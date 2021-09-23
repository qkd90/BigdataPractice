package com.data.data.hmly.action.labels;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.LabelItemService;
import com.data.data.hmly.service.LabelService;
import com.data.data.hmly.service.entity.Label;
import com.data.data.hmly.service.entity.LabelItem;
import com.data.data.hmly.service.vo.LabelsVo;
import com.data.data.hmly.service.vo.ProductLabelVo;
import com.data.data.hmly.service.vo.TagLabelTreeVo;
import com.framework.hibernate.util.Page;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.JsonFilter;
import com.zuipin.util.PropertiesManager;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class ProductLabelsAction  extends FrameBaseAction {
	private String json;
	protected Map<String, Object> result = new HashMap<String, Object>();
	@Resource
	private LabelItemService labelItemService;
	@Resource
	private LabelService labelService;
    @Resource
    private PropertiesManager propertiesManager;
	
	private String targetId;
	private String typeStr;
    private String fgDomain;
	
	
	 private Integer			page				= 1;
	 private Integer			rows				= 10;

    private Label label = new Label();

	/**
	 * 标签上移、下移
	 * @return
	 */
	public Result swapLabelOrder() {
		String toUpIdStr = (String) getParameter("toUpId");
		String toDownIdStr = (String) getParameter("toDownId");
		if (StringUtils.isNotBlank(toUpIdStr) && StringUtils.isNotBlank(toDownIdStr)) {
			labelItemService.doSwapOrder(Long.parseLong(toUpIdStr), Long.parseLong(toDownIdStr));
			simpleResult(result, true, "");
		} else {
			simpleResult(result, false, "");
		}

		return jsonResult(result);
	}

	 /**
	  * 取消选择时，删除item
	  * @return
	  */
	public Result deleteLabelItem() {
		String laId = (String) getParameter("labelIds");
		String targetId = (String) getParameter("targetId");
		String type = (String) getParameter("type");
		if (StringUtils.isNotBlank(targetId) && StringUtils.isNotBlank(laId)) {
			labelItemService.deleteLabelItem(Long.parseLong(laId), Long.parseLong(targetId), type);
			simpleResult(result, true, "");
		} else {
			simpleResult(result, false, "");
		}
		
		return jsonResult(result);
	}
	 /**
	  * 选择时，保存item
	  * @return
	  */
	public Result saveLabelItem() {
		String laId = (String) getParameter("labelIds");
		String targetId = (String) getParameter("targetId");
		String type = (String) getParameter("type");
		
		LabelItem item = labelItemService.saveItem(laId, targetId, type);
		
		if (item.getId() != null) {
			simpleResult(result, true, "");
		} else {
			simpleResult(result, false, "");
		}
		return jsonResult(result);
	}
	/**
	 * 编辑排序初始化加载 
	 * @return
	 */
	public Result editLaItemSort() {
		
		String laId = (String) getParameter("laId");
		String targetId = (String) getParameter("targetId");
		String type = (String) getParameter("type");
		LabelItem item = null;
		if (StringUtils.isNotBlank(targetId) && StringUtils.isNotBlank(laId)) {
			item = labelItemService.findItem(Long.parseLong(targetId), Long.parseLong(laId), type);
		}
		if (item != null) {
			result.put("sort", item.getOrder());
			simpleResult(result, true, "");
		} else {
			simpleResult(result, true, "");
		}
		
		return jsonResult(result);
	}
	/**
	 * 判断输入的序号是否冲突 
	 * @return
	 */
	public Result saveSort() {
		String sort = (String) getParameter("sort");
		String labelId = (String) getParameter("labelId");
		String targetId = (String) getParameter("targetId");
		String itemId = (String) getParameter("itemId");
		String type = (String) getParameter("type");
		boolean flag = false;
		if (StringUtils.isNotBlank(itemId)) {
			flag = labelItemService.editSort(Long.parseLong(itemId), Integer.parseInt(sort));
		} else {
			flag = labelItemService.saveSort(Long.parseLong(labelId), Long.parseLong(targetId), type, Integer.parseInt(sort));
		}
		
		
		simpleResult(result, flag, "");
		return jsonResult(result);

	}
	 
	/**
	 * 保存标签
	 * @return
	 */
	public Result saveLabels() {
		String targetId = (String) getParameter("targetId");
		String type = (String) getParameter("type");
		String labelIds = (String) getParameter("labelIds");
		String itemSorts = (String) getParameter("itemSorts");
		List<Long> laIds = null;
		
		
		
//		if(StringUtils.isNotBlank(targetId)){
//			if(StringUtils.isNotBlank(labelIds)){
				labelItemService.saveOrUpdate(targetId, labelIds, itemSorts, type);
//			}
//		}
		
		simpleResult(result, true, "");
		return jsonResult(result);
	}
	
	 
	public Result findTarIdsByLabelId() {
		String labelIdStr = (String) getParameter("labelId");
		String type = (String) getParameter("type");
		List<Long> targetIds = new ArrayList<Long>();
		if (StringUtils.isNotBlank(labelIdStr)) {
			targetIds = labelItemService.findTarIdsByLabelId(Long.parseLong(labelIdStr), type);
		}
		
		List<Long> newIds = new ArrayList<Long>();
		
		for (Long tId : targetIds) {
			if (tId != null) {
				newIds.add(tId);
			}
		}
		
		result.put("targetIds", newIds);
		simpleResult(result, true, "");
		return jsonResult(result);
	}
	
	public Result findLabelByTargetId() {
		
		String targetIdStr = (String) getParameter("targetId");
		String type = (String) getParameter("type");
		List<Label> labels = null;
		List<LabelsVo> labelList = new ArrayList<LabelsVo>();
		if (StringUtils.isNotBlank(targetIdStr)) {
			
			if (isSupperAdmin()) {
				labels = labelItemService.findAllByTargId(Long.parseLong(targetIdStr), type);
			} else if (isSiteAdmin()) {
				labels = labelItemService.findLabelSiteByTargId(getLoginUser().getSysSite().getId(), Long.parseLong(targetIdStr), type);
			} else {
				labels = labelItemService.findLabelUnitByTargId(getCompanyUnit(), Long.parseLong(targetIdStr), type);
			}
			
		}
		for (Label l : labels) {
			LabelsVo vo = new LabelsVo();

			vo.setId(l.getId());
			vo.setName(l.getName());
			labelList.add(vo);
		}
		result.put("rows", labelList);
		result.put("success", true);
		return json(JSONObject.fromObject(result, JsonFilter.getFilterConfig()));
		
	}
	 
	public Result loadProductGrid() {
		String lavelVosStr = (String) getParameter("lavelVos");
		String type = (String) getParameter("type"); 
		String pageStr = (String) getParameter("pageTotal"); 
		
		List<ProductLabelVo> lavelVos = new ArrayList<ProductLabelVo>();
		JSONArray jArray = null; 
		if (StringUtils.isNotBlank(lavelVosStr)) {
			lavelVosStr = "[" + lavelVosStr + "]";
			jArray = JSONArray.fromObject(lavelVosStr);
			for (Object object : jArray) {
				ProductLabelVo labelVo = new ProductLabelVo();
				
				
				JSONObject o = JSONObject.fromObject(object);
				  
				  Long targId = Long.parseLong(o.get("id").toString());
				  labelVo.setId(targId);
				  labelVo.setCityId(Long.parseLong(o.get("cityId").toString()));
				  labelVo.setCityName((String) o.get("cityName"));
				  labelVo.setName((String) o.get("name"));
				  List<Label> labels = null;
					if (isSupperAdmin()) {
						labels = labelItemService.findAllByTargId(targId, type);
					} else if (isSiteAdmin()) {
						labels = labelItemService.findLabelSiteByTargId(getLoginUser().getSysSite().getId(), targId, type);
					} else {
						labels = labelItemService.findLabelUnitByTargId(getCompanyUnit(), targId, type);
					}
				  
				  
				  labelVo.setLabels(labels);
				  lavelVos.add(labelVo);
				  
				  
			}
		}
		
		
		return datagrid(lavelVos, Integer.parseInt(pageStr));
	}
	 
	 
	public Result loadLabelTree() {
		
		List<LabelsVo> labelList = labelService.findAllTree();
//		Page pageInfo = null;
//
//		if (isSupperAdmin()) {
//			labelList = labelService.findAllTree(pageInfo);
//		} else if (isSiteAdmin()) {
//			labelList = labelService.findLabelSiteTree(getLoginUser().getSysSite().getId(), pageInfo);
//		} else {
//			labelList = labelService.findLabelUnitTree(getCompanyUnit(), pageInfo);
//		}
		
		result.put("rows", labelList);
		
		return json(JSONObject.fromObject(result, JsonFilter.getFilterConfig()));
		
		
	}
	
	
	public Result showLabelTree() {

		targetId = (String) getParameter("targetId");
		typeStr = (String) getParameter("type");
		
		List<Label> targLabels = getTragLabels( targetId, typeStr);
		
		List<LabelsVo> labelVoList = null;
		List<TagLabelTreeVo> tagLabelTreeList = new ArrayList<TagLabelTreeVo>();
		
		Page pageInfo = new Page(page, rows);
        try {
            // 标签查询名称解码
            if (com.zuipin.util.StringUtils.hasText(label.getSearchName())) {
                label.setSearchName(URLDecoder.decode(label.getSearchName(), "utf-8"));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
		if (isSupperAdmin()) {
			labelVoList = labelService.findAllTree(pageInfo, label);
		} else if (isSiteAdmin()) {
			labelVoList = labelService.findLabelSiteTree(getLoginUser().getSysSite().getId(), pageInfo, label);
		} else {
			labelVoList = labelService.findLabelUnitTree(getCompanyUnit(), pageInfo, label);
		}
		
		for (LabelsVo vo : labelVoList) {
			TagLabelTreeVo treeVo  = new TagLabelTreeVo();
			treeVo.setId(vo.getId());
			treeVo.setName(vo.getName());
			treeVo.setHasLabel("0");
			for (Label la : targLabels) {
				if (vo.getId() == la.getId()) {
					treeVo.setHasLabel("1");
					treeVo.setSort(la.getTargSort());
				}
			}
			if (vo.getChildren().size() > 0) {
				treeVo.setHasChild("1");
			} else {
				treeVo.setHasChild("0");
			}
			treeVo.setChildren(getChildTreeVo( vo, targLabels));
			int i = 1;
			for (TagLabelTreeVo childtreeVo : treeVo.getChildren()) {
				if ("1".equals(childtreeVo.getHasLabel())) {
					i = i + 1;
				}
			}
			
			if (i == treeVo.getChildren().size()) {
				treeVo.setHasLabel("1");
			}
			
			tagLabelTreeList.add(treeVo);
		}
		result.put("rows", tagLabelTreeList);
		result.put("total", pageInfo.getTotalCount());
		
		return json(JSONObject.fromObject(result, JsonFilter.getFilterConfig()));
	}
	
	public List<TagLabelTreeVo> getChildTreeVo(LabelsVo vo, List<Label> targLabels) {
		List<TagLabelTreeVo> tagLabelList = new ArrayList<TagLabelTreeVo>();
		for (LabelsVo childVo : vo.getChildren()) {
			
			TagLabelTreeVo childTreeVo  = new TagLabelTreeVo(); 
			childTreeVo.setHasLabel("0");
			for (Label la : targLabels) {
				if (childVo.getId() == la.getId()) {
					childTreeVo.setHasLabel("1");
					childTreeVo.setSort(la.getTargSort());
				}
			}
			childTreeVo.setHasChild("0");
			childTreeVo.setId(childVo.getId());
			childTreeVo.setName(childVo.getName());
			tagLabelList.add(childTreeVo);
	}
		
		return tagLabelList;
	}
	
	public  List<Label> getTragLabels(String targetId, String type) {
		List<Label> labels = null;
		if (StringUtils.isNotBlank(targetId)) {
			
			if (isSupperAdmin()) {
				labels = labelItemService.findAllByTargId(Long.parseLong(targetId), type);
			} else if (isSiteAdmin()) {
				labels = labelItemService.findLabelSiteByTargId(getLoginUser().getSysSite().getId(), Long.parseLong(targetId), type);
			} else {
				labels = labelItemService.findLabelUnitByTargId(getCompanyUnit(), Long.parseLong(targetId), type);
			}
			
		}
		
		return labels;
	}
	
	
	
	public Result selectLabels() {
		targetId = (String) getParameter("targetId");
		typeStr = (String) getParameter("type");
		return dispatch();
	}
	 
	public Result labelsManage() {
        fgDomain = propertiesManager.getString("FG_DOMAIN");
		return dispatch();
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

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	public String getTypeStr() {
		return typeStr;
	}

	public void setTypeStr(String typeStr) {
		this.typeStr = typeStr;
	}


//	public List<ProductLabelVo> getLavelVos() {
//		return lavelVos;
//	}
//
//
//	public void setLavelVos(List<ProductLabelVo> lavelVos) {
//		this.lavelVos = lavelVos;
//	}


    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public String getFgDomain() {
        return fgDomain;
    }

    public void setFgDomain(String fgDomain) {
        this.fgDomain = fgDomain;
    }
}
