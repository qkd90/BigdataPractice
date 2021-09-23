package com.data.data.hmly.action.line;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.line.LinedaysProductPriceService;
import com.data.data.hmly.service.line.entity.LinedaysProductPrice;
import com.framework.struts.AjaxCheck;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.JsonFilter;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by caiys on 2016/8/18.
 */
public class LinedaysProductPriceAction extends FrameBaseAction {
    @Resource
    private LinedaysProductPriceService linedaysProductPriceService;
    Map<String, Object> map = new HashMap<String, Object>();

    /**
     * 关联产品列表
     * @return
     */
    @AjaxCheck
    public Result listLineProduct() {
        String linedaysIdStr = (String) getParameter("linedaysId");
        String productTypeStr = (String) getParameter("productType");

        LinedaysProductPrice linedaysProductPrice = new LinedaysProductPrice();
        linedaysProductPrice.setLinedaysId(Long.valueOf(linedaysIdStr));
        linedaysProductPrice.setProductType(ProductType.valueOf(productTypeStr));
        List<LinedaysProductPrice> list = linedaysProductPriceService.listLineProduct(linedaysProductPrice);
        JsonConfig config = JsonFilter.getIncludeConfig();
        JSONArray json = JSONArray.fromObject(list, config);
        return json(json);
    }

    /**
     * 新增产品关联
     * @return
     */
    @AjaxCheck
    public Result relateProduct() {
        String ids = (String) getParameter("ids");
        String linedaysIdStr = (String) getParameter("linedaysId");
        String lineIdStr = (String) getParameter("lineId");
        String productTypeStr = (String) getParameter("productType");
        if (StringUtils.isNotBlank(ids)) {
            linedaysProductPriceService.doRelateProduct(ids, Long.valueOf(lineIdStr), Long.valueOf(linedaysIdStr), ProductType.valueOf(productTypeStr), getLoginUser());
        }
        simpleResult(map, true, "");
        return jsonResult(map);
    }

    /**
     * 取消线路产品关联
     * @return
     */
    @AjaxCheck
    public Result unRelateProduct() {
        String ids = (String) getParameter("ids");
        linedaysProductPriceService.doUnRelateProduct(ids);
        simpleResult(map, true, "");
        return jsonResult(map);
    }
}
