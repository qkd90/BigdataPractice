package com.data.data.hmly.service.build.builder;

import com.data.data.hmly.service.LabelService;
import com.data.data.hmly.service.SysSiteService;
import com.data.data.hmly.service.TbAreaService;
import com.data.data.hmly.service.area.AreaService;
import com.data.data.hmly.service.build.util.FreemarkerUtil;
import com.data.data.hmly.service.common.entity.Productimage;
import com.data.data.hmly.service.common.entity.enums.ProductStatus;
import com.data.data.hmly.service.entity.Ads;
import com.data.data.hmly.service.entity.Label;
import com.data.data.hmly.service.entity.SysSite;
import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.line.LineDaysPlanInfoService;
import com.data.data.hmly.service.line.LineDepartureService;
import com.data.data.hmly.service.line.LineImagesService;
import com.data.data.hmly.service.line.LineService;
import com.data.data.hmly.service.line.LinedaysService;
import com.data.data.hmly.service.line.LineexplainService;
import com.data.data.hmly.service.line.LinetypepriceService;
import com.data.data.hmly.service.line.LinetypepricedateService;
import com.data.data.hmly.service.line.entity.Line;
import com.data.data.hmly.service.line.entity.LineImages;
import com.data.data.hmly.service.line.entity.Linedays;
import com.data.data.hmly.service.line.entity.Linedaysplan;
import com.data.data.hmly.service.line.entity.Lineexplain;
import com.data.data.hmly.service.line.entity.Linetypeprice;
import com.data.data.hmly.service.line.entity.Linetypepricedate;
import com.data.data.hmly.service.line.entity.enums.LineImageType;
import com.data.data.hmly.service.line.entity.enums.LineStatus;
import com.data.data.hmly.service.plan.RecommendPlanService;
import com.data.data.hmly.service.plan.entity.RecommendPlan;
import com.data.data.hmly.service.scenic.ScenicInfoService;
import com.data.data.hmly.service.scenic.entity.ScenicInfo;
import com.framework.hibernate.util.Page;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zuipin.util.PropertiesManager;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guoshijie on 2015/10/21.
 */
@Component
public class LineBuilder {

    //定制精品首页
    private static final String LINE_CUSTOM_TOUR_INDEX_TEMPLATE = "/lvxbang/line/custom_tour_index.ftl";
    private static final String LINE_CUSTOM_TOUR_INDEX_TARGET = "/lvxbang/line/custom_tour_index.htm";
    //跟团游首页
    private static final String LINE_GROUP_TOUR_INDEX_TEMPLATE = "/lvxbang/line/group_tour_index.ftl";
    private static final String LINE_GROUP_TOUR_INDEX_TARGET = "/lvxbang/line/group_tour_index.htm";
    //自助游首页
    private static final String LINE_SELF_TOUR_INDEX_TEMPLATE = "/lvxbang/line/self_tour_index.ftl";
    private static final String LINE_SELF_TOUR_INDEX_TARGET = "/lvxbang/line/self_tour_index.htm";
    //自驾游首页
    private static final String LINE_DRIVE_TOUR_INDEX_TEMPLATE = "/lvxbang/line/drive_tour_index.ftl";
    private static final String LINE_DRIVE_TOUR_INDEX_TARGET = "/lvxbang/line/drive_tour_index.htm";


	private static final String LINE_SEARCHER_TEMPLATE = "/line/searcher.ftl";
	private static final String LINE_SEARCHER_TARGET = "/line/%d/searcher.htm";
	private static final String LINE_DETAIL_TEMPLATE = "/line/detail.ftl";
	private static final String LINE_DETAIL_TARGET = "/line/detail{id}.htm";

	private static final String LVXBANG_LINE_DETAIL_TEMPLATE = "/lvxbang/line/detail.ftl";
	private static final String LVXBANG_LINE_DETAIL_TARGET = "/lvxbang/line/detail%d.htm";
	private static final String LVXBANG_LINE_HEAD_TEMPLATE = "/lvxbang/line/head.ftl";
	private static final String LVXBANG_LINE_HEAD_TARGET = "/lvxbang/line/head%d.htm";


    @Resource
    private LabelService labelService;
	@Resource
	private LineService lineService;
	@Resource
	private SysSiteService siteService;
	@Resource
	private LineexplainService lineexplainService;
	@Resource
	private LinedaysService linedaysService;
	@Resource
	private LinetypepriceService linetypepriceService;
	@Resource
	private TbAreaService tbAreaService;
	@Resource
	private PropertiesManager propertiesManager;
	@Resource
	private AreaService areaService;
	@Resource
	private RecommendPlanService recommendPlanService;
	@Resource
	private ScenicInfoService scenicInfoService;
	@Resource
	private LineDaysPlanInfoService lineDaysPlanInfoService;
	@Resource
	private LineImagesService lineImagesService;
	@Resource
	private LinetypepricedateService linetypepricedateService;
	@Resource
	private LineDepartureService lineDepartureService;

