package com.data.data.hmly.action.region;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.TbAreaService;
import com.data.data.hmly.service.base.ActionResult;
import com.data.data.hmly.service.base.ErrorCode;
import com.data.data.hmly.service.base.ResultModel;
import com.data.data.hmly.service.base.util.GeoUtil;
import com.data.data.hmly.service.base.util.HttpUtil;
import com.data.data.hmly.service.base.util.JsonUtil;
import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.region.RegionMgrService;
import com.data.data.hmly.service.region.ScenicAreaMgrService;
import com.data.data.hmly.service.scenic.ScenicInfoMgrService;
import com.data.data.hmly.service.scenic.entity.Region;
import com.data.data.hmly.service.scenic.entity.ScenicArea;
import com.data.data.hmly.service.scenic.entity.ScenicInfo;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegionAction extends FrameBaseAction implements ModelDriven<Region> {

    @Resource
    private RegionMgrService regionMgrService;
    @Resource
    private ScenicInfoMgrService scenicInfoMgrService;
    @Resource
    private ScenicAreaMgrService scenicAreaMgrService;
    @Resource
    private TbAreaService tbAreaService;

    private Region region = new Region();

    /**
     * 返回区域列表页面
     *
     * @return
     */
    public Result toList() {
        return dispatch("/WEB-INF/jsp/map/regionList.jsp");
    }

    /**
     * 查询列表
     *
     * @return
     */
    public Result list() {
        final HttpServletRequest request = getRequest();
        Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
        ResultModel<Region> result = regionMgrService.page(Region.class, paramMap);
        return json(JSONObject.fromObject(result, JsonFilter.getIncludeConfig("city")));
    }

    /**
     * 查询列表
     *
     * @return
     */
    public Result listScenics() {
        final HttpServletRequest request = getRequest();
        Integer areaid = Integer.parseInt(request.getParameter("areaid"));
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("areaid", areaid);
        ResultModel<ScenicArea> result = scenicAreaMgrService.page(ScenicArea.class, paramMap);
        return json(JSONObject.fromObject(result, JsonFilter.getIncludeConfig("scenicInfo")));
    }

    public Result add() {
        final HttpServletRequest request = getRequest();
        Region si = new Region();
        request.setAttribute("region", si);
        return dispatch("/WEB-INF/jsp/map/regionBmapEdit.jsp");
    }

    public Result amapAdd() {
        final HttpServletRequest request = getRequest();
        Region si = new Region();
        request.setAttribute("region", si);
        return dispatch("/WEB-INF/jsp/map/regionAmapEdit.jsp");
    }

    public Result edit() throws UnsupportedEncodingException {
        final HttpServletRequest request = getRequest();
        Long id = Long.parseLong(request.getParameter("id"));
        Region si = regionMgrService.selById(id);
        request.setAttribute("region", si);
        return dispatch("/WEB-INF/jsp/map/regionBmapEdit.jsp");
    }

    public Result amapEdit() throws UnsupportedEncodingException {
        final HttpServletRequest request = getRequest();
        Long id = Long.parseLong(request.getParameter("id"));
        Region si = regionMgrService.selById(id);
        request.setAttribute("region", si);
        return dispatch("/WEB-INF/jsp/map/regionAmapEdit.jsp");
    }

    public Result del() {
        final HttpServletRequest request = getRequest();
        Long id = Long.parseLong(request.getParameter("id"));
        regionMgrService.del(id);
        return json(JSONObject.fromObject(ActionResult.createSuccess()));
    }

    public Result recompute() {
        Map<String, Object> result = new HashMap<String, Object>();
        String cityCode = getParameter("cityCode").toString();
        if (cityCode.length() != 6) {
            if (cityCode.length() != 6) {
                result.put("success", false);
                result.put("msg", "城市代码格式不正确, 至少6位");
            }
        }
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("cityCode", cityCode.substring(0, 4));
        List<Region> regions = regionMgrService.page(Region.class, paramMap).getRows();
        if (regions == null || regions.size() == 0) {
            result.put("success", false);
            result.put("msg", "该城市没有可用的景点区域!无法重算!请先为该城市划分酒店区域");
        }
        Map<Long, GeneralPath> paths = new HashMap<>();
        Map<Long, String> pathNames = new HashMap<>();
        Map<Long, Integer> pathOrders = new HashMap<>();
        for (Region region : regions) {
            scenicAreaMgrService.deleteByAreaId(region.getId());
            if (region.getPointStr() == null) {
                continue;
            }
            paths.put(region.getId(), GeoUtil.getGeneralPath(region.getPointStr()));
            pathNames.put(region.getId(), region.getName());
            pathOrders.put(region.getId(), 0);
        }
        paramMap.put("status", 1);
        paramMap.put("deleteFlag", "F");
        List<ScenicInfo> scenics = scenicInfoMgrService.list(ScenicInfo.class, paramMap);
        for (ScenicInfo scenic : scenics) {
            for (Map.Entry<Long, GeneralPath> path : paths.entrySet()) {
                if (GeoUtil.pointInArea(path.getValue(), scenic.getScenicGeoinfo().getBaiduLng(), scenic.getScenicGeoinfo().getBaiduLat())) {
                    ScenicArea area = new ScenicArea();
                    Region region = new Region();
                    region.setId(path.getKey());
                    area.setRegion(region);
                    area.setAreaName(pathNames.get(path.getKey()));
                    area.setScenicInfo(scenic);
//                    area.setScenicIds(scenic.getId());
                    area.setScenicName(scenic.getName());
                    int order = pathOrders.get(path.getKey()) + 1;
                    area.setRanking(order);
                    pathOrders.put(path.getKey(), order);
                    scenicAreaMgrService.insert(area);
                    continue;
                }
            }
        }
        scenicInfoMgrService.indexScenicInfo(scenics);
        result.put("success", true);
        result.put("msg", "");
        return json(JSONObject.fromObject(result));
    }

    public Result recomputeGoogle() {
        String cityCode = getParameter("cityCode").toString();
        if (cityCode.length() != 6)
            return json(JSONObject.fromObject(ActionResult.createFail(ErrorCode.ERROR_50001)));
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("cityCode", cityCode.substring(0, 4));
        List<Region> regions = regionMgrService.page(Region.class, paramMap).getRows();
        if (regions == null || regions.size() == 0) {
            return json(JSONObject.fromObject(ActionResult.createFail(ErrorCode.ERROR_52006)));
        }
        Map<Long, GeneralPath> paths = new HashMap<>();
        Map<Long, String> pathNames = new HashMap<>();
        Map<Long, Integer> pathOrders = new HashMap<>();
        for (Region region : regions) {
            scenicAreaMgrService.deleteByAreaId(region.getId());
            if (region.getPointStr() == null) {
                continue;
            }
            paths.put(region.getId(), GeoUtil.getGeneralPath(region.getPointStr()));
            pathNames.put(region.getId(), region.getName());
            pathOrders.put(region.getId(), 0);
        }
        paramMap.put("status", 1);
        paramMap.put("deleteFlag", "F");
        List<ScenicInfo> scenics = scenicInfoMgrService.list(ScenicInfo.class, paramMap);
        for (ScenicInfo scenic : scenics) {
            for (Map.Entry<Long, GeneralPath> path : paths.entrySet()) {
                if (GeoUtil.pointInArea(path.getValue(), scenic.getScenicGeoinfo().getGoogleLng(), scenic.getScenicGeoinfo().getGoogleLng())) {
                    ScenicArea area = new ScenicArea();
                    Region region = new Region();
                    region.setId(path.getKey());
                    area.setRegion(region);
                    area.setAreaName(pathNames.get(path.getKey()));
                    area.setScenicInfo(scenic);
//                    area.setScenicIds(scenic.getId());
                    area.setScenicName(scenic.getName());
                    int order = pathOrders.get(path.getKey()) + 1;
                    area.setRanking(order);
                    pathOrders.put(path.getKey(), order);
                    scenicAreaMgrService.insert(area);
                    continue;
                }
            }
        }

        return json(JSONObject.fromObject(ActionResult.createSuccess()));
    }

    public Result saveRegion() throws IOException {
        final HttpServletResponse response = getResponse();
        final HttpServletRequest request = getRequest();
        Long cityCode = Long.parseLong(getParameter("city.id").toString());
        Integer regionType = Integer.parseInt(request.getParameter("type"));
        if (region.getId() == 0) {
            // 新建区域
            region.setId(null);
            TbArea tbArea = tbAreaService.getArea(cityCode);
            region.setCity(tbArea);
            region.setRegionType(regionType);
            region.setCreateTime(new Date());
            region.setUpdateTime(new Date());
            regionMgrService.insert(region);
        } else {
            // 更新区域
            TbArea tbArea = tbAreaService.getArea(cityCode);
            Region sourceRegion = regionMgrService.selById(region.getId());
            sourceRegion.setName(region.getName());
            sourceRegion.setPointStr(region.getPointStr());
            sourceRegion.setPriority(region.getPriority());
            sourceRegion.setDescription(region.getDescription());
            sourceRegion.setCityCode(region.getCityCode());
            sourceRegion.setUpdateTime(new Date());
            sourceRegion.setCity(tbArea);
            sourceRegion.setRegionType(regionType);
            regionMgrService.update(sourceRegion);
        }
        JsonUtil.jsonToResponse(ActionResult.createSuccess(), response);
        return null;
    }

    @Override
    public Region getModel() {
        return region;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }
}
