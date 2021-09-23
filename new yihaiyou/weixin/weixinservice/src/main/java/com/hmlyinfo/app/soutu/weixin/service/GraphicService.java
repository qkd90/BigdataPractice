package com.hmlyinfo.app.soutu.weixin.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hmlyinfo.app.soutu.weixin.domain.Graphic;
import com.hmlyinfo.app.soutu.weixin.mapper.GraphicMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;

@Service
public class GraphicService extends BaseService<Graphic, Long>{

	@Autowired
	private GraphicMapper<Graphic> mapper;

	public List<Graphic> list(Map<String, Object> paramMap)
	{
		List<Graphic> lists = getMapper().list(paramMap);
		List<Graphic> result = new ArrayList<Graphic>();
		Map<String, Object> params = new HashMap<String, Object>();
		for (Graphic graphic : lists) {
			if(graphic.getType().equals("2")){
				if(graphic.getFatherId()==0)
				{
					Graphic father = graphic;
					params = new HashMap<String, Object>();
					params.put("fatherId", father.getId());
					List<Graphic> childs = ascList(params);
					father.setChildGraphics(childs);
					String idStr ="";
					for (Graphic child : childs) {
						idStr += child.getId()+ ",";
					}
					idStr += father.getId();
					father.setIdStr(idStr);
					result.add(father);
				}
			}else{
				result.add(graphic);
			}
		}
		return result;
	}
	
	/**
	 * 得到正序列表
	 * @param paramMap
	 * @return
	 */
	public List<Graphic> ascList(Map<String, Object> paramMap)
	{
		return mapper.ascList(paramMap);
	}
	
	/**
	 * 条件搜索
	 * @param paramMap
	 * @return
	 */
	public int searchCount(Map<String, Object> paramMap)
	{
		paramMap.put("operCount", true);
		return mapper.searchCount(paramMap);
	}
	
	/**
	 * 条件搜索
	 * @param paramMap
	 * @return
	 */
	public List<Graphic> searchList(Map<String, Object> paramMap)
	{
		List<Graphic> lists = mapper.searchList(paramMap);
		List<Graphic> result = new ArrayList<Graphic>();
		Map<String, Object> params = new HashMap<String, Object>();
		for (Graphic graphic : lists) {
			if(graphic.getType().equals("2")){
				if(graphic.getFatherId()==0)
				{
					Graphic father = graphic;
					params = new HashMap<String, Object>();
					params.put("fatherId", father.getId());
					List<Graphic> childs = ascList(params);
					father.setChildGraphics(childs);
					result.add(father);
				}
			}else{
				result.add(graphic);
			}
		}
		return result;
	}

	@Override
	public BaseMapper<Graphic> getMapper() 
	{
		return mapper;
	}
	
	@Override
	public String getKey() 
	{
		return "id";
	}
	
}
