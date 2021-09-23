package com.data.data.hmly.service.build.yhybuilder;

import com.data.data.hmly.service.LabelItemService;
import com.data.data.hmly.service.LabelService;
import com.data.data.hmly.service.build.util.FreemarkerUtil;
import com.data.data.hmly.service.common.ProductimageService;
import com.data.data.hmly.service.common.entity.Product;
import com.data.data.hmly.service.common.entity.Productimage;
import com.data.data.hmly.service.common.entity.enums.ProductSource;
import com.data.data.hmly.service.common.entity.enums.ProductStatus;
import com.data.data.hmly.service.cruiseship.CruiseShipDateService;
import com.data.data.hmly.service.cruiseship.CruiseShipService;
import com.data.data.hmly.service.cruiseship.entity.CruiseShip;
import com.data.data.hmly.service.cruiseship.entity.CruiseShipDate;
import com.data.data.hmly.service.entity.Ads;
import com.data.data.hmly.service.entity.Label;
import com.data.data.hmly.service.entity.LabelItem;
import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.goods.CategoryService;
import com.data.data.hmly.service.goods.CategoryTypeService;
import com.data.data.hmly.service.goods.entity.Category;
import com.data.data.hmly.service.goods.entity.CategoryType;
import com.data.data.hmly.service.hotel.HotelRegionService;
import com.data.data.hmly.service.hotel.HotelService;
import com.data.data.hmly.service.hotel.entity.Hotel;
import com.data.data.hmly.service.hotel.entity.HotelRegion;
import com.data.data.hmly.service.plan.RecommendPlanService;
import com.data.data.hmly.service.plan.entity.RecommendPlan;
import com.data.data.hmly.service.scenic.ScenicInfoService;
import com.data.data.hmly.service.scenic.entity.ScenicInfo;
import com.data.data.hmly.service.scenic.entity.enums.ScenicInfoType;
import com.data.data.hmly.service.ticket.TicketPriceService;
import com.data.data.hmly.service.ticket.TicketService;
import com.data.data.hmly.service.ticket.entity.Ticket;
import com.data.data.hmly.service.ticket.entity.enmus.TicketType;
import com.framework.hibernate.util.Page;
import com.google.common.collect.Lists;
import com.zuipin.util.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dy on 2017/1/7.
 */
@Service
public class YhyIndexBuilder {

    private Logger logger = Logger.getLogger(YhySailboatBuilder.class);

    @Resource
    private YhyAdsBuilder yhyAdsBuilder;
    @Resource
    private LabelService labelService;
    @Resource
    private LabelItemService labelItemService;
    @Resource
    private TicketPriceService ticketPriceService;
    @Resource
    private ScenicInfoService scenicInfoService;
    @Resource
    private CategoryService categoryService;
    @Resource
    private CategoryTypeService categoryTypeService;
    @Resource
    private TicketService ticketService;
    @Resource
    private RecommendPlanService recommendPlanService;
    @Resource
    private HotelRegionService hotelRegionService;
    @Resource
    private HotelService hotelService;
    @Resource
    private CruiseShipService cruiseShipService;
    @Resource
    private CruiseShipDateService cruiseShipDateService;
    @Resource
    private ProductimageService productimageService;

    private static final String YHY_INDEX_TEMPLATE = "/yhy/index/index.ftl";
    private static final String YHY_INDEX_TARGET = "/yhy/index/index.htm";

