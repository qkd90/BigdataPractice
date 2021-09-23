package com.data.data.hmly.action.lvxbang;

/**
 * Created by HMLY on 2016/6/2.
 */

import com.data.data.hmly.action.other.util.VisitHistoryCookieUtils;
import com.data.data.hmly.service.LabelService;
import com.data.data.hmly.service.TbAreaService;
import com.data.data.hmly.service.common.entity.enums.ProductStatus;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.entity.Label;
import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.hotel.HotelService;
import com.data.data.hmly.service.hotel.entity.Hotel;
import com.data.data.hmly.service.line.LineService;
import com.data.data.hmly.service.line.entity.Line;
import com.data.data.hmly.service.line.entity.enums.ProductAttr;
import com.data.data.hmly.service.line.request.LineSearchRequest;
import com.data.data.hmly.service.line.vo.LineSolrEntity;
import com.data.data.hmly.service.lvxbang.SuggestService;
import com.data.data.hmly.service.other.OtherVisitHistoryService;
import com.data.data.hmly.service.other.entity.OtherVisitHistory;
import com.data.data.hmly.service.scenic.ScenicInfoService;
import com.data.data.hmly.service.scenic.entity.ScenicInfo;
import com.framework.hibernate.util.Page;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.FileUtil;
import com.zuipin.util.JsonFilter;
import com.zuipin.util.StringUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


public class LineWebAction extends LxbAction {

    @Resource
    private LineService lineService;
//    @Resource
//    private PlaytitleService playtitleService;
    @Resource
    private SuggestService suggestService;
    @Resource
    private TbAreaService tbAreaService;
    @Resource
    private HotelService hotelService;
    @Resource
    private LabelService labelService;
    @Resource
    private ScenicInfoService scenicInfoService;
    @Resource
    private OtherVisitHistoryService otherVisitHistoryService;

    public Line line;
    public List<Line> lineList = new ArrayList<Line>();
    public Integer pageNo = 0;
    public Integer pageSize = 10;
    public LineSearchRequest lineSearchRequest = new LineSearchRequest();
    public Long cityId;
    public Long lineId;
    public TbArea city;
    public Long startCityId;
    public String startDate;
    public TbArea startCity;

    public Integer cloneNum;

    public final Integer hotSize = 5;

    public Result groupTourIndex() {
        setAttribute(LXBConstants.LVXBANG_LINE_GROUP_TOUR_INDEX_KEY, FileUtil.loadHTML(LXBConstants.LVXBANG_LINE_GROUP_TOUR_INDEX));
        return dispatch("/WEB-INF/jsp/lvxbang/line/group_tour_index.jsp");
    }

    public Result selfTourIndex() {
        setAttribute(LXBConstants.LVXBANG_LINE_SELF_TOUR_INDEX_KEY, FileUtil.loadHTML(LXBConstants.LVXBANG_LINE_SELF_TOUR_INDEX));
        return dispatch("/WEB-INF/jsp/lvxbang/line/self_tour_index.jsp");
    }

    public Result customTourIndex() {
        setAttribute(LXBConstants.LVXBANG_LINE_CUSTOM_TOUR_INDEX_KEY, FileUtil.loadHTML(LXBConstants.LVXBANG_LINE_CUSTOM_TOUR_INDEX));
        return dispatch("/WEB-INF/jsp/lvxbang/line/custom_tour_index.jsp");
    }

    public Result gtiMore() {
        final HttpServletRequest request = getRequest();
        String keyWord = request.getParameter("w");
        if (StringUtils.hasText(keyWord)) {
            List<TbArea> tbAreaList = tbAreaService.getAreaByName(keyWord);
            if (!tbAreaList.isEmpty()) {
                TbArea target = tbAreaList.get(0);
                return redirect("/group_tour_" + target.getCityCode() + ".html");
            }
        }
        return redirect("/group_tour_350200.html");
    }