    public void build() {
		buildSearcher();
		buildDetail();
	}

	public void buildOne(Long id) {
		Line line = lineService.loadLine(id);
		buildDetail(line);
	}


    // 线路各个首页广告位
    // 定制精品首页顶部左侧logo_320x50 lvxbang_line_cti_top_left_logo
    private static final String LVXBANG_LINE_CTI_TOP_LEFT_LOGO = "lvxbang_line_cti_top_left_logo";
    // 跟团游首页顶部左侧logo_320x50 lvxbang_line_gti_top_left_logo
    private static final String LVXBANG_LINE_GTI_TOP_LEFT_LOGO = "lvxbang_line_gti_top_left_logo";
    // 自助自驾游首页顶部左侧logo_320x50 lvxbang_line_sti_top_left_logo
    private static final String LVXBANG_LINE_STI_TOP_LEFT_LOGO = "lvxbang_line_sti_top_left_logo";
    // 定制精品首页顶部幻灯广告 lvxbang_line_cti_top_banner
    private static final String LVXBANG_LINE_CTI_TOP_BANNER = "lvxbang_line_cti_top_banner";
    // 跟团游首页顶部幻灯广告 lvxbang_line_gti_top_banner
    private static final String LVXBANG_LINE_GTI_TOP_BANNER = "lvxbang_line_gti_top_banner";
    // 自助自驾游首页顶部幻灯广告 lvxbang_line_sti_top_banner
    private static final String LVXBANG_LINE_STI_TOP_BANNER = "lvxbang_line_sti_top_banner";


    @Resource
    private AdsBuilder adsBuilder;

    /**
     * 定制精品首页生成
     */
    public void buildCustomTourIndex() {
        Map<Object, Object> data = new HashMap<Object, Object>();
        // 定制精品首页顶部左侧logo广告位
        List<Ads> topLeftLogoAdList = adsBuilder.getAds(LVXBANG_LINE_CTI_TOP_LEFT_LOGO);
        Ads topLeftLogoAd = null;
        if (!topLeftLogoAdList.isEmpty()) {
            topLeftLogoAd = topLeftLogoAdList.get(0);
        }
        // 获取定制精品首页顶部左侧导航数据
        this.getCustomToueIndexTopNavData(data);
        // 定制精品首页顶部幻灯广告
        List<Ads> topAds = adsBuilder.getAds(LVXBANG_LINE_CTI_TOP_BANNER);
        // 获取定制精品首页主版块周边自助自驾数据
        this.getCustomTourIndexMainQinziData(data);
        // 获取定制精品首页主版块国内自助自驾数据
        this.getCustomTourIndexMainMiyueData(data);
        // 获取定制精品首页主版块出境自助自驾数据
        this.getCustomTourIndexMainChujingData(data);

        // 定制精品首页游记攻略
        List<RecommendPlan> mainYoujigonglue = recommendPlanService.getRecommendPlanByLabel("定制精品-游记攻略", new Page(1, 6));
        // 定制精品首页热销排行
        List<Line> rexiaoLine = lineService.getLineIndexLine("定制精品-热销排行", new Page(1, 5));


        data.put("ctiTopLeftLogoAd", topLeftLogoAd);
        data.put("topAds", topAds);
        data.put("mainYoujigonglue", mainYoujigonglue);
        data.put("rexiaoLine", rexiaoLine);

        FreemarkerUtil.create(data, LINE_CUSTOM_TOUR_INDEX_TEMPLATE, LINE_CUSTOM_TOUR_INDEX_TARGET);
    }

