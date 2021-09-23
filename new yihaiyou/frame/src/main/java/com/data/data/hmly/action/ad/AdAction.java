package com.data.data.hmly.action.ad;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.enums.AdsOpenType;
import com.data.data.hmly.enums.AdsStatus;
import com.data.data.hmly.service.AdsService;
import com.data.data.hmly.service.SysResourceMapService;
import com.data.data.hmly.service.entity.Ads;
import com.data.data.hmly.service.entity.SysResourceMap;
import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.User;
import com.framework.hibernate.util.Page;
import com.framework.struts.AjaxCheck;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vacuity on 15/10/26.
 */
public class AdAction extends FrameBaseAction {

    Logger logger = Logger.getLogger(AdAction.class);

    @Resource
    private AdsService adsService;
    @Resource
    private SysResourceMapService sysResourceMapService;
    @Resource
    private PropertiesManager propertiesManager;

    private Long id;
    private int page;
    private int rows;
    private long adsLocation;
    private String adTitle;
    private Integer adSort;
    private String adsStime;
    private String adsEtime;
    private String adsOpenType;
    private String adsStatus;
    private String keyword;
    private String adsUrl;
    private String imgPath;
    private File img;
    private String fileName;
    private String ids;

    private Ads ads = new Ads();

    private String resourceContentType;	// image/jpeg
    private String folder;		// 图片目录
    private String resourceFileName;
    private File resource;

    // 返回数据
    Map<String, Object> map = new HashMap<String, Object>();


    public Result manage() {
        return dispatch();
    }

    public Result addAds() {
        return dispatch();
    }

    public Result editAdDetail() {
        if (id != null) {
            ads = adsService.get(id);
//            ads.setAdsStime(DateUtils.formatDate(ads.getStartTime()));
//            ads.setAdsEtime(DateUtils.formatDate(ads.getEndTime()));
        }
        return dispatch();
    }

    public Result uploadImg() {
        if (checkFileType()) {
			String suffix = resourceFileName.substring(resourceFileName.lastIndexOf("."));
            String newFileName = System.currentTimeMillis() + suffix;
            String newFilePath = StringUtils.defaultString(folder) + newFileName;
            QiniuUtil.upload(resource, newFilePath);
            map.put("url", QiniuUtil.URL + newFilePath);
            simpleResult(map, true, "");
            return jsonResult(map);
        } else {
            simpleResult(map, false, "图片格式不正确");
            return jsonResult(map);
        }

    }

    /**
     * 检查是否是图片格式
     * @author caiys
     * @date 2015年10月28日 下午3:19:01
     * @return
     */
    public boolean checkFileType() {
        if (StringUtils.isBlank(resourceContentType)) {
            return false;
        }
        return resourceContentType.startsWith("image/");
    }

    @AjaxCheck
    public Result search() {
        Date sTime = formatDate(adsStime, 1);
        Date eTime = formatDate(adsEtime, 2);
        Page pageInfo = new Page(page, rows);
        AdsOpenType type = null;
        if (adsOpenType != null && "NEWED".equals(adsOpenType)) {
            type = AdsOpenType.NEWED;
        } else if (adsOpenType != null && "NONE".equals(adsOpenType)) {
            type = AdsOpenType.NONE;
        } else if (adsOpenType != null && "SELF".equals(adsOpenType)) {
            type = AdsOpenType.SELF;
        }
        AdsStatus status = null;
        if (adsStatus != null && "UP".equals(adsStatus)) {
            status = AdsStatus.UP;
        } else if (adsStatus != null && "DOWN".equals(adsStatus)) {
            status = AdsStatus.DOWN;
        }
        SysUnit companyUnit = getCompanyUnit();
        List<Ads> adses = adsService.getAdsList(companyUnit, pageInfo, adsLocation, sTime, eTime, type, status, isSiteAdmin(), isSupperAdmin());

        return datagrid(adses, pageInfo.getTotalCount(), JsonFilter.getIncludeConfig("sysResourceMap"));
    }

