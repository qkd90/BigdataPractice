package com.data.data.hmly.action.lvxbang;

import com.data.data.hmly.service.area.DestinationService;
import com.data.data.hmly.service.handdrawing.HandDrawService;
import com.data.data.hmly.service.handdrawing.entity.HandDrawMap;
import com.data.data.hmly.service.hotel.HotelRoomIndexService;
import com.data.data.hmly.service.hotel.HotelService;
import com.data.data.hmly.service.lvxbang.AdvertisingService;
import com.data.data.hmly.service.lvxbang.PlanOperationService;
import com.data.data.hmly.service.lvxbang.request.PlanUpdateRequest;
import com.data.data.hmly.service.plan.PlanService;
import com.data.data.hmly.service.plan.RecommendPlanService;
import com.data.data.hmly.service.plan.entity.Plan;
import com.data.data.hmly.service.plan.request.RecommendPlanSearchRequest;
import com.data.data.hmly.service.plan.vo.RecommendPlanSolrEntity;
import com.data.data.hmly.service.restaurant.DelicacyService;
import com.data.data.hmly.service.restaurant.RestaurantService;
import com.data.data.hmly.service.restaurant.entity.Delicacy;
import com.data.data.hmly.service.restaurant.entity.Restaurant;
import com.data.data.hmly.service.restaurant.request.DelicacySearchRequest;
import com.data.data.hmly.service.restaurant.vo.DelicacySolrEntity;
import com.data.data.hmly.service.scenic.ScenicInfoService;
import com.data.data.hmly.service.scenic.entity.ScenicInfo;
import com.data.data.solr.MulticoreSolrTemplate;
import com.framework.hibernate.util.Page;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.opensymphony.xwork2.Result;
import com.zuipin.action.JxmallAction;
import com.zuipin.util.JsonFilter;
import net.sf.json.JSONArray;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Jonathan.Guo
 */
public class TestWebAction extends JxmallAction {

    @Resource
    private ScenicInfoService scenicInfoService;
    @Resource
    private RestaurantService restaurantService;
    @Resource
    private DelicacyService delicacyService;
    @Resource
    private PlanService planService;
    @Resource
    private RecommendPlanService recommendPlanService;
    @Resource
    private HotelService hotelService;
    @Resource
    private HandDrawService handDrawService;
    @Resource
    private AdvertisingService advertisingService;
    @Resource
    private DestinationService destinationService;
    @Resource
    private PlanOperationService planOperationService;
//    @Resource
//    private PlanBookingService planBookingService;
    @Resource
    private HotelRoomIndexService hotelRoomIndexService;
    @Resource
    private MulticoreSolrTemplate solrTemplate;

    private PlanUpdateRequest plan = new PlanUpdateRequest();

    public Result test() {
//        System.out.println(1);
//        return text(scenicInfoService.get(1270l).toString());
        String s = getRequest().getHeader("x-real-ip");
        return text(s);
    }

    public Result scenic() {
        ScenicInfo scenic = new ScenicInfo();

        try {
            if (getRequest().getParameter("startId") != null) {
                long startId = Long.parseLong(getRequest().getParameter("startId").toString());
                scenic.setStartId(startId);
            }
            if (getRequest().getParameter("endId") != null) {
                long endId = Long.parseLong(getRequest().getParameter("endId").toString());
                scenic.setEndId(endId);
            }
            scenicInfoService.indexScenicInfoAll(scenic);
            return text("索引成功");
        } catch ( Exception e) {
            e.printStackTrace();
            return text("索引失败!");
        }

    }

