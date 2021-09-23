package com.data.data.hmly.service.outOrder;

import com.data.data.hmly.service.SysUserService;
import com.data.data.hmly.service.balance.AccountLogService;
import com.data.data.hmly.service.balance.BalanceService;
import com.data.data.hmly.service.balance.entity.AccountLog;
import com.data.data.hmly.service.balance.entity.enums.AccountStatus;
import com.data.data.hmly.service.balance.entity.enums.AccountType;
import com.data.data.hmly.service.common.ProductValidateCodeService;
import com.data.data.hmly.service.common.entity.ProductValidateCode;
import com.data.data.hmly.service.common.entity.QuantitySales;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.common.entity.enums.QuantityFlagType;
import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.outOrder.dao.JszxOrderDetailDao;
import com.data.data.hmly.service.outOrder.entity.JszxOrder;
import com.data.data.hmly.service.outOrder.entity.JszxOrderDetail;
import com.data.data.hmly.service.outOrder.entity.enums.JszxOrderDetailStatus;
import com.data.data.hmly.service.outOrder.entity.enums.JszxOrderStatus;
import com.data.data.hmly.service.ticket.entity.TicketPrice;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.zuipin.util.NumberUtil;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Service;
//import sun.plugin2.os.windows.FLASHWINFO;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dy on 2016/2/24.
 */
@Service
public class JszxOrderDetailService {
    @Resource
    private JszxOrderDetailDao jszxOrderDetailDao;
    @Resource
    private JszxOrderService jszxOrderService;
    @Resource
    private ProductValidateCodeService validateCodeService;
    @Resource
    private SysUserService sysUserService;
    @Resource
    private AccountLogService accountLogService;
    @Resource
    private BalanceService balanceService;


    public List<JszxOrderDetail> getOrderDetailList(JszxOrder jszxOrder, JszxOrderDetail orderDetail) {

        Criteria<JszxOrderDetail> criteria = new Criteria<JszxOrderDetail>(JszxOrderDetail.class);

        if (orderDetail != null) {
            if (orderDetail.getUseStatus() != null) {
                criteria.ne("useStatus", orderDetail.getUseStatus());
            }

        }

        criteria.eq("jszxOrder", jszxOrder);
        return jszxOrderDetailDao.findByCriteria(criteria);

    }

    public void cancelOrderDetail(JszxOrder jszxOrder, JszxOrderDetail orderDetail, SysUser loginUser, SysUnit sysUnit) {
        SysUser sysUser = jszxOrder.getUser();
        if (jszxOrder.getStatus() == JszxOrderStatus.WAITING) {
            orderDetail.setUseStatus(JszxOrderDetailStatus.CANCEL);

            Float totalPrice = jszxOrder.getTotalPrice();
            Float price = orderDetail.getPrice();
            totalPrice = totalPrice - (price * orderDetail.getCount());
            jszxOrder.setTotalPrice(totalPrice);

            Float actualPayTotalPrice = jszxOrder.getActualPayPrice();
            Float actualPayPrice = orderDetail.getActualPay();
            Float restActualPayPrice = actualPayPrice - (actualPayPrice / orderDetail.getRestCount());
            orderDetail.setActualPay(restActualPayPrice);
            actualPayTotalPrice = actualPayTotalPrice - (actualPayPrice / orderDetail.getRestCount());

            jszxOrder.setActualPayPrice(actualPayTotalPrice);

            jszxOrderDetailDao.update(orderDetail);
            updateJszxOrderCancel(jszxOrder);
        } else if (jszxOrder.getStatus() == JszxOrderStatus.PAYED) {
            if (jszxOrder.getProType() != ProductType.line) {
                // 验证码更新
                ProductValidateCode productValidateCode = validateCodeService.findValidateCodeByOrderNo(orderDetail.getTicketNo());
                int orderCount = productValidateCode.getOrderCount();
                Float perActualPrice = orderDetail.getActualPay() / orderCount;
                balanceService.updateBalance(perActualPrice.doubleValue(), AccountType.refund, sysUser.getId(), jszxOrder.getSupplierId(), loginUser.getId(), orderDetail.getTicketNo(), orderDetail.getId());

                productValidateCode.setOrderCount(productValidateCode.getOrderCount() - 1);     // 可验票数-1
                productValidateCode.setRefundCount(productValidateCode.getRefundCount() + 1);   // 退款数＋１
                productValidateCode.syncUsed(orderDetail.getEndTime());
                productValidateCode.setUpdateTime(new Date());
                validateCodeService.update(productValidateCode);
                orderDetail.setActualPay(orderDetail.getActualPay() - perActualPrice);
                jszxOrder.setActualPayPrice(jszxOrder.getActualPayPrice() - perActualPrice);
                orderDetail.syncUseStatus(orderDetail, productValidateCode);
                jszxOrderDetailDao.update(orderDetail);
                updateJszxOrderCancel(jszxOrder);
            } else if (sysUnit.getId() != jszxOrder.getSupplierUnit().getId()) {
                orderDetail.setUseStatus(JszxOrderDetailStatus.REFUNDING);
                jszxOrderDetailDao.update(orderDetail);
                updateJszxOrderCancel(jszxOrder);
            } else {
                jszxOrder.setActualPayPrice(jszxOrder.getActualPayPrice() - orderDetail.getActualPay());
                balanceService.updateBalance(orderDetail.getActualPay().doubleValue(), AccountType.refund, sysUser.getId(), jszxOrder.getSupplierId(), loginUser.getId(), orderDetail.getTicketNo(), orderDetail.getId());
                orderDetail.setUseStatus(JszxOrderDetailStatus.CANCEL);
                jszxOrderDetailDao.update(orderDetail);
                updateJszxOrderCancel(jszxOrder);
            }
        }
    }

