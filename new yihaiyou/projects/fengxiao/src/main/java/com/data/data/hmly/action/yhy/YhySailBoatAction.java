package com.data.data.hmly.action.yhy;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.action.user.UserConstans;
import com.data.data.hmly.service.common.ProValidCodeService;
import com.data.data.hmly.service.common.entity.ProValidCode;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.order.OrderDetailService;
import com.data.data.hmly.service.order.OrderService;
import com.data.data.hmly.service.order.entity.Order;
import com.data.data.hmly.service.order.entity.OrderDetail;
import com.data.data.hmly.service.order.vo.OrderDataForm;
import com.data.data.hmly.util.PageData;
import com.framework.hibernate.util.Page;
import com.framework.struts.AjaxCheck;
import com.google.common.collect.Lists;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.JsonFilter;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dy on 2016/11/29.
 */
public class YhySailBoatAction extends FrameBaseAction {

    @Resource
    private OrderService orderService;
    @Resource
    private OrderDetailService orderDetailService;
    @Resource
    private ProValidCodeService proValidCodeService;

    private PageData<OrderDetail> orderDetailData = new PageData<OrderDetail>();
    private OrderDataForm orderDataFormVo = new OrderDataForm();
    private ProValidCode proValidCode = new ProValidCode();
    private Map<String, Object> map = new HashMap<String, Object>();
    private Long orderId;
    private Long orderDetailId;
    private Integer draw;
    private Integer start = 1;
    private Integer length = 10;
    private String codeId;


    public Result getValidCodeList() {
        Integer pageIndex = start / length + 1;
        Page page = new Page(pageIndex, length);
        SysUser loginuser = getLoginUser();
        Long supplierId = loginuser.getSysUnit().getCompanyUnit().getId();
        proValidCode.setSupplierId(supplierId);
        List<ProValidCode> proValidCodes = proValidCodeService.getValidateInfoList(proValidCode, page);
        result.put("data", proValidCodes);
        result.put("recordsTotal", page.getTotalCount());
        result.put("recordsFiltered", page.getTotalCount());
        result.put("draw", draw);
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig();
        return json(JSONObject.fromObject(result, jsonConfig));
    }

    public Result getValidateCodeInfo() {
        if (orderDetailId == null) {
            simpleResult(map, false, "很抱歉，无效参数!");
            JSONObject json = JSONObject.fromObject(map);
            return json(json);
        }
        SysUser loginuser = getLoginUser();
        Long supplierId = loginuser.getSysUnit().getCompanyUnit().getId();
        ProValidCode pvCode = new ProValidCode();
        pvCode.setSupplierId(supplierId);
        pvCode.setOrderDetailId(orderDetailId);
        map = proValidCodeService.getCheckValidateCodeInfo(pvCode);

        simpleResult(map, true, "");
        return json(JSONObject.fromObject(map, JsonFilter.getIncludeConfig("proValidCodeList")));
    }

    /**
     * 验票
     */
    @AjaxCheck
    public Result doCheck() {
        if (StringUtils.isBlank(codeId)) {
            simpleResult(map, false, "很抱歉，无效参数!");
            JSONObject json = JSONObject.fromObject(map);
            return json(json);
        }
        SysUser loginuser = getLoginUser();
        if (loginuser == null) {
            simpleResult(map, false, "很抱歉，用户未登录!");
            JSONObject json = JSONObject.fromObject(map);
            return json(json);
        }
        Long supplierId = loginuser.getSysUnit().getCompanyUnit().getId();
        ProValidCode pvCode = new ProValidCode();
        pvCode.setSupplierId(supplierId);
        ProValidCode productValidateCode = proValidCodeService.checkVliadateCode(codeId, pvCode);
        if (productValidateCode == null) {
            simpleResult(map, false, "很抱歉，当前验证码不存在！");
            JSONObject json = JSONObject.fromObject(map);
            return json(json);
        }
        if (productValidateCode.getUsed() == 1) {
            map.put("used", 1);
            map.put("orderId", productValidateCode.getOrderId());
            map.put("orderDetailId", productValidateCode.getOrderDetailId());
            simpleResult(map, false, "很抱歉，验证码已验证!");
            JSONObject json = JSONObject.fromObject(map);
            return json(json);
        } else if (productValidateCode.getUsed() == -1) {
            simpleResult(map, false, "很抱歉，验证码已经无效!");
            JSONObject json = JSONObject.fromObject(map);
            return json(json);
        }
        productValidateCode.syncUsed(productValidateCode.getInvalidTime()); // 有效期之前已经判断，此处暂时省略
        productValidateCode.setValidateUser(loginuser);
        proValidCodeService.update(productValidateCode);
        if (productValidateCode.getUsed() == -1) {
            simpleResult(map, false, "很抱歉，验证码已过期");
        } else {
            map.put("orderId", productValidateCode.getOrderId());
            map.put("orderDetailId", productValidateCode.getOrderDetailId());
            simpleResult(map, true, "恭喜您，验证成功！");
        }
        JSONObject json = JSONObject.fromObject(map);
        return json(json);
    }


    /**
     * 获取订单详情
     * @return
     */
    public Result getOrderDetail() {
        if (orderId != null && orderDetailId != null) {
            OrderDetail orderDetail = orderDetailService.get(orderDetailId);
            List<OrderDetail> orderDetails = Lists.newArrayList();
            orderDetails.add(orderDetail);
            map.put("orderDetails", orderDetails);
            simpleResult(map, true, "");
        } else {
            simpleResult(map, false, "");
        }
        return json(JSONObject.fromObject(map, JsonFilter.getIncludeConfig("operator")));
    }

    /**
     * 获取订单详情
     * @return
     */
    public Result orderList() {

        Integer pageIndex = start / length + 1;
        Page page = new Page(pageIndex, length);
        SysUser loginUser = getLoginUser();
        if (loginUser == null) {
            return dispatchlogin();
        }
        Boolean isSuperAdmin = isSupperAdmin();
        Boolean isSiteAdmin = isSiteAdmin();
        orderDataFormVo.setOrderProperty("createTime");
        List<OrderDetail> orderDetails = orderDetailService.listByAuthorize(orderDataFormVo, page, isSuperAdmin, isSiteAdmin, loginUser);
        result.put("data", orderDetails);
        result.put("recordsTotal", page.getTotalCount());
        result.put("recordsFiltered", page.getTotalCount());
        result.put("draw", draw);
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig("order", "product", "user", "operator");
        return json(JSONObject.fromObject(result, jsonConfig));
    }

    public PageData<OrderDetail> getOrderDetailData() {
        return orderDetailData;
    }

    public void setOrderDetailData(PageData<OrderDetail> orderDetailData) {
        this.orderDetailData = orderDetailData;
    }

    public OrderDataForm getOrderDataFormVo() {
        return orderDataFormVo;
    }

    public void setOrderDataFormVo(OrderDataForm orderDataFormVo) {
        this.orderDataFormVo = orderDataFormVo;
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

    public Integer getDraw() {
        return draw;
    }

    public void setDraw(Integer draw) {
        this.draw = draw;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public String getCodeId() {
        return codeId;
    }

    public void setCodeId(String codeId) {
        this.codeId = codeId;
    }

    public ProValidCode getProValidCode() {
        return proValidCode;
    }

    public void setProValidCode(ProValidCode proValidCode) {
        this.proValidCode = proValidCode;
    }
}
