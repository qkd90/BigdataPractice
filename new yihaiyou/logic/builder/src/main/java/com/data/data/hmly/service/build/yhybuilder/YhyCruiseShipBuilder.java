package com.data.data.hmly.service.build.yhybuilder;

import com.data.data.hmly.service.build.enums.BuilderStatus;
import com.data.data.hmly.service.build.util.FreemarkerUtil;
import com.data.data.hmly.service.common.ProductimageService;
import com.data.data.hmly.service.common.entity.Productimage;
import com.data.data.hmly.service.common.entity.enums.ProductStatus;
import com.data.data.hmly.service.cruiseship.CruiseShipDateService;
import com.data.data.hmly.service.cruiseship.CruiseShipPlanService;
import com.data.data.hmly.service.cruiseship.CruiseShipProjectClassifyService;
import com.data.data.hmly.service.cruiseship.CruiseShipService;
import com.data.data.hmly.service.cruiseship.entity.CruiseShip;
import com.data.data.hmly.service.cruiseship.entity.CruiseShipDate;
import com.data.data.hmly.service.cruiseship.entity.CruiseShipPlan;
import com.data.data.hmly.service.cruiseship.entity.CruiseShipProject;
import com.data.data.hmly.service.entity.Ads;
import com.data.data.hmly.service.goods.CategoryService;
import com.data.data.hmly.service.goods.CategoryTypeService;
import com.data.data.hmly.service.goods.entity.Category;
import com.data.data.hmly.service.goods.entity.CategoryType;
import com.data.data.hmly.util.Clock;
import com.framework.hibernate.util.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zuipin.util.ConcurrentUtil;
import com.zuipin.util.DateUtils;
import com.zuipin.util.GlobalTheadPool;
import com.zuipin.util.SpringContextHolder;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by dy on 2017/1/9.
 */
@Service
public class YhyCruiseShipBuilder {

    private Logger logger = Logger.getLogger(YhyCruiseShipBuilder.class);

    private static final String YHY_CRUISESHIP_INDEX_TEMPLATE = "/yhy/cruiseship/index.ftl";
    private static final String YHY_CRUISESHIP_INDEX_TARGET = "/yhy/cruiseship/index.htm";
    private static final String YHY_CRUISESHIP_DETAIL_TEMPLATE = "/yhy/cruiseship/detail.ftl";
    private static final String YHY_CRUISESHIP_DETAIL_TARGET = "/yhy/cruiseship/detail%d.htm";
    private static final String YHY_CRUISESHIP_HEAD_TEMPLATE = "/yhy/cruiseship/head.ftl";
    private static final String YHY_CRUISESHIP_HEAD_TARGET = "/yhy/cruiseship/head%d.htm";

    @Resource
    private YhyAdsBuilder yhyAdsBuilder;
    @Resource
    private CategoryService categoryService;
    @Resource
    private CategoryTypeService categoryTypeService;
    @Resource
    private CruiseShipService cruiseShipService;
    @Resource
    private CruiseShipProjectClassifyService cruiseShipProjectClassifyService;
    @Resource
    private CruiseShipPlanService cruiseShipPlanService;
    @Resource
    private ProductimageService productimageService;
    @Resource
    private CruiseShipDateService cruiseShipDateService;

    private CruiseShip cruiseShip = new CruiseShip();
    private CruiseShipDate cruiseShipDate = new CruiseShipDate();
    private CruiseShipProject cruiseShipProject = new CruiseShipProject();
    private final AtomicInteger buildingCount = new AtomicInteger();
    private final AtomicLong buildingCost = new AtomicLong();
    private static Long currentId;
    private BuilderStatus status = BuilderStatus.IDLE;
    private static final int PAGE_SIZE = 100;

