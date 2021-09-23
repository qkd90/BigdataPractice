package com.data.data.hmly.action.provalidatechanel;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.common.ProductService;
import com.data.data.hmly.service.common.entity.Product;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.provalidatechanel.ProductValidateChanelService;
import com.data.data.hmly.service.provalidatechanel.entity.ProductChanelData;
import com.data.data.hmly.service.provalidatechanel.entity.ProductValidateChanel;
import com.framework.hibernate.util.Page;
import com.opensymphony.xwork2.Result;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by dy on 2016/9/2.
 */
public class ProValidateChanelAction extends FrameBaseAction {

    @Resource
    private ProductValidateChanelService chanelService;

    @Resource
    private ProductService productService;

    private ProductValidateChanel chanel = new ProductValidateChanel();
    private Integer page = 1;
    private Integer pageSize = 10;
    private Map<String, Object> map = new HashMap<String, Object>();



    public Result productList() {

        List<ProductChanelData> chanelDatas = new ArrayList<ProductChanelData>();

        Page pageInfo = new Page(page, pageSize);
        chanel.setUser(getLoginUser());
        chanel.setCompany(getCompanyUnit());
        chanel.setProType(ProductType.scenic);
        chanelDatas = chanelService.getProductChanelList(chanel, pageInfo, isSupperAdmin(), isSiteAdmin());
        return datagrid(chanelDatas, pageInfo.getTotalCount());
    }


    public Result saveProChannel() {
        if (chanel.getId() == null) {
            if (chanel.getProduct().getId() != null) {
                Product product = productService.get(chanel.getProduct().getId());
                ProductValidateChanel tempChanel = new ProductValidateChanel();
                tempChanel.setProduct(product);
                tempChanel.setProType(product.getProType());
                tempChanel.setChanel(chanel.getChanel());
                tempChanel.setUser(getLoginUser());
                tempChanel.setCompany(getCompanyUnit());
                tempChanel.setCreateTime(new Date());
                tempChanel.setUpdateTime(new Date());
                chanelService.save(tempChanel);
            }
        } else {
            ProductValidateChanel tempChanel = chanelService.load(chanel.getId());
            if (chanel.getProduct().getId() != null) {
                Product product = productService.get(chanel.getProduct().getId());
                tempChanel.setProduct(product);
                tempChanel.setProType(product.getProType());
                tempChanel.setChanel(chanel.getChanel());
                tempChanel.setUser(getLoginUser());
                tempChanel.setCompany(getCompanyUnit());
                tempChanel.setUpdateTime(new Date());
                chanelService.update(tempChanel);
            }
        }
        simpleResult(map, true, "");
        return jsonResult(map);
    }

//  /provalidatechanel/proValidateChanel/chanelManage.jhtml
    public Result chanelManage() {
        return dispatch();
    }

    public ProductValidateChanel getChanel() {
        return chanel;
    }

    public void setChanel(ProductValidateChanel chanel) {
        this.chanel = chanel;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
