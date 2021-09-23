package com.data.data.hmly.action.yihaiyou;

import com.data.data.hmly.service.common.ProductimageService;
import com.data.data.hmly.service.common.entity.Product;
import com.data.data.hmly.service.common.entity.Productimage;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.JsonFilter;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dy on 2016/11/3.
 */
public class ProductImageWebAction extends BaseAction {

    @Resource
    private ProductimageService productimageService;

    private List<Productimage> productimageList = new ArrayList<Productimage>();
    private Productimage productimage = new Productimage();
    private Map<String, Object> result = new HashMap<String, Object>();
    private Long proId;
    private Long typePriceId;

    public Result getProImageList() {
        if (proId != null) {
            Product product = new Product();
            product.setId(proId);
            productimage.setProduct(product);
        }
        if (typePriceId != null) {
            productimage.setTargetId(typePriceId);
        }
        productimageList = productimageService.findProductimage(productimage, null, "showOrder", "asc");
        result.put("success", true);
        result.put("proImageList", productimageList);
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig();
        return json(JSONObject.fromObject(result, jsonConfig));
    }


    public List<Productimage> getProductimageList() {
        return productimageList;
    }

    public void setProductimageList(List<Productimage> productimageList) {
        this.productimageList = productimageList;
    }

    public Long getProId() {
        return proId;
    }

    public void setProId(Long proId) {
        this.proId = proId;
    }

    public Long getTypePriceId() {
        return typePriceId;
    }

    public void setTypePriceId(Long typePriceId) {
        this.typePriceId = typePriceId;
    }

    public Productimage getProductimage() {
        return productimage;
    }

    public void setProductimage(Productimage productimage) {
        this.productimage = productimage;
    }
}
