package com.data.data.hmly.action.line;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.enums.TargetType;
import com.data.data.hmly.service.LabelItemService;
import com.data.data.hmly.service.LabelService;
import com.data.data.hmly.service.TbAreaService;
import com.data.data.hmly.service.common.PlaytitleService;
import com.data.data.hmly.service.common.ProductService;
import com.data.data.hmly.service.common.ProductimageService;
import com.data.data.hmly.service.common.QuantitySalesDetailService;
import com.data.data.hmly.service.common.QuantitySalesService;
import com.data.data.hmly.service.common.entity.Playtitle;
import com.data.data.hmly.service.common.entity.Product;
import com.data.data.hmly.service.common.entity.Productimage;
import com.data.data.hmly.service.common.entity.QuantitySales;
import com.data.data.hmly.service.common.entity.QuantitySalesDetail;
import com.data.data.hmly.service.common.entity.enums.ProductStatus;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.common.entity.enums.QuantityType;
import com.data.data.hmly.service.entity.Label;
import com.data.data.hmly.service.entity.LabelItem;
import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.goods.CategoryService;
import com.data.data.hmly.service.goods.CategoryTypeService;
import com.data.data.hmly.service.goods.entity.Category;
import com.data.data.hmly.service.goods.entity.CategoryType;
import com.data.data.hmly.service.line.LineDepartureService;
import com.data.data.hmly.service.line.LineImagesService;
import com.data.data.hmly.service.line.LineService;
import com.data.data.hmly.service.line.LineexplainService;
import com.data.data.hmly.service.line.LineplaytitleService;
import com.data.data.hmly.service.line.LinetypepriceService;
import com.data.data.hmly.service.line.entity.Line;
import com.data.data.hmly.service.line.entity.LineDeparture;
import com.data.data.hmly.service.line.entity.LineImages;
import com.data.data.hmly.service.line.entity.Linedays;
import com.data.data.hmly.service.line.entity.Lineexplain;
import com.data.data.hmly.service.line.entity.Lineplaytitle;
import com.data.data.hmly.service.line.entity.Linetypeprice;
import com.data.data.hmly.service.line.entity.enums.Buypay;
import com.data.data.hmly.service.line.entity.enums.LineImageType;
import com.data.data.hmly.service.line.entity.enums.LineStatus;
import com.data.data.hmly.service.line.entity.enums.ProductAttr;
import com.data.data.hmly.service.line.entity.enums.TourPlaceType;
import com.data.data.hmly.service.search.SearchService;
import com.data.data.hmly.service.search.entitysearch.QuickSearch;
import com.framework.hibernate.util.Page;
import com.framework.struts.AjaxCheck;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.DateUtils;
import com.zuipin.util.JsonFilter;
import com.zuipin.util.PropertiesManager;
import com.zuipin.util.QiniuUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.data.solr.core.query.result.ScoredPage;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * 线路
 *
 * @author caiys
 * @date 2015年10月13日 下午4:12:38
 */
public class LineAction extends FrameBaseAction implements ModelDriven<Line> {

    /**
     *
     */
    private static final long serialVersionUID = 4395061833017783442L;
    @Resource
    private LineService lineService;
    @Resource
    private LineexplainService lineexplainService;
    @Resource
    private LinetypepriceService linetypepriceService;
    @Resource
    private PlaytitleService playtitleService;
    @Resource
    private LineplaytitleService lineplaytitleService;
    @Resource
    private CategoryService categoryService;

    @Resource
    private CategoryTypeService categoryTypeService;

    @Resource
    private TbAreaService tbAreaService;
    @Resource
    private ProductimageService productimageService;
    @Resource
    private SearchService searchService;
    @Resource
    private PropertiesManager propertiesManager;

    @Resource
    private QuantitySalesService quantitySalesService;

    @Resource
    private QuantitySalesDetailService quantitySalesDetailService;

    @Resource
    private ProductService productService;

    @Resource
    private LabelItemService labelItemService;

    @Resource
    private LabelService labelService;

    @Resource
    private LineImagesService lineImagesService;

    @Resource
    private TbAreaService tbareaService;
    @Resource
    private LineDepartureService lineDepartureService;




    private Integer page = 1;
    private Integer rows = 10;
    private Line line = new Line();

    private Product product;

    // 返回数据
    Map<String, Object> map = new HashMap<String, Object>();
    private String dateStartStr;
    private String dateEndStr;
    private List<Linetypeprice> linetypepricesDisplay;
    private List<Playtitle> playtitles;
    private Linetypeprice linetypeprice;
    private String productId;
    private Line lineDisplay;
    private Lineexplain lineexplainDisplay;
    private List<Lineplaytitle> lineplaytitlesDisplay;
    private List<Category> linecategorgs = new ArrayList<Category>();
    private String winIndex; // 面板索引
    private String filePath; // 图片路径
    private String childFolder; // 图片子目录
    private String fgDomain;
    private int day;
    private String isCheck;
    private Boolean startPlaceRequired; // 出发地（交通）是否必须

    private QuantitySales quantitySales;
    private LineDeparture lineDeparture = new LineDeparture();

    private List<String> lineImgPaths = new ArrayList<>();
    private Set<Linedays> linedays;


    public Result getProductImagesList() {
        SysUser sysUser = getLoginUser();
        String keyword = (String) getParameter("keyword");
        List<Productimage> productimages = productimageService.getImageListByKeyword(keyword, sysUser);
        for (Productimage productimage : productimages) {
            if (com.zuipin.util.StringUtils.isNotBlank(productimage.getChildFolder())) {
                productimage.setPath(QiniuUtil.URL + productimage.getPath());
            }
        }

        return datagrid(productimages);
    }


