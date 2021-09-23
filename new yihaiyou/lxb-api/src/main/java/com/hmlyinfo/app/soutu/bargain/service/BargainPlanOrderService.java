package com.hmlyinfo.app.soutu.bargain.service;

import com.hmlyinfo.app.soutu.account.domain.ThridPartyUser;
import com.hmlyinfo.app.soutu.account.service.MemberService;
import com.hmlyinfo.app.soutu.account.service.UserService;
import com.hmlyinfo.app.soutu.bargain.domain.BargainPlan;
import com.hmlyinfo.app.soutu.bargain.domain.BargainPlanOrder;
import com.hmlyinfo.app.soutu.bargain.domain.BargainPlanOrderItem;
import com.hmlyinfo.app.soutu.bargain.mapper.BargainPlanOrderMapper;
import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.base.properties.Config;
import com.hmlyinfo.app.soutu.scenicTicket.domain.PayOrder;
import com.hmlyinfo.app.soutu.scenicTicket.service.PayOrderService;
import com.hmlyinfo.base.exception.BizValidateException;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;
import com.hmlyinfo.base.util.Validate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BargainPlanOrderService extends BaseService<BargainPlanOrder, Long> {

    @Autowired
    BargainPlanOrderMapper<BargainPlanOrder> mapper;
    @Autowired
    BargainPlanOrderItemService bargainPlanOrderItemService;
    @Autowired
    BargainPlanService bargainPlanService;
    @Autowired
    UserService userService;
    @Autowired
    PayOrderService payOrderService;

    private static final String NOTIFY_URL = Config.get("ONSALE_NOTIFY_URL");

    @Override
    public BaseMapper<BargainPlanOrder> getMapper() {
        return mapper;
    }

    @Override
    public String getKey() {
        return "id";
    }

    public void order(BargainPlanOrder bargainPlanOrder, BargainPlanOrderItem[] bargainPlanOrderItems) {
        bargainPlanOrder.setStatus(BargainPlanOrder.STATUS_WAIT_PAY);
        BargainPlan bargainPlan = bargainPlanService.info(bargainPlanOrder.getBargainPlanId());
        Validate.notNull(bargainPlan);
        double amount = 0;
        for (BargainPlanOrderItem bargainPlanOrderItem : bargainPlanOrderItems) {
            if (bargainPlanOrderItem.getType() == BargainPlanOrderItem.TYPE_ADULT) {
                amount += bargainPlan.getSalePrice();
            } else if (bargainPlanOrderItem.getType() == BargainPlanOrderItem.TYPE_KID) {
                amount += bargainPlan.getKidPrice();
            }
        }
        bargainPlanOrder.setAmount(amount);
        bargainPlanOrder.setUserId(MemberService.getCurrentUserId());
        insert(bargainPlanOrder);
        for (BargainPlanOrderItem bargainPlanOrderItem : bargainPlanOrderItems) {
            bargainPlanOrderItem.setPlanOrderId(bargainPlanOrder.getId());
            bargainPlanOrderItemService.insert(bargainPlanOrderItem);
        }
    }

    public void update(BargainPlanOrder bargainPlanOrder, BargainPlanOrderItem[] bargainPlanOrderItems) {
        BargainPlanOrder localOrder = info(bargainPlanOrder.getId());
        Validate.dataAuthorityCheck(localOrder);
        BargainPlan bargainPlan = bargainPlanService.info(bargainPlanOrder.getBargainPlanId());
        double amount = 0;
        for (BargainPlanOrderItem bargainPlanOrderItem : bargainPlanOrderItems) {
            if (bargainPlanOrderItem.getType() == BargainPlanOrderItem.TYPE_ADULT) {
                amount += bargainPlan.getSalePrice();
            } else if (bargainPlanOrderItem.getType() == BargainPlanOrderItem.TYPE_KID) {
                amount += bargainPlan.getKidPrice();
            }
        }
        bargainPlanOrder.setAmount(amount);
        update(bargainPlanOrder);
        for (BargainPlanOrderItem bargainPlanOrderItem : bargainPlanOrderItems) {
            bargainPlanOrderItem.setPlanOrderId(bargainPlanOrder.getId());
            bargainPlanOrderItemService.edit(bargainPlanOrderItem);
        }
        List<BargainPlanOrderItem> localItemList = bargainPlanOrderItemService.list(Collections.<String, Object>singletonMap("planOrderId", localOrder.getId()));
        for (BargainPlanOrderItem localItem : localItemList) {
            boolean flag = false;
            for (BargainPlanOrderItem item : bargainPlanOrderItems) {
                if (localItem.getId().equals(item.getId())) {
                    flag = true;
                    break;
                }
            }
            if (flag) {
                continue;
            }
            bargainPlanOrderItemService.del(localItem.getId() + "");
        }
    }

    public Map<String, Object> prePayOrder(Long orderId, String ip) {
        BargainPlanOrder bargainPlanOrder = info(orderId);
        PayOrder payOrder = new PayOrder();
        payOrder.setUserId(MemberService.getCurrentUserId());
        payOrder.setBody(bargainPlanOrder.getOrderName());
        payOrder.setIp(ip);

        ThridPartyUser thirdPartyUser = userService.getThirdPartyUser(payOrder.getUserId());
        if (thirdPartyUser == null) {
            throw new BizValidateException(ErrorCode.ERROR_51001, "找不到当前用户的openId");
        }
        String openId = thirdPartyUser.getOpenId();

        payOrder.setOpenId(openId);
        payOrder.setTotalFee(bargainPlanOrder.getAmount());
        payOrderService.unifiedOrder(payOrder, PayOrderService.PAY_ORDER_TYPE_WEIXIN, NOTIFY_URL);
        bargainPlanOrder.setPayOrder(payOrder.getId());
        update(bargainPlanOrder);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("prePayId", payOrder.getPreOrderId());
        return result;
    }

    public BargainPlanOrder detail(Long id) {
        BargainPlanOrder bargainPlanOrder = info(id);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("planOrderId", id);
        List<BargainPlanOrderItem> list = bargainPlanOrderItemService.list(paramMap);
        bargainPlanOrder.setItemList(list);
        return bargainPlanOrder;
    }

    public long payOrderByPayOrder(Long payOrderId ) {
        List<BargainPlanOrder> list = list(Collections.<String, Object>singletonMap("payOrder", payOrderId));
        if (list.isEmpty()) {

            throw new BizValidateException(ErrorCode.ERROR_51001, "行程订单不存在");
        }
        BargainPlanOrder bargainPlanOrder = list.get(0);
        if (bargainPlanOrder.getStatus() == BargainPlanOrder.STATUS_WAIT_PAY) {
            payOrder(bargainPlanOrder);
        }
        return bargainPlanOrder.getId();
    }

    public void payOrder(Long id) {
        BargainPlanOrder bargainPlanOrder = info(id);
        payOrder(bargainPlanOrder);
    }

    public void payOrder(BargainPlanOrder bargainPlanOrder) {
        bargainPlanOrder.setStatus(BargainPlanOrder.STATUS_PAY_SUCCESS);
        update(bargainPlanOrder);
        BargainPlan bargainPlan = bargainPlanService.info(bargainPlanOrder.getBargainPlanId());
        bargainPlan.setBuyCount(bargainPlan.getBuyCount() + bargainPlanOrder.getAdultCount() + bargainPlanOrder.getKidCount());
        bargainPlanService.update(bargainPlan);
    }

    public void delete(Long id) {
        BargainPlanOrder bargainPlanOrder = info(id);
        Validate.dataAuthorityCheck(bargainPlanOrder);
        del(id + "");
    }
}
