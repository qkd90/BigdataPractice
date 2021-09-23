package com.data.data.hmly.action.yhy;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.TbAreaService;
import com.data.data.hmly.service.common.ProductimageService;
import com.data.data.hmly.service.common.entity.Productimage;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.contract.ContractService;
import com.data.data.hmly.service.contract.entity.Contract;
import com.data.data.hmly.action.yhy.vo.CompanyInfoData;
import com.data.data.hmly.action.yhy.vo.TopProductData;
import com.data.data.hmly.service.SysUnitService;
import com.data.data.hmly.service.SysUserService;
import com.data.data.hmly.service.common.ProductService;
import com.data.data.hmly.service.common.entity.Product;
import com.data.data.hmly.service.common.entity.enums.ProductStatus;
import com.data.data.hmly.service.common.entity.enums.ShowStatus;
import com.data.data.hmly.service.entity.*;
import com.data.data.hmly.service.order.OrderBillSummaryService;
import com.data.data.hmly.service.order.OrderDetailService;
import com.data.data.hmly.service.order.OrderService;
import com.data.data.hmly.service.order.entity.Order;
import com.data.data.hmly.service.order.entity.OrderBillSummary;
import com.data.data.hmly.service.order.entity.OrderDetail;
import com.data.data.hmly.service.order.entity.enums.OrderDetailStatus;
import com.data.data.hmly.service.yhy.YhyCompanyInfoService;
import com.data.data.hmly.util.Encryption;
import com.google.common.collect.Lists;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.Constants;
import com.zuipin.util.JsonFilter;
import com.zuipin.util.StringUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by zzl on 2016/11/15.
 */
public class YhyMainAction extends FrameBaseAction {

    private Map<String, Object> result = new HashMap<String, Object>();
    private Long productId;


    private OrderBillSummary orderBillSummary = new OrderBillSummary();
    private SysUnit company = new SysUnit();
    private Long orderId;
    private Long detailId;
    private Long orderDetailId;
    private Long priceTypeId;
    private CompanyInfoData companyInfoData = new CompanyInfoData();
    private List<Productimage> productimages = new ArrayList<Productimage>();
    private List<Long> ids = new ArrayList<Long>();
    private ProductType type;
    private OrderDetail orderDetail = new OrderDetail();


    private SysUnit unit = new SysUnit();
    private SysUser user				= new SysUser();
    private SysUnitDetail unitDetail			= new SysUnitDetail();
    private List<SysUnitImage> unitImages = Lists.newArrayList();
    private TbArea tbArea = new TbArea();

    @Resource
    private ProductService productService;
    @Resource
    private OrderBillSummaryService orderBillSummaryService;
    @Resource
    private SysUnitService unitService;
    @Resource
    private SysUserService sysUserService;
    @Resource
    private OrderService orderService;
    @Resource
    private OrderDetailService orderDetailService;
    @Resource
    private ContractService contractService;
    @Resource
    private YhyCompanyInfoService companyInfoService;
    @Resource
    private ProductimageService productimageService;
    @Resource
    private TbAreaService tbAreaService;



    public Result index() {
        List<SysMenu> hasSubMenus = (List<SysMenu>) getSession().getAttribute("hasSubMenus");
        if (hasSubMenus == null || hasSubMenus.isEmpty()) {
            return dispatch("/WEB-INF/jsp/yhy/yhyCommon/403.jsp");
        }
        // 默认调整第一个页面
        SysMenu menu = hasSubMenus.get(0);
        if (StringUtils.isBlank(menu.getUrl())) {
            return dispatch("/WEB-INF/jsp/yhy/yhyCommon/403.jsp");
        }
        return redirect(menu.getUrl());
    }



    public Result toHomestay() {
        return dispatch("/WEB-INF/jsp/yhy/yhyHotel/homestay_list.jsp");
    }
    public Result toHomestayRoomState() {
        return dispatch("/WEB-INF/jsp/yhy/yhyHotel/homestay_roomState.jsp");
    }
    public Result toHomestayList() {
        return dispatch("/WEB-INF/jsp/yhy/yhyHotel/homestay_list.jsp");
    }
    public Result toHomeStayPriceList() {
        return dispatch("/WEB-INF/jsp/yhy/yhyHotel/homestay_roomType_list.jsp");
    }

    public Result toHomeOrderDetail() {
        if (orderId == null && detailId == null) {
            return dispatch("/WEB-INF/jsp/yhy/yhyHotel/homestay_order.jsp");
        }
        orderDetail = orderDetailService.get(detailId);
        if (orderDetail.getStatus() == OrderDetailStatus.CANCELED
                || orderDetail.getStatus() == OrderDetailStatus.INVALID) {
            return dispatch("/WEB-INF/jsp/yhy/yhyHotel/homestay_order_detailConsole.jsp");
        } else {
            return dispatch("/WEB-INF/jsp/yhy/yhyHotel/homestay_order_detail.jsp");
        }
    }

