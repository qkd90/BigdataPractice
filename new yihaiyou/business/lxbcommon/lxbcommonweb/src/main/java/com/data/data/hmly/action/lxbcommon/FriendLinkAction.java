package com.data.data.hmly.action.lxbcommon;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.lxbcommon.LxbFriendLinkService;
import com.data.data.hmly.service.lxbcommon.entity.LxbFriendLink;
import com.framework.hibernate.util.Page;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.BeanUtils;
import com.zuipin.util.DateUtils;
import com.zuipin.util.QiniuUtil;
import com.zuipin.util.UUIDUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2016/4/15.
 */
public class FriendLinkAction extends FrameBaseAction {

    private Map<String, Object> result = new HashMap<String, Object>();

    private LxbFriendLink friendLink = new LxbFriendLink();
    private Integer page;
    private Integer rows;
    private String orderProperty;
    private String orderType;
    private String startTimeStr;
    private String endTimeStr;

    private String resourceContentType;	// image/jpeg
    private String folder;		// 图片目录
    private String resourceFileName;
    private File resource;

    @Resource
    private LxbFriendLinkService lxbFriendLinkService;

    public Result list() {
        return dispatch();
    }

    public Result getFriendLinkData() {
        SysUser loginUser = getLoginUser();
        if (loginUser == null) {
            return dispatchlogin();
        }
        Page page = new Page(this.page, this.rows);
        List<LxbFriendLink> friendLinks = lxbFriendLinkService.list(friendLink, page, orderProperty, orderType);
        return datagrid(friendLinks, page.getTotalCount());
    }

    public Result doSaveFriendLink() {
        SysUser loginUser = getLoginUser();
        if (loginUser == null) {
            result.put("success", false);
            result.put("msg", "请先登录");
            return json(JSONObject.fromObject(result));
        }
        if (friendLink.getId() != null) {
            LxbFriendLink targetFriendLink = lxbFriendLinkService.get(friendLink.getId());
            BeanUtils.copyProperties(friendLink, targetFriendLink, false, null);
            if (StringUtils.isNotBlank(friendLink.getLinkLogo())) {
                targetFriendLink.setLinkLogo(friendLink.getLinkLogo().replace(QiniuUtil.URL, ""));
            }
            targetFriendLink.setUpdateTime(new Date());
            lxbFriendLinkService.update(targetFriendLink);
        } else {
            friendLink.setCreateTime(new Date());
            if (StringUtils.isNotBlank(friendLink.getLinkLogo())) {
                friendLink.setLinkLogo(friendLink.getLinkLogo().replace(QiniuUtil.URL, ""));
            }
            if (StringUtils.isNotBlank(startTimeStr)) {
                friendLink.setStartTime(DateUtils.getDate(startTimeStr, "yyyy-MM-dd HH:mm:ss"));
            }
            if (StringUtils.isNotBlank(endTimeStr)) {
                friendLink.setEndTime(DateUtils.getDate(endTimeStr, "yyyy-MM-dd HH:mm:ss"));
            }
            lxbFriendLinkService.save(friendLink);
        }
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    public Result detailLink() {
        final HttpServletRequest request = getRequest();
        String idStr = request.getParameter("id");
        try {
            if (StringUtils.isNotBlank(idStr)) {
                Long id = Long.parseLong(idStr);
                LxbFriendLink friendLink = lxbFriendLinkService.get(id);
                if (friendLink != null) {
                    Field[] fields = friendLink.getClass().getDeclaredFields();
                    for (Field field : fields) {
                        field.setAccessible(true);
                        if (field.getName().equals("startTime")) {
                            result.put("startTimeStr", DateUtils.format((Date) field.get(friendLink), "yyyy-MM-dd HH:mm:ss"));
                        } else if (field.getName().equals("endTime")) {
                            result.put("endTimeStr", DateUtils.format((Date) field.get(friendLink), "yyyy-MM-dd HH:mm:ss"));
                        }
                        result.put("friendLink." + field.getName(), field.get(friendLink));
                    }
                } else {
                    result.put("success", false);
                    result.put("msg", "友情链接不存在!");
                }
            } else {
                result.put("success", false);
                result.put("msg", "参数错误!");
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("msg", "获取链接信息失败, 稍候重试!");
        }
        return json(JSONObject.fromObject(result));
    }


    public Result uploadLinkLogoImg() {
        if (checkFileType()) {
            String suffix = resourceFileName.substring(resourceFileName.lastIndexOf("."));
            String newFileName = UUIDUtil.getUUID() + suffix;
            String newFilePath = StringUtils.defaultString(folder) + newFileName;
            QiniuUtil.upload(resource, newFilePath);
            result.put("url", QiniuUtil.URL + newFilePath);
            simpleResult(result, true, "");
            return jsonResult(result);
        } else {
            simpleResult(result, false, "图片格式不正确");
            return jsonResult(result);
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

    public Map<String, Object> getResult() {
        return result;
    }

    public void setResult(Map<String, Object> result) {
        this.result = result;
    }

    public LxbFriendLink getFriendLink() {
        return friendLink;
    }

    public void setFriendLink(LxbFriendLink friendLink) {
        this.friendLink = friendLink;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public String getOrderProperty() {
        return orderProperty;
    }

    public void setOrderProperty(String orderProperty) {
        this.orderProperty = orderProperty;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getStartTimeStr() {
        return startTimeStr;
    }

    public void setStartTimeStr(String startTimeStr) {
        this.startTimeStr = startTimeStr;
    }

    public String getEndTimeStr() {
        return endTimeStr;
    }

    public void setEndTimeStr(String endTimeStr) {
        this.endTimeStr = endTimeStr;
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
}