    /**
     * 判断订单下的票号类型是否都被取消
     * @param jszxOrder
     */
    public void updateJszxOrderCancel(JszxOrder jszxOrder) {
        List<JszxOrderDetail> orderDetailList = getOutTicketList(jszxOrder);

        int refundSize = 0;

        for (JszxOrderDetail detail : orderDetailList) {

            if (detail.getUseStatus() == JszxOrderDetailStatus.CANCEL) {
                refundSize++;       //被取消的数量
            }
        }

        //判断被取消的个数是否与总票数相等
        if (refundSize == orderDetailList.size()) {
            //相等，则订单设为取消状态
            jszxOrder.setStatus(JszxOrderStatus.CANCELED);
        }
        jszxOrderService.update(jszxOrder, jszxOrder.getUser(), jszxOrder.getCompanyUnit());
    }


    /**
     *  申请取消门票
     * @param jszxOrder
     * @param jszxOrderDetails
     */
    public void cancelOutOrderTickets(JszxOrder jszxOrder, List<JszxOrderDetail> jszxOrderDetails) {


        List<JszxOrderDetail> roderTickets = getOutTicketList(jszxOrder);

        SysUser sysUser = jszxOrder.getUser();

        ProductValidateCode productValidateCode = validateCodeService.getPvCode(jszxOrder.getOrderNo());

        AccountLog accountLog = new AccountLog();
        accountLog.setOrderNo(jszxOrder.getOrderNo());
        accountLog.setUser(sysUser);
        accountLog.setCompanyUnit(jszxOrder.getCompanyUnit());
        accountLog.setType(AccountType.refund);
        accountLog.setStatus(AccountStatus.normal);
        accountLog.setDescription("退款");

        if (jszxOrderDetails.size() == productValidateCode.getOrderCount()) {

            Double totalPrice = 0d;

            jszxOrder.setStatus(JszxOrderStatus.CANCELED);
            jszxOrderService.update(jszxOrder, jszxOrder.getUser(), jszxOrder.getCompanyUnit());
            for (JszxOrderDetail tempTicket : jszxOrderDetails) {
                tempTicket.setUseStatus(JszxOrderDetailStatus.CANCEL);
                productValidateCode.setUsed(-1);
                int ocount = productValidateCode.getOrderCount();
                productValidateCode.setOrderCount(ocount - 1);
                String price = tempTicket.getPrice().toString();
                totalPrice = totalPrice + Double.parseDouble(price);
                sysUser.setBalance(sysUser.getBalance() + Double.parseDouble(price));
                sysUserService.updateUser(sysUser);
                validateCodeService.update(productValidateCode);
                jszxOrderDetailDao.update(tempTicket);
            }

            accountLog.setBalance(sysUser.getBalance());
            accountLog.setMoney(totalPrice);
            accountLogService.save(accountLog);

        } else {
            Double totalPrice = 0d;
            for (JszxOrderDetail tempTicket : jszxOrderDetails) {
                tempTicket.setUseStatus(JszxOrderDetailStatus.CANCEL);
                String price = tempTicket.getPrice().toString();
                totalPrice = totalPrice + Double.parseDouble(price);
                sysUser.setBalance(sysUser.getBalance() + Double.parseDouble(price));
                sysUserService.updateUser(sysUser);
                int ocount = productValidateCode.getOrderCount();
                productValidateCode.setOrderCount(ocount - 1);
                validateCodeService.update(productValidateCode);
                jszxOrderDetailDao.update(tempTicket);
            }

            accountLog.setBalance(sysUser.getBalance());
            accountLog.setMoney(totalPrice);
            accountLogService.save(accountLog);
        }


    }