    public Result toHotelCommentList() {
        if (priceTypeId != null) {
            priceTypeId = priceTypeId;
        }
        return dispatch("/WEB-INF/jsp/yhy/yhyHotel/homestay_comment_reply_list.jsp");
    }

    public Result toHotelCommentSummary() {
        return dispatch("/WEB-INF/jsp/yhy/yhyHotel/homestay_comment_summary.jsp");
    }

    public Result toHomestayOrder() {
        return dispatch("/WEB-INF/jsp/yhy/yhyHotel/homestay_order.jsp");
    }
    public Result toHomestayFinance() {
        return dispatch("/WEB-INF/jsp/yhy/yhyHotel/homestay_finance.jsp");
    }
    public Result toHomeFundsFlow() {
        unitDetail = unitService.findUnitById(getCompanyUnit().getId()).getSysUnitDetail();
        return dispatch("/WEB-INF/jsp/yhy/yhyHotel/homestay_fundsFlow.jsp");
    }
    public Result toHomestayTenant() {
        SysUnit unit = getCompanyUnit();
        company = unitService.findSysUnitById(unit.getId());
        user = sysUserService.findSysUserById(getLoginUser().getId());
        Contract contract = contractService.getContractByCompanyB(company);
        companyInfoData = companyInfoService.getCompanyInfo(company, user, contract);
        return dispatch("/WEB-INF/jsp/yhy/yhyHotel/homestay_tenant.jsp");
    }

    public Result orderBillSummaryDetail() {
        if (orderBillSummary.getId() != null) {
            orderBillSummary = orderBillSummaryService.get(orderBillSummary.getId());
        }
        return dispatch("/WEB-INF/jsp/yhy/yhyHotel/homestay_statement.jsp");
    }

    public Result toSailing() {
        return dispatch("/WEB-INF/jsp/yhy/yhySailboat/sailboat_list.jsp");
    }
    public Result toSailboatList() {
        return dispatch("/WEB-INF/jsp/yhy/yhySailboat/sailboat_list.jsp");
    }
    public Result toSailboatPriceList() {
        return dispatch("/WEB-INF/jsp/yhy/yhySailboat/sailboat_pricetype_list.jsp");
    }

    public Result toSailboatOrder() {
        return dispatch("/WEB-INF/jsp/yhy/yhySailboat/sailling_order.jsp");
    }

    public Result toSailboatOrderDetail() {
        if (orderId == null && orderDetailId == null) {
            return dispatchYhylogin();
        }
//        Order order = orderService.get(orderId);
        orderDetail = orderDetailService.get(orderDetailId);
        if (orderDetail.getStatus() == OrderDetailStatus.CANCELED) {
            return dispatch("/WEB-INF/jsp/yhy/yhySailboat/sailling_order_detailConsole.jsp");
        } else {
            return dispatch("/WEB-INF/jsp/yhy/yhySailboat/sailling_order_detail.jsp");
        }
    }

    public Result toSailboatCommentList() {
        if (priceTypeId != null) {
            priceTypeId = priceTypeId;
        }
        return dispatch("/WEB-INF/jsp/yhy/yhySailboat/sailboat_comment_reply_list.jsp");
    }

    public Result toSailboatCommentSummary() {
        return dispatch("/WEB-INF/jsp/yhy/yhySailboat/sailboat_comment_summary.jsp");
    }


    public Result toSailboatFinance() {
        return dispatch("/WEB-INF/jsp/yhy/yhySailboat/sailling_finance.jsp");
    }

    public Result toSailboatFundsFlow() {
        unitDetail = unitService.findUnitById(getCompanyUnit().getId()).getSysUnitDetail();
        return dispatch("/WEB-INF/jsp/yhy/yhySailboat/sailboat_fundsFlow.jsp");
    }

    public Result toSailboatStatment() {
        if (orderBillSummary.getId() != null) {
            orderBillSummary = orderBillSummaryService.get(orderBillSummary.getId());
        }
        return dispatch("/WEB-INF/jsp/yhy/yhySailboat/sailling_statement.jsp");
    }

    public Result toSailboatCheck() {
        return dispatch("/WEB-INF/jsp/yhy/yhySailboat/sailling_check.jsp");
    }
    public Result toSailboatCheckRecorde() {
        return dispatch("/WEB-INF/jsp/yhy/yhySailboat/sailling_checkRecorde.jsp");
    }

