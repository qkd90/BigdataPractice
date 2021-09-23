package com.data.data.hmly.service.build.builder;

import com.data.data.hmly.enums.LabelStatus;
import com.data.data.hmly.service.LabelService;
import com.data.data.hmly.service.TbAreaService;
import com.data.data.hmly.service.area.AreaService;
import com.data.data.hmly.service.build.util.FreemarkerUtil;
import com.data.data.hmly.service.entity.Label;
import com.data.data.hmly.service.entity.TbArea;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by guoshijie on 2015/10/23.
 */
@Component
public class CommonBuilder {

	private static final String HEADER_TEMPLATE = "/common/header.ftl";
	private static final String HEADER_TARGET   = "/common/header.htm";
	private static final String FOOTER_TEMPLATE = "/common/footer.ftl";
	private static final String FOOTER_TARGET   = "/common/footer.htm";
    private static final String LVXBANG_CITY_SELECTOR_TEMPLATE = "/lvxbang/common/citySelector.ftl";
    private static final String LVXBANG_CITY_SELECTOR_TARGET   = "/lvxbang/common/citySelector.htm";
    private static final String LVXBANG_HAND_DRAW_CITY_TEMPLATE = "/lvxbang/common/handDrawCity.ftl";
    private static final String LVXBANG_HAND_DRAW_CITY_TARGET   = "/lvxbang/common/handDrawCity.htm";


//    private static final int DESTINATION_NUMBER_SHOW_ON_SELECTOR = 6;

	@Resource
	private TbAreaService tbAreaService;
    @Resource
    private AreaService areaService;
	@Resource
	private LabelService labelService;

	private static Set<Long> municipalities = new HashSet<Long>();

	static {
		Long[] array = {110000l, 120000l, 310000l, 500000l, 710000l, 810000l, 820000l};
		municipalities = Sets.newHashSet(array);
	}

	public void build() {
		buildHeader();
		buildFooter();
	}

	public void buildHeader() {
		List<Map<String, Object>>[] list = getAreaList();
		Map<Object, Object> data = new HashMap<Object, Object>();
		data.put("areaList", list);

		FreemarkerUtil.create(data, HEADER_TEMPLATE, HEADER_TARGET);
	}

	public void buildFooter() {
		Map<Object, Object> data = new HashMap<Object, Object>();

		FreemarkerUtil.create(data, FOOTER_TEMPLATE, FOOTER_TARGET);
	}

	public List<Map<String, Object>>[] getAreaList() {
		Order order = Order.asc("pinyin");
		List<TbArea> list = tbAreaService.list(null, order);
		List<TbArea> filteredList = new ArrayList<TbArea>();
		for (TbArea area : list) {
			if (area.getLevel() != 2 && !municipalities.contains(area.getId())) {
				continue;
			}
			filteredList.add(area);
		}
		List<Map<String, Object>>[] result = new List[4];

		char index = 'a';
		while (index <= 'z') {
			String firstLetter = index + "";
			Map<String, Object> map = new HashMap<String, Object>();
			List<TbArea> subList = new ArrayList<TbArea>();
			for (TbArea area : filteredList) {
				if (StringUtils.isNotBlank(area.getPinyin()) && area.getPinyin().startsWith(firstLetter)) {
					subList.add(area);
				}
			}
			map.put("index", firstLetter.toUpperCase());
			map.put("list", subList);
			if (index <= 'f') {
				if (result[0] == null) {
					result[0] = new ArrayList<Map<String, Object>>();
				}
				result[0].add(map);
			} else if (index <= 'l') {
				if (result[1] == null) {
					result[1] = new ArrayList<Map<String, Object>>();
				}
				result[1].add(map);
			} else if (index <= 'r') {
				if (result[2] == null) {
					result[2] = new ArrayList<Map<String, Object>>();
				}
				result[2].add(map);
			} else if (index != 'u' && index != 'v') {
				if (result[3] == null) {
					result[3] = new ArrayList<Map<String, Object>>();
				}
				result[3].add(map);
			}
			index += 1;
		}

		return result;
	}