    public Result lineList() {

        Page pageInfo = new Page(page, rows);
        String name = (String) getParameter("name");
        String cityId = (String) getParameter("cityId");
        String labelId = (String) getParameter("labelId");
        String type = (String) getParameter("type");
        String tagIds = (String) getParameter("tagIds");
        TbArea area = null;
        Line info = null;
        if (StringUtils.isNotBlank(cityId)) {
            area = tbareaService.getArea(Long.parseLong(cityId));
        }
        info = new Line();
        if (StringUtils.isNotBlank(name)) {
            info.setName(name);
        }
        List<Line> scenics = new ArrayList<Line>();
        List<LineLabel> scenicLabels = new ArrayList<LineLabel>();
        if ("LINE".equals(type)) {
            info.setLineStatus(LineStatus.show);
            info.setStatus(ProductStatus.UP);
            scenics = lineService.getLineLabels(info, area, tagIds, pageInfo);
            for (Line sInfo : scenics) {
                LineLabel slabel = new LineLabel();

                slabel.setId(sInfo.getId());
                slabel.setName(sInfo.getName());
                slabel.setUpdateTime(DateUtils.format(sInfo.getUpdateTime(), "yyyy-MM-dd HH:mm:ss"));
                if (sInfo.getStartCityId() != null) {
                    slabel.setCityId(sInfo.getStartCityId());
                    slabel.setCityName(tbareaService.getArea(sInfo.getStartCityId()).getFullPath());
                }


                List<LabelItem> items = labelItemService.findItemByTargId(sInfo.getId(), TargetType.LINE);

                List<String> labelNames = new ArrayList<String>();
                List<Integer> itemSorts = new ArrayList<Integer>();
                List<Long> itemIds = new ArrayList<Long>();
                List<Long> labIds = new ArrayList<Long>();
                for (LabelItem it : items) {
                    if ((it.getTargetId()).equals(sInfo.getId())) {
                        if (StringUtils.isNotBlank(labelId)) {
                            Long lId = Long.parseLong(labelId);
                            List<Label> labels = labelService.findLabelsByParent(lId);
                            if (labels.size() > 0) {
                                for (Label la : labels) {
                                    if ((la.getId()).equals(it.getLabel().getId())) {
                                        slabel.setSort(it.getOrder());
                                        itemSorts.add(it.getOrder());
                                        labelNames.add(it.getLabel().getName());
                                        itemIds.add(it.getId());
                                        labIds.add(it.getLabel().getId());
                                    }
                                }

                            } else {
                                if (lId.equals(it.getLabel().getId())) {
                                    slabel.setSort(it.getOrder());
                                    itemSorts.add(it.getOrder());
                                    labelNames.add(it.getLabel().getName());
                                    itemIds.add(it.getId());
                                    labIds.add(it.getLabel().getId());
                                }
                            }

                        } else {
                            slabel.setSort(it.getOrder());
                            itemSorts.add(it.getOrder());
                            labelNames.add(it.getLabel().getName());
                            itemIds.add(it.getId());
                            labIds.add(it.getLabel().getId());
                        }
                    }

                }

                slabel.setLabelNames(labelNames);
                slabel.setItemSort(itemSorts);
                slabel.setLabelItems(itemIds);
                slabel.setLabelIds(labIds);
                scenicLabels.add(slabel);
            }
        }
        return datagrid(scenicLabels, pageInfo.getTotalCount());
    }

    /**
     * 初始化获取已有的拱量信息
     * @return
     */
    public Result getQuantitySalesDetailByquantitySaleId() {

        List<QuantitySalesDetail> quantitySalesDetails =  new ArrayList<QuantitySalesDetail>();
        String quantitySalesIddStr = (String) getParameter("quantitySalesId");
        if (StringUtils.isNotBlank(quantitySalesIddStr)) {
            quantitySales = quantitySalesService.load(Long.parseLong(quantitySalesIddStr));
            quantitySalesDetails = quantitySalesDetailService.getListByQuantitySales(quantitySales);
        }
        return  datagrid(quantitySalesDetails);
    }

    /**
     * 保存门票拱量数据
     * @return
     */
    public Result saveQuantitySales() {
        String quantitySalesStr = (String) getParameter("quantitySalesStr");

        String quantitySalesIdStr = (String) getParameter("quantitySalesId");
        String typePriceIdStr = (String) getParameter("typePriceId");
        String flagStr = (String) getParameter("flag");
        String qStartTime = (String) getParameter("q_startTime");
        String qEndTime = (String) getParameter("q_endTime");
        String typeStr = (String) getParameter("type");

        if (StringUtils.isNotBlank(quantitySalesIdStr)) {
            quantitySales = quantitySalesService.load(Long.parseLong(quantitySalesIdStr));
        } else {
            quantitySales = new QuantitySales();
        }

        if (!StringUtils.isNotBlank(typePriceIdStr)) {
            simpleResult(map, false, "门票编号获取失败！");
        }

        if (!StringUtils.isNotBlank(flagStr)) {
            simpleResult(map, false, "拱量对象获取失败！");
        }

        if (StringUtils.isNotBlank(qStartTime)) {
            quantitySales.setStartTime(DateUtils.getDate(qStartTime, "yyyy-MM-dd HH:mm"));
        }
        if (StringUtils.isNotBlank(qEndTime)) {
            quantitySales.setEndTime(DateUtils.getDate(qEndTime, "yyyy-MM-dd HH:mm"));
        }

        if (!StringUtils.isNotBlank(typeStr)) {
            simpleResult(map, false, "拱量方式获取失败！");
        }
//        quantitySalesService.delQuantitySalesByTypePriceId(Long.parseLong(ticketPriceIdStr));
        linetypeprice = linetypepriceService.getLinetypeprice(Long.parseLong(typePriceIdStr));
        quantitySales.setProduct(linetypeprice.getLine());
        quantitySales.setPriceTypeId(linetypeprice.getId());
        quantitySales.setFlag(quantitySales.setQuantityFlagType(flagStr));
        quantitySales.setType(quantitySales.setQuantityType(typeStr));
        quantitySales.setStatus(1);     //有效
        quantitySales.setCreateTime(new Date());
        quantitySales.setUpdateTime(new Date());

        if (quantitySales.getId() != null) {
            quantitySalesService.update(quantitySales);
        } else {
            quantitySalesService.save(quantitySales);
        }
        List<QuantitySalesDetail> quantitySalesDetails = new ArrayList<QuantitySalesDetail>();
        quantitySalesDetailService.delByQuantitySalesId(quantitySales.getId());

        if (StringUtils.isNotBlank(quantitySalesStr)) {
            JSONArray jsonArray = JSONArray.fromObject(quantitySalesStr);
            for (Object object : jsonArray) {

                QuantitySalesDetail quantitySalesDetail = new QuantitySalesDetail();

                JSONObject jsonObject = JSONObject.fromObject(object);
//                "numStart":0,"numEnd":45,"percent":"23","money":""
                String numStartStr = jsonObject.getString("numStart");
                String numEndStr = jsonObject.getString("numEnd");
                String moneyStr = jsonObject.getString("favorablePrice");
                String percentStr = jsonObject.getString("discount");
                String moneyChildStr = jsonObject.getString("favorablePriceChild");
                String percentChildStr = jsonObject.getString("discountChild");


                Float discount = null;
                Float favorablePrice = null;
                Float discountChild = null;
                Float favorablePriceChild = null;
                if (StringUtils.isNotBlank(moneyStr)) {
                    favorablePrice = Float.parseFloat(moneyStr);
                }
                if (StringUtils.isNotBlank(percentStr)) {
                    discount = Float.parseFloat(percentStr);
                }
                if (StringUtils.isNotBlank(moneyChildStr)) {
                    favorablePriceChild = Float.parseFloat(moneyChildStr);
                }
                if (StringUtils.isNotBlank(percentChildStr)) {
                    discountChild = Float.parseFloat(percentChildStr);
                }

                if (StringUtils.isNotBlank(numStartStr)) {
                    quantitySalesDetail.setNumStart(Integer.parseInt(numStartStr));
                }

                quantitySalesDetail.setQuantitySales(quantitySales);
                if (StringUtils.isNotBlank(numEndStr)) {
                    quantitySalesDetail.setNumEnd(Integer.parseInt(numEndStr));
                }
                if (quantitySales.getType() == QuantityType.percent) {
                    quantitySalesDetail.setDiscountChild(discountChild);
                    quantitySalesDetail.setDiscount(discount);
                } else {
                    quantitySalesDetail.setFavorablePriceChild(favorablePriceChild);
                    quantitySalesDetail.setFavorablePrice(favorablePrice);
                }
                quantitySalesDetails.add(quantitySalesDetail);
            }
            quantitySalesDetailService.saveAll(quantitySalesDetails);
            simpleResult(map, true, "保存成功！");

        } else {
            simpleResult(map, false, "保存失败");
        }

        return jsonResult(map);

    }

