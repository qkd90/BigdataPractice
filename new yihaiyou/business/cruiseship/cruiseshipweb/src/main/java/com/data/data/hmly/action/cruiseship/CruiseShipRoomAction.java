package com.data.data.hmly.action.cruiseship;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.common.ProductimageService;
import com.data.data.hmly.service.common.entity.Productimage;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.cruiseship.CruiseShipRoomService;
import com.data.data.hmly.service.cruiseship.CruiseShipService;
import com.data.data.hmly.service.cruiseship.entity.CruiseShip;
import com.data.data.hmly.service.cruiseship.entity.CruiseShipRoom;
import com.data.data.hmly.service.cruiseship.entity.enums.CruiseShipRoomType;
import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.SysUser;
import com.framework.struts.AjaxCheck;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.JsonFilter;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by caiys on 2016/9/18.
 */
public class CruiseShipRoomAction extends FrameBaseAction {
    @Resource
    private CruiseShipRoomService cruiseShipRoomService;
    @Resource
    private CruiseShipService cruiseShipService;
    @Resource
    private ProductimageService productimageService;

    private Long productId;
    private Long roomId;

    /**
     * 列表查询
     */
    @AjaxCheck
    public Result list() {
        List<CruiseShipRoom> cruiseShipRooms = new ArrayList<CruiseShipRoom>();
        if (productId != null) {
            CruiseShipRoom cruiseShipRoom = new CruiseShipRoom();
            cruiseShipRoom.setCruiseShipId(productId);
            cruiseShipRooms = cruiseShipRoomService.listCruiseShipRooms(cruiseShipRoom, getLoginUser(), isSiteAdmin(), isSupperAdmin());
        }
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig();
        return datagrid(cruiseShipRooms, jsonConfig);
    }

    /**
     * 添加、编辑保存
     * @return
     */
    @AjaxCheck
    public Result save() {
        // 页面参数
        String idStr = (String) getParameter("id");
        String cruiseShipIdStr = (String) getParameter("cruiseShipId");
        String name = (String) getParameter("name");
        String roomTypeStr = (String) getParameter("roomType");
        String deck = (String) getParameter("deck");
        String area = (String) getParameter("area");
        String facilities = (String) getParameter("facilities");
        String peopleNumStr = (String) getParameter("peopleNum");
        String forceFlagStr = (String) getParameter("forceFlag");
        String coverImage = (String) getParameter("coverImage");
        CruiseShipRoomType cruiseShipRoomType = CruiseShipRoomType.valueOf(roomTypeStr);
        Integer peopleNum = null;
        if (StringUtils.isNotBlank(peopleNumStr)) {
            peopleNum = Integer.valueOf(peopleNumStr);
        }
        Boolean forceFlag = null;
        if (StringUtils.isNotBlank(forceFlagStr)) {
            forceFlag = Boolean.valueOf(forceFlagStr);
        }

        CruiseShipRoom cruiseShipRoom = null;
        CruiseShip cruiseShip = cruiseShipService.findById(Long.valueOf(cruiseShipIdStr));
        if (StringUtils.isNotBlank(idStr)) {    // 编辑
            cruiseShipRoom = cruiseShipRoomService.findById(Long.parseLong(idStr));
            cruiseShipRoom.setUpdateTime(new Date());
        } else {    // 新增
            cruiseShipRoom = new CruiseShipRoom();
            cruiseShipRoom.setCreateTime(new Date());
            cruiseShipRoom.setUpdateTime(new Date());
            cruiseShipRoom.setCruiseShip(cruiseShip);
        }
        cruiseShipRoom.setName(name);
        cruiseShipRoom.setRoomType(cruiseShipRoomType);
        cruiseShipRoom.setDeck(deck);
        cruiseShipRoom.setArea(area);
        cruiseShipRoom.setFacilities(facilities);
        cruiseShipRoom.setPeopleNum(peopleNum);
        cruiseShipRoom.setForceFlag(forceFlag);
        if (StringUtils.isNotBlank(coverImage)) {
            cruiseShipRoom.setCoverImage(coverImage);
        }
        cruiseShipRoomService.saveOrUpdate(cruiseShipRoom);

        // 图片处理
        final HttpServletRequest request = getRequest();
        String[] imgPaths = request.getParameterValues("imgPaths");
        String[] imgDeleteIds = request.getParameterValues("imgDeleteIds");
        String childFolder = request.getParameter("roomChildFolder");
        String coverImgIdStr = request.getParameter("coverImgId");
        SysUser loginUser = getLoginUser();
        SysUnit sysUnit = getCompanyUnit();

        // 处理增加图片
        if (imgPaths != null && imgPaths.length > 0) {
            List<Productimage> productimageList = new ArrayList<Productimage>();
            for (String path : imgPaths) {
                Productimage productimage = new Productimage();
                productimage.setProduct(cruiseShip);
                productimage.setTargetId(cruiseShipRoom.getId());
                if (StringUtils.isNotBlank(childFolder)) {
                    productimage.setChildFolder(childFolder);
                }
                if (cruiseShipRoom.getCoverImage().equals(path)) {
                    // 先删除之前的封面
                    productimageService.doDelCoverByTargetId(cruiseShipRoom.getId());
                    productimage.setCoverFlag(true);
                } else {
                    productimage.setCoverFlag(false);
                }
                productimage.setPath(path);
                productimage.setCreateTime(new Date());
                productimage.setProType(ProductType.cruiseship);
                productimage.setUserId(loginUser.getId());
                productimage.setCompanyUnitId(sysUnit.getId());
                productimageList.add(productimage);
            }
            productimageService.saveAll(productimageList);
        }
        // 处理删除图片
        if (imgDeleteIds != null && imgDeleteIds.length > 0) {
            for (String s : imgDeleteIds) {
                productimageService.delById(Long.parseLong(s));
            }
        }
        // 处理封面
        if (StringUtils.isNotBlank(coverImgIdStr)) {
            Long coverImgId = Long.parseLong(coverImgIdStr);
            productimageService.doDelCoverByTargetId(cruiseShipRoom.getId());
            productimageService.doSetCoverById(coverImgId);
        }

        simpleResult(result, true, "");
        return jsonResult(result);
    }

    public Result delRoom() {
        Map<String, Object> result = new HashMap<String, Object>();
        if (roomId != null) {
            cruiseShipRoomService.delete(roomId);
            result.put("success", true);
            result.put("msg", "删除成功!");
        } else {
            result.put("success", false);
            result.put("msg", "删除失败, ID不能为空!");
        }
        return json(JSONObject.fromObject(result));
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }
}