    public Result saveAds() {
        Map<String, Object> result = new HashMap<String, Object>();
        Ads ads;
        if (id == 0) {
            ads = new Ads();
            ads.setAddTime(new Date());
            User user = getLoginUser();
            ads.setUser(user);
        } else {
            ads = adsService.get(id);
            ads.setId(id);
        }
        if (adsLocation != 0) {
            SysResourceMap sysResourceMap = sysResourceMapService.get(adsLocation);
            ads.setSysResourceMap(sysResourceMap);
        }

        if (adTitle != null) {
            ads.setAdTitle(adTitle);
        }

        Date st = formatDate(adsStime, 1);
        Date et = formatDate(adsEtime, 2);
        if (st != null) {
            ads.setStartTime(st);
        }
        if (et != null) {
            ads.setEndTime(et);
        }

        if (adsUrl != null && !"".equals(adsUrl)) {
            ads.setUrl(adsUrl);
        }
        if (adSort != null) {
            ads.setSort(adSort);
        }


        if (com.zuipin.util.StringUtils.isNotBlank(imgPath)) {
            ads.setImgPath(imgPath.replace(QiniuUtil.URL, ""));
        } else {
            ads.setImgPath(imgPath);
        }
        /*if (img != null) {
            String imgPath = propertiesManager.getString("IMG_DIR");
            String ext = fileName.substring(fileName.lastIndexOf("."));
            fileName = UUIDUtil.getUUID();
            String filePath = imgPath + "/banner/" + fileName + ext;
            // 转存文件
            try {
                FileUtils.copyFile(img, new File(filePath));
                ads.setImgPath("/banner/" + fileName + ext);
            } catch (IOException e) {
                // 文件存储失败
                logger.error("文件存储失败", e);
                result.put("success", false);
                result.put("msg", "广告图片转存失败! 检查路径是否存在以及是否有该目录权限!");
                return json(JSONObject.fromObject(result));
            }
        }*/

        if (adsOpenType != null && !"".equals(adsOpenType)) {
            AdsOpenType openType = null;
            if ("NONE".equals(adsOpenType)) {
                openType = AdsOpenType.NONE;
            } else if ("SELF".equals(adsOpenType)) {
                openType = AdsOpenType.SELF;
            } else if ("NEWED".equals(adsOpenType)) {
                openType = AdsOpenType.NEWED;
            }
            ads.setOpenType(openType);
        }

        if (adsStatus != null && !"".equals(adsStatus)) {
            AdsStatus status = null;
            if ("UP".equals(adsStatus)) {
                status = AdsStatus.UP;
            } else if ("DOWN".equals(adsStatus)) {
                status = AdsStatus.DOWN;
            }
            ads.setAdStatus(status);
        }
        ads.setSysUnit(getCompanyUnit());
        adsService.save(ads);
        result.put("success", true);
        result.put("msg", "保存成功");
//        return redirect("/ad/ad/manage.jhtml");
        return json(JSONObject.fromObject(result));
    }

    public Result delAds() {

        Ads ads = adsService.get(id);
        if (ads == null) {
            return redirect("/ad/ad/manage.jhtml");
        }
        ads.setAdStatus(AdsStatus.DEL);
        adsService.save(ads);

        return redirect("/ad/ad/manage.jhtml");
    }

    public Result getAdsMap() {
        ads = adsService.get(id);
        Map<String, Object> adsMap = new HashMap<String, Object>();
        adsMap.put("id", ads.getId());
        adsMap.put("adsLocation", ads.getSysResourceMap().getId());
        adsMap.put("adTitle", ads.getAdTitle());
        adsMap.put("adSort", ads.getSort());
        adsMap.put("adsStime", ads.getStartTime());
        adsMap.put("adsEtime", ads.getEndTime());
        adsMap.put("imgPath", ads.getImgPath());
        adsMap.put("adsUrl", ads.getUrl());
        adsMap.put("adsOpenType", ads.getOpenType());
        adsMap.put("adsStatus", ads.getAdStatus());
        return jsonResult(adsMap);
    }