    public void buildCruiseshipIndex() {

        Map<Object, Object> data = new HashMap<Object, Object>();
        //广告
        List<Ads> adses = yhyAdsBuilder.getAds(YhyAdsBuilder.YHY_CRUISESHIP_INDEX_TOP_BANNER);
        data.put("adses", adses);

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


        List<CruiseShip> hotSeasonCruiseShipList = Lists.newArrayList();
        List<CruiseShip> hotRecCruiseShipList = Lists.newArrayList();
        Page page = new Page(1, 5);
        cruiseShip.setStartDateRanges(new String[]{DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss")});
        //当季热门
        hotSeasonCruiseShipList = cruiseShipService.getCruiseShipListIndexData(cruiseShip, page, "avg(cosr.score)", "desc");
        data.put("hotSeasonCruiseShipList", hotSeasonCruiseShipList);

        //热门推荐
        hotRecCruiseShipList = cruiseShipService.getCruiseShipListIndexData(cruiseShip, page, "csd.minDiscountPrice");
        data.put("hotRecCruiseShipList", hotRecCruiseShipList);
        FreemarkerUtil.create(data, YHY_CRUISESHIP_INDEX_TEMPLATE, YHY_CRUISESHIP_INDEX_TARGET);
        logger.info("一海游游轮首页构建完成...!");
    }

    public CruiseShip getCruiseShip() {
        return cruiseShip;
    }

    public void setCruiseShip(CruiseShip cruiseShip) {
        this.cruiseShip = cruiseShip;
    }

    public void buildCruiseShipDetail(Long dateId) {
        cruiseShipDate = cruiseShipDateService.findById(dateId);
//        cruiseShip = cruiseShipService.findById(dateId);
        buildYhyDetail(cruiseShipDate);
    }

    private void buildYhyDetail(CruiseShipDate cruiseShipDate) {
        if (cruiseShipDate.getCruiseShip().getStatus() != ProductStatus.UP) {
            return;
        }
        Clock clock = new Clock();
        Map<Object, Object> data = Maps.newHashMap();
        data.put("cruiseShipDate", cruiseShipDate);
//        List<CruiseShipDate> cruiseShipDates = cruiseShipDateService.listByCruiseShipId(cruiseShip.getId(), new Date());
        List<Productimage> productimages = productimageService.findAllImagesByProIdAadTarId(cruiseShipDate.getCruiseShip().getId(), null, "cruiseship/info/");
        List<CruiseShipPlan> cruiseShipPlanList = cruiseShipPlanService.listByCruiseShipId(cruiseShipDate.getCruiseShip().getId());
        cruiseShip = cruiseShipDate.getCruiseShip();
        cruiseShip.getCruiseShipPlans().clear();
        cruiseShip.setCruiseShipPlans(new HashSet<CruiseShipPlan>(cruiseShipPlanList));
        data.put("productimages", productimages);

        cruiseShip.setStartDate(cruiseShipDate.getDate());
        cruiseShip.setDateId(cruiseShipDate.getId());
        cruiseShip.setPrice(cruiseShipDate.getMinDiscountPrice());
        data.put("cruiseShip", cruiseShip);

        for(CruiseShipPlan cruiseShipPlan : cruiseShipPlanList) {
            Date date = DateUtils.add(cruiseShipDate.getDate(), Calendar.DATE, cruiseShipPlan.getDay()-1);
            cruiseShipPlan.setBeginDay(date);
        }
        data.put("cruiseShipPlanList", cruiseShipPlanList);
        FreemarkerUtil.create(data, YHY_CRUISESHIP_HEAD_TEMPLATE, String.format(YHY_CRUISESHIP_HEAD_TARGET, cruiseShipDate.getId()));
        FreemarkerUtil.create(data, YHY_CRUISESHIP_DETAIL_TEMPLATE, String.format(YHY_CRUISESHIP_DETAIL_TARGET, cruiseShipDate.getId()));


        currentId = cruiseShipDate.getCruiseShip().getId();
        buildingCount.getAndIncrement();
        buildingCost.getAndAdd(clock.totalTime());
        logger.info("build cruiseShip detail#" + cruiseShipDate.getCruiseShip().getId() + " success, cost " + clock.totalTime());
    }

    public void buildYhyDetail(final Long startId, final Long endId) {
        GlobalTheadPool.instance.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                return buildYhyDetailTask(startId, endId);
            }
        });
    }

    public void buildYhyDetail() {
        if (status == BuilderStatus.RUNNING) {
            return;
        }
        GlobalTheadPool.instance.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                return buildYhyDetailTask();
            }
        });
    }

    private Object buildYhyDetailTask() {
        status = BuilderStatus.RUNNING;
        int current;
        int page = 1;
        do {
            SessionFactory sessionFactory = (SessionFactory) SpringContextHolder.getBean("sessionFactory");
            boolean participate = ConcurrentUtil.bindHibernateSessionToThread(sessionFactory);
            cruiseShip.setStatus(ProductStatus.UP);
            List<CruiseShipDate> cruiseShipDates = cruiseShipDateService.listByCruiseShipId(null, new Date());
            for (CruiseShipDate shipDate : cruiseShipDates) {
                buildYhyDetail(shipDate);
                currentId = shipDate.getId();
            }
            current = cruiseShipDates.size();
            page++;
            ConcurrentUtil.closeHibernateSessionFromThread(participate, sessionFactory);

        } while (current == PAGE_SIZE);
        status = BuilderStatus.IDLE;
        return null;
    }

    private Object buildYhyDetailTask(Long startId, Long endId) {
        status = BuilderStatus.RUNNING;
        buildingCount.set(0);
        buildingCost.set(0);
        currentId = startId - 1;
        int current;
        Clock clock = new Clock();
        do {
            SessionFactory sessionFactory = (SessionFactory) SpringContextHolder.getBean("sessionFactory");
            boolean participate = ConcurrentUtil.bindHibernateSessionToThread(sessionFactory);
//            List<CruiseShip> cruiseShipList = cruiseShipService.getInIdRange(currentId, endId, PAGE_SIZE);
            List<CruiseShipDate> cruiseShipDates = cruiseShipDateService.getInIdRange(currentId, endId, new Date(), PAGE_SIZE);
            for (CruiseShipDate shipDate : cruiseShipDates) {
                buildYhyDetail(shipDate);
                currentId = shipDate.getId();
                logger.info("build #" + shipDate.getId() + "finished, cost " + clock.elapseTime());
            }
            current = cruiseShipDates.size();
            ConcurrentUtil.closeHibernateSessionFromThread(participate, sessionFactory);
        } while (current == PAGE_SIZE);
        logger.info("build finished, cost " + clock.totalTime() + "ms");
        status = BuilderStatus.IDLE;
        return null;
    }

    public void buildAllCruiseship(Long cruiseShipId) {
        List<CruiseShipDate> cruiseShipDateList = cruiseShipDateService.listByCruiseShipId(cruiseShipId, new Date());
        for (CruiseShipDate shipDate : cruiseShipDateList) {
            buildYhyDetail(shipDate);
        }
    }
}
