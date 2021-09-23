package com.data.data.hmly.action.wechat;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.data.data.hmly.service.goods.CategoryService;
import com.data.data.hmly.service.goods.entity.Category;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.wechat.WechatDataImgTextService;
import com.data.data.hmly.service.wechat.WechatDataItemService;
import com.data.data.hmly.service.wechat.entity.WechatDataItem;
import com.data.data.hmly.service.wechat.entity.WechatDataNews;
import com.framework.struts.AjaxCheck;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.FileUtils;
import com.zuipin.util.PropertiesManager;

/**
 * Created by vacuity on 15/11/20.
 */
public class WechatDataNewsAction extends FrameBaseAction implements ModelDriven<WechatDataNews> {
    private static final long serialVersionUID = -617072372295001263L;
    @Resource
    private WechatDataImgTextService newsService;
    @Resource
    private WechatDataItemService itemService;
    @Resource
    private CategoryService categoryService;
    @Resource
    private PropertiesManager propertiesManager;
    private Map<String, Object> map = new HashMap<String, Object>();
    private WechatDataNews wechatDataNews = new WechatDataNews();
    private WechatDataItem wechatDataItem = new WechatDataItem();

    private File resource;
    private String resourceFileName;
    private String resourceContentType;    // image/jpeg
    private String oldFilePath;    // 旧图片路径
    private String folder;        // 图片目录

    private Integer page = 1;
    private Integer rows = 10;


    @AjaxCheck
    public Result saveText() {

        return jsonResult(map);
    }

    @AjaxCheck
    public Result delPerNews() {
        String newsId = (String) getParameter("newsId");

        if (!StringUtils.isEmpty(newsId)) {
            newsService.delNewsById(Long.parseLong(newsId));
            simpleResult(map, true, "");
        } else {
            simpleResult(map, false, "");
        }

        return jsonResult(map);
    }

    /**
     * 保存图文素材
     *
     * @return
     */

    @AjaxCheck
    public Result saveNews() {
        String itemId = (String) getParameter("itemId");
        String data = (String) getParameter("data");
        JSONObject obj = JSONObject.parseObject(data);
        if (StringUtils.isEmpty(obj.getString("id"))) {
            wechatDataNews.setAbstractText(obj.getString("abstractText"));
            wechatDataNews.setImg_path(obj.getString("img_path"));
            if (!StringUtils.isEmpty(obj.getString("is_checked"))) {
                if ("1".equals(obj.getString("is_checked"))) {
                    String content = obj.getString("content");
                    wechatDataNews.setContent(content);
                } else {
                    wechatDataNews.setContent(obj.getString("content"));
                }
            }
            if (obj.getLong("category") != null) {
                Category category = categoryService.findById(obj.getLong("category"));
                wechatDataNews.setCategory(category);
            }
            wechatDataNews.setTitle(obj.getString("title"));
            wechatDataNews.setAuthor(obj.getString("author"));
            wechatDataNews.setIsChecked(obj.getString("is_checked"));
            wechatDataNews.setUrl(obj.getString("url"));
            wechatDataNews.setUser(getLoginUser());
            wechatDataNews.setCreateTime(new Date());
            wechatDataNews.setUpdateTime(new Date());

            if (!StringUtils.isEmpty(itemId)) {
                wechatDataItem = itemService.load(Long.parseLong(itemId));
                wechatDataNews.setDataItem(wechatDataItem);
            }

            newsService.save(wechatDataNews);

        } else {

            wechatDataNews = newsService.findNewsById(Long.parseLong(obj.getString("id")));
            wechatDataNews.setAbstractText(obj.getString("abstractText"));
            wechatDataNews.setImg_path(obj.getString("img_path"));
            wechatDataNews.setContent(obj.getString("content"));
            if (!StringUtils.isEmpty(obj.getString("title"))) {
                wechatDataNews.setTitle(obj.getString("title"));
            }
            if (obj.getLong("category") != null) {
                Category category = categoryService.findById(obj.getLong("category"));
                wechatDataNews.setCategory(category);
            }
            wechatDataNews.setUrl(obj.getString("url"));
            wechatDataNews.setAuthor(obj.getString("author"));
            if (!StringUtils.isEmpty(obj.getString("is_checked"))) {
                wechatDataNews.setIsChecked(obj.getString("is_checked"));
            }
            wechatDataNews.setUpdateTime(new Date());

            newsService.update(wechatDataNews);

        }

        map.put("id", wechatDataNews.getId());
        map.put("userId", wechatDataNews.getUser().getId());
        map.put("itemId", wechatDataNews.getDataItem().getId());
        map.put("index", obj.getString("index"));
        simpleResult(map, true, "");
        return jsonResult(map);
    }


    /**
     * 重新上传时异步删除旧图片
     *
     * @return
     * @author caiys
     * @date 2015年10月30日 上午9:01:22
     */
    @AjaxCheck
    public Result delFile() {
        // 删除旧图片
        if (StringUtils.isNotBlank(oldFilePath)) {
            String staticPath = propertiesManager.getString("IMG_DIR");
            try {
                FileUtils.deleteFile(staticPath + oldFilePath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        simpleResult(map, true, "");
        return jsonResult(map);
    }

    @AjaxCheck
    public Result upload() {
        if (checkFileType()) {
            String suffix = resourceFileName.substring(resourceFileName.lastIndexOf("."));
            suffix = suffix.toLowerCase();
            String newFileName = System.currentTimeMillis() + suffix;
//			String staticPath = getServletContext("/").getRealPath("");
            String staticPath = propertiesManager.getString("IMG_DIR");
//			String staticPath = getRealyPath("/static");
            String newFilePath = StringUtils.defaultString(folder) + newFileName;
            FileUtils.copy(resource, staticPath + newFilePath);
            // 删除旧图片
            if (StringUtils.isNotBlank(oldFilePath)) {
                try {
                    FileUtils.deleteFile(staticPath + oldFilePath);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            map.put("url", "/static" + newFilePath);
            simpleResult(map, true, "");
            return jsonResult(map);
        } else {
            simpleResult(map, false, "图片格式不正确");
            return jsonResult(map);
        }
    }

    /**
     * 检查是否是图片格式
     *
     * @return
     * @author caiys
     * @date 2015年10月28日 下午3:19:01
     */
    public boolean checkFileType() {
        if (StringUtils.isBlank(resourceContentType)) {
            return false;
        }
        return resourceContentType.startsWith("image/");
    }


    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }


    @Override
    public WechatDataNews getModel() {
        // TODO Auto-generated method stub
        return wechatDataNews;
    }


    public WechatDataNews getWechatDataNews() {
        return wechatDataNews;
    }


    public void setWechatDataNews(WechatDataNews wechatDataNews) {
        this.wechatDataNews = wechatDataNews;
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

    public WechatDataItem getWechatDataItem() {
        return wechatDataItem;
    }

    public void setWechatDataItem(WechatDataItem wechatDataItem) {
        this.wechatDataItem = wechatDataItem;
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


}