    public Result stiMore() {
        final HttpServletRequest request = getRequest();
        String keyWord = request.getParameter("w");
        if (StringUtils.hasText(keyWord)) {
            List<TbArea> tbAreaList = tbAreaService.getAreaByName(keyWord);
            if (!tbAreaList.isEmpty()) {
                TbArea target = tbAreaList.get(0);
                return redirect("/self_tour_" + target.getCityCode() + ".html");
            }
        }
        return redirect("/self_tour_350200.html");
    }

    public Result ctiMore() {
        final HttpServletRequest request = getRequest();
        String keyWord = request.getParameter("w");
        if (StringUtils.hasText(keyWord)) {
            List<TbArea> tbAreaList = tbAreaService.getAreaByName(keyWord);
            if (!tbAreaList.isEmpty()) {
                TbArea target = tbAreaList.get(0);
                return redirect("/self_tour_" + target.getCityCode() + ".html");
            }
        }
        return redirect("/self_tour_350200.html");
    }

    public Result list() {
        if  (cityId != null) {
            city = tbAreaService.getById(cityId);
        }
        return dispatch();
    }

    public Result customMadeList() {
        if  (cityId != null) {
            city = tbAreaService.getById(cityId);
        }
        return dispatch("/WEB-INF/jsp/lvxbang/line/custom_made_list.jsp");
    }

    public Result groupTourList() {
        if  (cityId != null) {
            city = tbAreaService.getById(cityId);
        }
        return dispatch("/WEB-INF/jsp/lvxbang/line/group_tour_list.jsp");
    }

    public Result selfTourList() {
        if  (cityId != null) {
            city = tbAreaService.getById(cityId);
        }
        if (startCityId != null) {
            startCity = tbAreaService.getById(startCityId);
        }
        return dispatch("/WEB-INF/jsp/lvxbang/line/self_tour_list.jsp");
    }

    public Result selfDriverList() {
        if  (cityId != null) {
            city = tbAreaService.getById(cityId);
        }
        return dispatch("/WEB-INF/jsp/lvxbang/line/self_driver_list.jsp");
    }


    public Result getLineLabelName() {
        List<String> list = lineService.getLineLabelName();
        return json(JSONArray.fromObject(list));
    }

    public Result getLineStartCity() {
        List<TbArea> list = lineService.getLineStartCity();
        return json(JSONArray.fromObject(list));
    }

    public Result getLineList() {
        Page page = new Page(pageNo, pageSize);
        List<LineSolrEntity> list = lineService.listFromSolr(lineSearchRequest, page);
        return json(JSONArray.fromObject(list));
    }

    public Result hotCustomMade() {
        List<Line> list = lineService.completeLinePrice(lineService.getAreaHotLine(cityId, ProductAttr.custommade, new Page(1, 10)));
        if (list.size() > hotSize) {
            list = list.subList(0, hotSize);
        }
        return json(JSONArray.fromObject(list, JsonFilter.getIncludeConfig()));
    }

    public Result hotGroupTour() {
        List<Line> list = lineService.completeLinePrice(lineService.getAreaHotLine(cityId, ProductAttr.gentuan, new Page(1, 10)));
        if (list.size() > hotSize) {
            list = list.subList(0, hotSize);
        }
        return json(JSONArray.fromObject(list, JsonFilter.getIncludeConfig()));
    }

    public Result hotScenic() {
        List<ScenicInfo> list = scenicInfoService.getTop10Scenic(cityId);
        return json(JSONArray.fromObject(list, JsonFilter.getIncludeConfig()));
    }

    public Result hotHotel() {
        TbArea city = tbAreaService.getById(cityId);
        Label searchLabel = new Label();
        searchLabel.setName("热门酒店");
        Label label = labelService.findUnique(searchLabel);
        List<Hotel> list = hotelService.getHotAreaHotel(label, city, new Page(1, hotSize));
        if (list.size() < hotSize) {
            List<Long> hotelIdList = Lists.transform(list, new Function<Hotel, Long>() {
                @Override
                public Long apply(Hotel hotel) {
                    return hotel.getId();
                }
            });
            Hotel hotelCondition = new Hotel();
            hotelCondition.setCityId(cityId);
            hotelCondition.setPrice(-1f);
            hotelCondition.setStatus(ProductStatus.UP);
            List<Hotel> additionalHotels = hotelService.listWithoutCount(hotelCondition, new Page(1, hotSize), "score", "desc");
            for (Hotel additionalHotel : additionalHotels) {
                if (list.size() >= hotSize) {
                    break;
                }
                if (!hotelIdList.contains(additionalHotel.getId())) {
                    list.add(additionalHotel);
                }
            }
        }
        return json(JSONArray.fromObject(list, JsonFilter.getIncludeConfig()));
    }