    public void buildLxbCitySelector() {
        Map<Object, Object> data = Maps.newHashMap();
        List<TbArea> destinations = areaService.getHomeHotArea();
//        if (destinations.size() > DESTINATION_NUMBER_SHOW_ON_SELECTOR) {
//            destinations = destinations.subList(0, DESTINATION_NUMBER_SHOW_ON_SELECTOR);
//        }
		Label label = new Label();
		label.setName("通用目的地-国内");
		label.setStatus(LabelStatus.USE);
		List<Label> labels = labelService.list(label, null);
		if (!labels.isEmpty()) {
			List<TbArea> sortAreas = areaService.getTrafficAreas(labels.get(0).getId());
			List<Map<String, Object>> sortMap = sortAreasList(sortAreas);
			List<Map<String, List<Object>>> letterSortAreas = letterSortAreasList(sortMap);
			data.put("letterSortAreas", letterSortAreas);
		}
        data.put("hot", destinations);
		data.put("abroadAreas", getAbroadArea());
        FreemarkerUtil.create(data, LVXBANG_CITY_SELECTOR_TEMPLATE, LVXBANG_CITY_SELECTOR_TARGET);
    }

	public Map<String, Object> getAbroadArea() {
		Label searchLabel = new Label();
		searchLabel.setName("通用目的地-境外");
		searchLabel.setStatus(LabelStatus.USE);
		Label parent = labelService.findUnique(searchLabel);
		List<Label> firstlList = labelService.getAllChildsLabels(parent);
		Map<String, Object> firstMap = Maps.newLinkedHashMap();
		for (Label first : firstlList) {
			List<TbArea> areaList = areaService.getAreaByLabel(first);
			firstMap.put(first.getAlias(), areaList);
		}
		return firstMap;
	}

    public void buildLxbHandDrawCity() {
        Map<Object, Object> data = Maps.newHashMap();
        List<TbArea> sortAreas = areaService.getHandDrawCityList();

        List<Map<String, Object>> sortMap = groupBySpelling(sortAreas);
        data.put("areaMaps", sortMap);
        FreemarkerUtil.create(data, LVXBANG_HAND_DRAW_CITY_TEMPLATE, LVXBANG_HAND_DRAW_CITY_TARGET);
    }

    public List<Map<String, Object>> groupBySpelling(List<TbArea> sortAreas) {

        Map<String, List<TbArea>> map = Maps.newHashMap();
        for (TbArea a : sortAreas) {
            if (a.getPinyin() != null) {
                String first = a.getPinyin().substring(0, 1).toUpperCase();
                List<TbArea> list = map.get(first);
                if (list == null) {
                    list = Lists.newArrayList();
                }
                list.add(a);
                map.put(first, list);

            }
        }
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        for (Map.Entry<String, List<TbArea>> entry : map.entrySet()) {
            Map<String, Object> temp = Maps.newHashMap();
            temp.put("name", entry.getKey());
            temp.put("list", entry.getValue());
            result.add(temp);
        }
        Collections.sort(result, new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                char a = o1.get("name").toString().charAt(0);
                char b = o2.get("name").toString().charAt(0);
                return a - b;
            }
        });
        return result;
    }

	public List<Map<String, Object>> sortAreasList(List<TbArea> sortAreas) {

		Map<String, List<TbArea>> map = Maps.newHashMap();
		for (TbArea a : sortAreas) {
			if (a.getPinyin() != null) {
				String first = a.getPinyin().substring(0, 1).toUpperCase();
				List<TbArea> list = map.get(first);
				if (list == null) {
					list = new ArrayList();
				}
				list.add(a);
				map.put(first, list);

			}
		}
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (Map.Entry<String, List<TbArea>> entry : map.entrySet()) {
			Map<String, Object> temp = Maps.newHashMap();
			temp.put("name", entry.getKey());
			temp.put("list", entry.getValue());
			result.add(temp);
		}
		Collections.sort(result, new Comparator<Map<String, Object>>() {
			@Override
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				char a = o1.get("name").toString().charAt(0);
				char b = o2.get("name").toString().charAt(0);
				return a - b;
			}
		});
		return result;
	}

	public List<Map<String, List<Object>>> letterSortAreasList(List<Map<String, Object>> sortMap) {
		List<Map<String, List<Object>>> result = new ArrayList<Map<String, List<Object>>>();
		String[] letterRanges = new String[]{"A-E", "F-J", "K-P", "Q-W", "X-Z"};
		for (int i = 0; i < letterRanges.length; i++) {
			Map<String, List<Object>> rangeMap = Maps.newHashMap();
			rangeMap.put("letterRange", new ArrayList<Object>());
			result.add(rangeMap);
		}
		for (Map<String, Object> map : sortMap) {
			for (int i = 0; i < letterRanges.length; i++) {
				String letterRange = letterRanges[i];
				String[] letters = letterRange.split("-");
				if (letters[0].compareTo((String) map.get("name")) <= 0 && letters[1].compareTo((String) map.get("name")) >= 0) {
					result.get(i).get("letterRange").add(map);
				}
			}
		}
		return result;
	}

}