    /**
     * 获取定制精品首页顶部左侧导航数据
     *
     * @param data
     */
    private void getCustomToueIndexTopNavData(Map<Object, Object> data) {
        // 顶级导航标签列表
        Label condition = new Label();
        condition.setName("定制精品-面板");
        Label topNavParentLabel = labelService.findUnique(condition);
        List<Label> topNavLabelList = labelService.getAllChildsLabels(topNavParentLabel);
//        List<Label> topNavLabelList = labelService.findParentLabelListByName("TOP定制精品首页左侧一级导航");
        Map<Object, Object> ctiTopNavData = new HashMap<Object, Object>();
        for (Label topLabel : topNavLabelList) {
            // 一级导航目的地列表
//            List<TbArea> firstAreaList = areaService.getLineIndexArea(topLabel.getName().substring(3) + "左侧", new Page(1, 8));
            List<TbArea> firstAreaList = areaService.getLineIndexArea(topLabel.getName(), new Page(1, 8));
            ctiTopNavData.put(topLabel.getName() + "_fst_area", firstAreaList);
            // 二级导航标签列表
            List<Label> secondNavLabelList = labelService.getAllChildsLabels(topLabel);
            ctiTopNavData.put(topLabel.getName() + "_snd_label", secondNavLabelList);
            for (Label secondLabel : secondNavLabelList) {
                // 三级导航目的地列表
                List<TbArea> thirdAreaList = areaService.getLineIndexArea(secondLabel.getName(), new Page(1, 15));
                ctiTopNavData.put(secondLabel.getName() + "_trd_area", thirdAreaList);
            }
        }
        data.put("topNavLabelList", topNavLabelList);
        data.put("ctiTopNavData", ctiTopNavData);

    }

    /**
     * 获取定制精品首页主版块//家庭亲子//数据
     *
     * @param data
     */
    private void getCustomTourIndexMainQinziData(Map<Object, Object> data) {
        Label condition = new Label();
        condition.setName("定制精品-家庭亲子");
        Label ctiQinziLabel = labelService.findUnique(condition);
        List<Label> ctiMainQinziLabelList = labelService.getAllChildsLabels(ctiQinziLabel);
        Map<Object, Object> ctiQinziData = new HashMap<Object, Object>();
        for (Label label : ctiMainQinziLabelList) {
            List<TbArea> areaList = areaService.getLineIndexArea(label.getName(), new Page(1, 6));
            List<Line> lineList = lineService.getLineIndexLine(label.getName(), new Page(1, 6));
            ctiQinziData.put(label.getName() + "_area", areaList);
            ctiQinziData.put(label.getName() + "_line", lineList);
        }
        data.put("ctiMainQinziLabelList", ctiMainQinziLabelList);
        data.put("ctiQinziData", ctiQinziData);
    }

    /**
     * 获取定制精品首页主版块//蜜月摄影//数据
     *
     * @param data
     */
    private void getCustomTourIndexMainMiyueData(Map<Object, Object> data) {
        Label condition = new Label();
        condition.setName("定制精品-蜜月摄影");
        Label ctiMiyueLabel = labelService.findUnique(condition);
        List<Label> ctiMainMiyueLabelList = labelService.getAllChildsLabels(ctiMiyueLabel);
        Map<Object, Object> ctiMiyueData = new HashMap<Object, Object>();
        for (Label label : ctiMainMiyueLabelList) {
            List<TbArea> areaList = areaService.getLineIndexArea(label.getName(), new Page(1, 6));
            List<Line> lineList = lineService.getLineIndexLine(label.getName(), new Page(1, 6));
            ctiMiyueData.put(label.getName() + "_area", areaList);
            ctiMiyueData.put(label.getName() + "_line", lineList);
        }
        data.put("ctiMainMiyueLabelList", ctiMainMiyueLabelList);
        data.put("ctiMiyueData", ctiMiyueData);
    }

    /**
     * 获取定制精品首页主版块出境自助自驾数据
     *
     * @param data
     */
    private void getCustomTourIndexMainChujingData(Map<Object, Object> data) {
        Label condition = new Label();
        condition.setName("定制精品-出境定制");
        Label ctiChujingLabel = labelService.findUnique(condition);
        List<Label> ctiMainChujingLabelList = labelService.getAllChildsLabels(ctiChujingLabel);
        Map<Object, Object> ctiChujingData = new HashMap<Object, Object>();
        for (Label label : ctiMainChujingLabelList) {
            List<TbArea> areaList = areaService.getLineIndexArea(label.getName(), new Page(1, 6));
            List<Line> lineList = lineService.getLineIndexLine(label.getName(), new Page(1, 6));
            ctiChujingData.put(label.getName() + "_area", areaList);
            ctiChujingData.put(label.getName() + "_line", lineList);
        }
        data.put("ctiMainChujingLabelList", ctiMainChujingLabelList);
        data.put("ctiChujingData", ctiChujingData);
    }


