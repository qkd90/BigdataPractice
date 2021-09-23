package com.data.data.hmly.action.yhypc;

import com.data.data.hmly.action.yhypc.vo.CommentVo;
import com.data.data.hmly.service.CommentWebService;
import com.data.data.hmly.service.LabelService;
import com.data.data.hmly.service.ScenicWebService;
import com.data.data.hmly.service.entity.Label;
import com.data.data.hmly.service.scenic.ScenicInfoService;
import com.data.data.hmly.service.scenic.entity.ScenicInfo;
import com.data.data.hmly.service.scenic.request.ScenicSearchRequest;
import com.data.data.hmly.service.scenic.vo.ScenicSolrEntity;
import com.data.data.hmly.service.ticket.TicketDatepriceService;
import com.data.data.hmly.service.ticket.TicketPriceService;
import com.data.data.hmly.service.ticket.entity.TicketDateprice;
import com.data.data.hmly.service.ticket.entity.TicketPrice;
import com.data.data.hmly.service.ticket.response.TicketPriceAddInfo;
import com.framework.hibernate.util.Page;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.FileUtil;
import com.zuipin.util.JsonFilter;
import com.zuipin.util.StringUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by huangpeijie on 2017-01-09,0009.
 */
public class ScenicWebAction extends YhyAction {
    @Resource
    private LabelService labelService;
    @Resource
    private ScenicInfoService scenicInfoService;
    @Resource
    private TicketPriceService ticketPriceService;
    @Resource
    private TicketDatepriceService ticketDatepriceService;
    @Resource
    private CommentWebService commentWebService;
    @Resource
    private ScenicWebService scenicWebService;

    public Integer pageIndex;
    public Integer pageSize;
    public ScenicSearchRequest searchRequest = new ScenicSearchRequest();
    public Long scenicId;
    private String searchWord;
    private String labelId;
    private String cityId;
    private List<CommentVo> commentVos = new ArrayList<CommentVo>();
    private Integer commentCount;

    public ScenicInfo scenicInfo;

    public Result index() {
        setAttribute(YhyConstants.YHY_SCENIC_INDEX_KEY, FileUtil.loadHTML(YhyConstants.YHY_SCENIC_INDEX));
        return dispatch();
    }

    public Result list() {
        try {
            if (StringUtils.hasText(searchWord)) {
                searchWord = URLDecoder.decode(searchWord, "UTF-8");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return dispatch();
    }

    public Result getThemeList() {
        Label condition = new Label();
        condition.setName("公共标签_景点主题");
        Label rootThemeLabel = labelService.findUnique(condition);
        List<Label> themeLabels = labelService.getAllChildsLabels(rootThemeLabel);
        if (themeLabels.isEmpty()) {
            result.put("success", false);
            result.put("msg", "没有数据");
            return json(JSONObject.fromObject(result));
        }
        result.put("success", true);
        result.put("theme", themeLabels);
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig();
        return json(JSONObject.fromObject(result, jsonConfig));
    }

    public Result getTotalPage() {
        Long result = scenicInfoService.countFromSolr(searchRequest);
        return json(JSONArray.fromObject(result));
    }

    public Result getScenicList() {
        Page page = new Page(pageIndex, pageSize);
        List<ScenicSolrEntity> solrEntities = scenicInfoService.listFromSolr(searchRequest, page);
        result.put("data", solrEntities);
        return json(JSONObject.fromObject(result));
    }

    public Result detail() {
        if (scenicId != null) {
            scenicInfo = scenicWebService.findAllDetailById(scenicId);
            commentCount = commentWebService.commentCount(scenicInfo.getScenicCommentList());
            if (scenicInfo != null) {
                return dispatch();
            } else {
                return redirect("/yhypc/scenic/index.jhtml");
            }
        } else {
            return redirect("/yhypc/scenic/index.jhtml");
        }
    }

    public Result getNearByScenic() {
        final HttpServletRequest request = getRequest();
        String lngStr = request.getParameter("lng");
        String latStr = request.getParameter("lat");
        String disStr = request.getParameter("dis");
        if (StringUtils.hasText(lngStr) && StringUtils.hasText(latStr)) {
            Double lng = Double.parseDouble(lngStr);
            Double lat = Double.parseDouble(latStr);
            Float dis = Float.parseFloat(disStr);
            List<ScenicSolrEntity> scenicSolrEntities = scenicInfoService.findNearByScenic(lng, lat, dis, new Page(0, 7));
            result.put("data", scenicSolrEntities.subList(1, 7));
        }
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig();
        return json(JSONObject.fromObject(result, jsonConfig));
    }

    public Result getTicketList() {
        List<TicketPrice> ticketPriceList = ticketPriceService.findTicketPriceBy(scenicId);
        for (TicketPrice ticketPrice : ticketPriceList) {
            List<TicketDateprice> datepriceList = ticketDatepriceService.findTypePriceDate(ticketPrice.getId(), new Date(), null, 1, "priPrice", "asc");
            if (!datepriceList.isEmpty()) {
                ticketPrice.setMinDiscountPrice(datepriceList.get(0).getPriPrice());
            }
            List<TicketPriceAddInfo> addinfoDetailList = ticketPriceService.findTicketPriceAddInfoList(ticketPrice);
            ticketPrice.setAddInfoList(addinfoDetailList);
        }
        result.put("success", true);
        result.put("ticketList", ticketPriceList);
        return json(JSONObject.fromObject(result, JsonFilter.getIncludeConfig()));
    }

    public String getLabelId() {
        return labelId;
    }

    public void setLabelId(String labelId) {
        this.labelId = StringUtils.htmlEncode(labelId);
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = StringUtils.htmlEncode(cityId);
    }

    public List<CommentVo> getCommentVos() {
        return commentVos;
    }

    public void setCommentVos(List<CommentVo> commentVos) {
        this.commentVos = commentVos;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public String getSearchWord() {
        return searchWord;
    }

    public void setSearchWord(String searchWord) {
        this.searchWord = StringUtils.htmlEncode(searchWord);
    }
}