    public JszxOrderDetail load(Long id) {
        return jszxOrderDetailDao.load(id);
    }

    public JszxOrderDetail get(Long id) {
        Criteria<JszxOrderDetail> criteria = new Criteria<JszxOrderDetail>(JszxOrderDetail.class);
        criteria.createCriteria("jszxOrder");
        criteria.eq("id", id);
        return jszxOrderDetailDao.findUniqueByCriteria(criteria);
    }



    public void update(JszxOrderDetail jszxOrderDetail) {
        jszxOrderDetail.setUpdateTime(new Date());
        jszxOrderDetailDao.update(jszxOrderDetail);
    }

    public void save(JszxOrderDetail jszxOrderDetail) {
        jszxOrderDetail.setUseStatus(JszxOrderDetailStatus.UNUSED);
        jszxOrderDetail.setCreateTime(new Date());
        jszxOrderDetail.setTicketNo(NumberUtil.getRunningNo());
        jszxOrderDetail.setCreateTime(new Date());
        jszxOrderDetail.setUpdateTime(new Date());
        jszxOrderDetailDao.save(jszxOrderDetail);
    }

    public void saveAll(List<JszxOrderDetail> jszxOrderDetails) {
        for (JszxOrderDetail orderDetail : jszxOrderDetails) {
            save(orderDetail);
        }
    }



    public List<JszxOrderDetail> findOutOrderTicketsByOrderNo(String ticketNo, JszxOrderDetailStatus status) {

        Criteria<JszxOrderDetail> criteria = new Criteria<JszxOrderDetail>(JszxOrderDetail.class);
        criteria.eq("ticketNo", ticketNo);
        criteria.eq("useStatus", status);


        return jszxOrderDetailDao.findByCriteria(criteria);
    }


    public void updateAll(List<JszxOrderDetail> jszxOrderDetails) {
        jszxOrderDetailDao.updateAll(jszxOrderDetails);
    }



    public List<JszxOrderDetail> getOutTicketList( JszxOrder jszxOrder) {

        Criteria<JszxOrderDetail> criteria = new Criteria<JszxOrderDetail>(JszxOrderDetail.class);

        if (jszxOrder.getJszxOrderDetailName() != null) {
            criteria.like("ticketName", jszxOrder.getJszxOrderDetailName(), MatchMode.ANYWHERE);
        }
        if (jszxOrder.getDetailUseStatus() != null) {
            criteria.eq("useStatus", jszxOrder.getDetailUseStatus());
        }

        criteria.eq("jszxOrder", jszxOrder);

        List<JszxOrderDetail> orderDetails = jszxOrderDetailDao.findByCriteria(criteria);

        if (jszxOrder.getProType() == ProductType.scenic) {
            return getJszxOrderDetailCodeList(orderDetails);
        } else {
            if (jszxOrder.getStartUseTime() != null) {
                criteria.le("startTime", jszxOrder.getStartUseTime());
            }

            if (jszxOrder.getEndUseTime() != null) {
                criteria.le("startTime", jszxOrder.getEndUseTime());
            }
            return orderDetails;
        }
    }


    public List<JszxOrderDetail> getUnUsedOrderDetailList( JszxOrder jszxOrder) {

        Criteria<JszxOrderDetail> criteria = new Criteria<JszxOrderDetail>(JszxOrderDetail.class);

        criteria.eq("jszxOrder", jszxOrder);
        criteria.add(Restrictions.or(Restrictions.eq("useStatus", JszxOrderDetailStatus.UNUSED),
                Restrictions.eq("useStatus", JszxOrderDetailStatus.REFUNDING)));
        List<JszxOrderDetail> orderDetails = jszxOrderDetailDao.findByCriteria(criteria);
        return orderDetails;

    }