    /**
     * 自助自驾游首页生成
     */
    public void buildSelfTourIndex() {
        Map<Object, Object> data = new HashMap<Object, Object>();

        // 自助自驾游首页顶部左侧logo广告位
        List<Ads> topLeftLogoAdList = adsBuilder.getAds(LVXBANG_LINE_STI_TOP_LEFT_LOGO);
        Ads topLeftLogoAd = null;
        if (!topLeftLogoAdList.isEmpty()) {
            topLeftLogoAd = topLeftLogoAdList.get(0);
        }

        // 获取自助自驾游顶部左侧导航数据
        this.getSelfTourIndexTopNavData(data);

        // 自助自驾游首页顶部幻灯广告
        List<Ads> topAds = adsBuilder.getAds(LVXBANG_LINE_STI_TOP_BANNER);

        // 获取自助自驾游首页主版块周边自助自驾数据
        this.getSelfTourIndexMainZhoubianData(data);
        // 获取自助自驾游首页主版块国内自助自驾数据
        this.getSelfTourIndexMainGuoneiData(data);
        // 获取自助自驾首页主版块出境自助自驾数据
        this.getSelfTourIndexMainChujingData(data);

        // 自助自驾游首页游记攻略
        List<RecommendPlan> mainYoujigonglue = recommendPlanService.getRecommendPlanByLabel("自助自驾-游记攻略", new Page(1, 6));
        // 自助自驾游首页热销排行
        List<Line> rexiaoLine = lineService.getLineIndexLine("自助自驾-热销排行", new Page(1, 5));

        data.put("stiTopLeftLogoAd", topLeftLogoAd);
        data.put("topAds", topAds);
        data.put("mainYoujigonglue", mainYoujigonglue);
        data.put("rexiaoLine", rexiaoLine);

        FreemarkerUtil.create(data, LINE_SELF_TOUR_INDEX_TEMPLATE, LINE_SELF_TOUR_INDEX_TARGET);
    }

    /**
     * 获取自助自驾游首页顶部左侧导航数据
     * @param data
     */
    private void getSelfTourIndexTopNavData(Map<Object, Object> data) {
        // 顶级导航标签列表
        Label condition = new Label();
        condition.setName("自助自驾-面板");
        Label topNavParentLabel = labelService.findUnique(condition);
        List<Label> topNavLabelList = labelService.getAllChildsLabels(topNavParentLabel);
//        List<Label> topNavLabelList = labelService.findParentLabelListByName("TOP自助自驾游首页左侧一级导航");
        Map<Object, Object> stiTopNavData = new HashMap<Object, Object>();
        for (Label topLabel : topNavLabelList) {
            // 一级导航目的地列表
            List<TbArea> firstAreaList = areaService.getLineIndexArea(topLabel.getName(), new Page(1, 8));
//            List<TbArea> firstAreaList = areaService.getLineIndexArea(topLabel.getName().substring(3) + "左侧", new Page(1, 8));
            stiTopNavData.put(topLabel.getName() + "_fst_area", firstAreaList);
            // 二级导航标签列表
            List<Label> secondNavLabelList = labelService.getAllChildsLabels(topLabel);
            stiTopNavData.put(topLabel.getName() + "_snd_label", secondNavLabelList);
            for (Label secondLabel : secondNavLabelList) {
                // 三级导航目的地列表
                List<TbArea> thirdAreaList = areaService.getLineIndexArea(secondLabel.getName(), new Page(1, 15));
                stiTopNavData.put(secondLabel.getName() + "_trd_area", thirdAreaList);
            }
        }
        data.put("topNavLabelList", topNavLabelList);
        data.put("stiTopNavData", stiTopNavData);
    }

    /**
     * 获取自助自驾游首页主版块周边自助自驾数据
     * @param data
     */
    private void getSelfTourIndexMainZhoubianData(Map<Object, Object> data) {
        Label condition = new Label();
        condition.setName("自助自驾-周边自助自驾");
        Label stiZhoubianLabel = labelService.findUnique(condition);
        List<Label> stiMainZhoubianLabelList = labelService.getAllChildsLabels(stiZhoubianLabel);
        Map<Object, Object> stiZhoubianData = new HashMap<Object, Object>();
        for (Label label : stiMainZhoubianLabelList) {
            List<TbArea> areaList = areaService.getLineIndexArea(label.getName(), new Page(1, 6));
            List<Line> lineList = lineService.getLineIndexLine(label.getName(), new Page(1, 6));
            stiZhoubianData.put(label.getName() + "_area", areaList);
            stiZhoubianData.put(label.getName() + "_line", lineList);
        }
        data.put("stiMainZhoubianLabelList", stiMainZhoubianLabelList);
        data.put("stiZhoubianData", stiZhoubianData);
    }

