package pub.makers.shop.purchaseGoods.service;

import pub.makers.shop.base.enums.ClientType;
import pub.makers.shop.baseGood.service.BaseGoodBizService;
import pub.makers.shop.baseGood.vo.BaseGoodVo;
import pub.makers.shop.cargo.entity.CargoSkuSupplyPrice;
import pub.makers.shop.purchaseGoods.entity.PurchaseGoodsSku;
import pub.makers.shop.purchaseGoods.vo.PurchaseGoodsSampleVo;
import pub.makers.shop.purchaseGoods.vo.PurchaseGoodsSkuVo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by kok on 2017/6/1.
 */
public interface PurchaseGoodsSkuBizService extends BaseGoodBizService {

    /**
     * 商品上架
     */
    void upGoodSku(Long skuId, Integer num, Long userId);

    /**
     * 商品上架 定时上架用
     */
    void upGoodSkuSchedule(Long skuId, Integer num, Long userId);

    /**
     * 商品下架
     */
    void downGoodSku(Long skuId, Long userId);

    /**
     * 修改上架数量
     */
    void updateUpSkuNum(Long skuId, Integer num, Long userId);

    /**
     * 商品sku列表
     */
    List<PurchaseGoodsSkuVo> getGoodsSkuList(String goodId, String storeLevelId);

    /**
     * 商品样品
     */
    PurchaseGoodsSampleVo getGoodSample(String goodId);

    /**
     * 商品样品 key为skuId
     */
    Map<String, PurchaseGoodsSampleVo> getGoodSampleBySku(List<String> skuIdList);

    /**
     * 获取sku价格
     */
    BigDecimal getSkuPrice(String skuId, String storeLevelId);

    /**
     * 批量获取sku价格
     */
    Map<String, BigDecimal> getSkuPrice(List<String> skuIdList, String storeLevelId);

    /**
     * 查询cargoSku价格
     */
    Map<String, BigDecimal> getSkuPriceByCargoSku(List<String> cargoSkuIdList, String storeLevelId);


    Map<String, CargoSkuSupplyPrice> getSkuPriceFull(List<String> skuIdList, String storeLevelId);

    /**
     * 查询商品信息(上架商品)
     */
    List<BaseGoodVo> getGoodSkuListBySkuId(List<String> skuIdList, String storeLevelId, ClientType type);

    /**
     * 查询商品信息
     */
    List<BaseGoodVo> getAllGoodSkuListBySkuId(List<String> skuIdList, String storeLevelId, ClientType type);

    /**
     * 查询商品信息(上架商品)
     */
    List<BaseGoodVo> getGoodSkuListByGoodId(List<String> goodIdList, String storeLevelId, ClientType type);

    /**
     * 查询商品信息
     */
    List<BaseGoodVo> getAllGoodSkuListByGoodId(List<String> goodIdList, String storeLevelId, ClientType type);
    
    /**
     * 查询列表中散茶的供货价
     * @return
     */
    Map<String, List<CargoSkuSupplyPrice>> querySanchaPrice(Set<String> cargoSkuIds);

    /**
     * 查询列表中是散茶的sku信息
     * @return
     */
    List<PurchaseGoodsSku> querySanchaSku(Set<String> cargoSkuIds);

    /**
     * 批量获取sku价格
     */
    Map<String, BigDecimal> getSkuPriceByGood(List<String> goodIdList, String storeLevelId);

}