    /**
     * 拱量设置页面
     * @return
     */
    public Result quantitySalesDialog() {

        String typePriceId = (String) getParameter("typePriceId");

        if (StringUtils.isNotBlank(typePriceId)) {
            linetypeprice = linetypepriceService.getLinetypeprice(Long.parseLong(typePriceId));
            quantitySales = quantitySalesService.getQuantitySalesByTypePriceId(linetypeprice.getId());

        }
        return dispatch();
    }


    /**
     * 功能首页
     *
     * @return
     * @author caiys
     * @date 2015年10月14日 下午2:51:18
     */
    public Result manage() {
        fgDomain = propertiesManager.getString("FG_DOMAIN");
        SysUnit sysUnit = getCompanyUnit();
        CategoryType categoryType = categoryTypeService.getByType("line");
        linecategorgs = categoryService.getValidCategoryList(categoryType, getLoginUser(), getCompanyUnit(), isSiteAdmin(), isSupperAdmin());
        return dispatch();
    }

    /**
     * 线路审核列表
     *
     * @return
     * @author caiys
     * @date 2015年10月14日 下午2:51:18
     */
    public Result auditList() {
        fgDomain = propertiesManager.getString("FG_DOMAIN");
        SysUnit sysUnit = getCompanyUnit();
        CategoryType categoryType = categoryTypeService.getByType("line");
        linecategorgs = categoryService.getValidCategoryList(categoryType, getLoginUser(), getCompanyUnit(), isSiteAdmin(), isSupperAdmin());
        return dispatch();
    }

    /**
     * 提交审核线路
     * @return
     */
    public Result subCheckLine() {

        if (StringUtils.isNotBlank(productId)) {
            line = lineService.loadLine(Long.parseLong(productId));
            line.setLineStatus(LineStatus.checking);
            lineService.update(line);
            simpleResult(map, true, "");
        } else {
            simpleResult(map, false, "");
        }
        return jsonResult(map);
    }


    /**
     * 审核线路
     * @return
     */
    public Result checkLine() {
        if (StringUtils.isNotBlank(productId)) {
            line = lineService.loadLine(Long.parseLong(productId));
            line.setLineStatus(LineStatus.show);
            lineService.update(line);
            simpleResult(map, true, "");
        } else {
            simpleResult(map, false, "");
        }
        return jsonResult(map);
    }

    /**
     * 列表查询
     *
     * @return
     * @author caiys
     * @date 2015年10月14日 下午2:51:44
     */
    @AjaxCheck
    public Result search() {
        // 参数
        // String aiFilter = (String) getParameter("aiFilter");
        String keyword = (String) getParameter("keyword");
        String sourceStr = (String) getParameter("sourceStr");
        if (StringUtils.isNotBlank(keyword)) {
            line.setKeyword(keyword);
        }
        if (StringUtils.isNotBlank(sourceStr)) {
            line.setSourceStr(sourceStr);
        }

        Page pageInfo = new Page(page, rows);
        List<Line> lines = lineService.findLineList(line, pageInfo, getLoginUser(), isSiteAdmin(), isSupperAdmin());

        String[] includeConfig = new String[]{"linestatistic"};
//		if (StringUtils.isNotBlank(line.getAgentLine())) { // 查询被代理线路
//			includeConfig = new String[]{"linestatistic", "topProduct"};
//		}
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig(includeConfig);
        return datagrid(lines, pageInfo.getTotalCount(), jsonConfig);
        /*return datagrid(lines, pageInfo.getTotalCount(), "line", "user", "companyUnit", "parent", "supplier", "topProduct", "productimage",
                "lineacrosscitys", "lineexplain", "linetypeprices", "lineplaytitles");*/
    }

    /**
     * 新增线路向导页面
     *
     * @return
     * @author caiys
     * @date 2015年10月14日 下午3:03:54
     */
    public Result addWizard() {
        return dispatch();
    }

    /**
     * 第一步：添加线路描述
     *
     * @return
     * @author caiys
     * @date 2015年10月14日 下午3:11:43
     */
    public Result addStep1() {
        SysUnit sysUnit = getCompanyUnit();
        CategoryType categoryType = categoryTypeService.getByType("line");
        linecategorgs = categoryService.listValid(categoryType, sysUnit);
        return dispatch();
    }