    /**
     * 获取自助自驾游首页主版块国内自助自驾数据
     * @param data
     */
    private void getSelfTourIndexMainGuoneiData(Map<Object, Object> data) {
        Label condition = new Label();
        condition.setName("自助自驾-国内自助自驾");
        Label stiGuoneiLabel = labelService.findUnique(condition);
        List<Label> stiMainGuoneiLabelList = labelService.getAllChildsLabels(stiGuoneiLabel);
        Map<Object, Object> stiGuoneiData = new HashMap<Object, Object>();
        for (Label label : stiMainGuoneiLabelList) {
            List<TbArea> areaList = areaService.getLineIndexArea(label.getName(), new Page(1, 6));
            List<Line> lineList = lineService.getLineIndexLine(label.getName(), new Page(1, 6));
            stiGuoneiData.put(label.getName() + "_area", areaList);
            stiGuoneiData.put(label.getName() + "_line", lineList);
        }
        data.put("stiMainGuoneiLabelList", stiMainGuoneiLabelList);
        data.put("stiGuoneiData", stiGuoneiData);
    }

    /**
     * 获取自助自驾游首页主版块出境自助自驾数据
     * @param data
     */
    private void getSelfTourIndexMainChujingData(Map<Object, Object> data) {
        Label condition = new Label();
        condition.setName("自助自驾-出境自助自驾");
        Label stiChujingLabel = labelService.findUnique(condition);
        List<Label> stiMainChujingLabelList = labelService.getAllChildsLabels(stiChujingLabel);
        Map<Object, Object> stiChujingData = new HashMap<Object, Object>();
        for (Label label : stiMainChujingLabelList) {
            List<TbArea> areaList = areaService.getLineIndexArea(label.getName(), new Page(1, 6));
            List<Line> lineList = lineService.getLineIndexLine(label.getName(), new Page(1, 6));
            stiChujingData.put(label.getName() + "_area", areaList);
            stiChujingData.put(label.getName() + "_line", lineList);
        }
        data.put("stiMainChujingLabelList", stiMainChujingLabelList);
        data.put("stiChujingData", stiChujingData);
    }


    /**
     * 跟团游首页生成
     */
    public void buildGroupTourIndex() {
        Map<Object, Object> data = new HashMap<Object, Object>();
        // 跟团游首页顶部左侧logo广告位
        List<Ads> topLeftLogoAdList = adsBuilder.getAds(LVXBANG_LINE_GTI_TOP_LEFT_LOGO);
        Ads topLeftLogoAd = null;
        if (!topLeftLogoAdList.isEmpty()) {
            topLeftLogoAd = topLeftLogoAdList.get(0);
        }
        // 获取跟团游首页顶部左侧导航数据
        this.getGroupTourIndexTopNavData(data);

        // 跟团游首页顶部幻灯广告
        List<Ads> topAds = adsBuilder.getAds(LVXBANG_LINE_GTI_TOP_BANNER);

        // 获取主版块周边游数据
        this.getGroupTourIndexMainZhoubianData(data);

        // 获取主版块国内跟团游数据
        this.getGroupTourIndexMainGuoneiData(data);

        // 获取跟团游首页主版块出境短线数据
        this.getGroupTourIndexMainChujingDData(data);
//
        // 获取跟团游首页主版块出境长线数据
        this.getGroupTourIndexMainChujingCData(data);

        // 跟团游首页游记攻略
        List<RecommendPlan> mainYoujigonglue = recommendPlanService.getRecommendPlanByLabel("跟团游-游记攻略", new Page(1, 6));
        // 跟团游首页热销排行
        List<Line> rexiaoLine = lineService.getLineIndexLine("跟团游-热销排行", new Page(1, 5));

        data.put("gtiTopLeftLogoAd", topLeftLogoAd);
        data.put("topAds", topAds);
        data.put("mainYoujigonglue", mainYoujigonglue);
        data.put("rexiaoLine", rexiaoLine);

        FreemarkerUtil.create(data, LINE_GROUP_TOUR_INDEX_TEMPLATE, LINE_GROUP_TOUR_INDEX_TARGET);
    }

