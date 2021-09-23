package com.data.data.hmly.action.transportation;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.transportation.TransportationService;
import com.data.data.hmly.service.transportation.entity.Transportation;
import com.framework.hibernate.util.Page;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.QiniuUtil;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dy on 2016/8/23.
 */
public class TransportationAction extends FrameBaseAction {

    @Resource
    private TransportationService transportationService;
    private Transportation transportation = new Transportation();

    private Integer page = 1;
    private Integer pageSize = 10;
    private File resource;
    private String resourceFileName;
    private String resourceContentType;	// image/jpeg
    private String oldFilePath;	// 旧图片路径
    private String folder;		// 图片目录

    private Map<String, Object> map = new HashMap<String, Object>();


    public Result delTransport() {
        if (transportation.getId() != null) {
            transportationService.delTransport(transportation);
            simpleResult(map, true, "");
        } else {
            simpleResult(map, false, "");
        }
        return jsonResult(map);
    }

    public Result saveTransport() {

        if (transportation.getId() != null) {
            Transportation tempTrasport = transportationService.get(transportation.getId());
            tempTrasport.setName(transportation.getName());
            tempTrasport.setAddress(transportation.getAddress());
            tempTrasport.setCityCode(transportation.getCityCode());
            tempTrasport.setCityId(transportation.getCityId());
            tempTrasport.setCityName(transportation.getCityName());
            tempTrasport.setCode(transportation.getCode());
            tempTrasport.setCoverImg(transportation.getCoverImg());
            tempTrasport.setGcjLat(transportation.getLat());
            tempTrasport.setGcjLng(transportation.getGcjLng());
            tempTrasport.setLat(transportation.getLat());
            tempTrasport.setLng(transportation.getLng());
            tempTrasport.setRegionName(transportation.getRegionName());
            tempTrasport.setSearchName(transportation.getSearchName());
            tempTrasport.setStatus(transportation.getStatus());
            tempTrasport.setTelephone(transportation.getTelephone());
            transportationService.update(tempTrasport);
            simpleResult(map, true, "");
        } else {
            transportationService.save(transportation);
            simpleResult(map, true, "");
        }
        return jsonResult(map);

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

    public Result editTransport() {
        if (transportation.getId() != null) {
            transportation = transportationService.get(transportation.getId());
        }
        return dispatch();
    }

    public Result list() {
        Page pageInfo = new Page(page, pageSize);
        List<Transportation> transportationList = transportationService.list(transportation, pageInfo, "id");
        return datagrid(transportationList, pageInfo.getTotalCount());
    }

//    /transportation/transportation/transportManage.jhtml
    public Result transportManage() {
        return dispatch();
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

    public Transportation getTransportation() {
        return transportation;
    }

    public void setTransportation(Transportation transportation) {
        this.transportation = transportation;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public File getResource() {
        return resource;
    }

    public void setResource(File resource) {
        this.resource = resource;
    }

    public String getResourceFileName() {
        return resourceFileName;
    }

    public void setResourceFileName(String resourceFileName) {
        this.resourceFileName = resourceFileName;
    }

    public String getResourceContentType() {
        return resourceContentType;
    }

    public void setResourceContentType(String resourceContentType) {
        this.resourceContentType = resourceContentType;
    }

    public String getOldFilePath() {
        return oldFilePath;
    }

    public void setOldFilePath(String oldFilePath) {
        this.oldFilePath = oldFilePath;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }
}
