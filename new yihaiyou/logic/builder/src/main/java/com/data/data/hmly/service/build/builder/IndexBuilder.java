package com.data.data.hmly.service.build.builder;

import com.data.data.hmly.enums.LabelStatus;
import com.data.data.hmly.service.AdsService;
import com.data.data.hmly.service.LabelService;
import com.data.data.hmly.service.SysResourceMapService;
import com.data.data.hmly.service.SysSiteService;
import com.data.data.hmly.service.SysUnitService;
import com.data.data.hmly.service.area.AreaService;
import com.data.data.hmly.service.build.util.FreemarkerUtil;
import com.data.data.hmly.service.common.entity.Productimage;
import com.data.data.hmly.service.common.entity.enums.ProductStatus;
import com.data.data.hmly.service.entity.Ads;
import com.data.data.hmly.service.entity.Label;
import com.data.data.hmly.service.entity.SysResourceMap;
import com.data.data.hmly.service.entity.SysSite;
import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.hotel.HotelService;
import com.data.data.hmly.service.hotel.entity.Hotel;
import com.data.data.hmly.service.line.LineImagesService;
import com.data.data.hmly.service.line.LineService;
import com.data.data.hmly.service.line.LinetypepricedateService;
import com.data.data.hmly.service.line.entity.Line;
import com.data.data.hmly.service.line.entity.LineImages;
import com.data.data.hmly.service.line.entity.Linetypeprice;
import com.data.data.hmly.service.line.entity.Linetypepricedate;
import com.data.data.hmly.service.line.entity.enums.LineImageType;
import com.data.data.hmly.service.line.entity.enums.LineStatus;
import com.data.data.hmly.service.plan.RecommendPlanService;
import com.data.data.hmly.service.plan.entity.RecommendPlan;
import com.data.data.hmly.service.scenic.ScenicInfoService;
import com.data.data.hmly.service.scenic.entity.ScenicInfo;
import com.data.data.hmly.service.ticket.TicketService;
import com.data.data.hmly.service.ticket.entity.Ticket;
import com.framework.hibernate.util.Page;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zuipin.util.PropertiesManager;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by guoshijie on 2015/10/22.
 */
@Component
public class IndexBuilder {

	private static final String INDEX_LINE_TEMPLATE = "/index/line.ftl";
	private static final String INDEX_LINE_TARGET = "/index/line{id}.htm";
	private static final String INDEX_TICKET_TEMPLATE = "/index/ticket.ftl";
	private static final String INDEX_TICKET_TARGET = "/index/ticket{id}.htm";
	private static final String INDEX_SUPPLIER_TEMPLATE = "/index/supplier.ftl";
	private static final String INDEX_SUPPLIER_TARGET = "/index/supplier{id}.htm";
	private static final String INDEX_AD_TOP_SCROLL_BANNER_TEMPLATE = "/index/ad.ftl";
	private static final String INDEX_AD_TOP_SCROLL_BANNER_TARGET = "/index/ad{id}.htm";
	private static final String LVXBANG_INDEX_TEMPLATE = "/lvxbang/index.ftl";
	private static final String LVXBANG_INDEX_TARGET = "/lvxbang/index.htm";
	private static final String LVXBANG_INDEX_BANNER = "lvxbang_index_banner";
	private static final String TOP_SCROLL_BANNER = "top_roll_banner";
//	public static final int DESTINATION_NUMBER_SHOW_ON_INDEX = 7;	// 首页目的数量，不能随意修改数字

	@Resource
	private LineService lineService;
	@Resource
	private TicketService ticketService;
	@Resource
	private SysUnitService sysUnitService;
	@Resource
	private AdsService adsService;
	@Resource
	private SysResourceMapService sysResourceMapService;
	@Resource
	private SysSiteService sysSiteService;
    @Resource
    private AreaService areaService;
    @Resource
    private AdsBuilder adsBulider;
	@Resource
	private PropertiesManager propertiesManager;
    @Resource
    private LabelService labelService;
	@Resource
	private RecommendPlanService recommendPlanService;
	@Resource
	private LinetypepricedateService linetypepricedateService;
	@Resource
	private LineImagesService lineImagesService;
	@Resource
	private ScenicInfoService scenicInfoService;
	@Resource
	private HotelService hotelService;

    public void build() {
		List<SysSite> sysSiteList = sysSiteService.findAllSite(new Page(0, Integer.MAX_VALUE));
		for (SysSite sysSite : sysSiteList) {
			buildAds(sysSite);
			buildIndexLine(sysSite);
			buildIndexTicket(sysSite);
			buildIndexSupplier(sysSite);
		}
	}