    /**
     *  获取跟团游首页顶部左侧导航数据
     * @param data
     */
    private void getGroupTourIndexTopNavData(Map<Object, Object> data) {
        // 顶级导航标签列表
        // 顶级导航标签列表
        Label condition = new Label();
        condition.setName("跟团游-面板");
        Label topNavParentLabel = labelService.findUnique(condition);
        List<Label> topNavLabelList = labelService.getAllChildsLabels(topNavParentLabel);
//        List<Label> topNavLabelList = labelService.findParentLabelListByName("TOP跟团游首页左侧一级导航");
        Map<Object, Object> gtiTopNavData = new HashMap<Object, Object>();
        for (Label topLabel : topNavLabelList) {
            // 一级导航目的地列表
            List<TbArea> firstAreaList = areaService.getLineIndexArea(topLabel.getName(), new Page(1, 8));
            gtiTopNavData.put(topLabel.getName() + "_fst_area", firstAreaList);
            // 二级导航标签列表
            List<Label> secondNavLabelList = labelService.getAllChildsLabels(topLabel);
            gtiTopNavData.put(topLabel.getName() + "_snd_label", secondNavLabelList);
            for (Label secondLabel : secondNavLabelList) {
                // 三级导航目的地列表
                List<TbArea> thirdAreaList = areaService.getLineIndexArea(secondLabel.getName(), new Page(1, 15));
                gtiTopNavData.put(secondLabel.getName() + "_trd_area", thirdAreaList);
            }
        }
        data.put("topNavLabelList", topNavLabelList);
        data.put("gtiTopNavData", gtiTopNavData);
    }

    /**
     * 获取跟团游首页主版块周边游数据
     * @param data
     */
    private void getGroupTourIndexMainZhoubianData(Map<Object, Object> data) {
        Label condition = new Label();
        condition.setName("跟团游-周边跟团游");
        Label gtiZhoubianLabel = labelService.findUnique(condition);
        List<Label> gtiMainZhoubianLabelList = labelService.getAllChildsLabels(gtiZhoubianLabel);
        Map<Object, Object> gtiZhoubianData = new HashMap<Object, Object>();
        for (Label label : gtiMainZhoubianLabelList) {
            List<TbArea> areaList = areaService.getLineIndexArea(label.getName(), new Page(1, 6));
            List<Line> lineList = lineService.getLineIndexLine(label.getName(), new Page(1, 6));
            gtiZhoubianData.put(label.getName() + "_area", areaList);
            gtiZhoubianData.put(label.getName() + "_line", lineList);
        }
        data.put("gtiMainZhoubianLabelList", gtiMainZhoubianLabelList);
        data.put("gtiZhoubianData", gtiZhoubianData);
    }

    /**
     * 获取跟团游首页主版块国内跟团游数据
     * @param data
     */
    private void getGroupTourIndexMainGuoneiData(Map<Object, Object> data) {
        Label condition = new Label();
        condition.setName("跟团游-国内跟团游");
        Label gtiGuoneiLabel = labelService.findUnique(condition);
        List<Label> gtiMainGuoneiLabelList = labelService.getAllChildsLabels(gtiGuoneiLabel);
        Map<Object, Object> gtiGuoneiData = new HashMap<Object, Object>();
        for (Label label : gtiMainGuoneiLabelList) {
            List<TbArea> areaList = areaService.getLineIndexArea(label.getName(), new Page(1, 6));
            List<Line> lineList = lineService.getLineIndexLine(label.getName(), new Page(1, 6));
            gtiGuoneiData.put(label.getName() + "_area", areaList);
            gtiGuoneiData.put(label.getName() + "_line", lineList);
        }
        data.put("gtiMainGuoneiLabelList", gtiMainGuoneiLabelList);
        data.put("gtiGuoneiData", gtiGuoneiData);
    }

    /**
     * 获取跟团游首页主版块出境短线数据
     * @param data
     */
    private void getGroupTourIndexMainChujingDData(Map<Object, Object> data) {
        Label condition = new Label();
        condition.setName("跟团游-出境短线");
        Label gtiChujingDLabel = labelService.findUnique(condition);
        List<Label> gtiMainChujingDLabelList = labelService.getAllChildsLabels(gtiChujingDLabel);
        Map<Object, Object> gtiChujingDData = new HashMap<Object, Object>();
        for (Label label : gtiMainChujingDLabelList) {
            List<TbArea> areaList = areaService.getLineIndexArea(label.getName(), new Page(1, 6));
            List<Line> lineList = lineService.getLineIndexLine(label.getName(), new Page(1, 6));
            gtiChujingDData.put(label.getName() + "_area", areaList);
            gtiChujingDData.put(label.getName() + "line", lineList);
        }
        data.put("gtiMainChujingDLabelList", gtiMainChujingDLabelList);
        data.put("gtiChujingDData", gtiChujingDData);
    }