    public Result scenic2() {
        String ids = "1042926,1042935,1042944,1042945,1042960,1042961,1042929,1042934,1042939,1042941,1042949,1042952,1042955,1042956,1042957,1042958,1042959,1042962,1042963,1042964,1042965,1042966,1042967,1042968,1042970,1042971,1042972,1042973,1042974,1042978,1042979,1042980,1042981,1042983,1042984,1042985,1042986,1042987,1042988,1042989,1042990,1042992,1042993,1059640,1059641,1059642,1059643,1059644,1059645,1059646,1059648,1059650,1059651,1059652,1059653,1059654,1059655,1059656,1059658,1059659,1059660,1059662,1059664,1059666,1059667,1059668,1059669,1059671,1059672,1059673,1059674,1059675,1059677,1059678,1059679,1059681,1059683,1059685,1059687,1059691,1059692,1059693,1059695,1059696,1059698,1059701,1059702,1059704,1059705,1059706,1059707,1059708,1059710,1059711,1059712,1059713,1059715,1059716,1059717,1059718,1059719,1059720,1059722,1059723,1059724,1059725,1059726,1059728,1059730,1059733,1059735,1059739,1059740,1059741,1059746,1059747,1059749,1059751,1059752,1059754,1059759,1059761,1059763,1059766,1059767,1059771,1059772,1059774,1059780,1059781,1059783,1059784,1059785,1059789,1059790,1059791,1059792,1059793,1059794,1059796,1059797,1059798,1059802,1059804,1059805,1059806,1059807,1059808,1059809,1059811,1059817,1059820,1059821,1059822,1059823,1059824,1059825,1059830,1059833,1059835,1059837,1059839,1059840,1059841,1059843,1059844,1059846,1059848,1059849,1059850,1059853,1059854,1059858,1059860,1059866,1059868,1059872,1059873,1059874,1059876,1059877,1059878,1059880,1059884,1059886,1059888,1059890,1059891,1059893,1059894,1059898,1059900,1059901,1059902,1059904,1059905,1059907,1059908,1059911,1059912,1059913,1059914,1059915,1059916,1059917,1059922,1059924,1059925,1059926,1059928,1059929,1059930,1059932,1059933,1059937,1059938,1059939,1059942,1059943,1059944,1059947,1059948,1059950,2060068,2060069,2060074,2060076,2060080,2060081,2060082,2060084,2060085,2060086,2060087,1059649,1059686,1059699,1059703,1059736,1059757,1059826,1059832,1059847,1059855,1059861,1059871,1059879,1059919,1059945,2060088,1059690,1059731,1059734,1059738,1059742,1059744,1059758,1059760,1059765,1059768,1059769,1059773,1059795,1059800,1059801,1059810,1059812,1059816,1059818,1059819,1059829,1059852,1059856,1059867,1059869,1059899,1059920,2060073,2060075,1059647,1059657,1059663,1059670,1059676,1059680,1059684,1059694,1059700,1059709,1059727,1059729,1059750,1059756,1059762,1059770,1059775,1059777,1059779,1059786,1059799,1059803,1059815,1059828,1059836,1059838,1059842,1059857,1059862,1059865,1059881,1059882,1059885,1059887,1059892,1059927,1059931,1059940,2060077,1059665,1059682,1059689,1059697,1059732,1059737,1059743,1059745,1059748,1059753,1059755,1059764,1059776,1059778,1059813,1059814,1059827,1059831,1059834,1059859,1059863,1059864,1059870,1059875,1059883,1059889,1059895,1059897,1059903,1059906,1059918,1059921,1059923,1059946,2060083,1059661,1059714,1059721,1059787,1059845,1059851,1059896,1059909,1059910";
        String[] list = ids.split(",");
        for (String s : list) {
            scenicInfoService.indexScenicInfo(scenicInfoService.get(Long.parseLong(s)));
        }
        return text("123");
    }

    public Result restaurant() {
        List<Restaurant> list = restaurantService.listAll();
        List<String> names = Lists.transform(list, new Function<Restaurant, String>() {
            @Override
            public String apply(Restaurant restaurant) {
                return restaurant.getName();
            }
        });
        return json(JSONArray.fromObject(names));
    }

    public Result delicacy() {
        List<Delicacy> list = delicacyService.listAll();
        List<String> names = Lists.transform(list, new Function<Delicacy, String>() {
            @Override
            public String apply(Delicacy delicacy) {
                return delicacy.getName();
            }
        });
        return json(JSONArray.fromObject(names));
    }

    public Result delicacy2() {
//		Delicacy delicacy = delicacyService.get(5l);
        delicacyService.indexDelicacy();
        return text("1234");
    }

    public Result delicacy3() {
        DelicacySearchRequest request = new DelicacySearchRequest();
        List<DelicacySolrEntity> list = delicacyService.listFromSolr(request, new Page(0, 5));
        return json(JSONArray.fromObject(list));
    }

    public Result plan() {
        List<Plan> list = planService.listAll();
        List<String> names = Lists.transform(list, new Function<Plan, String>() {
            @Override
            public String apply(Plan plan) {
                return plan.getName();
            }
        });
        return json(JSONArray.fromObject(names));
    }

    public Result plan2() {
        return text(plan.id.toString());
    }

    public Result plan3() {
//        List<PlanBookingResponse> result = planBookingService.doBook(Long.valueOf(getParameter("planId").toString()), null, null);
//        for (PlanBookingResponse response : result) {
//            System.out.println(response.getCityName());
//        }
        return text("success");
    }

    public Result recommend() {
        recommendPlanService.indexRecommendPlan(null, null);
        return text("123");
    }

    public Result recommend2() {
        RecommendPlanSearchRequest request = new RecommendPlanSearchRequest();
        List<RecommendPlanSolrEntity> list = recommendPlanService.listFromSolr(request, new Page(0, 10));
        return json(JSONArray.fromObject(list));
    }

    public Result hotel() {
        hotelService.indexHotel();
//        hotelRoomIndexService.indexHotelRoom();
        return text("123");
    }

    public Result hotel2() {
//        hotelRoomIndexService.indexHotelRoom();
        return text("123");
    }

    public PlanUpdateRequest getPlan() {
        return plan;
    }

    public void setPlan(PlanUpdateRequest plan) {
        this.plan = plan;
    }

    public Result handDraw() {
        List<HandDrawMap> handDrawMaps = handDrawService.getByCity(Long.valueOf(getParameter("city").toString()));
        return json(JSONArray.fromObject(handDrawMaps, JsonFilter.getIncludeConfig()));
    }

    public Result ads() {
        return json(JSONArray.fromObject(advertisingService.getIndexBanner(), JsonFilter.getIncludeConfig()));
    }

    public Result destination() {
        destinationService.indexDelicacy();
        return text("success");
    }

    public Result testExport() {
        planOperationService.exportRecommend(Long.valueOf(getParameter("planId").toString()));
        return text("123");
    }

    public Result deleteSolrByCity() {
        String cityId = getParameter("cityId").toString();
        solrTemplate.deleteByQuery("type:scenic_info AND cityId:" + cityId, "products");
        return text("success");
    }

    public Result delRec() {
        String id = getParameter("id").toString();
        solrTemplate.deleteById(id, "products");
        return text("123");
    }
}