    public Result companyInfoEdit() {

        if (getLoginUser() != null) {
            unit = unitService.findSysUnitById(getCompanyUnit().getId());
            unitDetail = unit.getSysUnitDetail();
            user = sysUserService.findCompanyManager(getCompanyUnit().getId(), UserType.CompanyManage);
        }

        return dispatch("/WEB-INF/jsp/yhy/yhyCommon/companyInfoEdit.jsp");
    }

    public Result toSailboatTenant() {
        SysUnit unit = getCompanyUnit();
        company = unitService.findSysUnitById(unit.getId());
        user = sysUserService.findSysUserById(getLoginUser().getId());
        Contract contract = contractService.getContractByCompanyB(company);
        companyInfoData = companyInfoService.getCompanyInfo(company, user, contract);
        return dispatch("/WEB-INF/jsp/yhy/yhySailboat/sailling_tenant.jsp");
    }

    public Result toCompanyTenant() {
        SysUnit unit = getCompanyUnit();
        company = unitService.findSysUnitById(unit.getId());
        user = sysUserService.findSysUserById(getLoginUser().getId());
        Contract contract = contractService.getContractByCompanyB(company);
        companyInfoData = companyInfoService.getCompanyInfo(company, user, contract);
        return dispatch("/WEB-INF/jsp/yhy/yhyHotel/homestay_tenant.jsp");
    }

    public Result doSaveCompanyInfo() {
        SysUser sysUser = new SysUser();
        if (user.getId() != null) {
            sysUser = sysUserService.load(user.getId());
            sysUser.setEmail(user.getEmail());
            sysUser.setQqNo(user.getQqNo());
        } else {
            result.put("noLogin", true);
            simpleResult(result, false, "");
            return jsonResult(result);
        }
        SysUnit sysUnit = new SysUnit();
        if (unit.getId() != null) {
            sysUnit = unitService.findUnitById(unit.getId());
            sysUnit.setName(unit.getName());
            TbArea tbArea = new TbArea();
            tbArea.setId(unit.getArea().getId());
            sysUnit.setArea(tbArea);
            sysUnit.setAddress(unit.getAddress());
        }
        SysUnitDetail sysUnitDetail = new SysUnitDetail();
        if (sysUnit != null) {
            sysUnitDetail = sysUnit.getSysUnitDetail();
            sysUnitDetail.setCertificateType(unitDetail.getCertificateType());
            sysUnitDetail.setLegalPerson(unitDetail.getLegalPerson());
            sysUnitDetail.setLegalIdCardNo(unitDetail.getLegalIdCardNo());
            sysUnitDetail.setSupplierType(unitDetail.getSupplierType());
            sysUnitDetail.setMainBusiness(unitDetail.getMainBusiness());
            sysUnitDetail.setIntroduction(unitDetail.getIntroduction());
            sysUnitDetail.setTelphone(unitDetail.getTelphone());
            sysUnitDetail.setContactName(unitDetail.getContactName());
            sysUnitDetail.setMobile(unitDetail.getMobile());
            sysUnitDetail.setCrtacc(unitDetail.getCrtacc());
            sysUnitDetail.setCrtbnk(unitDetail.getCrtbnk());
            sysUnitDetail.setCrtnam(unitDetail.getCrtnam());
            tbArea = tbAreaService.getArea(unitDetail.getCrtCity().getId());
            sysUnitDetail.setCrtCity(tbArea);
        }
        sysUserService.doInivited(sysUser, sysUnit, sysUnitDetail, unitImages, null);

        simpleResult(result, true, "");
        return jsonResult(result);

    }


    public Result getAreaList() {
        List<TbArea> tbAreas = Lists.newArrayList();
        if (tbArea.getId() == null) {
            return json(JSONArray.fromObject(tbAreas));
        }
        tbAreas = tbAreaService.findArea(tbArea.getId(), null, tbArea.getLevel());

        return json(JSONArray.fromObject(tbAreas, JsonFilter.getIncludeConfig()));
    }

    public Result getProducts() {
        SysUser loginUser = getLoginUser();
        if (loginUser != null) {
            SysUnit companyUnit = loginUser.getSysUnit().getCompanyUnit();
            TopProductData selProduct = (TopProductData) getSession().getAttribute("product");
            List<Product> productList = productService.getByCompanyUnit(companyUnit);
            List<TopProductData> topProductDataList = new ArrayList<TopProductData>();
            for (Product product : productList) {
                TopProductData topProductData = new TopProductData();
                if (product.getOriginId() == null) {
                    topProductData.setId(product.getId());
                } else {
                    topProductData.setId(product.getOriginId());
                }
                topProductData.setName(product.getName());
                topProductDataList.add(topProductData);
            }
            // not select all
            if (selProduct == null || (selProduct != null && (selProduct.getId() != null || !"全部".equals(selProduct.getName())))) {
                // check sel or default select first one
                if ((selProduct == null || !topProductDataList.contains(selProduct)) && !topProductDataList.isEmpty()) {
                    getSession().setAttribute("product", topProductDataList.get(0));
                }
            }
            result.put("productList", topProductDataList);
            result.put("success", true);
            result.put("msg", "");
            JsonConfig jsonConfig = JsonFilter.getIncludeConfig();
            return json(JSONObject.fromObject(result, jsonConfig));
        }
        return null;
    }