    public Result count() {

        Long count = lineService.countFromSolr(lineSearchRequest);
        return json(JSONArray.fromObject(count));

    }

    public Result interest() {
        OtherVisitHistory ovh = new OtherVisitHistory();
        String cookieId = VisitHistoryCookieUtils.getCookieId(getRequest(), getResponse());
        ovh.setCookieId(cookieId);
        ovh.setDeleteFlag(false);
        Member user = getLoginUser(false);
        if (user != null) {
            ovh.setUserId(user.getId());
        }
        List<OtherVisitHistory> visitCountTop = otherVisitHistoryService.findVisitLineCountTop(ovh, 5);
        for (OtherVisitHistory otherVisitHistory : visitCountTop) {
            otherVisitHistory.setPrice(lineService.getLinePrice(otherVisitHistory.getResObjectId()));
            otherVisitHistory.setImgPath(lineService.getLineCover(otherVisitHistory.getResObjectId()));
        }
        result.put("data", visitCountTop);
        simpleResult(result, true, "");
        return json(JSONObject.fromObject(result));
    }

    public Result history() {
        OtherVisitHistory ovh = new OtherVisitHistory();
        ovh.setResType(ProductType.line);
        String cookieId = VisitHistoryCookieUtils.getCookieId(getRequest(), getResponse());
        ovh.setCookieId(cookieId);
        ovh.setDeleteFlag(false);
        Member user = getLoginUser(false);
        if (user != null) {
            ovh.setUserId(user.getId());
        }
        List<OtherVisitHistory> visitHistorys = otherVisitHistoryService.findOtherVisitHistoryTop(ovh, 5);
        for (OtherVisitHistory otherVisitHistory : visitHistorys) {
            otherVisitHistory.setPrice(lineService.getLinePrice(otherVisitHistory.getResObjectId()));
            otherVisitHistory.setImgPath(lineService.getLineCover(otherVisitHistory.getResObjectId()));
        }
        result.put("data", visitHistorys);
        simpleResult(result, true, "");
        return json(JSONObject.fromObject(result));
    }

    public Result suggest() {
        ServletActionContext.getResponse().setHeader("Access-Control-Allow-Origin", "*");
        String name = (String) getParameter("name");
        List<Long> cityIds = new ArrayList<>();
        if (cityId != null) {
            cityIds.add(cityId);
        } else {
            return json(JSONArray.fromObject(suggestService.suggestLine(name, 4)));
        }

        return json(JSONArray.fromObject(suggestService.suggestLine(cityIds, name, 4)));
    }

    public Result detail() {
        setAttribute(LXBConstants.LVXBANG_LINE_DETAIL_KEY, FileUtil.loadHTML(LXBConstants.LVXBANG_LINE_DETAIL + lineId));
        setAttribute(LXBConstants.LVXBANG_LINE_HEAD_KEY, FileUtil.loadHTML(LXBConstants.LVXBANG_LINE_HEAD + lineId));
        return dispatch();
    }

    public Result getSameCompanyLine() {
        List<Line> list = Lists.newArrayList();
        return json(JSONArray.fromObject(list, JsonFilter.getIncludeConfig()));
    }

    //索引所有线路数据
    public Result indexLine() {
        try {
         lineService.indexAllLine();
        } catch (Exception e) {
            e.printStackTrace();
            return text("error");
        }
        return text("success");
    }

    public Result cloneLine() throws CloneNotSupportedException {
        Line line = lineService.loadLine(lineId);
        if (line == null || cloneNum == null) {
            return text("error");
        }
        for (int i = 0; i < cloneNum; i++) {
            lineService.createCloneLine(line);
        }
        return text("success");
    }
}
