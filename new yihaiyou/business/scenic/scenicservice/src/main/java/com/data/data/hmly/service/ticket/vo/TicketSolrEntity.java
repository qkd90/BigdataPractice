package com.data.data.hmly.service.ticket.vo;

import com.data.data.hmly.service.common.entity.Product;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.common.entity.enums.SolrType;
import com.data.data.hmly.service.common.vo.SolrEntity;
import com.data.data.hmly.service.entity.LabelItem;
import com.data.data.hmly.service.ticket.entity.Ticket;
import com.data.data.hmly.service.ticket.entity.TicketPrice;
import com.data.data.hmly.service.ticket.entity.enmus.TicketType;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by huangpeijie on 2015-12-24,0024.
 */
@SolrDocument(solrCoreName = "products")
public class TicketSolrEntity extends SolrEntity<Ticket> {

	
    @Field
    private Long id;
    @Field
    private String name;
    @Field
    private Boolean parent;
    @Field
    private Long scenicId;
    @Field
    private TicketType ticketType;
    @Field
	private List<Long> labelIds;
    @Field
    private String sceAji;
    @Field
    private String productImg;
    @Field
    private String payWay;
    @Field
    private Float disCountPrice;
    @Field
    private Float yjPrice = 0.0f;
    @Field
    private List<String> crossCitys = new ArrayList<String>();
    @Field
    private Long topProductId;
    @Field
    private Long supplierId;
    @Field
    private String supplierName;
    @Field
    private String address;
    @Field
    private String supplierLogo;
    @Field
    private Integer productScore;
    @Field
    private Integer satisfaction;
    @Field
    private Integer orderNum;
    @Field
    private Integer commentCount;
    @Field
    private final SolrType type = SolrType.scenic;
    @Field
    private String typeid;
    @Field
    private Date updateTime;
    @Field
    private Integer showOrder;
    @Field
    private Boolean isTodayValid;

    public TicketSolrEntity(Ticket ticket) {
        this.id = ticket.getId();
        this.name = ticket.getName();
        this.parent = true;
        this.ticketType = ticket.getTicketType();
        this.sceAji = ticket.getSceAji();
        this.productImg = ticket.getTicketImgUrl();
        this.orderNum = ticket.getOrderCounts();
        this.payWay = ticket.getPayway();
        this.showOrder = ticket.getShowOrder();
        this.isTodayValid = false;
        Set<TicketPrice> ticketPriceSet = ticket.getTicketPriceSet();
        if (ticketPriceSet != null) {
            for (TicketPrice ticketPrice : ticketPriceSet) {
                this.isTodayValid = ticketPrice.getIsTodayValid() || this.isTodayValid;
            }
        }
//        if (this.disCountPrice == null || this.disCountPrice.equals(Float.MAX_VALUE)) {
//            this.disCountPrice = 0f;
//        }
        if (ticket.getId() != null) {
        	 Set<LabelItem> itemSets = ticket.getLabelItems();
	        	List<Long> laIds = new ArrayList<Long>();
	        	if (itemSets != null && itemSets.size() > 0) {
	        		for (LabelItem item : itemSets) {
	        			laIds.add(item.getLabel().getId());
	        		}
	        	}
	        	this.labelIds = laIds;
        }
        this.address = ticket.getAddress();
        if (ticket.getScenicInfo() != null) {
            this.scenicId = ticket.getScenicInfo().getId();
            if (ticket.getScenicInfo().getCity() != null) {
                this.crossCitys.add(ticket.getScenicInfo().getCity().getName());
            }
//            if (ticket.getScenicInfo().getScore() != null) {
//                this.productScore = ticket.getScenicInfo().getScore() / 20;
//            }
//            if (ticket.getScenicInfo().getScenicOther() != null) {
//                this.address = ticket.getScenicInfo().getScenicOther().getAddress();
//            }
            if (ticket.getScenicInfo().getScenicStatistics() != null
                    && ticket.getScenicInfo().getScenicStatistics().getSatisfaction() != null) {
                this.satisfaction = ticket.getScenicInfo().getScenicStatistics().getSatisfaction();
            }
        }
        Product topProduct = ticket.getTopProduct();
        if (topProduct != null) {
            this.topProductId = topProduct.getId();
            if (ticket.getCompanyUnit() != null) {
                this.supplierId = ticket.getCompanyUnit().getId();
            }
            if (topProduct.getUser().getSysUnit().getSysUnitDetail() != null) {
                this.supplierName = topProduct.getUser().getSysUnit().getSysUnitDetail().getBrandName();
                this.supplierLogo = topProduct.getUser().getSysUnit().getSysUnitDetail().getLogoImgPath();
            }
        } else {
            if (ticket.getUser() != null) {
                this.supplierName = ticket.getUser().getSysUnit().getSysUnitDetail().getBrandName();
                this.supplierLogo = ticket.getUser().getSysUnit().getSysUnitDetail().getLogoImgPath();
            }
        }
        this.typeid = String.format("%s%d", ProductType.scenic.name(), ticket.getId());
        this.updateTime = ticket.getUpdateTime();
    }

    public TicketSolrEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }

    public String getSceAji() {
        return sceAji;
    }

    public void setSceAji(String sceAji) {
        this.sceAji = sceAji;
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    public Float getDisCountPrice() {
        return disCountPrice;
    }

    public void setDisCountPrice(Float disCountPrice) {
        this.disCountPrice = disCountPrice;
    }

    public Float getYjPrice() {
        return yjPrice;
    }

    public void setYjPrice(Float yjPrice) {
        this.yjPrice = yjPrice;
    }

    public Boolean getParent() {
        return parent;
    }

    public void setParent(Boolean parent) {
        this.parent = parent;
    }



    public List<String> getCrossCitys() {
        return crossCitys;
    }

    public void setCrossCitys(List<String> crossCitys) {
        this.crossCitys = crossCitys;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierLogo() {
        return supplierLogo;
    }

    public void setSupplierLogo(String supplierLogo) {
        this.supplierLogo = supplierLogo;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public Long getTopProductId() {
        return topProductId;
    }

    public void setTopProductId(Long topProductId) {
        this.topProductId = topProductId;
    }

    @Override
    public SolrType getType() {
        return type;
    }

    @Override
    public String getTypeid() {
        return typeid;
    }

    public void setTypeid(String typeid) {
        this.typeid = typeid;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public boolean isParent() {
        return parent;
    }

    public void setParent(boolean parent) {
        this.parent = parent;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getScenicId() {
        return scenicId;
    }

    public void setScenicId(Long scenicId) {
        this.scenicId = scenicId;
    }

	public List<Long> getLabelIds() {
		return labelIds;
	}

	public void setLabelIds(List<Long> labelIds) {
		this.labelIds = labelIds;
	}

    public Integer getProductScore() {
        return productScore;
    }

    public void setProductScore(Integer productScore) {
        this.productScore = productScore;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getSatisfaction() {
        return satisfaction;
    }

    public void setSatisfaction(Integer satisfaction) {
        this.satisfaction = satisfaction;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Integer getShowOrder() {
        return showOrder;
    }

    public void setShowOrder(Integer showOrder) {
        this.showOrder = showOrder;
    }

    public Boolean getIsTodayValid() {
        return isTodayValid;
    }

    public void setIsTodayValid(Boolean isTodayValid) {
        this.isTodayValid = isTodayValid;
    }
}
