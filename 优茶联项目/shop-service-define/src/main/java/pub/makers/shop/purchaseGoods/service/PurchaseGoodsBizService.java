package pub.makers.shop.purchaseGoods.service;

import pub.makers.shop.base.enums.ClientType;
import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.baseGood.vo.BaseGoodVo;
import pub.makers.shop.cargo.entity.vo.ImageGroupVo;
import pub.makers.shop.purchaseGoods.entity.PurchaseGoods;
import pub.makers.shop.purchaseGoods.pojo.PurchaseGoodsQuery;
import pub.makers.shop.purchaseGoods.vo.PurchaseGoodsVo;
import pub.makers.shop.tradeGoods.entity.TradeGood;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by dy on 2017/4/14.
 */
public interface PurchaseGoodsBizService {

    PurchaseGoods getPurGoods(PurchaseGoods goods);

    /**
     * 商品列表查询结果数
     */
    Integer getSearchGoodsCount(PurchaseGoodsQuery purchaseGoodsQuery);

    /**
     * 商品列表查询结果数
     */
    Integer getSearchGoodsCountFromMongo(PurchaseGoodsQuery purchaseGoodsQuery);

    /**
     * 商品列表查询
     */
    List<BaseGoodVo> getSearchGoodsList(PurchaseGoodsQuery purchaseGoodsQuery);

    /**
     * 商品列表查询
     */
    List<BaseGoodVo> getSearchGoodsListFromMongo(PurchaseGoodsQuery purchaseGoodsQuery);

    /**
     * 商品详情
     */
    PurchaseGoodsVo getPcGoodsDetail(String goodId);

    /**
     * 商品详情
     */
    PurchaseGoodsVo getGoodsDetail(String goodId, String storeLevelId, ClientType clientType);

    /**
     * 商品轮播图
     */
    ImageGroupVo getGoodsAlbum(String goodId, ClientType clientType);

    /**
     * pc端详情页加载数据
     */
    PurchaseGoodsVo getPcGoodsOnloadDetail(String goodId, String storeLevelId);

    /**
     * 列表查询条件
     */
    ResultData getListSearchParams(String classifyId, String storeLevelId);

    /**
     * 获取商品利润
     */
    BigDecimal getGoodProfit(String cargoId, String storeLevelId);

    /**
     * 采购商品对应商城商品
     */
    TradeGood getTradeGoodId(String goodId);

    /**
     * 更新分类启用标识
     */
    void updateClassifyValid();
}