    public Result editAds() {
        ads = adsService.get(id);
        return redirect("/ad/ad/manage.jhtml");
//        return dispatch();
    }

    @AjaxCheck
    public Result delByIds() {
        String ids = (String) getParameter("ids");
        if (StringUtils.isNotBlank(ids)) {
            adsService.delByIds(ids);
        }
        simpleResult(map, true, "");
        return jsonResult(map);
    }

    @AjaxCheck
    public Result doUpByIds() {
        String ids = (String) getParameter("ids");
        if (StringUtils.isNotBlank(ids)) {
            adsService.doUpByIds(ids);
        }
        simpleResult(map, true, "");
        return jsonResult(map);
    }

    @AjaxCheck
    public Result doDownByIds() {
        String ids = (String) getParameter("ids");
        if (StringUtils.isNotBlank(ids)) {
            adsService.doDownByIds(ids);
        }
        simpleResult(map, true, "");
        return jsonResult(map);
    }

    public Result getPositionList() {
        List<SysResourceMap> mapList = sysResourceMapService.getMapList();
        JsonConfig config = JsonFilter.getIncludeConfig(null);
        JSONArray json = JSONArray.fromObject(mapList, config);
        return json(json);
    }

    public Date formatDate(String time, int type) {
        if (time == null || "".equals(time)) {
            return null;
        }
        if (type == 1) {
            time += " 00:00:00";
        } else {
            time += " 23:59:59";
        }
        String pat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(pat);        // 实例化模板对象
        Date date = null;
        try {
            date = sdf.parse(time);   // 将给定的字符串中的日期提取出来
        } catch (Exception e) {            // 如果提供的字符串格式有错误，则进行异常处理
            e.printStackTrace();       // 打印异常信息
        }

        return date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public long getAdsLocation() {
        return adsLocation;
    }

    public void setAdsLocation(long adsLocation) {
        this.adsLocation = adsLocation;
    }

    public String getAdTitle() {
        return adTitle;
    }

    public void setAdTitle(String adTitle) {
        this.adTitle = adTitle;
    }

    public String getAdsStime() {
        return adsStime;
    }

    public void setAdsStime(String adsStime) {
        this.adsStime = adsStime;
    }

    public String getAdsEtime() {
        return adsEtime;
    }

    public void setAdsEtime(String adsEtime) {
        this.adsEtime = adsEtime;
    }

    public String getAdsOpenType() {
        return adsOpenType;
    }

    public void setAdsOpenType(String adsOpenType) {
        this.adsOpenType = adsOpenType;
    }

    public String getAdsStatus() {
        return adsStatus;
    }

    public void setAdsStatus(String adsStatus) {
        this.adsStatus = adsStatus;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getAdsUrl() {
        return adsUrl;
    }

    public void setAdsUrl(String adsUrl) {
        this.adsUrl = adsUrl;
    }

    public File getImg() {
        return img;
    }

    public void setImg(File img) {
        this.img = img;
    }

    public Integer getAdSort() {
        return adSort;
    }

    public void setAdSort(Integer adSort) {
        this.adSort = adSort;
    }

    public String getFileName() {
        return fileName;
    }

    public void setImgFileName(String fileName) {
        this.fileName = fileName;
    }

    public Ads getAds() {
        return ads;
    }

    public void setAds(Ads ads) {
        this.ads = ads;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getResourceContentType() {
        return resourceContentType;
    }

    public void setResourceContentType(String resourceContentType) {
        this.resourceContentType = resourceContentType;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getResourceFileName() {
        return resourceFileName;
    }

    public void setResourceFileName(String resourceFileName) {
        this.resourceFileName = resourceFileName;
    }

    public File getResource() {
        return resource;
    }

    public void setResource(File resource) {
        this.resource = resource;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
}