    /**
     * 第二步：添加线路报价
     *
     * @return
     * @author caiys
     * @date 2015年10月14日 下午3:11:47
     */
    public Result addStep2() {
        // 起始时间 - 当前时间第二天
        Date dateStart = DateUtils.add(new Date(), Calendar.DAY_OF_MONTH, 1);
        dateStartStr = DateUtils.format(dateStart, "yyyy-MM-dd");
        // 结束时间 - 当前时间次月最后一天
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateStart);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 2);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);
        dateEndStr = DateUtils.format(calendar.getTime(), "yyyy-MM-dd");

        // 如果是编辑类型报价
        String linetypepriceId = (String) getParameter("linetypepriceId");
        if (StringUtils.isNotBlank(linetypepriceId)) {
            linetypeprice = linetypepriceService.getLinetypeprice(Long.valueOf(linetypepriceId));
        }

        return dispatch();
    }

    /**
     * 第二步：添加线路报价-价格类型列表
     *
     * @return
     * @author caiys
     * @date 2015年10月14日 下午7:55:02
     */
    public Result addStep21() {
        String productId = (String) getParameter("productId");
        line.setId(Long.valueOf(productId));
        line = lineService.loadLine(Long.valueOf(productId));
//        line = lineService.loadLine(Long.valueOf(1));
        Linetypeprice linetypeprice = new Linetypeprice();
        linetypeprice.setLine(line);
        lineDeparture = lineDepartureService.getDepartureByLine(line);
        linetypepricesDisplay = linetypepriceService.findLinetypepriceList(linetypeprice);
        return dispatch();
    }

    /**
     * 第三步：添加行程内容
     *
     * @return
     * @author caiys
     * @date 2015年10月14日 下午3:11:49
     */
    public Result addStep3() {
        fgDomain = propertiesManager.getString("FG_DOMAIN");
        playtitles = playtitleService.findPlaytitleList(new Playtitle());
        return dispatch();
    }

    /**
     * 第四步：线路发布成功
     *
     * @return
     * @author caiys
     * @date 2015年10月14日 下午3:11:52
     */
    public Result addStep4() {
        return dispatch();
    }

    /**
     * 修改线路向导页面
     *
     * @return
     * @author caiys
     * @date 2015年10月14日 下午3:03:54
     */
    public Result editWizard() {
        winIndex = (String) getParameter("winIndex");
        productId = (String) getParameter("productId");
        return dispatch();
    }


    public Result lineView() {
        fgDomain = propertiesManager.getString("FG_DOMAIN");
        String productId = (String) getParameter("productId");
        lineDisplay = lineService.loadLine(Long.valueOf(productId));
        Productimage pi = new Productimage();
        pi.setProduct(lineDisplay);
        pi.setProType(ProductType.line);

        if (StringUtils.isNotBlank(isCheck)) {
            isCheck = isCheck;
        }

        List<Productimage> productimages = productimageService.findProductimage(pi, null);
        if (productimages.size() > 0) {
            filePath = productimages.get(0).getPath();
            childFolder = productimages.get(0).getChildFolder();
        }
        Long category = lineDisplay.getCategory();
        if (category != null) {
            Category linecategory = categoryService.findById(category);
            if (linecategory != null)
                linecategorgs.add(linecategory);
        }
        // 起始时间 - 当前时间第二天
        Date dateStart = DateUtils.add(new Date(), Calendar.DAY_OF_MONTH, 1);
        dateStartStr = DateUtils.format(dateStart, "yyyy-MM-dd");
        // 结束时间 - 当前时间次月最后一天
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateStart);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 6);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);
        dateEndStr = DateUtils.format(calendar.getTime(), "yyyy-MM-dd");

        // 如果是编辑类型报价
        String linetypepriceId = (String) getParameter("linetypepriceId");
        if (StringUtils.isNotBlank(linetypepriceId)) {
            linetypeprice = linetypepriceService.getLinetypeprice(Long.valueOf(linetypepriceId));
        }

        lineDisplay = lineService.loadLine(Long.valueOf(productId));
        lineDisplay.setSiteurl(lineDisplay.getCompanyUnit().getSysSite().getSiteurl());
        Linetypeprice linetypeprice = new Linetypeprice();
        if (lineDisplay.getTopProduct() != null) { // 顶级不为空，价格取顶级价格
            Line l = new Line();
            l.setId(lineDisplay.getTopProduct().getId());
            linetypeprice.setLine(l);
        } else {
            linetypeprice.setLine(lineDisplay);
        }
        linetypepricesDisplay = linetypepriceService.findLinetypepriceList(linetypeprice);

        lineDisplay = lineService.loadLine(Long.valueOf(productId));
        Lineexplain lineexplainParam = new Lineexplain();
        lineexplainParam.setLineId(Long.valueOf(productId));
        List<Lineexplain> lineexplains = lineexplainService.findLineexplain(lineexplainParam);
        if (lineexplains.size() > 0) {
            lineexplainDisplay = lineexplains.get(0);
        }
        Lineplaytitle lineplaytitle = new Lineplaytitle();
        lineplaytitle.setLineId(Long.valueOf(productId));
        lineplaytitlesDisplay = lineplaytitleService.findLineplaytitle(lineplaytitle);
        playtitles = playtitleService.findPlaytitleList(new Playtitle());
        if (lineexplainDisplay != null && lineexplainDisplay.getLinedays().size() > 0) {
            day = lineexplainDisplay.getLinedays().size();
        }
        return dispatch();
    }


    /**
     * 线路组合关联
     * @return
     */
    public Result lineRelate() {
        String productId = (String) getParameter("productId");
        lineDisplay = lineService.loadLine(Long.valueOf(productId));
        Lineexplain lineexplainParam = new Lineexplain();
        lineexplainParam.setLineId(Long.valueOf(productId));
        List<Lineexplain> lineexplains = lineexplainService.findLineexplain(lineexplainParam);
        if (lineexplains.size() > 0) {
            lineexplainDisplay = lineexplains.get(0);
            linedays = lineexplainDisplay.getLinedays();
        }
        return dispatch();
    }


    /**
     * 第一步：修改线路描述
     *
     * @return
     * @author caiys
     * @date 2015年10月14日 下午3:11:43
     */
    public Result editStep1() {
        fgDomain = propertiesManager.getString("FG_DOMAIN");
        String productId = (String) getParameter("productId");
        lineDisplay = lineService.loadLine(Long.valueOf(productId));
        // 出发城市
        if (lineDisplay.getStartCityId() != null) {
            TbArea city = tbAreaService.getArea(lineDisplay.getStartCityId());
            TbArea province = city.getFather();
            if (province != null) {
                lineDisplay.setStartProvinceId(province.getId());
                TbArea country = province.getFather();
                if (country != null) {
                    lineDisplay.setStartCountryId(country.getId());
                }
            }
        }
        // 到达城市
        if (lineDisplay.getArriveCityId() != null) {
            TbArea city = tbAreaService.getArea(lineDisplay.getArriveCityId());
            TbArea province = city.getFather();
            if (province != null) {
                lineDisplay.setArriveProvinceId(province.getId());
                TbArea country = province.getFather();
                if (country != null) {
                    lineDisplay.setArriveCountryId(country.getId());
                }
            }
        }
        Productimage pi = new Productimage();
        pi.setProduct(lineDisplay);
        pi.setProType(ProductType.line);
       // List<Productimage> productimages = productimageService.findProductimage(pi, null);
        /*if (productimages.size() > 0) {
            filePath = productimages.get(0).getPath();
            childFolder = productimages.get(0).getChildFolder();
        }*/
        SysUnit sysUnit = getCompanyUnit();
        CategoryType categoryType = categoryTypeService.getByType("line");
        linecategorgs = categoryService.listValid(categoryType, sysUnit);
        // 出发地是否必须
        startPlaceRequired = true;
        if (lineDisplay.getProductAttr() == ProductAttr.ziyou || lineDisplay.getProductAttr() == ProductAttr.zijia
                || (lineDisplay.getProductAttr() == ProductAttr.gentuan && lineDisplay.getTourPlaceType() == TourPlaceType.dest)) {
            startPlaceRequired = false;
        }

        return dispatch();
    }

    public Result editLineHomeImags() {

        List<LineImages> lineImageses = new ArrayList<LineImages>();

        if (StringUtils.isNotBlank(productId)) {
            lineImageses = lineImagesService.listImagesByLineId(Long.parseLong(productId), LineImageType.home);
        }
        return datagrid(lineImageses);
    }

    /**
     * 第二步：修改线路报价
     *
     * @return
     * @author caiys
     * @date 2015年10月14日 下午3:11:47
     */
    public Result editStep2() {
        fgDomain = propertiesManager.getString("FG_DOMAIN");
        productId = (String) getParameter("productId");
        // 起始时间 - 当前时间第二天
        Date dateStart = DateUtils.add(new Date(), Calendar.DAY_OF_MONTH, 1);
        dateStartStr = DateUtils.format(dateStart, "yyyy-MM-dd");
        // 结束时间 - 当前时间次月最后一天
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateStart);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 30);
        dateEndStr = DateUtils.format(calendar.getTime(), "yyyy-MM-dd");

        // 如果是编辑类型报价
        String linetypepriceId = (String) getParameter("linetypepriceId");
        if (StringUtils.isNotBlank(linetypepriceId)) {
            linetypeprice = linetypepriceService.getLinetypeprice(Long.valueOf(linetypepriceId));
        }

        return dispatch();
    }

    /**
     * 第二步：修改线路报价
     *
     * @return
     * @author caiys
     * @date 2015年10月14日 下午3:11:47
     */
    public Result viewStep2() {
        fgDomain = propertiesManager.getString("FG_DOMAIN");
        productId = (String) getParameter("productId");
        // 起始时间 - 当前时间第二天
        Date dateStart = DateUtils.add(new Date(), Calendar.DAY_OF_MONTH, 1);
        dateStartStr = DateUtils.format(dateStart, "yyyy-MM-dd");
        // 结束时间 - 当前时间次月最后一天
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateStart);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 6);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);
        dateEndStr = DateUtils.format(calendar.getTime(), "yyyy-MM-dd");

        // 如果是编辑类型报价
        String linetypepriceId = (String) getParameter("linetypepriceId");
        if (StringUtils.isNotBlank(linetypepriceId)) {
            linetypeprice = linetypepriceService.getLinetypeprice(Long.valueOf(linetypepriceId));
        }

        return dispatch();
    }

    /**
     * 第二步：修改线路报价-价格类型列表
     *
     * @return
     * @author caiys
     * @date 2015年10月14日 下午7:55:02
     */
    public Result editStep21() {
        fgDomain = propertiesManager.getString("FG_DOMAIN");
        String productId = (String) getParameter("productId");
        lineDisplay = lineService.loadLine(Long.valueOf(productId));
//        lineDisplay = lineService.loadLine(Long.valueOf(1));
        lineDisplay.setSiteurl(lineDisplay.getCompanyUnit().getSysSite().getSiteurl());
        Linetypeprice linetypeprice = new Linetypeprice();
        if (lineDisplay.getTopProduct() != null) { // 顶级不为空，价格取顶级价格
            Line l = new Line();
            l.setId(lineDisplay.getTopProduct().getId());
            linetypeprice.setLine(l);
        } else {
            linetypeprice.setLine(lineDisplay);
        }
        lineDeparture = lineDepartureService.getDepartureByLine(lineDisplay);
        linetypepricesDisplay = linetypepriceService.findLinetypepriceList(linetypeprice);
        return dispatch();
    }

    /**
     * 第三步：修改行程内容
     *
     * @return
     * @author caiys
     * @date 2015年10月14日 下午3:11:49
     */
    public Result editStep3() {
        fgDomain = propertiesManager.getString("FG_DOMAIN");
        productId = (String) getParameter("productId");
        lineDisplay = lineService.loadLine(Long.valueOf(productId));
        Lineexplain lineexplainParam = new Lineexplain();
        lineexplainParam.setLineId(Long.valueOf(productId));
        List<Lineexplain> lineexplains = lineexplainService.findLineexplain(lineexplainParam);
        if (lineexplains.size() > 0) {
            lineexplainDisplay = lineexplains.get(0);
        }
        Lineplaytitle lineplaytitle = new Lineplaytitle();
        lineplaytitle.setLineId(Long.valueOf(productId));
        lineplaytitlesDisplay = lineplaytitleService.findLineplaytitle(lineplaytitle);
        playtitles = playtitleService.findPlaytitleList(new Playtitle());
        if (lineexplainDisplay != null && lineexplainDisplay.getLinedays().size() > 0) {
            day = lineexplainDisplay.getLinedays().size();
        }
        return dispatch();
    }

    /**
     * 第四步：线路修改成功
     *
     * @return
     * @author caiys
     * @date 2015年10月14日 下午3:11:52
     */
    public Result editStep4() {
        productId = (String) getParameter("productId");
        lineDisplay = lineService.loadLine(Long.valueOf(productId));
        return dispatch();
    }

    /**
     * 功能描述：保存线路基础信息
     *
     * @return
     * @author caiys
     * @date 2015年10月16日 下午4:51:43
     */
    @AjaxCheck
    public Result saveLine() {
        final HttpServletRequest request = getRequest();
        String productId = (String) getParameter("productId");
        String filePath = (String) getParameter("imgPaths");
        String[] lineImgPaths = request.getParameterValues("lineImgPaths");
        String[] lingImgDeleteIds = request.getParameterValues("lingImgDeleteIds");
        String childFolder = (String) getParameter("childFolder");
//        Long lineId = 0l;
        if (StringUtils.isBlank(line.getGoWay())) {
            line.setGoWay(null);
        }
        if (StringUtils.isBlank(line.getBackWay())) {
            line.setBackWay(null);
        }

        /*Productimage productimage = null;
        if (StringUtils.isNotBlank(filePath)) {
            productimage = new Productimage();
            productimage.setPath(filePath);
            productimage.setProType(ProductType.line);
            productimage.setChildFolder(childFolder);
            productimage.setCoverFlag(true);
            productimage.setUserId(getLoginUser().getId());
            productimage.setCompanyUnitId(getLoginUser().getSysUnit().getCompanyUnit().getId());
            productimage.setCreateTime(new Date());
        }*/
        // 如果发团性质为“跟团游”，且发团地点为“目的成团”，出发地设置为空
        if (line.getProductAttr() == ProductAttr.gentuan && line.getTourPlaceType() == TourPlaceType.dest) {
            line.setStartCityId(null);
        }
        if (line.getProductAttr() != ProductAttr.gentuan) {
            line.setTourPlaceType(null);
        }

        if (StringUtils.isNotBlank(productId)) {
            Line oldLine = lineService.loadLine(Long.valueOf(productId));
            oldLine.setProductNo(line.getProductNo());
            oldLine.setName(line.getName());
            oldLine.setStartCityId(line.getStartCityId());
            oldLine.setPreOrderDay(line.getPreOrderDay());
            oldLine.setSuggestOrderHour(line.getSuggestOrderHour());
            oldLine.setValidOrderDay(line.getValidOrderDay());
            oldLine.setGoWay(line.getGoWay());
            oldLine.setBackWay(line.getBackWay());
            oldLine.setWayDesc(line.getWayDesc());
            oldLine.setShowOrder(line.getShowOrder());
            oldLine.setProductRemark(line.getProductRemark());
            oldLine.setAppendTitle(line.getAppendTitle());
            oldLine.setArriveCityId(line.getArriveCityId());
            oldLine.setShortDesc(line.getShortDesc());
            oldLine.setTourPlaceType(line.getTourPlaceType());
            oldLine.setCombineType(line.getCombineType());
            oldLine.setUpdateTime(new Date());
            if (!oldLine.getAgentFlag()) {    // 如果是代理线路编辑不更新disable属性
                oldLine.setLineType(line.getLineType());
                oldLine.setProductAttr(line.getProductAttr());
                oldLine.setBuypay(line.getBuypay());
                oldLine.setPaySet(line.getPaySet());
                oldLine.setConfirmAndPay(line.getConfirmAndPay());
                oldLine.setScoreExchange(line.getScoreExchange());
            }
            oldLine.setCategory(line.getCategory());
            lineService.updateLine(oldLine, childFolder);
        } else {
            line.setProductNo(null);
            line.setTopProduct(line); // 默认原线路是自己
            line.setStatus(ProductStatus.DOWN);
            line.setProType(ProductType.line);
            line.setLineStatus(LineStatus.hide); // 默认隐藏，最后保存时校验信息完整性更新状态
            line.setUser(getLoginUser());
            line.setCompanyUnit(getCompanyUnit());
            line.setOrderSum(doCreateRandomNum() + 1000);
            line.setCollectSum(doCreateRandomNum() + 1000);
            line.setCommentSum(doCreateRandomNum() + 1000);
            line.setSatisfaction(doCreateSatisfactionNum());
            line.setShowOrder(doCreateRandomNum());
            line.setCreateTime(new Date());
            line.setUserId(getLoginUser().getId());
            line.setUpdateTime(new Date());
            lineService.saveLine(line);
            productId = String.valueOf(line.getId());
            line.setProductNo(productId);   // 更新产品编号
            lineService.update(line);
        }
        // ??
//        lineImagesService.delByLineId(Long.parseLong(productId), LineImageType.home);

        // zzl 修改
        // 新增图片
        if (lineImgPaths != null && lineImgPaths.length > 0) {
            List<LineImages> lineImagesList = new ArrayList<LineImages>();
            for (String path : lineImgPaths) {
                LineImages lineImages = new LineImages();
                lineImages.setLineId(Long.parseLong(productId));
                lineImages.setLineImageType(LineImageType.home);
                lineImages.setImageUrl(path);
                lineImagesList.add(lineImages);
            }
            lineImagesService.saveAll(lineImagesList);
        }
        // 删除图片
        if (lingImgDeleteIds != null && lingImgDeleteIds.length > 0) {
            for (String idStr : lingImgDeleteIds) {
                lineImagesService.delete(Long.parseLong(idStr));
            }
        }
//        if (StringUtils.isNotBlank(filePath)) {
//            JSONArray jsonFileArr = JSONArray.fromObject(filePath);
//            List<LineImages> lineImageses = new ArrayList<LineImages>();
//            for (Object object : jsonFileArr) {
//                JSONObject jsonFile = JSONObject.fromObject(object);
//                LineImages lineImages = new LineImages();
//                lineImages.setLineId(Long.parseLong(productId));
//                lineImages.setLineImageType(LineImageType.home);
//                lineImages.setImageUrl(jsonFile.getString("imgUrl"));
//                lineImages.setImageDesc(jsonFile.getString("title"));
//                lineImageses.add(lineImages);
//            }
//            lineImagesService.saveAll(lineImageses);
//        }

        map.put("id", productId);
        simpleResult(map, true, "");
        return jsonResult(map);
    }

    /**
     * 根据更新状态，多个逗号分隔
     *
     * @return
     * @author caiys
     * @date 2015年10月26日 下午8:07:05
     */
    @AjaxCheck
    public Result doUpdateStatus() {
        String ids = (String) getParameter("ids");
        String lineStatus = (String) getParameter("lineStatus");
        String reason = (String) getParameter("reason");
        if (StringUtils.isNotBlank(ids)) {
            map = lineService.updateLineStatus(ids, LineStatus.valueOf(lineStatus), reason, getLoginUser());
        }
        return jsonResult(map);
    }

    /**
     * 根据标识批量删除，多个逗号分隔
     *
     * @return
     * @author caiys
     * @date 2015年10月26日 下午8:07:05
     */
    @AjaxCheck
    public Result delBatch() {
        String ids = (String) getParameter("ids");
        if (StringUtils.isNotBlank(ids)) {
            lineService.updateLineStatus(ids, LineStatus.del, null, getLoginUser());
        }
        simpleResult(map, true, "");
        return jsonResult(map);
    }

    /**
     * 根据标识批量隐藏，多个逗号分隔
     *
     * @return
     * @author caiys
     * @date 2015年10月26日 下午8:07:05
     */
    @AjaxCheck
    public Result hideBatch() {
        String ids = (String) getParameter("ids");
        if (StringUtils.isNotBlank(ids)) {
            lineService.updateLineStatus(ids, LineStatus.hide, null, getLoginUser());
        }
        simpleResult(map, true, "");
        return jsonResult(map);
    }

    /**
     * 根据标识批量显示，多个逗号分隔
     *
     * @return
     * @author caiys
     * @date 2015年10月26日 下午8:07:05
     */
    @AjaxCheck
    public Result showBatch() {
        String ids = (String) getParameter("ids");
        if (StringUtils.isNotBlank(ids)) {
            map = lineService.updateLineStatus(ids, LineStatus.show, null, getLoginUser());
        }
        return jsonResult(map);
    }

    /**
     * 根据标识批量重发
     *
     * @return
     * @author caiys
     * @date 2015年10月26日 下午8:07:05
     */
    @AjaxCheck
    public Result rePubBatch() {
        String ids = (String) getParameter("ids");
        if (StringUtils.isNotBlank(ids)) {
            lineService.updatePubBatch(ids);
        }
        simpleResult(map, true, "");
        return jsonResult(map);
    }

    /**
     * 根据标识批量设置有购物有自费
     *
     * @return
     * @author caiys
     * @date 2015年10月26日 下午8:07:05
     */
    @AjaxCheck
    public Result buyPayBatch() {
        String ids = (String) getParameter("ids");
        if (StringUtils.isNotBlank(ids)) {
            lineService.updateBuypay(ids, Buypay.buyPay);
        }
        simpleResult(map, true, "");
        return jsonResult(map);
    }

    /**
     * 根据标识批量更新排序
     *
     * @return
     * @author caiys
     * @date 2015年10月26日 下午8:07:05
     */
    @AjaxCheck
    public Result updateOrderBatch() {
        String idValues = (String) getParameter("idValues");
        if (StringUtils.isNotBlank(idValues)) {
            lineService.updateOrderBatch(idValues);
        }
        simpleResult(map, true, "");
        return jsonResult(map);
    }

    /**
     * 获取省、市列表
     *
     * @return
     * @author caiys
     * @date 2015年10月27日 下午1:59:15
     */
    public Result listArea() {
        String fatherId = (String) getParameter("fatherId");
        List<TbArea> areas = new ArrayList<TbArea>();
        if (StringUtils.isNotBlank(fatherId)) {
            areas = tbAreaService.getCityByPro(fatherId, 2);
        } else {
            areas = tbAreaService.getCityByPro("100000", 1);
        }
        JsonConfig config = JsonFilter.getIncludeConfig();
        JSONArray json = JSONArray.fromObject(areas, config);
        return json(json);
    }

    /**
     * 获取分类名称
     *
     * @return
     * @author caiys
     * @date 2015年10月27日 下午4:35:48
     */
    @AjaxCheck
    public Result findCategoryName() {
        String categoryId = (String) getParameter("categoryId");
        map.put("categoryName", "");
        if (StringUtils.isNotBlank(categoryId)) {
            Category lc = categoryService.findById(Long.valueOf(categoryId));
            if (lc != null) {
                map.put("categoryName", lc.getName());
            }
        }
        simpleResult(map, true, "");
        return jsonResult(map);
    }

    /**
     * 获取分类名称
     *
     * @return
     * @author caiys
     * @date 2015年10月27日 下午4:35:48
     */
    @AjaxCheck
    public Result copyLine() {
        String productId = (String) getParameter("productId");
        Long lineId = Long.valueOf(productId);
        String staticPath = propertiesManager.getString("IMG_DIR");
        lineService.doCopyLine(lineId, getLoginUser(), staticPath);
        simpleResult(map, true, "");
        return jsonResult(map);
    }

    /**
     * 获取酒店、景点、美食餐厅
     *
     * @return
     * @throws SolrServerException
     * @author caiys
     * @date 2015年10月27日 下午1:59:15
     */
    public Result listSite() throws SolrServerException {
        String keyword = (String) getParameter("q");
        String proTypes = (String) getParameter("proTypes");
        String[] proTypeArray = proTypes.split(",");
//		Arrays.asList(proTypeArray);
        List<String> types = Arrays.asList(proTypeArray);
        List<Map<String, String>> rows = new ArrayList<Map<String, String>>();
        if (StringUtils.isNotBlank(keyword)) {
            ScoredPage<QuickSearch> result = searchService.QuciSearch(keyword, types);
            List<QuickSearch> content = result.getContent();
            for (QuickSearch quickSearch : content) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("typeName", quickSearch.getName());
                map.put("type", quickSearch.getType());
                map.put("cityId", quickSearch.getCity_code());
                if (ProductType.hotel.name().equals(quickSearch.getType())) {
                    map.put("typeId", String.valueOf(quickSearch.getHotelId()));
                    rows.add(map);
                }
                if (ProductType.scenic.name().equals(quickSearch.getType())) {
                    map.put("typeId", String.valueOf(quickSearch.getScenicId()));
                    rows.add(map);
                }
                if (ProductType.restaurant.name().equals(quickSearch.getType())) {
                    map.put("typeId", String.valueOf(quickSearch.getRestaurantId()));
                    map.put("foodId", String.valueOf(quickSearch.getFoodId()));
                    rows.add(map);
                }
            }
            // TODO 测试代码
            /*
             * for (int i = 0; i < 10; i++) {
			 * Random r = new Random();
			 * if (i < 3) {
			 * Map<String, String> map = new HashMap<String, String>();
			 * map.put("typeName", "hotel名称" + r.nextInt(10000));
			 * map.put("type", "hotel");
			 * map.put("cityId", "350200");
			 * map.put("typeId", String.valueOf(i + 1));
			 * rows.add(map);
			 * continue;
			 * } else if (i < 6) {
			 * Map<String, String> map = new HashMap<String, String>();
			 * map.put("typeName", "scenic名称" + r.nextInt(10000));
			 * map.put("type", "scenic");
			 * map.put("cityId", "350200");
			 * map.put("typeId", String.valueOf(i + 1));
			 * rows.add(map);
			 * continue;
			 * } else {
			 * Map<String, String> map = new HashMap<String, String>();
			 * map.put("typeName", "restaurant名称" + r.nextInt(10000));
			 * map.put("type", "restaurant");
			 * map.put("cityId", "350200");
			 * map.put("typeId", String.valueOf(i + 1));
			 * map.put("foodId", String.valueOf(i + 1 + 100));
			 * rows.add(map);
			 * continue;
			 * }
			 * }
			 */

        }
        JSONArray json = JSONArray.fromObject(rows);
        return json(json);
    }

    public Result indexLines() {

        lineService.doIndexLines();
        return text("indexLines");
    }



    public Result facetLines() throws SolrServerException {

        // lineService.createFacets(sysSite);
        return text("facetLines");
    }

    public Result queryLines() throws SolrServerException, UnsupportedEncodingException {
        getParameter("q");
        // lineService.queryLine(q);
        return text("facetLines");
    }


    public Integer doCreateRandomNum() {
        Random r = new Random();
        Integer resultNum = 0;
        while (true) {
            resultNum = r.nextInt(1000);
            if (resultNum > 0) {
                break;
            }
        }
        return resultNum;
    }

    public Integer doCreateSatisfactionNum() {
        Random r = new Random();
        Integer resultNum = 0;
        while (true) {
            resultNum = r.nextInt(100);
            if (resultNum > 90) {
                break;
            }
        }
        return resultNum;
    }

    @Override
    public Line getModel() {
        return line;
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

    public String getDateStartStr() {
        return dateStartStr;
    }

    public void setDateStartStr(String dateStartStr) {
        this.dateStartStr = dateStartStr;
    }

    public String getDateEndStr() {
        return dateEndStr;
    }

    public void setDateEndStr(String dateEndStr) {
        this.dateEndStr = dateEndStr;
    }

    public List<Linetypeprice> getLinetypepricesDisplay() {
        return linetypepricesDisplay;
    }

    public void setLinetypepricesDisplay(List<Linetypeprice> linetypepricesDisplay) {
        this.linetypepricesDisplay = linetypepricesDisplay;
    }

    public List<Playtitle> getPlaytitles() {
        return playtitles;
    }

    public void setPlaytitles(List<Playtitle> playtitles) {
        this.playtitles = playtitles;
    }

    public Linetypeprice getLinetypeprice() {
        return linetypeprice;
    }

    public void setLinetypeprice(Linetypeprice linetypeprice) {
        this.linetypeprice = linetypeprice;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Line getLineDisplay() {
        return lineDisplay;
    }

    public void setLineDisplay(Line lineDisplay) {
        this.lineDisplay = lineDisplay;
    }

    public Lineexplain getLineexplainDisplay() {
        return lineexplainDisplay;
    }

    public void setLineexplainDisplay(Lineexplain lineexplainDisplay) {
        this.lineexplainDisplay = lineexplainDisplay;
    }

    public List<Lineplaytitle> getLineplaytitlesDisplay() {
        return lineplaytitlesDisplay;
    }

    public void setLineplaytitlesDisplay(List<Lineplaytitle> lineplaytitlesDisplay) {
        this.lineplaytitlesDisplay = lineplaytitlesDisplay;
    }

    public List<Category> getLinecategorgs() {
        return linecategorgs;
    }

    public void setLinecategorgs(List<Category> linecategorgs) {
        this.linecategorgs = linecategorgs;
    }

    public String getWinIndex() {
        return winIndex;
    }

    public void setWinIndex(String winIndex) {
        this.winIndex = winIndex;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getChildFolder() {
        return childFolder;
    }

    public void setChildFolder(String childFolder) {
        this.childFolder = childFolder;
    }

    public String getFgDomain() {
        return fgDomain;
    }

    public void setFgDomain(String fgDomain) {
        this.fgDomain = fgDomain;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public QuantitySales getQuantitySales() {
        return quantitySales;
    }

    public void setQuantitySales(QuantitySales quantitySales) {
        this.quantitySales = quantitySales;
    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    public LineDeparture getLineDeparture() {
        return lineDeparture;
    }

    public void setLineDeparture(LineDeparture lineDeparture) {
        this.lineDeparture = lineDeparture;
    }

    public String getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(String isCheck) {
        this.isCheck = isCheck;
    }

    public Boolean getStartPlaceRequired() {
        return startPlaceRequired;
    }

    public void setStartPlaceRequired(Boolean startPlaceRequired) {
        this.startPlaceRequired = startPlaceRequired;
    }

    public Set<Linedays> getLinedays() {
        return linedays;
    }

    public void setLinedays(Set<Linedays> linedays) {
        this.linedays = linedays;
    }
}