    public void buildIndex() {
        Page page = null;
        Map<Object, Object> data = new HashMap<Object, Object>();
        //广告
        List<Ads> adses = yhyAdsBuilder.getAds(YhyAdsBuilder.YHY_INDEX_TOP_BANNER);
        data.put("adses", adses);

        //景点主题
        Label condition = new Label();
        condition.setName("公共标签_景点主题");
        Label rootThemeLabel = labelService.findUnique(condition);
        List<Label> themeLabels = labelService.getAllChildsLabels(rootThemeLabel);
        data.put("scenicThemes", themeLabels);

        //酒店位置
        List<HotelRegion> regions = hotelRegionService.listByCity(String.valueOf(350200L));
        data.put("regions", regions);

        //登船地点
        ScenicInfo scenicInfo = new ScenicInfo();
        scenicInfo.setScenicType(ScenicInfoType.sailboat);
        TbArea tbArea = new TbArea();
        tbArea.setId(3502L);
        scenicInfo.setCity(tbArea);
        scenicInfo.setStatus(1);
        List<ScenicInfo> scenicInfos = scenicInfoService.list(scenicInfo, null);
        data.put("scenicInfos", scenicInfos);




        //游轮品牌
        CategoryType categoryType = categoryTypeService.getByType("cruise_ship_brand");
        Category category = new Category();
        category.setType(categoryType);
        category.setParentId(0L);
        List<Category> categories = categoryService.getCategoryList(category);
        data.put("categorieList", categories);


        //游轮航线
        categoryType = categoryTypeService.getByType("cruise_ship_route");
        category.setType(categoryType);
        category.setParentId(0L);
        List<Category> cruiseshipLines = categoryService.getCategoryList(category);
        data.put("cruiseshipLines", cruiseshipLines);


        //当季热销
        condition.setName("一海游PC端_首页_当季热销");
        page = new Page(1, 4);
        List<LabelItem> seasonHotProductList = getProductByLabelList(condition, page);
        data.put("seasonHotProductList", seasonHotProductList);

        //热门景点
        ScenicInfo qryScenic = new ScenicInfo();
        qryScenic.setCityIdLong(3502L);
        page = new Page(1, 6);
        qryScenic.setStatus(1);
        List<ScenicInfo> scenicInfoList = scenicInfoService.getScenicListIndexData(qryScenic, page, "ranking", "asc");
        data.put("scenicInfoList", scenicInfoList);



        //酒店民宿首页数据
        Hotel hotel = new Hotel();
        page = new Page(1, 6);
        hotel.setCityId(3502L);
        hotel.setStatus(ProductStatus.UP);
        List<Hotel> hotels = hotelService.getHotelListIndexData(hotel, page, "h.ranking");
        data.put("hotels", hotels);


        //游轮旅游首页数据
        List<CruiseShip> cruiseShipList = Lists.newArrayList();
        CruiseShip cruiseShip = new CruiseShip();
        page = new Page(1, 5);
        cruiseShip.setStatus(ProductStatus.UP);
        cruiseShip.setStartDateRanges(new String[]{DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss")});
        cruiseShipList = cruiseShipService.getCruiseShipListIndexData(cruiseShip, page, "csd.date");
        data.put("cruiseShipList", cruiseShipList);


        //海上休闲首页数据
        Ticket ticket = new Ticket();
        List<TicketType> includeTicketTypeList = Lists.newArrayList();
        includeTicketTypeList.add(TicketType.sailboat);
        includeTicketTypeList.add(TicketType.yacht);
        includeTicketTypeList.add(TicketType.huanguyou);
        ticket.setIncludeTicketTypeList(includeTicketTypeList);
        ticket.setStatus(ProductStatus.UP);
        page = new Page(1, 5);
        List<Product> sailboatList = ticketService.findListBySql(ticket, page);
        data.put("sailboatList", sailboatList);

        //游记攻略首页数据
        RecommendPlan recommendPlan = new RecommendPlan();
        recommendPlan.setCity(tbArea);
        recommendPlan.setStatus(2);
        List<RecommendPlan> recommendPlans = recommendPlanService.list(recommendPlan, new Page(1, 4), "viewNum", "desc");
        data.put("recommendPlans", recommendPlans);

        FreemarkerUtil.create(data, YHY_INDEX_TEMPLATE, YHY_INDEX_TARGET);
        logger.info("一海游首页构建完成...!");
    }

    private List<LabelItem> getProductByLabelList(Label condition, Page page) {
        List<LabelItem> labelItemList = labelItemService.findItemsByLabel(condition, page);
        List<LabelItem> labelItems = Lists.newArrayList();
        for (LabelItem labelItem : labelItemList) {
            switch (labelItem.getTargetType()) {
                case SAILBOAT:
                    Ticket sailboat = ticketService.loadTicket(labelItem.getTargetId());
                    labelItem.setTargetName(sailboat.getName());
                    List<Productimage> productimageList = productimageService.listImagesBy(sailboat.getId(), true, 1);
                    if (!productimageList.isEmpty()) {
                        labelItem.setTargetCover(productimageList.get(0).getPath());
                    }
                    labelItem.setTargetSource(sailboat.getSource().toString());
                    labelItem.setTargetPrice(ticketPriceService.findTicketMinPrice(sailboat));
                    labelItems.add(labelItem);
                    break;
                case SCENIC:
                    ScenicInfo scenicInfo = scenicInfoService.get(labelItem.getTargetId());
                    labelItem.setTargetName(scenicInfo.getName());
                    labelItem.setTargetCover(scenicInfo.getCover());
                    labelItem.setTargetPrice(scenicInfo.getPrice());
                    labelItem.setTargetSource(ProductSource.CTRIP.toString());
                    labelItems.add(labelItem);
                    break;
                case CRUISESHIP:
                    CruiseShip cruiseShip = cruiseShipService.findById(labelItem.getTargetId());
                    CruiseShipDate cruiseShipDate = cruiseShipDateService.getMinPriceDate(cruiseShip, new Date());
                    if (cruiseShipDate != null) {
                        labelItem.setTargetId(cruiseShipDate.getId());
                        labelItem.setTargetName(cruiseShip.getName());
                        labelItem.setTargetCover(cruiseShip.getCoverImage());
                        labelItem.setTargetSource(cruiseShip.getSource().toString());
                        labelItem.setTargetPrice(cruiseShipService.getMinPriceByCruiseShip(cruiseShip));
                        labelItems.add(labelItem);
                    }
                    break;
                case HOTEL:
                    Hotel hotel = hotelService.get(labelItem.getTargetId());
                    labelItem.setTargetName(hotel.getName());
                    productimageList = productimageService.listImagesBy(hotel.getId(), true, 1);
                    if (!productimageList.isEmpty()) {
                        labelItem.setTargetCover(productimageList.get(0).getPath());
                    }
                    labelItem.setTargetSource(hotel.getSource().toString());
                    labelItem.setTargetPrice(hotelService.getMinPriceByHotel(hotel));
                    labelItems.add(labelItem);
                    break;
                case RECOMMEND_PLAN:
                    break;
                case DELICACY:
                    break;
                case TICKET:
                    break;
                default:
                    break;

            }
        }

        return labelItems;
    }


}

