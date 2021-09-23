package com.data.data.hmly.service.provalidatechanel;

import com.data.data.hmly.service.common.dao.ProductDao;
import com.data.data.hmly.service.common.entity.Product;
import com.data.data.hmly.service.common.entity.enums.ProductStatus;
import com.data.data.hmly.service.provalidatechanel.dao.ProductValidateChanelDao;
import com.data.data.hmly.service.provalidatechanel.entity.ProductChanelData;
import com.data.data.hmly.service.provalidatechanel.entity.ProductValidateChanel;
import com.data.data.hmly.service.ticket.dao.TicketDao;
import com.data.data.hmly.service.ticket.entity.Ticket;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.zuipin.util.DateUtils;
import com.zuipin.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dy on 2016/9/2.
 */
@Service
public class ProductValidateChanelService {
    @Resource
    private ProductValidateChanelDao productValidateChanelDao;

    @Resource
    private ProductDao productDao;

    @Resource
    private TicketDao ticketDao;


    public List<ProductChanelData> getProductChanelList(ProductValidateChanel chanel, Page pageInfo, Boolean supperAdmin, Boolean siteAdmin) {

        List<ProductChanelData> productChanelDatas = new ArrayList<ProductChanelData>();
        Ticket product = new Ticket();
        if (chanel.getProduct() != null) {
            if (chanel.getProduct().getId() != null) {
                product.setId(chanel.getProduct().getId());
            }
            if (StringUtils.isNotBlank(chanel.getProduct().getName())) {
                product.setName(chanel.getProduct().getName());
            }
        }
        product.setUser(chanel.getUser());
        product.setCompanyUnit(chanel.getCompany());
        product.setProType(chanel.getProType());
        List<Ticket> productList = getProductListByChanel(product, pageInfo, chanel, supperAdmin, siteAdmin);
        for (Product p : productList) {
            ProductChanelData productChanelData = new ProductChanelData();
            productChanelData.setId(p.getId());
            productChanelData.setName(p.getName());
            ProductValidateChanel productValidateChanel = getProChanel(chanel, p);
            if (productValidateChanel != null) {
                productChanelData.setChannelId(productValidateChanel.getId());
                productChanelData.setChanel(productValidateChanel.getChanel());
                productChanelData.setUserId(productValidateChanel.getUser().getId());
                productChanelData.setUserName(productValidateChanel.getUser().getUserName());
                productChanelData.setUpdateTime(productValidateChanel.getUpdateTime());
            }
            productChanelDatas.add(productChanelData);
        }
        return productChanelDatas;
    }

    private ProductValidateChanel getProChanel(ProductValidateChanel chanel, Product product) {
        List<ProductValidateChanel> productChanels = new ArrayList<ProductValidateChanel>();
        Criteria<ProductValidateChanel> criteria = new Criteria<ProductValidateChanel>(ProductValidateChanel.class);
        criteria.eq("product.id", product.getId());
        criteria.eq("proType", chanel.getProType());
        if (chanel.getChanel() != null) {
            criteria.eq("chanel", chanel.getChanel());
        }
        productChanels = productValidateChanelDao.findByCriteria(criteria);
        if (productChanels.isEmpty()) {
            return null;
        }
        return productChanels.get(0);
    }


    public List<Ticket> getProductListByChanel(Ticket product, Page pageInfo, ProductValidateChanel chanel, Boolean supperAdmin, Boolean siteAdmin) {

        StringBuffer sb = new StringBuffer("from Ticket p");
        List params = new ArrayList();
        if (!supperAdmin) {
            sb.append(" where p.companyUnit.sysSite.id=?");
            params.add(product.getUser().getSysSite().getId());
//            criteria.createCriteria("companyUnit", "u", JoinType.INNER_JOIN);
//            criteria.eq("u.sysSite.id", sysUser.getSysSite().getId());
            if (!siteAdmin) {
                sb.append(" and p.companyUnit.id=?");
                params.add(product.getUser().getSysUnit().getCompanyUnit().getId());
            }
        }
        if (params.isEmpty()) {
            sb.append(" where p.status!=?");
            params.add(ProductStatus.DEL);
        } else {
            sb.append(" and p.status!=?");
            params.add(ProductStatus.DEL);
        }


        if (product.getProType() != null) {
            sb.append(" and p.proType=?");
            params.add(product.getProType());
        }
        if (StringUtils.isNotBlank(product.getName())) {
            sb.append(" and p.name like ?");
            params.add(product.getName());
        }
        if (product.getId() != null) {
            sb.append(" and p.id = ?");
            params.add(product.getId());
        }
        if (chanel.getChanel() != null || StringUtils.isNotBlank(chanel.getUpdateTimeStartStr()) || StringUtils.isNotBlank(chanel.getUpdateTimeEndStr())) {
            sb.append(" and exists(select 1 from ProductValidateChanel pvc where pvc.proType=?");
            params.add(product.getProType());
            sb.append(" and pvc.product.id=p.id");
            if (chanel.getChanel() != null) {
                sb.append(" and pvc.chanel=?");
                params.add(chanel.getChanel());
            }
            if (StringUtils.isNotBlank(chanel.getUpdateTimeStartStr())) {
                sb.append(" and pvc.updateTime >= ?");
                params.add(DateUtils.toDate(chanel.getUpdateTimeStartStr()));
            }
            if (StringUtils.isNotBlank(chanel.getUpdateTimeEndStr())) {
                sb.append(" and pvc.updateTime <= ?");
                params.add(DateUtils.toDate(chanel.getUpdateTimeEndStr()));
            }
            sb.append(")");
        }
        return ticketDao.findByHQL(sb.toString(), pageInfo, params.toArray());
    }

    public ProductValidateChanel load(Long id) {
        return productValidateChanelDao.load(id);
    }

    public void save(ProductValidateChanel tempChanel) {
        productValidateChanelDao.save(tempChanel);
    }

    public void update(ProductValidateChanel tempChanel) {
        productValidateChanelDao.update(tempChanel);
    }
}