    public Result selTopDropProduct() {
        SysUser loginUser = getLoginUser();
        TopProductData selProduct = new TopProductData();
        if (productId == null) {
            selProduct.setName("全部");
            selProduct.setId(null);
            getSession().setAttribute("product", selProduct);
            result.put("success", true);
        } else {
            Product product = productService.get(productId);
            Product targetPro = null;
            ShowStatus showStatus = product.getShowStatus();
            if (ShowStatus.HIDE_FOR_CHECK.equals(showStatus)) {
                targetPro = productService.getByOriginId(product.getId());
            } else if (ShowStatus.SHOW.equals(showStatus)) {
                targetPro = product;
            }
            if (targetPro != null && !ProductStatus.DEL.equals(targetPro.getStatus())) {
                if (targetPro.getOriginId() == null) {
                    selProduct.setId(targetPro.getId());
                } else {
                    selProduct.setId(targetPro.getOriginId());
                }
                selProduct.setName(targetPro.getName());
                getSession().setAttribute("product", selProduct);
                result.put("success", true);
                result.put("msg", "");
            } else {
                List<Product> productList = productService.getByCompanyUnit(loginUser.getSysUnit().getCompanyUnit());
                if (!productList.isEmpty()) {
                    product = productList.get(0);
                    if (product.getOriginId() == null) {
                        selProduct.setId(product.getId());
                    } else {
                        selProduct.setId(product.getOriginId());
                    }
                    selProduct.setName(product.getName());
                    getSession().setAttribute("product", selProduct);
                }
                result.put("success", false);
                result.put("msg", "选择的产品不存在!");
            }
        }
        return json(JSONObject.fromObject(result));
    }

    public Result pictrueSorting() {
        if (productId != null) {
            productimages = productimageService.findAllImagesByProIdAadTarId(productId, null, null, "showOrder", "asc");
        }
        return dispatch();
    }

    public Result saveProductImageSort() {
        if (ids.isEmpty()) {
            simpleResult(result, false, "操作失败，无数据提交！");
            return jsonResult(result);
        }
        productimageService.saveProductImageSort(ids);
        simpleResult(result, true, "操作成功！");
        return jsonResult(result);

    }

    public Map<String, Object> getResult() {
        return result;
    }

    public void setResult(Map<String, Object> result) {
        this.result = result;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }


    public OrderBillSummary getOrderBillSummary() {
        return orderBillSummary;
    }

    public void setOrderBillSummary(OrderBillSummary orderBillSummary) {
        this.orderBillSummary = orderBillSummary;
    }

    public SysUnit getCompany() {
        return company;
    }

    public void setCompany(SysUnit company) {
        this.company = company;
    }

    public SysUser getUser() {
        return user;
    }

    public void setUser(SysUser user) {
        this.user = user;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(Long orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public Long getDetailId() {
        return detailId;
    }

    public void setDetailId(Long detailId) {
        this.detailId = detailId;
    }

    public Long getPriceTypeId() {
        return priceTypeId;
    }

    public void setPriceTypeId(Long priceTypeId) {
        this.priceTypeId = priceTypeId;
    }

    public CompanyInfoData getCompanyInfoData() {
        return companyInfoData;
    }

    public void setCompanyInfoData(CompanyInfoData companyInfoData) {
        this.companyInfoData = companyInfoData;
    }

    public List<Productimage> getProductimages() {
        return productimages;
    }

    public void setProductimages(List<Productimage> productimages) {
        this.productimages = productimages;
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    public SysUnit getUnit() {
        return unit;
    }

    public void setUnit(SysUnit unit) {
        this.unit = unit;
    }

    public SysUnitDetail getUnitDetail() {
        return unitDetail;
    }

    public void setUnitDetail(SysUnitDetail unitDetail) {
        this.unitDetail = unitDetail;
    }

    public List<SysUnitImage> getUnitImages() {
        return unitImages;
    }

    public void setUnitImages(List<SysUnitImage> unitImages) {
        this.unitImages = unitImages;
    }

    public OrderDetail getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(OrderDetail orderDetail) {
        this.orderDetail = orderDetail;
    }

    public TbArea getTbArea() {
        return tbArea;
    }

    public void setTbArea(TbArea tbArea) {
        this.tbArea = tbArea;
    }
}
