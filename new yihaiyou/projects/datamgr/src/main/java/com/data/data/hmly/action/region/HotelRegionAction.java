package com.data.data.hmly.action.region;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.TbAreaService;
import com.data.data.hmly.service.base.ActionResult;
import com.data.data.hmly.service.base.ErrorCode;
import com.data.data.hmly.service.base.ResultModel;
import com.data.data.hmly.service.base.util.GeoUtil;
import com.data.data.hmly.service.base.util.HttpUtil;
import com.data.data.hmly.service.base.util.JsonUtil;
import com.data.data.hmly.service.common.entity.enums.ProductStatus;
import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.hotel.HotelService;
import com.data.data.hmly.service.hotel.entity.Hotel;
import com.data.data.hmly.service.hotel.entity.HotelArea;
import com.data.data.hmly.service.hotel.entity.HotelRegion;
import com.data.data.hmly.service.region.HotelAreaMgrService;
import com.data.data.hmly.service.region.HotelRegionMgrService;
import com.framework.hibernate.util.Page;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.JsonFilter;
import net.sf.json.JSONObject;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.geom.GeneralPath;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HotelRegionAction extends FrameBaseAction implements ModelDriven<HotelRegion> {

    @Resource
    private HotelRegionMgrService hotelRegionMgrService;
    @Resource
    private HotelService hotelService;
    @Resource
    private HotelAreaMgrService hotelAreaMgrService;
    @Resource
    private TbAreaService tbAreaService;

    private HotelRegion region = new HotelRegion();

    /**
     * 返回区域列表页面
     *
     * @return
     */
    public Result toList() {
        return dispatch("/WEB-INF/jsp/map/hotelRegionList.jsp");
    }

    /**
     * 查询列表
     *
     * @return
     */
    public Result list() {
        final HttpServletRequest request = getRequest();
        Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
        ResultModel<HotelRegion> result = hotelRegionMgrService.page(HotelRegion.class, paramMap);
        return json(JSONObject.fromObject(result, JsonFilter.getIncludeConfig("city")));
    }

    /**
     * 查询列表
     *
     * @return
     */
    public Result listHotels() {
        final HttpServletRequest request = getRequest();
        Integer areaid = Integer.parseInt(request.getParameter("areaid"));
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("areaid", areaid);
        ResultModel<HotelArea> result = hotelAreaMgrService.page(HotelArea.class, paramMap);
        return json(JSONObject.fromObject(result, JsonFilter.getIncludeConfig("hotel")));
    }

    public Result add() {
        final HttpServletRequest request = getRequest();
        HotelRegion si = new HotelRegion();
        request.setAttribute("region", si);
        return dispatch("/WEB-INF/jsp/map/hotelRegionBmapEdit.jsp");
    }

    public Result edit() throws UnsupportedEncodingException {
        final HttpServletRequest request = getRequest();
        Long id = Long.parseLong(request.getParameter("id"));
        HotelRegion si = hotelRegionMgrService.selById(id);
        request.setAttribute("region", si);
        return dispatch("/WEB-INF/jsp/map/hotelRegionBmapEdit.jsp");
    }

    public Result del() {
        final HttpServletRequest request = getRequest();
        Long id = Long.parseLong(request.getParameter("id"));
        hotelRegionMgrService.del(id);
        return json(JSONObject.fromObject(ActionResult.createSuccess()));
    }

    public Result recompute() {
        Map<String, Object> result = new HashMap<String, Object>();
        String cityCode = getParameter("cityCode").toString();
        if (cityCode.length() != 6) {
            result.put("success", false);
            result.put("msg", "城市代码格式不正确, 至少6位");
        }
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("cityCode", cityCode.substring(0, 4));
//        paramMap.put("cityId", Integer.parseInt(cityCode));
        List<HotelRegion> regions = hotelRegionMgrService.page(HotelRegion.class, paramMap).getRows();
        if (regions == null || regions.size() == 0) {
            result.put("success", false);
            result.put("msg", "该城市没有可用的酒店区域!无法重算!请先为该城市划分酒店区域");
        }
        Map<Long, GeneralPath> paths = new HashMap<>();
        Map<Long, String> pathNames = new HashMap<>();
        Map<Long, Integer> pathOrders = new HashMap<>();
        for (HotelRegion region : regions) {
            hotelAreaMgrService.deleteByAreaId(region.getId());
            if (region.getPointStr() == null) {
                continue;
            }
            paths.put(region.getId(), GeoUtil.getGeneralPath(region.getPointStr()));
            pathNames.put(region.getId(), region.getName());
            pathOrders.put(region.getId(), 0);
        }
        Hotel condition = new Hotel();
        condition.setStatus(ProductStatus.UP);
        condition.setCityId(Long.parseLong(cityCode));
        List<Hotel> hotels = hotelService.list(condition, new Page(1, 100000));
        for (Hotel hotel : hotels) {
            LOG.info("酒店ID: " + hotel.getId());
            if (hotel.getExtend() == null) {
                continue;
            }
            if (hotel.getExtend().getLatitude() == null || hotel.getExtend().getLongitude() == null) {
                continue;
            }
            for (Map.Entry<Long, GeneralPath> path : paths.entrySet()) {
                if (GeoUtil.pointInArea(path.getValue(), hotel.getExtend().getLongitude(), hotel.getExtend().getLatitude())) {
                    HotelArea area = new HotelArea();
                    HotelRegion region = new HotelRegion();
                    region.setId(path.getKey());
                    area.setRegion(region);
                    area.setAreaName(pathNames.get(path.getKey()));
                    area.setHotelId(hotel.getId());
                    area.setHotelName(hotel.getName());
                    int order = pathOrders.get(path.getKey()) + 1;
                    area.setRanking(order);
                    pathOrders.put(path.getKey(), order);
                    hotelAreaMgrService.insert(area);
                    continue;
                }
            }
        }
        hotelService.indexHotels(hotels);
        result.put("success", true);
        result.put("msg", "");
        return json(JSONObject.fromObject(result));
    }

    public Result saveRegion() throws IOException {
        final HttpServletResponse response = getResponse();
        Long cityCode = Long.parseLong(getParameter("city.id").toString());
        if (region.getId() == 0) {
            region.setId(null);
            TbArea tbArea = tbAreaService.getArea(cityCode);
            region.setCity(tbArea);
            hotelRegionMgrService.insert(region);
        } else {
            TbArea tbArea = tbAreaService.getArea(cityCode);
            region.setCity(tbArea);
            hotelRegionMgrService.update(region);
        }
        JsonUtil.jsonToResponse(ActionResult.createSuccess(), response);
        return null;
    }

    @Override
    public HotelRegion getModel() {
        return region;
    }

    public HotelRegion getRegion() {
        return region;
    }

    public void setRegion(HotelRegion region) {
        this.region = region;
    }
}