    public List<JszxOrderDetail> getOrderDetailListByDate( JszxOrder jszxOrder, JszxOrderDetail jszxOrderDetail) {

        Criteria<JszxOrderDetail> criteria = new Criteria<JszxOrderDetail>(JszxOrderDetail.class);

        criteria.eq("jszxOrder", jszxOrder);

        if (jszxOrder.getProType() == ProductType.line) {
            criteria.le("startTime", jszxOrderDetail.getStartTime());
        } else {
            criteria.le("endTime", jszxOrderDetail.getEndTime());
        }
        return jszxOrderDetailDao.findByCriteria(criteria);

    }
    public List<JszxOrderDetail> getOrderDetailList( JszxOrder jszxOrder, JszxOrderDetailStatus orderDetailStatus, String ticketName) {

        Criteria<JszxOrderDetail> criteria = new Criteria<JszxOrderDetail>(JszxOrderDetail.class);
        if (ticketName != null) {
            criteria.like("ticketName", ticketName, MatchMode.ANYWHERE);
        }
        if (orderDetailStatus != null) {
            criteria.eq("useStatus", orderDetailStatus);
        }

        criteria.eq("jszxOrder", jszxOrder);
        List<JszxOrderDetail> orderDetails = jszxOrderDetailDao.findByCriteria(criteria);
        return orderDetails;

    }

    public List<JszxOrderDetail> getJszxOrderDetailCodeList( List<JszxOrderDetail> orderDetails) {

        List<String> orderNoList = new ArrayList<String>();

        for (JszxOrderDetail detail : orderDetails) {



            orderNoList.add(detail.getTicketNo());
        }
        List<JszxOrderDetail> newDetails = new ArrayList<JszxOrderDetail>();

        List<ProductValidateCode> codeList = validateCodeService.findValidateCodesByOrderNo(orderNoList);

        if (codeList.isEmpty()) {
            return orderDetails;
        } else {

            for (ProductValidateCode validateCode : codeList) {

                for (JszxOrderDetail orderDetail : orderDetails) {

                    if ((orderDetail.getTicketNo()).equals(validateCode.getTicketNo())) {
                        orderDetail.setCode(validateCode.getCode());
                        orderDetail.fmtTicketDetailUseStatus(orderDetail, validateCode);
                        newDetails.add(orderDetail);
                    }
                }
            }
            return newDetails;

        }

    }



    /**
     * 验票成功后，把未被使用的门票改为已使用状态
     * @param orderNo
     * @param validateCount
     * @param orderId
     */
    public void saveValidateSuccessOrderDetail(String orderNo, Integer validateCount, Long orderId, JszxOrderDetailStatus status) {
        JszxOrder jszxOrder = jszxOrderService.load(orderId);
        JszxOrderDetail jszxOrderDetail = getOrderDetailByOrderNo(orderNo, jszxOrder);
        jszxOrderDetail.setRestCount(jszxOrderDetail.getRestCount() - validateCount);
        jszxOrderDetail.setUseStatus(status);         //设置为已使用状态
        jszxOrderDetail.setUseTime(new Date());
        jszxOrderDetail.setUpdateTime(new Date());
        update(jszxOrderDetail);                   //更新订单门票

    }

    /**
     * 通过orderNo和JszxOrder取出JszxOrderDetail对象
     * @param orderNo
     * @return
     */
    public JszxOrderDetail getOrderDetailByOrderNo(String orderNo, JszxOrder order) {

        Criteria<JszxOrderDetail> criteria = new Criteria<JszxOrderDetail>(JszxOrderDetail.class);
        criteria.eq("ticketNo", orderNo);
        criteria.eq("jszxOrder", order);
//        criteria.ne("useStatus", JszxOrderDetailStatus.UNUSED);
        return jszxOrderDetailDao.findUniqueByCriteria(criteria);
    }


    /**
     * 通过orderNo取出JszxOrderDetail对象
     * @param orderNo
     * @return
     */
    public JszxOrderDetail getOrderDetailByOrderNo(String orderNo) {

        Criteria<JszxOrderDetail> criteria = new Criteria<JszxOrderDetail>(JszxOrderDetail.class);
        criteria.eq("ticketNo", orderNo);
//        criteria.ne("useStatus", JszxOrderDetailStatus.UNUSED);
        return jszxOrderDetailDao.findUniqueByCriteria(criteria);
    }