    /**
     * 获取跟团游首页主版块出境长线数据
     * @param data
     */
    private void getGroupTourIndexMainChujingCData(Map<Object, Object> data) {
        Label condition = new Label();
        condition.setName("跟团游-出境长线");
        Label gtiChujingCLabel = labelService.findUnique(condition);
        List<Label> gtiMainChujingCLabelList = labelService.getAllChildsLabels(gtiChujingCLabel);
        Map<Object, Object> gtiChujingCData = new HashMap<Object, Object>();
        for (Label label : gtiMainChujingCLabelList) {
            List<TbArea> areaList = areaService.getLineIndexArea(label.getName(), new Page(1, 6));
            List<Line> lineList = lineService.getLineIndexLine(label.getName(), new Page(1, 6));
            gtiChujingCData.put(label.getName() + "_area", areaList);
            gtiChujingCData.put(label.getName() + "_line", lineList);
        }
        data.put("gtiMainChujingCLabelList", gtiMainChujingCLabelList);
        data.put("gtiChujingCData", gtiChujingCData);
    }

    /**
     * 自驾游首页生成
     */
    @Deprecated
    public void buildDriveTourIndex() {
        Map<Object, Object> data = new HashMap<Object, Object>();
        FreemarkerUtil.create(data, LINE_DRIVE_TOUR_INDEX_TEMPLATE, LINE_DRIVE_TOUR_INDEX_TARGET);
    }

	public void buildDetail() {
		Line lineCondition = new Line();
		lineCondition.setStatus(ProductStatus.UP);
		lineCondition.setLineStatus(LineStatus.show);
		List<SysSite> sites = siteService.findAllSite(new Page(0, Integer.MAX_VALUE));
		for (SysSite sysSite : sites) {
			List<Line> list = lineService.findLineBySite(lineCondition, new Page(0, Integer.MAX_VALUE), sysSite);
			for (Line line : list) {
				buildDetail(line);
			}
		}
	}

	public void buildDetail(Line line) {
		Map<Object, Object> data = new HashMap<Object, Object>();
		data.put("line", line);

		Linetypeprice linetypepriceCondition = new Linetypeprice();
		if (line.getTopProduct() == null) {
			linetypepriceCondition.setLine(line);
		} else {
			linetypepriceCondition.setLine((Line) line.getTopProduct());
		}
		List<Linetypeprice> linetypepriceList = linetypepriceService.findLinetypepriceList(linetypepriceCondition);
		data.put("priceTypeList", linetypepriceList);
		if (!linetypepriceList.isEmpty()) {
			linetypepriceList.get(0).getLinetypepricedate();
		}

		TbArea tbArea = tbAreaService.getById(line.getStartCityId());
		data.put("startCity", tbArea);

		Linedays linedaysCondition = new Linedays();
		linedaysCondition.setLineId(line.getId());
		List<Linedays> linedaysList = linedaysService.list(linedaysCondition, null);
		data.put("lineDayList", linedaysList);

		Lineexplain lineexplain = lineexplainService.getByLine(line.getId());
		data.put("lineExplain", lineexplain);

		for (Productimage productimage : line.getProductimage()) {
			if (productimage.getCoverFlag()) {
				line.setLineImageUrl(productimage.getPath());
			}
		}
		data.put("imguriPreffix", getImguriPreffix());

		FreemarkerUtil.create(data, LINE_DETAIL_TEMPLATE, LINE_DETAIL_TARGET.replace("{id}", line.getId().toString()));
	}

