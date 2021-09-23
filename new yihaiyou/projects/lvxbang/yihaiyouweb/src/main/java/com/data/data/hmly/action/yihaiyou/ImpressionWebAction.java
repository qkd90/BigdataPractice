package com.data.data.hmly.action.yihaiyou;

import com.data.data.hmly.action.yihaiyou.request.ImpressionUpdateRequest;
import com.data.data.hmly.action.yihaiyou.request.Photo;
import com.data.data.hmly.action.yihaiyou.request.PlaceSearchRequest;
import com.data.data.hmly.action.yihaiyou.response.ImpressionResponse;
import com.data.data.hmly.action.yihaiyou.response.PlaceResponse;
import com.data.data.hmly.service.ImpressionMobileService;
import com.data.data.hmly.service.common.util.UUIDUtil;
import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.impression.ImpressionService;
import com.data.data.hmly.service.impression.entity.Impression;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.framework.hibernate.util.Page;
import com.framework.struts.AjaxCheck;
import com.framework.struts.NeedLogin;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.QiniuUtil;
import net.sf.json.JSONObject;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.security.auth.login.LoginException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by huangpeijie on 2016-04-13,0013.
 */
public class ImpressionWebAction extends BaseAction {
    @Resource
    private ImpressionService impressionService;
    @Resource
    private ImpressionMobileService impressionMobileService;

    private final ObjectMapper mapper = new ObjectMapper();

    public Member user;
    public Long imprId;
    public String json;
    public File img;
    //    public String imgContentType;
    public String imgFileName;
    public String section;
    public ImpressionUpdateRequest impressionUpdateRequest;
    public PlaceSearchRequest placeSearchRequest;
    public Integer pageNo;
    public Integer pageSize;

    /**
     * 保存印象
     *
     * @return
     */
    @AjaxCheck
    @NeedLogin
    public Result save() throws IOException, LoginException {
        impressionUpdateRequest = mapper.readValue(json, ImpressionUpdateRequest.class);
        user = getLoginUser();
        Impression impression = impressionMobileService.saveImpression(impressionUpdateRequest, user);
        result.put("id", impression.getId());
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    /**
     * 我的印象
     *
     * @return
     */
    @AjaxCheck
    @NeedLogin
    public Result list() throws LoginException {
        user = getLoginUser();
        Impression impression = new Impression();
        impression.setUser(user);
        Page page = new Page(pageNo, pageSize);
        List<Impression> impressions = impressionService.listMyImpression(impression, page);
        List<ImpressionResponse> responses = Lists.transform(impressions, new Function<Impression, ImpressionResponse>() {
            @Override
            public ImpressionResponse apply(Impression input) {
                return new ImpressionResponse(input);
            }
        });
        if (page.getPageIndex() >= page.getPageCount()) {
            result.put("nomore", true);
        } else {
            result.put("nomore", false);
        }
        result.put("impressionList", responses);
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    /**
     * 印象列表
     *
     * @return
     */
    @AjaxCheck
    public Result listAll() {
        Page page = new Page(pageNo, pageSize);
        List<Impression> impressions = impressionService.list(page);
        List<ImpressionResponse> responses = Lists.transform(impressions, new Function<Impression, ImpressionResponse>() {
            @Override
            public ImpressionResponse apply(Impression input) {
                return new ImpressionResponse(input);
            }
        });
        if (page.getPageIndex() >= page.getPageCount()) {
            result.put("nomore", true);
        } else {
            result.put("nomore", false);
        }
        result.put("impressionList", responses);
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    /**
     * 印象详情
     *
     * @return
     */
    @AjaxCheck
    public Result detail() {
        Impression impression = impressionService.get(imprId);
        if (impression == null || new Integer(1).equals(impression.getDeleteFlag())) {
            result.put("success", false);
            return json(JSONObject.fromObject(result));
        }
        impressionService.addBrowsingNum(impression.getId());
        ImpressionResponse response = impressionMobileService.impressionDetail(impression);
        result.put("impression", response);
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    /**
     * 删除印象
     *
     * @return
     */
    @AjaxCheck
    @NeedLogin
    public Result delete() throws LoginException {
        Impression impression = impressionService.get(imprId);
        user = getLoginUser();
        if (impression == null) {
            result.put("success", false);
            return json(JSONObject.fromObject(result));
        }
        if (!impression.getUser().getId().equals(user.getId())) {
            result.put("success", false);
            return json(JSONObject.fromObject(result));
        }
        impressionService.delete(impression);
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    /**
     * 上传图片
     *
     * @return
     */
    @AjaxCheck
    public Result uploadPhoto() throws IOException {
        String suffix = imgFileName.substring(imgFileName.indexOf("."));
        String path = section + "/" + UUIDUtil.getUUID() + suffix;
        Integer width = 0;
        Integer height = 0;
        BufferedImage bufferedImage = ImageIO.read(img);
        if (bufferedImage != null) {
            width = bufferedImage.getWidth();
            height = bufferedImage.getHeight();
        }
        QiniuUtil.upload(img, path);
        Photo photo = new Photo();
        photo.setHeight(height);
        photo.setWidth(width);
        photo.setImgUrl(path);
        result.put("photo", photo);
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    /**
     * 搜索印象地点
     *
     * @return
     */
    @AjaxCheck
    public Result placeList() throws IOException {
        placeSearchRequest = mapper.readValue(json, PlaceSearchRequest.class);
        Page page = new Page(pageNo, pageSize);
        List<PlaceResponse> list = impressionMobileService.placeList(placeSearchRequest, page);
        if (page.getPageIndex() >= page.getPageCount()) {
            result.put("nomore", true);
        } else {
            result.put("nomore", false);
        }
        result.put("placeList", list);
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }
}
