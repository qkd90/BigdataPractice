package com.data.data.hmly.action.cruiseship;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.cruiseship.CruiseShipDeckService;
import com.data.data.hmly.service.cruiseship.entity.CruiseShipDeck;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.JsonFilter;
import com.zuipin.util.StringUtils;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2016/9/26.
 */
public class CruiseShipDeckAction extends FrameBaseAction {

    @Resource
    private CruiseShipDeckService cruiseShipDeckService;

    private CruiseShipDeck cruiseShipDeck = new CruiseShipDeck();
    private Long productId;
    private String imgPaths;
    private Long deckId;

    public Result list() {
        List<CruiseShipDeck> cruiseShipDeckList = new ArrayList<CruiseShipDeck>();
        if (productId != null) {
            cruiseShipDeckList = cruiseShipDeckService.listByCruiseShipId(productId);
        }
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig();
        return datagrid(cruiseShipDeckList, cruiseShipDeckList.size(), jsonConfig);
    }

    public Result save() {
        Map<String, Object> result = new HashMap<String, Object>();
        if (cruiseShipDeck.getId() != null) {
            // 更新甲板信息
            CruiseShipDeck sourceCruiseShipDeck = cruiseShipDeckService.get(cruiseShipDeck.getId());
            sourceCruiseShipDeck.setLevel(cruiseShipDeck.getLevel());
            sourceCruiseShipDeck.setLevelDesc(cruiseShipDeck.getLevelDesc());
            sourceCruiseShipDeck.setDeckFacility(cruiseShipDeck.getDeckFacility());
            if (StringUtils.hasText(imgPaths)) {
                sourceCruiseShipDeck.setShapeImage(imgPaths);
            }
            sourceCruiseShipDeck.setUpdateTime(new Date());
            cruiseShipDeckService.update(sourceCruiseShipDeck);
            result.put("success", true);
            result.put("msg", "更新甲板信息成功!");
        } else {
            // 新增甲板信息
            cruiseShipDeck.setCreateTime(new Date());
            if (StringUtils.hasText(imgPaths)) {
                cruiseShipDeck.setShapeImage(imgPaths);
            }
            cruiseShipDeckService.save(cruiseShipDeck);
            result.put("success", true);
            result.put("msg", "保存甲板信息成功!");
        }
        return json(JSONObject.fromObject(result));
    }

    public Result delDeck() {
        Map<String, Object> result = new HashMap<String, Object>();
        if (deckId != null) {
            cruiseShipDeckService.delete(deckId);
            result.put("success", true);
            result.put("msg", "删除成功!");
        } else {
            result.put("success", false);
            result.put("msg", "删除失败, ID不能为空!");
        }
        return json(JSONObject.fromObject(result));
    }

    public CruiseShipDeck getCruiseShipDeck() {
        return cruiseShipDeck;
    }

    public void setCruiseShipDeck(CruiseShipDeck cruiseShipDeck) {
        this.cruiseShipDeck = cruiseShipDeck;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getImgPaths() {
        return imgPaths;
    }

    public void setImgPaths(String imgPaths) {
        this.imgPaths = imgPaths;
    }

    public Long getDeckId() {
        return deckId;
    }

    public void setDeckId(Long deckId) {
        this.deckId = deckId;
    }
}