	public void buildLXBDetail(Long id) {
		Line line = lineService.loadLine(id);
		Map<Object, Object> data = new HashMap<Object, Object>();
        List<Line> lines = completeLinePrice(Lists.newArrayList(line));
        data.put("line", line);
        if (!lines.isEmpty()) {
            data.put("linePrice", lines.get(0).getPrice());
        }

        data.put("lineImages", lineImagesService.listImagesByLineId(line.getId(), LineImageType.home));

		Linetypeprice linetypepriceCondition = new Linetypeprice();
		if (line.getTopProduct() == null) {
			linetypepriceCondition.setLine(line);
		} else {
			linetypepriceCondition.setLine((Line) line.getTopProduct());
		}
		List<Linetypeprice> linetypepriceList = linetypepriceService.findLinetypepriceList(linetypepriceCondition);
		data.put("priceTypeList", linetypepriceList);
		if (!linetypepriceList.isEmpty()) {
			linetypepriceList.get(0).getLinetypepricedate();
		}

		TbArea startCity = tbAreaService.getById(line.getStartCityId());
		data.put("startCity", startCity);

        if (startCity != null) {
            data.put("startCityList", startCityList(line, startCity));
        }

		TbArea arriveCity = tbAreaService.getById(line.getArriveCityId());
		data.put("arriveCity", arriveCity);

		data.put("lineDeparture", lineDepartureService.getDepartureWithInfoByLine(line));

		RecommendPlan recommendPlan = new RecommendPlan();
		recommendPlan.setCity(arriveCity);
		recommendPlan.setStatus(2);
		List<RecommendPlan> hotRecommendPlan = recommendPlanService.list(recommendPlan, new Page(1, 5), "viewNum", "desc");
		data.put("hotRecommendPlan", hotRecommendPlan);

		List<Line> hotLine = lineService.getAreaHotLine(line.getArriveCityId(), new Page(1, 5));
		data.put("hotLine", completeLinePrice(hotLine));

		List<Line> sameCompanyLine = lineService.getSameCompanyLine(line);
		data.put("sameCompanyLine", sameCompanyLine);

		List<Line> recommendLine = lineService.getAreaHotLine(line.getArriveCityId(), line.getProductAttr(), new Page(1, 5));
		data.put("recommendLine", completeLinePrice(recommendLine));

		data.put("hotScenicInfo", hotScenicInfo(line));

		Linedays linedaysCondition = new Linedays();
		linedaysCondition.setLineId(line.getId());
		List<Linedays> linedaysList = linedaysService.list(linedaysCondition, null);
		for (Linedays linedays : linedaysList) {
			for (Linedaysplan linedaysplan : linedays.getLinedaysplan()) {
				linedaysplan.setPlanInfoList(lineDaysPlanInfoService.listByLineDaysPlanId(linedaysplan.getId()));
				linedaysplan.setPlanInfoImagesList(lineImagesService.listByLineDaysPlanId(linedaysplan.getId(), LineImageType.detail));
			}
		}
		data.put("lineDayList", linedaysList);

		Lineexplain lineexplain = lineexplainService.getByLine(line.getId());
		data.put("lineExplain", lineexplain);

		List<TbArea> hotDestination = areaService.getHomeHotArea();
		data.put("hotDestination", hotDestination);
		FreemarkerUtil.create(data, LVXBANG_LINE_HEAD_TEMPLATE, String.format(LVXBANG_LINE_HEAD_TARGET, line.getId()));
		FreemarkerUtil.create(data, LVXBANG_LINE_DETAIL_TEMPLATE, String.format(LVXBANG_LINE_DETAIL_TARGET, line.getId()));
	}

    public Map<String, Line> startCityList(Line line, TbArea startCity) {
		List<Line> lineList = lineService.getSameCompanyLine(line);
		Map<String, Line> map = Maps.newLinkedHashMap();
        map.put(startCity.getName(), line);
        List<Line> list = Lists.newArrayList();
        completeLinePrice(line, list);
        list.addAll(completeLinePrice(lineList));
        for (Line line1 : list) {
            TbArea area = areaService.get(line1.getStartCityId());
            Line line2 = map.get(area.getName());
            if (line2 == null) {
                map.put(area.getName(), line1);
            }
		}
        return map;
	}

	public List<ScenicInfo> hotScenicInfo(Line line) {
		ScenicInfo scenicInfo = new ScenicInfo();
		TbArea tbArea = new TbArea();
		tbArea.setId(line.getArriveCityId() / 100);
		scenicInfo.setCity(tbArea);
		scenicInfo.setStatus(1);
		return scenicInfoService.list(scenicInfo, new Page(1, 4), "ranking", "asc");
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
		}
        if (min.equals(Float.MAX_VALUE)) {
            min = 0f;
        }
        line.setPrice(min);
        list.add(line);
	}

	public void buildDetailWeb(Long id) {
		Line line = lineService.loadLine(id);
		buildDetail(line);
	}

	public void buildSearcher() {
		List<SysSite> sites = siteService.findAllSite(new Page(1, Integer.MAX_VALUE));
		for (SysSite sysSite : sites) {
			Map<Object, Object> data = new HashMap<Object, Object>();
			QueryResponse facets = lineService.createFacets(sysSite);
			for (FacetField field : facets.getFacetFields()) {
				List<String> fieldValues = Lists.transform(field.getValues(), new Function<Count, String>() {
					@Override
					public String apply(Count count) {
						return count.getName();
					}
				});
				if (!fieldValues.isEmpty()) {
					data.put(field.getName(), fieldValues);
				}
			}
			FreemarkerUtil.create(data, LINE_SEARCHER_TEMPLATE, String.format(LINE_SEARCHER_TARGET, sysSite.getId()));
		}
	}

	public List<TbArea> getValidDestination() {
		TbArea tbArea = new TbArea();
		tbArea.setLevel(1);
		// todo: TbArea valid rule
		return tbAreaService.list(tbArea, null);
	}

	public String getImguriPreffix() {
        return propertiesManager.getString("mall.imguri.preffix");
	}

}
