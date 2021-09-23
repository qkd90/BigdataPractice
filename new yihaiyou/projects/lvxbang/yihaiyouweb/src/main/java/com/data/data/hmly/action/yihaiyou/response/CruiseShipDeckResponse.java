package com.data.data.hmly.action.yihaiyou.response;

import com.data.data.hmly.service.cruiseship.entity.CruiseShipDeck;
import com.zuipin.util.QiniuUtil;
import com.zuipin.util.StringUtils;

/**
 * Created by huangpeijie on 2016-09-22,0022.
 */
public class CruiseShipDeckResponse {
    private Integer level;
    private String levelDesc;
    private String deckFacility;
    private String shapeImage;

    public CruiseShipDeckResponse() {
    }

    public CruiseShipDeckResponse(CruiseShipDeck cruiseShipDeck) {
        this.level = cruiseShipDeck.getLevel();
        this.levelDesc = cruiseShipDeck.getLevelDesc();
        this.deckFacility = cruiseShipDeck.getDeckFacility();
        this.shapeImage = cover(cruiseShipDeck.getShapeImage());
    }

    private String cover(String cover) {
        if (StringUtils.isBlank(cover)) {
            return "";
        }
        if (cover.startsWith("http")) {
            return cover;
        }
        return QiniuUtil.URL + cover;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getLevelDesc() {
        return levelDesc;
    }

    public void setLevelDesc(String levelDesc) {
        this.levelDesc = levelDesc;
    }

    public String getDeckFacility() {
        return deckFacility;
    }

    public void setDeckFacility(String deckFacility) {
        this.deckFacility = deckFacility;
    }

    public String getShapeImage() {
        return shapeImage;
    }

    public void setShapeImage(String shapeImage) {
        this.shapeImage = shapeImage;
    }
}