    public List<JszxOrderDetail> getOrderDetailListByRefund(JszxOrder jszxOrder, int refund) {

        Criteria<JszxOrderDetail> criteria = new Criteria<JszxOrderDetail>(JszxOrderDetail.class);
        criteria.eq("jszxOrder", jszxOrder);
        criteria.ne("refundCount", refund);
        return jszxOrderDetailDao.findByCriteria(criteria);
    }


    /**
     * 取出未使用的门票
     * @param jszxOrder
     * @param status
     * @return
     */
    public List<JszxOrderDetail> getOutTicketList( JszxOrder jszxOrder, JszxOrderDetailStatus status) {

        Criteria<JszxOrderDetail> criteria = new Criteria<JszxOrderDetail>(JszxOrderDetail.class);
        criteria.eq("jszxOrder", jszxOrder);
        criteria.eq("useStatus", status);
        return jszxOrderDetailDao.findByCriteria(criteria);

    }

    public List<JszxOrderDetail> getOutTicketListByoutOrderId(Page page, JszxOrder jszxOrder) {

        Criteria<JszxOrderDetail> criteria = new Criteria<JszxOrderDetail>(JszxOrderDetail.class);

        criteria.eq("jszxOrder", jszxOrder);

        return jszxOrderDetailDao.findByCriteria(criteria, page);

    }

    public void delAllOutOrderTicket(JszxOrder jszxOrder, List<TicketPrice> ticketPriceList) {

        Criteria<JszxOrderDetail> criteria = new Criteria<JszxOrderDetail>(JszxOrderDetail.class);
        criteria.eq("jszxOrder", jszxOrder);
        criteria.in("typePriceId", ticketPriceList);
        List<JszxOrderDetail> jszxOrderDetails = jszxOrderDetailDao.findByCriteria(criteria);
        jszxOrderDetailDao.deleteAll(jszxOrderDetails);
    }

    public void delOutOrderTicket(JszxOrderDetail jszxOrderDetail) {
        Criteria<JszxOrderDetail> criteria = new Criteria<JszxOrderDetail>(JszxOrderDetail.class);
        criteria.eq("jszxOrder", jszxOrderDetail.getJszxOrder());
        criteria.eq("typePriceId", jszxOrderDetail.getTypePriceId());
        List<JszxOrderDetail> jszxOrderDetails = jszxOrderDetailDao.findByCriteria(criteria);
        jszxOrderDetailDao.deleteAll(jszxOrderDetails);
    }

    public void delOutOrderTicketByOutOrder(JszxOrder jszxOrder) {

        Criteria<JszxOrderDetail> criteria = new Criteria<JszxOrderDetail>(JszxOrderDetail.class);

        criteria.eq("jszxOrder", jszxOrder);

        List<JszxOrderDetail> jszxOrderDetails = jszxOrderDetailDao.findByCriteria(criteria);

        jszxOrderDetailDao.deleteAll(jszxOrderDetails);

    }

    /**
     * 功能描述：获取产品下的已经设置拱量的价格类型
     * @param jszxOrder
     * @param typePriceId
     * @param quantitySales
     * @return
     */
    public List<JszxOrderDetail> getDetailListByQuantity(JszxOrder jszxOrder, Long typePriceId, QuantitySales quantitySales) {
        Criteria<JszxOrderDetail> criteria = new Criteria<JszxOrderDetail>(JszxOrderDetail.class);


        if (quantitySales.getFlag() != QuantityFlagType.global) {   //判断拱量是否是全局还是公司
            criteria.createCriteria("jszxOrder", "jo", JoinType.LEFT_OUTER_JOIN);
            criteria.eq("jo.product", jszxOrder.getProduct());
            criteria.eq("jo.companyUnit", jszxOrder.getCompanyUnit());
        }
        criteria.eq("typePriceId", typePriceId);

        if (quantitySales.getEndTime() != null) {
            criteria.le("createTime", quantitySales.getEndTime());
        }

        if (quantitySales.getStartTime() != null) {
            criteria.ge("createTime", quantitySales.getStartTime());
        }


        return jszxOrderDetailDao.findByCriteria(criteria);

    }
}
