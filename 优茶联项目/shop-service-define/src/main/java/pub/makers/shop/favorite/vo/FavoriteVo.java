package pub.makers.shop.favorite.vo;

import pub.makers.shop.baseGood.vo.BaseGoodVo;
import pub.makers.shop.favorite.entity.Favorite;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by dy on 2017/6/19.
 */
public class FavoriteVo implements Serializable {

    /** 主键 */
    private String id;

    /** 商品id */
    private String goodsId;

    /** 店铺id */
    private String shopId;

    /** 用户id */
    private String userId;

    /** 商品数量 */
    private Integer goodsCount;

    /** 商品价格 */
    private BigDecimal goodsPrice;

    /** 创建时间 */
    private Date createDate;

    /** 更新时间 */
    private Date updateDate;

    /**  */
    private String delFlag;

    private BaseGoodVo good;

    private String isValid;

    private String message;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(Integer goodsCount) {
        this.goodsCount = goodsCount;
    }

    public BigDecimal getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(BigDecimal goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public BaseGoodVo getGood() {
        return good;
    }

    public void setGood(BaseGoodVo good) {
        this.good = good;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static FavoriteVo fromFavorite(Favorite favorite) {
        FavoriteVo vo = new FavoriteVo();
        vo.setId(favorite.getId() == null ? null : favorite.getId().toString());
        vo.setGoodsId(favorite.getGoodsId() == null ? null : favorite.getGoodsId().toString());
        vo.setShopId(favorite.getShopId() == null ? null : favorite.getShopId().toString());
        vo.setUserId(favorite.getUserId() == null ? null : favorite.getUserId().toString());
        vo.setGoodsCount(favorite.getGoodsCount() == null ? null : favorite.getGoodsCount());
        vo.setGoodsPrice(favorite.getGoodsPrice() == null ? null : favorite.getGoodsPrice());
        vo.setCreateDate(favorite.getCreateDate());
        vo.setUpdateDate(favorite.getUpdateDate());
        return vo;
    }
}