	public void buildAds(SysSite sysSite) {
		List<SysResourceMap> resourceList = sysResourceMapService.getByDesc(TOP_SCROLL_BANNER);
		List<Ads> topAds = new ArrayList<Ads>();
		SysUser user = new SysUser();
		user.setSysSite(sysSite);
		for (SysResourceMap sysResourceMap : resourceList) {
			Ads condition = new Ads();
			condition.setSysResourceMap(sysResourceMap);
			condition.setUser(user);
			List<Ads> list = adsService.getAdsList(condition, new Page(0, Integer.MAX_VALUE));
			topAds.addAll(list);
		}
		Map<Object, Object> data = new HashMap<Object, Object>();
		data.put("topAds", topAds);
		data.put("imguriPreffix", getImguriPreffix());
		FreemarkerUtil.create(data, INDEX_AD_TOP_SCROLL_BANNER_TEMPLATE,
				INDEX_AD_TOP_SCROLL_BANNER_TARGET.replace("{id}", sysSite.getId().toString()));
	}

	public void buildIndexLine(SysSite sysSite) {
		Map<Object, Object> data = new HashMap<Object, Object>();
		Line lineCondition = new Line();
		lineCondition.setStatus(ProductStatus.UP);
		lineCondition.setLineStatus(LineStatus.show);
		List<Line> list = lineService.findLineBySite(lineCondition, new Page(0, 4), sysSite);
		for (Line line : list) {
			for (Productimage productimage : line.getProductimage()) {
				if (productimage.getCoverFlag()) {
					line.setLineImageUrl(productimage.getPath());
				}
			}
		}
		data.put("lineList", list);
		data.put("imguriPreffix", getImguriPreffix());
		FreemarkerUtil.create(data, INDEX_LINE_TEMPLATE, INDEX_LINE_TARGET.replace("{id}", sysSite.getId().toString()));
	}

	public void buildIndexSupplier(SysSite sysSite) {
		Map<Object, Object> data = new HashMap<Object, Object>();
		List<SysUnit> supplier = sysUnitService.findUnitList(new Page(0, 6), sysSite, false);
		data.put("supplierList", supplier);
		data.put("imguriPreffix", getImguriPreffix());
		FreemarkerUtil.create(data, INDEX_SUPPLIER_TEMPLATE, INDEX_SUPPLIER_TARGET.replace("{id}", sysSite.getId().toString()));
	}

	public void buildIndexTicket(SysSite sysSite) {
		Ticket condition = new Ticket();
		SysUser user = new SysUser();
		user.setSysSite(sysSite);
		condition.setUser(user);
		List<Ticket> list = ticketService.findTicketList(condition, new Page(0, 4));
		for (Ticket ticket : list) {
			for (Productimage productimage : ticket.getProductimage()) {
				if (productimage.getCoverFlag()) {
					ticket.setTicketImgUrl(productimage.getPath());
				}
			}
		}
		Map<Object, Object> data = new HashMap<Object, Object>();
		data.put("ticketList", list);
		data.put("imguriPreffix", getImguriPreffix());
		FreemarkerUtil.create(data, INDEX_TICKET_TEMPLATE, INDEX_TICKET_TARGET.replace("{id}", sysSite.getId().toString()));
	}

	public void buildLXBIndex() {
		Map<Object, Object> data = Maps.newHashMap();
		List<TbArea> destinations = areaService.getHomeHotArea();
        List<Ads> adses = adsBulider.getAds(LVXBANG_INDEX_BANNER);
		List<RecommendPlan> goodRecommendPlans = recommendPlanService.getHomeGoodRecommendPlans();
        data.put("hot", destinations);
		data.put("adses", adses);
		data.put("goodRecommendPlans", goodRecommendPlans);
		Label label = new Label();
//		label.setName("飞机目的地");
//		List<Label> labels = labelService.list(label, null);
//		if (!labels.isEmpty()) {
//			List<TbArea> flightSortAreas = areaService.getTrafficAreas(labels.get(0).getId());
//			List<Map<String, Object>> flightSortMap = sortAreasList(flightSortAreas);
//			List<Map<String, List<Object>>> flightLetterSortAreas = letterSortAreasList(flightSortMap);
//			data.put("flightLetterSortAreas", flightLetterSortAreas);
//			data.put("flightAreaMaps", flightSortMap);
//		}
//
//		label.setName("火车目的地");
//		labels = labelService.list(label, null);
//		if (!labels.isEmpty()) {
//			List<TbArea> trainSortAreas = areaService.getTrafficAreas(labels.get(0).getId());
//			List<Map<String, Object>> trainSortMap = sortAreasList(trainSortAreas);
//			List<Map<String, List<Object>>> trainLetterSortAreas = letterSortAreasList(trainSortMap);
//			data.put("trainLetterSortAreas", trainLetterSortAreas);
//			data.put("trainAreaMaps", trainSortMap);
//		}

        label.setName("通用目的地-国内");
		label.setStatus(LabelStatus.USE);
        List<Label> labels = labelService.list(label, null);
        if (!labels.isEmpty()) {
            List<TbArea> sortAreas = areaService.getTrafficAreas(labels.get(0).getId());
            List<Map<String, Object>> sortMap = sortAreasList(sortAreas);
            List<Map<String, List<Object>>> letterSortAreas = letterSortAreasList(sortMap);
            data.put("letterSortAreas", letterSortAreas);
        }

		data.put("abroadAreas", getAbroadArea());

		Label searchLabel = new Label();
		searchLabel.setName("首页-面板-定制需求-主题定制");
		Label parent = labelService.findUnique(searchLabel);

		data.put("mbztdz", labelService.getAllChildsLabels(parent));

		searchLabel = new Label();
		searchLabel.setName("首页-面板-自助自驾-线路特色");
		parent = labelService.findUnique(searchLabel);

		data.put("mbxlts", labelService.getAllChildsLabels(parent));
		data.put("mbdzjp", panelArea("首页-面板-定制精品"));
		data.put("mbgty", panelArea("首页-面板-跟团游"));

		data.put("dzjp", getLines("首页-定制精品"));
		data.put("zbly", getLines("首页-周边旅游"));
		data.put("gnly", getLines("首页-国内旅游"));
		data.put("cjdx", getLines("首页-出境短线"));
		data.put("cjcx", getLines("首页-出境长线"));
		data.put("yltm", getLines("首页-邮轮特卖"));
		data.put("zjly", getLines("首页-自驾旅游"));
		data.put("zzly", getLines("首页-自助旅游"));
		data.put("jdth", getHotels("首页-酒店特惠"));
		data.put("mptj", getScenics("首页-门票特价"));
		FreemarkerUtil.create(data, LVXBANG_INDEX_TEMPLATE, LVXBANG_INDEX_TARGET);
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

	public Map<String, List<Object>> getLines(String labelName) {
		Label searchLabel = new Label();
		searchLabel.setName(labelName);
		searchLabel.setStatus(LabelStatus.USE);
		Label parent = labelService.findUnique(searchLabel);
		List<Label> labelList = labelService.getAllChildsLabels(parent);
		Map<String, List<Object>> map = Maps.newLinkedHashMap();
		Page page = new Page(1, 6);
		for (Label label : labelList) {
			List<Object> list = Lists.newArrayList();
			List<TbArea> areaList = areaService.findByLabel(label, page);
			List<Line> lineList = lineService.findByLabel(label, page);
			list.add(areaList);
			list.add(completeLinePrice(lineList));
			map.put(label.getAlias(), list);
		}
		return map;
	}

	public Map<String, List<Object>> getHotels(String labelName) {
		Label searchLabel = new Label();
		searchLabel.setName(labelName);
		Label parent = labelService.findUnique(searchLabel);
		List<Label> labelList = labelService.getAllChildsLabels(parent);
		Map<String, List<Object>> map = Maps.newLinkedHashMap();
		Page page = new Page(1, 6);
		for (Label label : labelList) {
			List<Object> list = Lists.newArrayList();
			List<TbArea> areaList = areaService.findByLabel(label, page);
			List<Hotel> hotelList = hotHotel();
			list.add(areaList);
			list.add(hotelList);
			map.put(label.getAlias(), list);
		}
		return map;
	}

	public List<Hotel> hotHotel() {
		Label searchLabel = new Label();
		searchLabel.setName("热门酒店");
		Label label = labelService.findUnique(searchLabel);
		List<Hotel> list = hotelService.getHotAreaHotel(label, null, new Page(1, 6));
		if (list.size() < 6) {
			List<Long> hotelIdList = Lists.transform(list, new Function<Hotel, Long>() {
				@Override
				public Long apply(Hotel hotel) {
					return hotel.getId();
				}
			});
			Hotel hotelCondition = new Hotel();
			hotelCondition.setPrice(-1f);
			hotelCondition.setStatus(ProductStatus.UP);
			List<Hotel> additionalHotels = hotelService.listWithoutCount(hotelCondition, new Page(1, 6), "score", "desc");
			for (Hotel additionalHotel : additionalHotels) {
				if (list.size() >= 6) {
					break;
				}
				if (!hotelIdList.contains(additionalHotel.getId())) {
					list.add(additionalHotel);
				}
			}
		}
		return list;
	}

	public Map<String, List<Object>> getScenics(String labelName) {
		Label searchLabel = new Label();
		searchLabel.setName(labelName);
		Label parent = labelService.findUnique(searchLabel);
		List<Label> labelList = labelService.getAllChildsLabels(parent);
		Map<String, List<Object>> map = Maps.newLinkedHashMap();
		Page page = new Page(1, 6);
		for (Label label : labelList) {
			List<Object> list = Lists.newArrayList();
			List<TbArea> areaList = areaService.findByLabel(label, page);
			List<ScenicInfo> scenicList = scenicInfoService.findIndexScenic(page);
			list.add(areaList);
			list.add(scenicList);
			map.put(label.getAlias(), list);
		}
		return map;
	}

	public Map<String, Object> panelArea(String labelName) {
		Label searchLabel = new Label();
		searchLabel.setName(labelName);
		searchLabel.setStatus(LabelStatus.USE);
		Label parent = labelService.findUnique(searchLabel);
		List<Label> labelList = labelService.getAllChildsLabels(parent);
		Map<String, Object> map = Maps.newLinkedHashMap();
		Page page = new Page(1, 4);
		for (Label label : labelList) {
			List<TbArea> areaList = areaService.findByLabel(label, page);
			map.put(label.getAlias(), areaList);
		}
		return map;
	}

	public List<Line> completeLinePrice(List<Line> lineList) {
		List<Line> list = Lists.newArrayList();
		for (Line line : lineList) {
			completeLinePrice(line, list);
		}
		for (Line line : list) {
			List<LineImages> images = lineImagesService.listImagesByLineId(line.getId(), LineImageType.home);
			if (!images.isEmpty()) {
				line.setCover(images.get(0).getImageUrl());
			}
		}
		return list;
	}

	public void completeLinePrice(Line line, List<Line> list) {
		List<Linetypeprice> linetypeprices = Lists.newArrayList(line.getLinetypeprices());
		List<Long> typepriceIds = Lists.transform(linetypeprices, new Function<Linetypeprice, Long>() {
			@Override
			public Long apply(Linetypeprice input) {
				return input.getId();
			}
		});
		Date date = new Date();
		Float min = Float.MAX_VALUE;
		for (Long typepriceId : typepriceIds) {
			List<Linetypepricedate> linetypepricedates = linetypepricedateService.findTypePriceDate(typepriceId, date, null);
			for (Linetypepricedate linetypepricedate : linetypepricedates) {
				min = Math.min(min, linetypepricedate.getDiscountPrice() + linetypepricedate.getRebate());
			}
			if (!min.equals(Float.MAX_VALUE)) {
				line.setPrice(min);
				list.add(line);
				return;
			}
		}
		line.setPrice(0f);
		list.add(line);
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

    /**
     * 城市字母分类
     * @param sortMap
     * @return
     */
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

        // 将中文转换为英文
    @SuppressWarnings("deprecation")
	public static String getEname(String name) {
        HanyuPinyinOutputFormat pyFormat = new HanyuPinyinOutputFormat();
        pyFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        pyFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        pyFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
        try {
        	String pinyinName = PinyinHelper.toHanyuPinyinString(name, pyFormat, "");
        	pinyinName = pinyinName.substring(0, 1);
            return pinyinName;
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }
        return null;
    }
	// 姓、名的第一个字母需要为大写
    public static String getUpEname(String name) {
        char[] strs = name.toCharArray();
        String newname = null;
        if (strs.length == 2) {
            newname = toUpCase(getEname("" + strs[0])) + " "
                    + toUpCase(getEname("" + strs[1]));
        } else if (strs.length == 3) {
            newname = toUpCase(getEname("" + strs[0])) + " "
                    + toUpCase(getEname("" + strs[1] + strs[2]));
        } else if (strs.length == 4) {
            newname = toUpCase(getEname("" + strs[0] + strs[1])) + " "
                    + toUpCase(getEname("" + strs[2] + strs[3]));
        } else {
            newname = toUpCase(getEname(name));
        }
        return newname;
    }
     // 首字母大写
     private static String toUpCase(String str) {
         StringBuffer newstr = new StringBuffer();
         newstr.append(str.substring(0, 1).toUpperCase()).append(
             str.substring(1, str.length()));
         return newstr.toString();
     }

	public String getImguriPreffix() {
        return propertiesManager.getString("mall.imguri.preffix");
	}

}
