package com.data.data.hmly.service.outOrder;

import com.data.data.hmly.service.QuantityUnitNumService;
import com.data.data.hmly.service.common.MsgService;
import com.data.data.hmly.service.common.ProductValidateCodeService;
import com.data.data.hmly.service.common.QuantitySalesDetailService;
import com.data.data.hmly.service.common.QuantitySalesService;
import com.data.data.hmly.service.common.entity.Product;
import com.data.data.hmly.service.common.entity.ProductValidateCode;
import com.data.data.hmly.service.common.entity.QuantitySales;
import com.data.data.hmly.service.common.entity.QuantitySalesDetail;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.common.entity.enums.QuantityType;
import com.data.data.hmly.service.common.util.DateUtils;
import com.data.data.hmly.service.common.util.HTMLFilterUtils;
import com.data.data.hmly.service.dao.SysSiteDao;
import com.data.data.hmly.service.dao.SysUnitDao;
import com.data.data.hmly.service.entity.SysSite;
import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.entity.UnitType;
import com.data.data.hmly.service.outOrder.dao.JszxOrderDao;
import com.data.data.hmly.service.outOrder.dao.JszxOrderDetailDao;
import com.data.data.hmly.service.outOrder.entity.DatagridEntity.PurchaseEntity;
import com.data.data.hmly.service.outOrder.entity.DatagridEntity.SalesEntity;
import com.data.data.hmly.service.outOrder.entity.JszxOrder;
import com.data.data.hmly.service.outOrder.entity.JszxOrderDetail;
import com.data.data.hmly.service.outOrder.entity.enums.JszxOrderDetailPriceType;
import com.data.data.hmly.service.outOrder.entity.enums.JszxOrderDetailStatus;
import com.data.data.hmly.service.outOrder.entity.enums.JszxOrderStatus;
import com.data.data.hmly.service.outOrder.entity.enums.SourceType;
import com.data.data.hmly.service.ticket.TicketExplainService;
import com.data.data.hmly.service.ticket.TicketPriceService;
import com.data.data.hmly.service.ticket.TicketService;
import com.data.data.hmly.service.ticket.entity.Ticket;
import com.data.data.hmly.service.ticket.entity.TicketExplain;
import com.data.data.hmly.service.ticket.entity.TicketPrice;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.ning.http.util.DateUtil;
import com.zuipin.util.NumberUtil;
import com.zuipin.util.PropertiesManager;
import com.zuipin.util.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.hibernate.criterion.*;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * Created by dy on 2016/2/23.
 */
@Service
public class JszxOrderService {

    @Resource
    private JszxOrderDao dao;
    @Resource
    private SysSiteDao sysSiteDao;
    @Resource
    private SysUnitDao sysUnitDao;
    @Resource
    private JszxOrderDetailService jszxOrderDetailService;

    @Resource
    private JszxOrderDetailDao jszxOrderDetailDao;

    @Resource
    private ProductValidateCodeService productValidateCodeService;

    @Resource
    private QuantityUnitNumService quantityUnitNumService;

    @Resource
    private QuantitySalesService quantitySalesService;

    @Resource
    private QuantitySalesDetailService quantitySalesDetailService;

    @Resource
    private PropertiesManager propertiesManager;

    @Resource
    private TicketService ticketService;

    @Resource
    private TicketPriceService ticketPriceService;

    @Resource
    private MsgService msgService;

    @Resource
    private TicketExplainService explainService;

//    @Resource
//    private OutOrderTicketService outOrderTicketService;

    @Resource
    private SysSiteDao siteDao;





    /**
     * 功能描述：计算拱量价格
     * @param quantitySales
     * @param count
     * @param price
     * @param type
     * @return
     */
    public Float getQuantityTotalPrice(QuantitySales quantitySales, Integer count, Float price, String type) {

        List<QuantitySalesDetail> quantitySalesDetails = quantitySales.getQuantitySalesDetailList();
        Float quantityTotalPrice = 0f;
        if (quantitySalesDetails.isEmpty()) {
            return quantityTotalPrice = price * count;
        }
        QuantitySalesDetail quantitySalesDetail = quantitySalesDetails.get(0);
        Float favorablePrice = 0f;
        Float discount = 0f;
        if (quantitySales.getType() == QuantityType.money) {
            if ("child".equals(type)) {
                favorablePrice = quantitySalesDetail.getFavorablePriceChild();
            } else {
                favorablePrice = quantitySalesDetail.getFavorablePrice();
            }
            quantityTotalPrice = (price - favorablePrice) * count;
        } else {
            if ("child".equals(type)) {
                discount = quantitySalesDetail.getDiscountChild();
            } else {
                discount = quantitySalesDetail.getDiscount();
            }
            quantityTotalPrice = (price * discount / 100) * count;
        }
        return quantityTotalPrice;
    }

    public QuantitySales getQuantitySales(Product product, Long typePriceId, SysUnit sysUnit, JszxOrder jszxOrder) {

        QuantitySales quantitySales = new QuantitySales();
        //产品类型下的拱量主体信息
        quantitySales = quantitySalesService.getQuantitySalesByTypePriceId(typePriceId);

        //订单数量在产品价格类型拱量范围的拱量配置信息
        if (quantitySales != null) {

            SysUnit supplerUnit = product.getCompanyUnit();

            //公司拱量配置初始数量
            Integer basicSum = quantityUnitNumService.getConditionNumByQuantityUnits(sysUnit, supplerUnit);

            //产品下已使用的价格类型数量
            Integer proSum = getOrderTypeCounts(typePriceId, jszxOrder, quantitySales);

            //统计公司拱量配置初始数量和产品下单价格类型数量
            Integer resultSum = proSum + basicSum;

            doCompareNum(quantitySales, resultSum);
        }
        return quantitySales;
    }


    /**
     * 功能描述：比较数量，并获取符合的拱量配置信息
     * @param quantitySales
     * @param resultSum
     */
    public void doCompareNum(QuantitySales quantitySales, Integer resultSum) {
        List<QuantitySalesDetail> quantitySalesDetails = quantitySalesDetailService.getQuantityByProCounts(quantitySales);
        List<QuantitySalesDetail> newQuantityDetail = new ArrayList<QuantitySalesDetail>();
        if (!quantitySalesDetails.isEmpty()) {
            for (QuantitySalesDetail q : quantitySalesDetails) {

                QuantitySalesDetail qMax = quantitySalesDetails.get(quantitySalesDetails.size() - 1);

                if (resultSum >= qMax.getNumStart()) {
                    newQuantityDetail.add(qMax);
                    break;
                }
                if (resultSum < q.getNumEnd() && resultSum >= q.getNumStart()) {
                    newQuantityDetail.add(q);
                    break;
                }
            }
            quantitySales.setQuantitySalesDetailList(newQuantityDetail);
        }
    }


    public Integer getOrderTypeCounts(Long typePriceId, JszxOrder jszxOrder, QuantitySales quantitySales) {
        List<JszxOrderDetail> jszxOrderDetailList = new ArrayList<JszxOrderDetail>();
        jszxOrderDetailList = jszxOrderDetailService.getDetailListByQuantity(jszxOrder, typePriceId, quantitySales);
        Integer sum = 0;
        if (jszxOrder.getProduct().getProType() == ProductType.scenic) {
            for (JszxOrderDetail jszxOrderDetail : jszxOrderDetailList) {
                ProductValidateCode productValidateCode = productValidateCodeService.getPvCode(jszxOrderDetail.getTicketNo());
                if (productValidateCode != null) {
                    Integer initOrderCount = 0;
                    Integer orderCount = 0;
                    Integer refundCount = 0;
                    if (productValidateCode.getOrderInitCount() != null) {
                        initOrderCount = productValidateCode.getOrderInitCount();
                    }
                    if (productValidateCode.getOrderCount() != null) {
                        orderCount = productValidateCode.getOrderCount();
                    }
                    if (productValidateCode.getRefundCount() != null) {
                        refundCount = productValidateCode.getRefundCount();
                    }
                    sum += initOrderCount - orderCount - refundCount;
                }
            }
        } else {
            for (JszxOrderDetail jszxOrderDetail : jszxOrderDetailList) {
                if (jszxOrderDetail.getUseStatus() == JszxOrderDetailStatus.USED) {
                    sum += jszxOrderDetail.getCount();
                }
            }
        }
        return sum;

    }






    public Map<String, Object> getTicketExcel(JszxOrder jszxOrder, SysUnit companyUnit, SysUser sysUser, boolean isSiteAdmin, boolean isSupperAdmin, boolean isSuplier) {


        Map<String, Object> map = new HashMap<String, Object>();

        Workbook wb = new HSSFWorkbook();

        CellStyle cellStyle = wb.createCellStyle();
        Font font = wb.createFont();

        Sheet sheet = wb.createSheet("门票订单");
        Row row = sheet.createRow(0);


        //表头
        createFirstCell(wb, row, cellStyle, font, (short) 0, "类型");
        createFirstCell(wb, row, cellStyle, font, (short) 1, "订单号");
        createFirstCell(wb, row, cellStyle, font, (short) 2, "下单时间");
        createFirstCell(wb, row, cellStyle, font, (short) 3, "产品名称");
        createFirstCell(wb, row, cellStyle, font, (short) 4, "订单状态");
        createFirstCell(wb, row, cellStyle, font, (short) 5, "票种");
        createFirstCell(wb, row, cellStyle, font, (short) 6, "有效期");
        createFirstCell(wb, row, cellStyle, font, (short) 7, "数量");
        createFirstCell(wb, row, cellStyle, font, (short) 8, "使用状态");
        createFirstCell(wb, row, cellStyle, font, (short) 9, "销售价（元）");
        createFirstCell(wb, row, cellStyle, font, (short) 10, "分销价（元）");
        createFirstCell(wb, row, cellStyle, font, (short) 11, "分销额（元）");
        createFirstCell(wb, row, cellStyle, font, (short) 12, "合计（元）");
        createFirstCell(wb, row, cellStyle, font, (short) 13, "实际支付（元）");
        createFirstCell(wb, row, cellStyle, font, (short) 14, "取票人姓名");
        createFirstCell(wb, row, cellStyle, font, (short) 15, "取票人手机");
        createFirstCell(wb, row, cellStyle, font, (short) 16, "订单备注");
        createFirstCell(wb, row, cellStyle, font, (short) 17, "分销商信息");
        createFirstCell(wb, row, cellStyle, font, (short) 18, "经办人");
        createFirstCell(wb, row, cellStyle, font, (short) 19, "经办人手机");


        List<JszxOrder> jszxOrders = getJxzxOrderListToExcel(jszxOrder, companyUnit, sysUser, isSiteAdmin, isSupperAdmin, isSuplier);

        if (jszxOrders.isEmpty()) {
            map.put("success", false);
            map.put("info", "当前没有可导出的数据！");
            return map;
        }

        int j = 1;
        for (JszxOrder jszxOrderExcel : jszxOrders) {

            jszxOrderExcel.setStartUseTime(jszxOrder.getStartUseTime());
            jszxOrderExcel.setEndUseTime(jszxOrder.getEndUseTime());
            jszxOrderExcel.setDetailUseStatus(jszxOrder.getDetailUseStatus());
            jszxOrderExcel.setJszxOrderDetailName(jszxOrder.getJszxOrderDetailName());
            List<JszxOrderDetail> jszxOrderDetailList = jszxOrderDetailService.getOutTicketList(jszxOrderExcel);

            int mergCount = jszxOrderDetailList.size();

            float refundPrice = 0f;
            for (JszxOrderDetail jszxOrderDetail1 : jszxOrderDetailList) {

                if (jszxOrderDetail1.getRefundCount() != null && jszxOrderDetail1.getRefundCount() > 0) {
                    refundPrice = refundPrice + (jszxOrderDetail1.getPrice() * jszxOrderDetail1.getRefundCount());
                }

            }


            for (JszxOrderDetail jszxOrderDetail : jszxOrderDetailList) {


                Row rowFor = sheet.createRow(j);
                //类型
                createTableCell(wb, rowFor, cellStyle, font, (short) 0, "门票");
                //订单号
                createTableCell(wb, rowFor, cellStyle, font, (short) 1, jszxOrderExcel.getOrderNo());

                //下单时间
                String createTime = DateUtils.format(jszxOrderExcel.getCreateTime(), "yyyy-MM-dd HH:mm");
                createTableCell(wb, rowFor, cellStyle, font, (short) 2, createTime);

                //产品名称
                createTableCell(wb, rowFor, cellStyle, font, (short) 3, jszxOrderExcel.getProduct().getName());

                //订单状态
                createTableCell(wb, rowFor, cellStyle, font, (short) 4, jszxOrderExcel.getStatus().getDescription());

                //票种
                createTableCell(wb, rowFor, cellStyle, font, (short) 5, jszxOrderDetail.getTicketName());

                //有效期
                String startTime = DateUtils.format(jszxOrderDetail.getStartTime(), "yyyy-MM-dd");
                String endTime = DateUtils.format(jszxOrderDetail.getEndTime(), "yyyy-MM-dd");
                createTableCell(wb, rowFor, cellStyle, font, (short) 6, startTime + "-" + endTime);

                //数量
                createTableCell(wb, rowFor, cellStyle, font, (short) 7, jszxOrderDetail.getCount().toString());

                //使用状态
                String useStatusStr = "";

                ProductValidateCode productValidateCode = new ProductValidateCode();
                productValidateCode.setTicketNo(jszxOrderDetail.getTicketNo());
                productValidateCode.setOrderId(jszxOrderExcel.getId());

                productValidateCode = productValidateCodeService.getValidateByProductCode(productValidateCode);

                Integer totalCount = 0;

                if (productValidateCode != null && productValidateCode.getOrderInitCount() != null) {
                    totalCount = productValidateCode.getOrderInitCount();  //总数量
                }

                Integer orderCount = 0;
                if (productValidateCode != null && productValidateCode.getOrderCount() != null) {
                    orderCount = productValidateCode.getOrderCount();    //可验票数量
                }

                Integer refundCount = 0;
                if (productValidateCode != null && productValidateCode.getRefundCount() != null) {
                    refundCount = productValidateCode.getRefundCount();     //退款数量
                }

                String refundCountStr = "";
                if (refundCount != null && refundCount != 0) {
                    refundCountStr = "已退款x" + refundCount + "，";
                }

                String orderCountStr = "";
                if (orderCount != null && orderCount != 0) {
                    orderCountStr = "未验票x" + orderCount + "，";
                }

                String hasedCheckStr = "";
                int hasedCount = totalCount - orderCount - refundCount;     //已验票数量
                if (hasedCount != 0) {
                    hasedCheckStr = "已验票x" + hasedCount + "，";
                }


                if (jszxOrderDetail.getUseStatus() == JszxOrderDetailStatus.UNUSED
                        || jszxOrderDetail.getUseStatus() == JszxOrderDetailStatus.USED) {
                    useStatusStr = useStatusStr + hasedCheckStr;
                    useStatusStr = useStatusStr + orderCountStr;
                    useStatusStr = useStatusStr + refundCountStr;
                }
                if (jszxOrderDetail.getUseStatus() == JszxOrderDetailStatus.CANCEL) {
                    useStatusStr = "已取消，";
                }
                if (jszxOrderExcel.getStatus() == JszxOrderStatus.UNPAY) {
                    useStatusStr = "待付款，";
                }
                if (jszxOrderExcel.getStatus() == JszxOrderStatus.WAITING) {
                    useStatusStr = "待确认，";
                }

                if (useStatusStr.length() > 0 ) {
                    useStatusStr = useStatusStr.substring(0, useStatusStr.length() - 1);
                }

                createTableCell(wb, rowFor, cellStyle, font, (short) 8, useStatusStr);

                Float salesPrice = 0f;

                if (jszxOrderDetail.getSalesPrice() != null) {
                    salesPrice = jszxOrderDetail.getSalesPrice();
                }

                //销售价
                createTableCell(wb, rowFor, cellStyle, font, (short) 9, salesPrice.toString());

                //分销价
                createTableCell(wb, rowFor, cellStyle, font, (short) 10, jszxOrderDetail.getPrice().toString());

                //分销额
                createTableCell(wb, rowFor, cellStyle, font, (short) 11, (jszxOrderDetail.getPrice() * totalCount) + "");

                //合计
                createTableCell(wb, rowFor, cellStyle, font, (short) 12, jszxOrderExcel.getTotalPrice().toString());

                //实际支付金额
                createTableCell(wb, rowFor, cellStyle, font, (short) 13, (jszxOrderExcel.getActualPayPrice()) + "");

                //取票人姓名
                createTableCell(wb, rowFor, cellStyle, font, (short) 14, jszxOrderExcel.getContact());

                //取票人手机
                createTableCell(wb, rowFor, cellStyle, font, (short) 15, jszxOrderExcel.getPhone());

                //订单备注
                createTableCell(wb, rowFor, cellStyle, font, (short) 16, jszxOrderExcel.getSource());

                //分销商信息
                createTableCell(wb, rowFor, cellStyle, font, (short) 17, jszxOrderExcel.getProduct().getCompanyUnit().getName());

                //经办人
                createTableCell(wb, rowFor, cellStyle, font, (short) 18, jszxOrderExcel.getUser().getAccount());

                //经办人手机
                createTableCell(wb, rowFor, cellStyle, font, (short) 19, jszxOrderExcel.getUser().getMobile());

                j++;

            }

            //判断类型数量是否大于1
            if (mergCount > 1) {
                sheet.addMergedRegion(new CellRangeAddress(j - mergCount, j - 1, 1, 1));
                sheet.addMergedRegion(new CellRangeAddress(j - mergCount, j - 1, 0, 0));
                sheet.addMergedRegion(new CellRangeAddress(j - mergCount, j - 1, 2, 2));
                sheet.addMergedRegion(new CellRangeAddress(j - mergCount, j - 1, 3, 3));
                sheet.addMergedRegion(new CellRangeAddress(j - mergCount, j - 1, 4, 4));
                sheet.addMergedRegion(new CellRangeAddress(j - mergCount, j - 1, 12, 12));
                sheet.addMergedRegion(new CellRangeAddress(j - mergCount, j - 1, 12, 12));
                sheet.addMergedRegion(new CellRangeAddress(j - mergCount, j - 1, 13, 13));
                sheet.addMergedRegion(new CellRangeAddress(j - mergCount, j - 1, 14, 14));
                sheet.addMergedRegion(new CellRangeAddress(j - mergCount, j - 1, 15, 15));
                sheet.addMergedRegion(new CellRangeAddress(j - mergCount, j - 1, 16, 16));
                sheet.addMergedRegion(new CellRangeAddress(j - mergCount, j - 1, 17, 17));
                sheet.addMergedRegion(new CellRangeAddress(j - mergCount, j - 1, 18, 18));
                sheet.addMergedRegion(new CellRangeAddress(j - mergCount, j - 1, 19, 19));
            }

        }


        sheet.autoSizeColumn(0); //adjust width of the first column
        sheet.autoSizeColumn(1); //adjust width of the second column
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);
        sheet.autoSizeColumn(4);
        sheet.autoSizeColumn(5);
        sheet.autoSizeColumn(6);
        sheet.autoSizeColumn(7);
        sheet.autoSizeColumn(8);
        sheet.autoSizeColumn(9);
        sheet.autoSizeColumn(10);
        sheet.autoSizeColumn(11);
        sheet.autoSizeColumn(12);
        sheet.autoSizeColumn(13);
        sheet.autoSizeColumn(14);
        sheet.autoSizeColumn(15);
        sheet.autoSizeColumn(16);
        sheet.autoSizeColumn(17);
        sheet.autoSizeColumn(18);
        sheet.autoSizeColumn(19);



        FileOutputStream fileOut = null;
        String fileName = null;

        if (isSuplier) {
            fileName = "ticket_cell_" + new Date().getTime() + ".xls";
        } else {
            fileName = "ticket_purchase_" + new Date().getTime() + ".xls";
        }


        String staticPath = propertiesManager.getString("IMG_DIR");
        String fn = staticPath + "/tempfile/" + fileName;

        File fileDir = new File(staticPath + "/tempfile/");
        if (!fileDir.exists()) {
            fileDir.mkdir();
        }

        try {
            fileOut = new FileOutputStream(fn);
            wb.write(fileOut);
            fileOut.close();
        } catch (IOException e) {
            System.out.println(e);
        }

        map.put("downloadUrl", "/tempfile/" + fileName);
        map.put("success", true);
        map.put("info", "导出成功！");
        return map;
    }


    /**
     * 线路订单表格导出
     * @param jszxOrder
     * @param companyUnit
     * @param sysUser
     * @param isSiteAdmin
     * @param isSupperAdmin
     * @return
     */
    public Map<String, Object> getLineExcel(JszxOrder jszxOrder, SysUnit companyUnit, SysUser sysUser, boolean isSiteAdmin, boolean isSupperAdmin, boolean isSuplier) {

        Map<String, Object> map = new HashMap<String, Object>();

        Workbook wb = new HSSFWorkbook();

        CellStyle cellStyle = wb.createCellStyle();
        Font font = wb.createFont();

        Sheet sheet = wb.createSheet("线路订单");
        Row row = sheet.createRow(0);

        //表头
        createFirstCell(wb, row, cellStyle, font, (short) 0, "类型");
        createFirstCell(wb, row, cellStyle, font, (short) 1, "订单号");
        createFirstCell(wb, row, cellStyle, font, (short) 2, "下单时间");
        createFirstCell(wb, row, cellStyle, font, (short) 3, "产品名称");
        createFirstCell(wb, row, cellStyle, font, (short) 4, "订单状态");
        createFirstCell(wb, row, cellStyle, font, (short) 5, "票种");
        createFirstCell(wb, row, cellStyle, font, (short) 6, "出发日期");
        createFirstCell(wb, row, cellStyle, font, (short) 7, "总人数");
        createFirstCell(wb, row, cellStyle, font, (short) 8, "出游状态");
        createFirstCell(wb, row, cellStyle, font, (short) 9, "销售价");
        createFirstCell(wb, row, cellStyle, font, (short) 10, "分销价");
        createFirstCell(wb, row, cellStyle, font, (short) 11, "分销额");
        createFirstCell(wb, row, cellStyle, font, (short) 12, "总额(元)");
        createFirstCell(wb, row, cellStyle, font, (short) 13, "实际支付(元)");
        createFirstCell(wb, row, cellStyle, font, (short) 14, "游客姓名/联系电话/身份证号");
        createFirstCell(wb, row, cellStyle, font, (short) 15, "订单备注");
        createFirstCell(wb, row, cellStyle, font, (short) 16, "供应商");
        createFirstCell(wb, row, cellStyle, font, (short) 17, "经办人");
        createFirstCell(wb, row, cellStyle, font, (short) 18, "经办人手机");



        int j = 1;

        List<JszxOrder> jszxOrders = getJxzxOrderListToExcel(jszxOrder, companyUnit, sysUser, isSiteAdmin, isSupperAdmin, isSuplier);

        if (jszxOrders.isEmpty()) {
            map.put("success", false);
            map.put("info", "当前没有可导出的数据！");
            return map;
        }


        for (JszxOrder jszxOrderExcel :jszxOrders) {
            jszxOrderExcel.setStartUseTime(jszxOrder.getStartUseTime());
            jszxOrderExcel.setEndUseTime(jszxOrder.getEndUseTime());
            jszxOrderExcel.setDetailUseStatus(jszxOrder.getDetailUseStatus());
            jszxOrderExcel.setJszxOrderDetailName(jszxOrder.getJszxOrderDetailName());
            List<JszxOrderDetail> jszxOrderDetailList = jszxOrderDetailService.getOutTicketList(jszxOrderExcel);
            Map<Long, List<JszxOrderDetail>> mapList = new HashMap<Long, List<JszxOrderDetail>>();

            int mergCount = 0;

            //每个门票类型都发送一次
            for (JszxOrderDetail orderDetail : jszxOrderDetailList) {
                List<JszxOrderDetail> orderDetailMapList = new ArrayList<JszxOrderDetail>();
                for (JszxOrderDetail orderDetail2 : jszxOrderDetailList) {
                    if (orderDetail.getTypePriceId() == orderDetail2.getTypePriceId()) {
                        orderDetailMapList.add(orderDetail2);
                    }
                }
                mapList.put(orderDetail.getTypePriceId(), orderDetailMapList);
            }

            //获取退款金额
            float refundTotalPrice = 0f;
            for (Map.Entry<Long, List<JszxOrderDetail>> entry1 : mapList.entrySet()) {
                List<JszxOrderDetail> typeOrderList2 = entry1.getValue();
                for (JszxOrderDetail jszxOrderDetail2 : typeOrderList2) {

                    if (jszxOrderDetail2.getUseStatus() == JszxOrderDetailStatus.CANCEL) {

                        if (jszxOrderDetail2.getType() == JszxOrderDetailPriceType.child) {
                            refundTotalPrice = refundTotalPrice + jszxOrderDetail2.getPrice();
                        } else {
                            refundTotalPrice = refundTotalPrice + jszxOrderDetail2.getPrice();
                        }
                    }

                }
            }

            mergCount = mapList.size();
            for (Map.Entry<Long, List<JszxOrderDetail>> entry1 : mapList.entrySet()) {
                Long typePriceId = entry1.getKey();
                List<JszxOrderDetail> typeOrderList1 = entry1.getValue();


                Row rowFor = sheet.createRow(j);

//                createCell(wb, row, cellStyle, font, (short) 0, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_BOTTOM);

                //订单类型
                createTableCell(wb, rowFor, cellStyle, font, (short) 0, "线路");

                //订单编号
                createTableCell(wb, rowFor, cellStyle, font, (short) 1, jszxOrderExcel.getOrderNo());

                //创建时间
                Date createTime = jszxOrderExcel.getCreateTime();
                String createTimeStr = DateUtils.format(createTime, "yyyy-MM-dd HH:mm");
                createTableCell(wb, rowFor, cellStyle, font, (short) 2, createTimeStr);

                //产品名称
                createTableCell(wb, rowFor, cellStyle, font, (short) 3, jszxOrderExcel.getProduct().getName());

                //订单状态
                String confirmDes = "";

                if (jszxOrderExcel.getIsConfirm() == 1) {
                    confirmDes = "已确认";
                } else if (jszxOrderExcel.getIsConfirm() == -1) {
                    confirmDes = "已拒绝";
                } else if (jszxOrderExcel.getIsConfirm() == 0) {
                    confirmDes = "未确认";
                } else {
                    confirmDes = "无需确认";
                }
                createTableCell(wb, rowFor, cellStyle, font, (short) 4, jszxOrderExcel.getStatus().getDescription() + "    " + confirmDes);

                //票种
                createTableCell(wb, rowFor, cellStyle, font, (short) 5, typeOrderList1.get(0).getTicketName());

                //出发时间
                Date startTime = typeOrderList1.get(0).getStartTime();
                String startTimeStr = DateUtils.format(startTime, "yyyy-MM-dd");
                createTableCell(wb, rowFor, cellStyle, font, (short) 6, startTimeStr);


                int chlidCount = 0;     //儿童数量
                int refundCount = 0;    //退款数量
                int unUsedCount = 0;    //未使用数量
                int refundingCount = 0; //退款中数量
                int playingCount = 0;   //出游中
                int playedCount = 0;    //已出游数量
                int totalCount = typeOrderList1.size();     //总数


                float salesPriceAdult = 0f;         //销售价

                float distributionPrice = 0f;       //分销总价

                float distributionAdult = 0f;       //成人分销单价
                float distributionChild = 0f;       //儿童分销单价


                for (JszxOrderDetail jszxOrderDetail1 : typeOrderList1) {
                    if (jszxOrderDetail1.getType() == JszxOrderDetailPriceType.child) {
                        if (jszxOrderDetail1.getSalesPrice() != null) {
                            salesPriceAdult = salesPriceAdult + jszxOrderDetail1.getSalesPrice();
                        }
                        if (jszxOrderDetail1.getPrice() != null) {
                            distributionPrice = distributionPrice + jszxOrderDetail1.getPrice();
                        }
                        distributionChild = jszxOrderDetail1.getPrice();
                        chlidCount++;
                    } else {
                        if (jszxOrderDetail1.getSalesPrice() != null) {
                            salesPriceAdult = salesPriceAdult + jszxOrderDetail1.getSalesPrice();
                        }
                        if (jszxOrderDetail1.getPrice() != null) {
                            distributionPrice = distributionPrice + jszxOrderDetail1.getPrice();
                        }
                        distributionAdult = jszxOrderDetail1.getPrice();
                    }
                    if (jszxOrderDetail1.getUseStatus() == JszxOrderDetailStatus.USED) {
                        playedCount++;
                    }
                    if (jszxOrderDetail1.getUseStatus() == JszxOrderDetailStatus.CANCEL) {
                        refundCount++;
                    }
                    if (jszxOrderDetail1.getUseStatus() == JszxOrderDetailStatus.UNUSED) {
                        unUsedCount++;
                    }
                    if (jszxOrderDetail1.getUseStatus() == JszxOrderDetailStatus.REFUNDING) {
                        refundingCount++;
                    }

                }
                //数量
                if (chlidCount != 0) {
                    createTableCell(wb, rowFor, cellStyle, font, (short) 7, typeOrderList1.size() + "（" + chlidCount + "儿童）");
                } else {
                    createTableCell(wb, rowFor, cellStyle, font, (short) 7, typeOrderList1.size() + "");
                }

                //出游状态
                long timeLong = DateUtils.getDateDiffLong(new Date(), typeOrderList1.get(0).getStartTime());
                String chuyouStatus = "";
                if (timeLong < 89999999 && timeLong > 0) {

                    String playingStr = "";
                    String refundedStr = "";
                    String refundingStr = "";

                    if ((totalCount - refundCount - refundingCount) != 0) {
                        playingStr = "出游中x" + (totalCount - refundCount - refundingCount) + "，";
                    }

                    if (refundCount != 0) {
                        refundedStr = "已退款x" + refundCount + "，";
                    }

                    if (refundingCount != 0) {
                        refundingStr = "退款中x" + refundingCount + "，";
                    }

                    if (refundingCount != 0) {
                        refundingStr = "退款中x" + refundingCount + "，";
                    }

                    if (jszxOrderExcel.getStatus() == JszxOrderStatus.WAITING) {
                        chuyouStatus = chuyouStatus + "未确认x" + (totalCount - refundCount) + "，";
                        chuyouStatus = chuyouStatus + "已取消x" + refundCount;
                    }

                    if (jszxOrderExcel.getStatus() == JszxOrderStatus.PAYED
                            && jszxOrderExcel.getIsConfirm() == 1) {
                        chuyouStatus = chuyouStatus + playingStr;
                        chuyouStatus = chuyouStatus + refundingStr;
                        chuyouStatus = chuyouStatus + refundedStr;
                    } else if (jszxOrderExcel.getStatus() == JszxOrderStatus.PAYED && jszxOrderExcel.getIsConfirm() == -2) {
                        chuyouStatus = chuyouStatus + playingStr;
                        chuyouStatus = chuyouStatus + refundingStr;
                        chuyouStatus = chuyouStatus + refundedStr;
                    } else if (jszxOrderExcel.getStatus() == JszxOrderStatus.WAITING && jszxOrderExcel.getIsConfirm() == 0) {
                        chuyouStatus = chuyouStatus + "待确认x" + unUsedCount + "，";
                        chuyouStatus = chuyouStatus + "已取消x" + refundCount + "，";
                    } else {
                        chuyouStatus = "订单无效，";
                    }

                    if (jszxOrderExcel.getStatus() == JszxOrderStatus.CANCELED) {
                        chuyouStatus = "订单无效，";
                    }

                    if (chuyouStatus.length() > 0 ) {
                        chuyouStatus = chuyouStatus.substring(0, chuyouStatus.length() - 1);
                    }


                } else if (timeLong > 89999999) {

                    String refundedStr = "";
                    String refundingStr = "";
                    String playedStr = "";
                    String unUsedStr = "";

                    if (playedCount != 0) {
                        playedStr = "已出游x" + playedCount + "，";
                    }

                    if (refundCount != 0) {
                        refundedStr = "已退款x" + refundCount + "，";
                    }

                    if (refundingCount != 0) {
                        refundingStr = "退款中x" + refundingCount + "，";
                    }

                    if (unUsedCount != 0) {
                        unUsedStr = "已过期x" + unUsedCount + "，";
                    }




                    if (jszxOrderExcel.getStatus() == JszxOrderStatus.PAYED
                            && jszxOrderExcel.getIsConfirm() == 1) {
                        chuyouStatus = chuyouStatus + playedStr;
                        chuyouStatus = chuyouStatus + unUsedStr;
                        chuyouStatus = chuyouStatus + refundingStr;
                        chuyouStatus = chuyouStatus + refundedStr;
                    } else if (jszxOrderExcel.getStatus() == JszxOrderStatus.PAYED && jszxOrderExcel.getIsConfirm() == -2) {
                        chuyouStatus = chuyouStatus + playedStr;
                        chuyouStatus = chuyouStatus + unUsedStr;
                        chuyouStatus = chuyouStatus + refundingStr;
                        chuyouStatus = chuyouStatus + refundedStr;
                    } else if (jszxOrderExcel.getStatus() == JszxOrderStatus.WAITING && jszxOrderExcel.getIsConfirm() == 0) {
                        chuyouStatus = chuyouStatus + "待确认x" + unUsedCount + "，";
                        chuyouStatus = chuyouStatus + unUsedStr;
                        chuyouStatus = chuyouStatus + "已取消x" + refundCount + "，";
                    } else {
                        chuyouStatus = "订单无效，";
                    }

                    if (jszxOrderExcel.getStatus() == JszxOrderStatus.WAITING) {
                        chuyouStatus = "订单已过期，";
                    }

                    if (jszxOrderExcel.getStatus() == JszxOrderStatus.CANCELED) {
                        chuyouStatus = "订单无效，";
                    }

                    if (chuyouStatus.length() > 0 ) {
                        chuyouStatus = chuyouStatus.substring(0, chuyouStatus.length() - 1);
                    }

                } else {


                    String refundedStr = "";
                    String refundingStr = "";
                    String unUsedStr = "";

                    if (unUsedCount != 0) {
                        unUsedStr = "未出游x" + unUsedCount + "，";
                    }

                    if (refundCount != 0) {
                        refundedStr = "已退款x" + refundCount + "，";
                    }

                    if (refundingCount != 0) {
                        refundingStr = "退款中x" + refundingCount + "，";
                    }

                    if (jszxOrderExcel.getStatus() == JszxOrderStatus.PAYED
                            && jszxOrderExcel.getIsConfirm() == 1) {
                        chuyouStatus = chuyouStatus + unUsedStr;
                        chuyouStatus = chuyouStatus + refundingStr;
                        chuyouStatus = chuyouStatus + refundedStr;
                    } else if (jszxOrderExcel.getStatus() == JszxOrderStatus.PAYED && jszxOrderExcel.getIsConfirm() == -2) {
                        chuyouStatus = chuyouStatus + unUsedStr;
                        chuyouStatus = chuyouStatus + refundingStr;
                        chuyouStatus = chuyouStatus + refundedStr;

                    } else if (jszxOrderExcel.getStatus() == JszxOrderStatus.WAITING && jszxOrderExcel.getIsConfirm() == 0) {
                        chuyouStatus = chuyouStatus + "待确认x" + unUsedCount + "，";
                        chuyouStatus = chuyouStatus + "已取消x" + refundCount + "，";
                    } else {
                        chuyouStatus = "订单无效，";
                    }

                    if (jszxOrderExcel.getStatus() == JszxOrderStatus.CANCELED) {
                        chuyouStatus = "订单无效，";
                    }

                    if (chuyouStatus.length() > 0 ) {
                        chuyouStatus = chuyouStatus.substring(0, chuyouStatus.length() - 1);
                    }

                }
                createTableCell(wb, rowFor, cellStyle, font, (short) 8, chuyouStatus);



                //销售价
                createTableCell(wb, rowFor, cellStyle, font, (short) 9, salesPriceAdult + "");

                //分销价
                if (chlidCount > 0) {
                    createTableCell(wb, rowFor, cellStyle, font, (short) 10, distributionAdult + "，" + distributionChild + "（儿童）");
                } else {
                    createTableCell(wb, rowFor, cellStyle, font, (short) 10, distributionAdult + "");
                }


                //分销额
                createTableCell(wb, rowFor, cellStyle, font, (short) 11, distributionPrice + "");

                //总额
                createTableCell(wb, rowFor, cellStyle, font, (short) 12, jszxOrderExcel.getTotalPrice().toString());

                //实际支付
                createTableCell(wb, rowFor, cellStyle, font, (short) 13, (jszxOrderExcel.getActualPayPrice()) + "");

                //联系人信息
                String contactInfo = jszxOrderExcel.getContact() + "|" + jszxOrderExcel.getPhone();
                if (jszxOrderExcel.getIdcard() != null) {
                    contactInfo = jszxOrderExcel.getContact() + "|" + jszxOrderExcel.getPhone() + "|" + jszxOrderExcel.getIdcard();
                }
                createTableCell(wb, rowFor, cellStyle, font, (short) 14, contactInfo);


                //备注
                createTableCell(wb, rowFor, cellStyle, font, (short) 15, jszxOrderExcel.getSource());

                //供应商
                createTableCell(wb, rowFor, cellStyle, font, (short) 16, jszxOrderExcel.getProduct().getCompanyUnit().getName());

                //经办人
                createTableCell(wb, rowFor, cellStyle, font, (short) 17, jszxOrderExcel.getUser().getAccount());

                //经办人手机
                createTableCell(wb, rowFor, cellStyle, font, (short) 18, jszxOrderExcel.getUser().getMobile());

                j++; //行数加1
            }


            //判断类型数量是否大于1
            if (mergCount > 1) {

                sheet.addMergedRegion(new CellRangeAddress(
                        j - mergCount, //first row (0-based)
                        j - 1, //last row  (0-based)
                        1, //first column (0-based)
                        1  //last column  (0-based)
                ));
                sheet.addMergedRegion(new CellRangeAddress(j - mergCount, j - 1, 0, 0));
                sheet.addMergedRegion(new CellRangeAddress(j - mergCount, j - 1, 2, 2));
                sheet.addMergedRegion(new CellRangeAddress(j - mergCount, j - 1, 3, 3));
                sheet.addMergedRegion(new CellRangeAddress(j - mergCount, j - 1, 4, 4));
                sheet.addMergedRegion(new CellRangeAddress(j - mergCount, j - 1, 12, 12));
                sheet.addMergedRegion(new CellRangeAddress(j - mergCount, j - 1, 12, 12));
                sheet.addMergedRegion(new CellRangeAddress(j - mergCount, j - 1, 13, 13));
                sheet.addMergedRegion(new CellRangeAddress(j - mergCount, j - 1, 14, 14));
                sheet.addMergedRegion(new CellRangeAddress(j - mergCount, j - 1, 15, 15));
                sheet.addMergedRegion(new CellRangeAddress(j - mergCount, j - 1, 16, 16));
                sheet.addMergedRegion(new CellRangeAddress(j - mergCount, j - 1, 16, 17));
                sheet.addMergedRegion(new CellRangeAddress(j - mergCount, j - 1, 16, 18));


            }


        }

        sheet.autoSizeColumn(0); //adjust width of the first column
        sheet.autoSizeColumn(1); //adjust width of the second column
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);
        sheet.autoSizeColumn(4);
        sheet.autoSizeColumn(5);
        sheet.autoSizeColumn(6);
        sheet.autoSizeColumn(7);
        sheet.autoSizeColumn(8);
        sheet.autoSizeColumn(9);
        sheet.autoSizeColumn(10);
        sheet.autoSizeColumn(11);
        sheet.autoSizeColumn(12);
        sheet.autoSizeColumn(13);
        sheet.autoSizeColumn(14);
        sheet.autoSizeColumn(15);
        sheet.autoSizeColumn(16);
        sheet.autoSizeColumn(17);
        sheet.autoSizeColumn(18);
        FileOutputStream fileOut = null;

        String fileName = null;

        if (isSuplier) {
            fileName = "line_sell_" + new Date().getTime() + ".xls";
        } else {
            fileName = "line_purchase_" + new Date().getTime() + ".xls";
        }


        String staticPath = propertiesManager.getString("IMG_DIR");
        String fn = staticPath + "/tempfile/" + fileName;
        File fileDir = new File(staticPath + "/tempfile/");
        if (!fileDir.exists()) {
            fileDir.mkdir();
        }
        try {

            fileOut = new FileOutputStream(fn);
            wb.write(fileOut);
            fileOut.close();
        } catch (IOException e) {
            System.out.println(e);
        }

        map.put("downloadUrl", "/tempfile/" + fileName);
        map.put("success", true);
        map.put("info", "导出成功！");
        return map;


    }

    /**
     * 创建表格头体列
     * @param wb
     * @param row
     * @param column
     * @param value
     */
    public void createFirstCell(Workbook wb, Row row, CellStyle style, Font font, short column, String value) {
        Cell cell = row.createCell(column);
        cell.setCellValue(value);
        CellStyle cellStyle = getFirstStyle(style, font);
        cell.setCellStyle(cellStyle);
    }

    /**
     * 创建表格主体列
     * @param wb
     * @param row
     * @param column
     * @param value
     */
    public void createTableCell(Workbook wb, Row row, CellStyle style, Font font, short column, String value) {
        Cell cell = row.createCell(column);
        cell.setCellValue(value);
        CellStyle cellStyle = getTableStyle(style, font);
        cell.setCellStyle(cellStyle);
    }

    /**
     * 表格头体样式
     * @param style
     * @param font
     * @return
     */
    public CellStyle getFirstStyle(CellStyle style, Font font) {
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 11); // 设置字体大小
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style.setAlignment(CellStyle.ALIGN_CENTER); // 左右居中
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER); // 上下居中
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setFillBackgroundColor(IndexedColors.BLUE.getIndex()); //设置背景颜色
        style.setWrapText(true);
        style.setFont(font);
        return style;
    }

    /**
     * 表格主体样式
     * @param style
     * @param font
     * @return
     */
    public CellStyle getTableStyle(CellStyle style, Font font) {

        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 11); // 设置字体大小
        font.setBoldweight(Font.BOLDWEIGHT_NORMAL);
        style.setAlignment(CellStyle.ALIGN_LEFT); // 左右居中
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER); // 上下居中
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setWrapText(true);
        style.setFont(font);
        return style;
    }


    public List<JszxOrderDetail> getLineTypeOrderList(List<JszxOrderDetail> lineOrderDetailList) {

        Map<Long, List<JszxOrderDetail>> mapList = new HashMap<Long, List<JszxOrderDetail>>();


        //每个门票类型都发送一次
        for (JszxOrderDetail orderDetail : lineOrderDetailList) {
            List<JszxOrderDetail> orderDetailMapList = new ArrayList<JszxOrderDetail>();
            for (JszxOrderDetail orderDetail2 : lineOrderDetailList) {
                if (orderDetail.getTypePriceId() == orderDetail2.getTypePriceId()) {
                    orderDetailMapList.add(orderDetail2);
                }
            }
            mapList.put(orderDetail.getTypePriceId(), orderDetailMapList);
        }


        for (Map.Entry<Long, List<JszxOrderDetail>> entry1 : mapList.entrySet()) {
            Long typePriceId = entry1.getKey();
            List<JszxOrderDetail> typeOrderList1 = entry1.getValue();

            Map<String, List<JszxOrderDetail>> mapList2 = new HashMap<String, List<JszxOrderDetail>>();

            for (JszxOrderDetail jszxOrderDetail : typeOrderList1) {

                List<JszxOrderDetail> orderDetailMapList = new ArrayList<JszxOrderDetail>();

                for (JszxOrderDetail jszxOrderDetail2 : typeOrderList1) {

                    if (jszxOrderDetail.getType() == jszxOrderDetail2.getType()) {

                        orderDetailMapList.add(jszxOrderDetail2);
                    }
                }
                mapList2.put(jszxOrderDetail.getType().toString(), orderDetailMapList);
            }

            JszxOrderDetail entry1OrderDetail = typeOrderList1.get(0);

            JszxOrderDetail jszxOrderDetail = new JszxOrderDetail();

            jszxOrderDetail.setTicketName(entry1OrderDetail.getTicketName());
//            jszxOrderDetail.set



        }


        return lineOrderDetailList;


    }

    /**
     * 取出订单下的所有门票票种
     * @param jszxOrderList
     * @return
     */
    public List<JszxOrder> getLineJszxOrderTypetList(JszxOrder jszxOrder, List<JszxOrder> jszxOrderList) {
        List<JszxOrder> jszxOrders = new ArrayList<JszxOrder>();
        for (JszxOrder jszxOrderExcel : jszxOrderList) {
            List<JszxOrderDetail> jszxOrderDetails = new ArrayList<JszxOrderDetail>();
            jszxOrderExcel.setDetailUseStatus(jszxOrder.getDetailUseStatus());
            jszxOrderExcel.setJszxOrderDetailName(jszxOrder.getJszxOrderDetailName());
            jszxOrderExcel.setEndUseTime(jszxOrder.getEndUseTime());
            jszxOrderExcel.setStartUseTime(jszxOrder.getStartUseTime());
            List<JszxOrderDetail> jszxOrderDetailList = jszxOrderDetailService.getOutTicketList(jszxOrderExcel);
            Map<Long, List<JszxOrderDetail>> mapList = new HashMap<Long, List<JszxOrderDetail>>();
            int mergCount = 0;
            //每个门票类型都发送一次
            for (JszxOrderDetail orderDetail : jszxOrderDetailList) {
                List<JszxOrderDetail> orderDetailMapList = new ArrayList<JszxOrderDetail>();
                for (JszxOrderDetail orderDetail2 : jszxOrderDetailList) {
                    if (orderDetail.getTypePriceId() == orderDetail2.getTypePriceId()) {
                        orderDetailMapList.add(orderDetail2);
                    }
                }
                mapList.put(orderDetail.getTypePriceId(), orderDetailMapList);
            }
            //获取退款金额
            float refundTotalPrice = 0f;
            for (Map.Entry<Long, List<JszxOrderDetail>> entry1 : mapList.entrySet()) {
                List<JszxOrderDetail> typeOrderList2 = entry1.getValue();
                for (JszxOrderDetail jszxOrderDetail2 : typeOrderList2) {
                    if (jszxOrderDetail2.getUseStatus() == JszxOrderDetailStatus.CANCEL) {
                        if (jszxOrderDetail2.getType() == JszxOrderDetailPriceType.child) {
                            refundTotalPrice = refundTotalPrice + jszxOrderDetail2.getPrice();
                        } else {
                            refundTotalPrice = refundTotalPrice + jszxOrderDetail2.getPrice();
                        }
                    }

                }
            }
            for (Map.Entry<Long, List<JszxOrderDetail>> entry1 : mapList.entrySet()) {
                Long typePriceId = entry1.getKey();
                List<JszxOrderDetail> typeOrderList1 = entry1.getValue();
                JszxOrderDetail jszxOrderDetail = typeOrderList1.get(0);
                jszxOrderDetail.setCount(typeOrderList1.size());    //数量

                int chlidCount = 0;     //儿童数量
                int refundCount = 0;    //退款数量
                int unUsedCount = 0;    //未使用数量
                int refundingCount = 0; //退款中数量
                int playingCount = 0;   //出游中
                int playedCount = 0;    //已出游数量
                int totalCount = typeOrderList1.size();     //总数

                float salesPriceAdult = 0f;         //销售价

                float distributionPrice = 0f;       //分销总价
                float distributionAdult = 0f;       //成人分销单价
                float distributionChild = 0f;       //儿童分销单价
                for (JszxOrderDetail jszxOrderDetail1 : typeOrderList1) {
                    if (jszxOrderDetail1.getType() == JszxOrderDetailPriceType.child) {
                        if (jszxOrderDetail1.getSalesPrice() != null) {
                            salesPriceAdult = salesPriceAdult + jszxOrderDetail1.getSalesPrice();
                        }
                        if (jszxOrderDetail1.getPrice() != null) {
                            distributionPrice = distributionPrice + jszxOrderDetail1.getPrice();
                        }
                        distributionChild = jszxOrderDetail1.getPrice();
                        chlidCount++;
                    } else {
                        if (jszxOrderDetail1.getSalesPrice() != null) {
                            salesPriceAdult = salesPriceAdult + jszxOrderDetail1.getSalesPrice();
                        }
                        if (jszxOrderDetail1.getPrice() != null) {
                            distributionPrice = distributionPrice + jszxOrderDetail1.getPrice();
                        }
                        distributionAdult = jszxOrderDetail1.getPrice();
                    }
                    if (jszxOrderDetail1.getUseStatus() == JszxOrderDetailStatus.USED) {
                        playedCount++;
                    }
                    if (jszxOrderDetail1.getUseStatus() == JszxOrderDetailStatus.CANCEL) {
                        refundCount++;
                    }
                    if (jszxOrderDetail1.getUseStatus() == JszxOrderDetailStatus.UNUSED) {
                        unUsedCount++;
                    }
                    if (jszxOrderDetail1.getUseStatus() == JszxOrderDetailStatus.REFUNDING) {
                        refundingCount++;
                    }

                }
                //出游状态
                long timeLong = DateUtils.getDateDiffLong(new Date(), typeOrderList1.get(0).getStartTime());
                String chuyouStatus = "";
                if (timeLong < 89999999 && timeLong > 0) {

                    String playingStr = "";
                    String refundedStr = "";
                    String refundingStr = "";

                    if ((totalCount - refundCount - refundingCount) != 0) {
                        playingStr = "出游中x" + (totalCount - refundCount - refundingCount) + "，";
                    }

                    if (refundCount != 0) {
                        refundedStr = "已退款x" + refundCount + "，";
                    }

                    if (refundingCount != 0) {
                        refundingStr = "退款中x" + refundingCount + "，";
                    }

                    if (refundingCount != 0) {
                        refundingStr = "退款中x" + refundingCount + "，";
                    }

                    if (jszxOrderExcel.getStatus() == JszxOrderStatus.WAITING) {
                        chuyouStatus = chuyouStatus + "未确认x" + (totalCount - refundCount) + "，";
                        chuyouStatus = chuyouStatus + "已取消x" + refundCount;
                    }

                    if (jszxOrderExcel.getStatus() == JszxOrderStatus.PAYED
                            && jszxOrderExcel.getIsConfirm() == 1) {
                        chuyouStatus = chuyouStatus + playingStr;
                        chuyouStatus = chuyouStatus + refundingStr;
                        chuyouStatus = chuyouStatus + refundedStr;
                    } else if (jszxOrderExcel.getStatus() == JszxOrderStatus.PAYED && jszxOrderExcel.getIsConfirm() == -2) {
                        chuyouStatus = chuyouStatus + playingStr;
                        chuyouStatus = chuyouStatus + refundingStr;
                        chuyouStatus = chuyouStatus + refundedStr;
                    } else if (jszxOrderExcel.getStatus() == JszxOrderStatus.WAITING && jszxOrderExcel.getIsConfirm() == 0) {
                        chuyouStatus = chuyouStatus + "待确认x" + unUsedCount + "，";
                        chuyouStatus = chuyouStatus + "已取消x" + refundCount + "，";
                    } else {
                        chuyouStatus = "订单无效，";
                    }

                    if (jszxOrderExcel.getStatus() == JszxOrderStatus.CANCELED) {
                        chuyouStatus = "订单无效，";
                    }

                    if (chuyouStatus.length() > 0 ) {
                        chuyouStatus = chuyouStatus.substring(0, chuyouStatus.length() - 1);
                    }


                } else if (timeLong > 89999999) {

                    String refundedStr = "";
                    String refundingStr = "";
                    String playedStr = "";
                    String unUsedStr = "";

                    if (playedCount != 0) {
                        playedStr = "已出游x" + playedCount + "，";
                    }

                    if (refundCount != 0) {
                        refundedStr = "已退款x" + refundCount + "，";
                    }

                    if (refundingCount != 0) {
                        refundingStr = "退款中x" + refundingCount + "，";
                    }

                    if (unUsedCount != 0) {
                        unUsedStr = "已过期x" + unUsedCount + "，";
                    }
                    if (jszxOrderExcel.getStatus() == JszxOrderStatus.PAYED
                            && jszxOrderExcel.getIsConfirm() == 1) {
                        chuyouStatus = chuyouStatus + playedStr;
                        chuyouStatus = chuyouStatus + unUsedStr;
                        chuyouStatus = chuyouStatus + refundingStr;
                        chuyouStatus = chuyouStatus + refundedStr;
                    } else if (jszxOrderExcel.getStatus() == JszxOrderStatus.PAYED && jszxOrderExcel.getIsConfirm() == -2) {
                        chuyouStatus = chuyouStatus + playedStr;
                        chuyouStatus = chuyouStatus + unUsedStr;
                        chuyouStatus = chuyouStatus + refundingStr;
                        chuyouStatus = chuyouStatus + refundedStr;
                    } else if (jszxOrderExcel.getStatus() == JszxOrderStatus.WAITING && jszxOrderExcel.getIsConfirm() == 0) {
                        chuyouStatus = chuyouStatus + "待确认x" + unUsedCount + "，";
                        chuyouStatus = chuyouStatus + unUsedStr;
                        chuyouStatus = chuyouStatus + "已取消x" + refundCount + "，";
                    } else {
                        chuyouStatus = "订单无效，";
                    }

                    if (jszxOrderExcel.getStatus() == JszxOrderStatus.WAITING) {
                        chuyouStatus = "订单已过期，";
                    }
                    if (jszxOrderExcel.getStatus() == JszxOrderStatus.CANCELED) {
                        chuyouStatus = "订单无效，";
                    }

                    if (chuyouStatus.length() > 0 ) {
                        chuyouStatus = chuyouStatus.substring(0, chuyouStatus.length() - 1);
                    }
                } else {
                    String refundedStr = "";
                    String refundingStr = "";
                    String unUsedStr = "";
                    if (unUsedCount != 0) {
                        unUsedStr = "未出游x" + unUsedCount + "，";
                    }
                    if (refundCount != 0) {
                        refundedStr = "已退款x" + refundCount + "，";
                    }
                    if (refundingCount != 0) {
                        refundingStr = "退款中x" + refundingCount + "，";
                    }
                    if (jszxOrderExcel.getStatus() == JszxOrderStatus.PAYED
                            && jszxOrderExcel.getIsConfirm() == 1) {
                        chuyouStatus = chuyouStatus + unUsedStr;
                        chuyouStatus = chuyouStatus + refundingStr;
                        chuyouStatus = chuyouStatus + refundedStr;
                    } else if (jszxOrderExcel.getStatus() == JszxOrderStatus.PAYED && jszxOrderExcel.getIsConfirm() == -2) {
                        chuyouStatus = chuyouStatus + unUsedStr;
                        chuyouStatus = chuyouStatus + refundingStr;
                        chuyouStatus = chuyouStatus + refundedStr;

                    } else if (jszxOrderExcel.getStatus() == JszxOrderStatus.WAITING && jszxOrderExcel.getIsConfirm() == 0) {
                        chuyouStatus = chuyouStatus + "待确认x" + unUsedCount + "，";
                        chuyouStatus = chuyouStatus + "已取消x" + refundCount + "，";
                    } else {
                        chuyouStatus = "订单无效，";
                    }

                    if (jszxOrderExcel.getStatus() == JszxOrderStatus.CANCELED) {
                        chuyouStatus = "订单无效，";
                    }

                    if (chuyouStatus.length() > 0 ) {
                        chuyouStatus = chuyouStatus.substring(0, chuyouStatus.length() - 1);
                    }
                }

                jszxOrderDetail.setDetailStatus(chuyouStatus);

                jszxOrderDetails.add(jszxOrderDetail);
            }
            jszxOrderExcel.setJszxOrderDetailList(jszxOrderDetails);
            jszxOrders.add(jszxOrderExcel);
        }
        return jszxOrders;

    }


    /**
     * 取出订单下的所有门票票种
     * @param jszxOrderList
     * @return
     */
    public List<JszxOrder> getTicketJszxOrderTypetList(JszxOrder jszxOrder, List<JszxOrder> jszxOrderList) {
        List<JszxOrder> jszxOrders = new ArrayList<JszxOrder>();
        //门票订单列表
        for (JszxOrder order : jszxOrderList) {
            List<JszxOrderDetail> jszxOrderDetailList = jszxOrderDetailService.getOrderDetailList(order, jszxOrder.getDetailUseStatus(), jszxOrder.getJszxOrderDetailName());
            for (JszxOrderDetail jszxOrderDetail : jszxOrderDetailList) {
                String useStatusStr = "";   //使用状态
                ProductValidateCode productValidateCode = new ProductValidateCode();
                productValidateCode.setTicketNo(jszxOrderDetail.getTicketNo());
                productValidateCode.setOrderId(order.getId());
                productValidateCode = productValidateCodeService.getValidateByProductCode(productValidateCode);
                Integer totalCount = 0;
                if (productValidateCode != null && productValidateCode.getOrderInitCount() != null) {
                    totalCount = productValidateCode.getOrderInitCount();  //总数量
                }
                Integer orderCount = 0;
                if (productValidateCode != null && productValidateCode.getOrderCount() != null) {
                    orderCount = productValidateCode.getOrderCount();    //可验票数量
                }
                Integer refundCount = 0;
                if (productValidateCode != null && productValidateCode.getRefundCount() != null) {
                    refundCount = productValidateCode.getRefundCount();     //退款数量
                }
                String refundCountStr = "";
                if (refundCount != null && refundCount != 0) {
                    refundCountStr = "退款x" + refundCount + "，";
                }
                String orderCountStr = "";
                if (orderCount != null && orderCount != 0) {
                    orderCountStr = "未验票x" + orderCount + "，";
                }
                String hasedCheckStr = "";
                int hasedCount = totalCount - orderCount - refundCount;     //已验票数量
                if (hasedCount != 0) {
                    hasedCheckStr = "已验票x" + hasedCount + "，";
                }
                if (jszxOrderDetail.getUseStatus() == JszxOrderDetailStatus.UNUSED
                        || jszxOrderDetail.getUseStatus() == JszxOrderDetailStatus.USED) {
                    useStatusStr = useStatusStr + hasedCheckStr;
                    useStatusStr = useStatusStr + orderCountStr;
                    useStatusStr = useStatusStr + refundCountStr;
                } else if (jszxOrderDetail.getUseStatus() == JszxOrderDetailStatus.CANCEL) {
                    useStatusStr = "已取消，";
                }

                if (order.getStatus() == JszxOrderStatus.UNPAY) {
                    useStatusStr = "待付款，";
                }
                if (order.getStatus() == JszxOrderStatus.WAITING) {
                    useStatusStr = "待确认，";
                }

                if (useStatusStr.length() > 0 ) {
                    useStatusStr = useStatusStr.substring(0, useStatusStr.length() - 1);
                }
                jszxOrderDetail.setDetailStatus(useStatusStr);
            }
            order.setJszxOrderDetailList(jszxOrderDetailList);
            jszxOrders.add(order);
        }

        return jszxOrders;
    }

    public List<JszxOrderDetail> getOrderDetailList(List<JszxOrderDetail> jszxOrderDetailList) {

        List<JszxOrderDetail> jszxDetailList = new ArrayList<JszxOrderDetail>();

        for (JszxOrderDetail jszxOrderDetail : jszxOrderDetailList) {




        }

        return jszxDetailList;
    }


    /**
     * 门票订单短信发送接口
     * @param jszxOrder
     * @param siteId
     */
    public void doSendMsg(JszxOrder jszxOrder, Long siteId) {
        List<JszxOrderDetail> jszxOrderDetailList = jszxOrderDetailService.getOutTicketList(jszxOrder);
        //每个门票类型都发送一次
        for (JszxOrderDetail orderDetail : jszxOrderDetailList) {
            if (orderDetail.getUseStatus() == JszxOrderDetailStatus.UNUSED) {
                ProductValidateCode productValidateCode = productValidateCodeService.getPvCode(orderDetail.getTicketNo());
                if (productValidateCode == null) {
                    productValidateCode = new ProductValidateCode();
                    String ticketName = jszxOrder.getProduct().getName() + "+" + orderDetail.getTicketName();
                    productValidateCode.setTicketName(ticketName);
                    productValidateCode.setOrderNo(jszxOrder.getOrderNo());
                    Ticket ticket = ticketService.loadTicket(jszxOrder.getProduct().getId());
                    if (ticket != null) {
                        productValidateCode.setScenicId(ticket.getScenicInfo().getId());
                    }
                    productValidateCode.setTicketNo(orderDetail.getTicketNo());
                    productValidateCode.setTicketId(orderDetail.getId());
                    productValidateCode.setProduct(jszxOrder.getProduct());
                    productValidateCode.setBuyerName(jszxOrder.getContact());
                    productValidateCode.setBuyerMobile(jszxOrder.getPhone());
                    productValidateCode.setOrderInitCount(orderDetail.getCount());
                    productValidateCode.setOrderCount(orderDetail.getCount());
                    productValidateCode.setRefundCount(0);
                    productValidateCode.setOrderId(jszxOrder.getId());
                }

                Ticket ticket = ticketService.loadTicket(jszxOrder.getProduct().getId());
                TicketPrice ticketPrice = ticketPriceService.getPrice(orderDetail.getTypePriceId());
                TicketExplain explain = explainService.findExplainByTicketId(ticket.getId());

                String content = "您已成功购买：" + ticket.getName() + "+" + orderDetail.getTicketName()
                        + "，数量：" + orderDetail.getCount()
                        + "张，";
                String tips = "";

                if (orderDetail.getStartTime() != null) {
                    tips += "，有效期：";
                    tips += DateUtils.format(orderDetail.getStartTime(), "yyyy-MM-dd");
                    tips += "至";
                    tips += DateUtils.format(orderDetail.getEndTime(), "yyyy-MM-dd");
                }

                tips += "，请及时到景区验票入园。";
                if (ticketPrice != null && StringUtils.isNotBlank(ticketPrice.getOrderKnow())) {
                    String result = ticketPrice.getOrderKnow();
                    result = HTMLFilterUtils.doHTMLFilter(result);
                    result = result.replaceAll("&nbsp;", " ");
                    result = result.replaceAll("\\r|\\n|\\t", "");
                    result = org.apache.commons.lang3.StringUtils.substring(result, 0, 500);
                    tips += result;
                }
                msgService.addSendMsgCode(productValidateCode, content, tips);
            }
        }

    }

    /**
     * 门票订单短信发送(单发)
     * @param orderDetail
     * @param siteId
     */
    public void doSendMsg(JszxOrderDetail orderDetail, Long siteId) {
        JszxOrder jszxOrder = orderDetail.getJszxOrder();
        //每个门票类型都发送一次
        if (orderDetail.getUseStatus() == JszxOrderDetailStatus.UNUSED) {
            ProductValidateCode productValidateCode = productValidateCodeService.getPvCode(orderDetail.getTicketNo());
            if (productValidateCode == null) {
                productValidateCode = new ProductValidateCode();
                String ticketName = jszxOrder.getProduct().getName() + "+" + orderDetail.getTicketName();
                productValidateCode.setTicketName(ticketName);
                productValidateCode.setOrderNo(jszxOrder.getOrderNo());
                Ticket ticket = ticketService.loadTicket(jszxOrder.getProduct().getId());
                if (ticket != null) {
                    productValidateCode.setScenicId(ticket.getScenicInfo().getId());
                }
                productValidateCode.setTicketNo(orderDetail.getTicketNo());
                productValidateCode.setTicketId(orderDetail.getId());
                productValidateCode.setProduct(jszxOrder.getProduct());
                productValidateCode.setBuyerName(jszxOrder.getContact());
                productValidateCode.setBuyerMobile(jszxOrder.getPhone());
                productValidateCode.setOrderInitCount(orderDetail.getCount());
                productValidateCode.setOrderCount(orderDetail.getCount());
                productValidateCode.setRefundCount(0);
                productValidateCode.setOrderId(jszxOrder.getId());
            }

            Ticket ticket = ticketService.loadTicket(jszxOrder.getProduct().getId());
            TicketPrice ticketPrice = ticketPriceService.getPrice(orderDetail.getTypePriceId());
            TicketExplain explain = explainService.findExplainByTicketId(ticket.getId());

            String content = "您已成功购买：" + ticket.getName() + "+" + orderDetail.getTicketName()
                    + "，数量：" + orderDetail.getCount()
                    + "张，";
            String tips = "";

            if (orderDetail.getStartTime() != null) {
                tips += "，有效期：";
                tips += DateUtils.format(orderDetail.getStartTime(), "yyyy-MM-dd");
                tips += "至";
                tips += DateUtils.format(orderDetail.getEndTime(), "yyyy-MM-dd");
            }

            tips += "，请及时到景区验票入园。";
            if (ticketPrice != null && StringUtils.isNotBlank(ticketPrice.getOrderKnow())) {
                String result = ticketPrice.getOrderKnow();
                result = HTMLFilterUtils.doHTMLFilter(result);
                result = result.replaceAll("&nbsp;", " ");
                result = result.replaceAll("\\r|\\n|\\t", "");
                result = org.apache.commons.lang3.StringUtils.substring(result, 0, 500);
                tips += result;
            }
            msgService.addSendMsgCode(productValidateCode, content, tips);
        }


    }



    /**
     * 获取客户订单，取出供应商是自己的订单
     * @param jszxOrder
     * @param loginUser
     *@param companyUnit @return
     */
    public List<JszxOrder> getClientOrderList(JszxOrder jszxOrder, SysUser loginUser,
                                              SysUnit companyUnit, boolean isSiteAdmin,
                                              boolean isSupperAdmin, Page pageInfo) {
//        Criteria<JszxOrder> criteria = new Criteria<JszxOrder>(JszxOrder.class);

        //主查询对象
        //子查询对象
        DetachedCriteria detail = DetachedCriteria.forClass(JszxOrderDetail.class, "detail");

        DetachedCriteria order = DetachedCriteria.forClass(JszxOrder.class, "order");
        order.createCriteria("order.user", "u", JoinType.INNER_JOIN);
        foramtDetachedCriteria(jszxOrder, order, "order");

        if (jszxOrder.getDetailUseStatus() != null) {
            //子查询条件
            detail.add(Restrictions.eq("detail.useStatus", jszxOrder.getDetailUseStatus()));
        }

        if (jszxOrder.getStartUseTime() != null) {
            //子查询条件
            detail.add(Restrictions.ge("detail.startTime", jszxOrder.getStartUseTime()));
        }

        if (jszxOrder.getEndUseTime() != null) {
            //子查询条件
            detail.add(Restrictions.le("detail.startTime", jszxOrder.getEndUseTime()));
        }

        // 酒店订单专用(退房时间范围条件)
        if (jszxOrder.getStartCheckoutTime() != null) {
            //子查询条件
            detail.add(Restrictions.ge("detail.endTime", jszxOrder.getEndUseTime()));
        }
        if (jszxOrder.getEndCheckoutTime() != null) {
            //子查询条件
            detail.add(Restrictions.le("detail.endTime", jszxOrder.getEndUseTime()));
        }


        if (jszxOrder.getJszxOrderDetailName() != null) {
            //子查询条件
            detail.add(Restrictions.like("detail.endTime", jszxOrder.getJszxOrderDetailName(), MatchMode.ANYWHERE));
        }

        //加子查询与主查询的主外键关联关系，若有多个条件则适当增加
        detail.add(Property.forName("order.id").eqProperty("detail.jszxOrder.id"));

        order.add(Subqueries.exists(detail.setProjection(Projections.property("detail.id"))));

        // 数据过滤
        if (isSiteAdmin) {
            SysSite sysSite = sysSiteDao.load(loginUser.getSysSite().getId());
            List<SysUnit> units = sysUnitDao.findUnitBySite(sysSite);
            String ids = "";
            for (SysUnit s : units) {
                ids = ids + s.getId().toString() + ",";
            }
            order.add(Restrictions.in("order.supplierUnit", units));
        } else if (!isSupperAdmin && !isSiteAdmin) {
            if (companyUnit != null) {
                order.add(Restrictions.eq("order.supplierUnit", companyUnit));
            }
        }




        order.addOrder(Order.desc("updateTime"));

        return dao.findByCriteria(order, pageInfo);

    }


    /**
     * 通过订单编号和产品，把已经支付的订单去除
     * @param orderNo
     * @param product
     * @return
     */
    public JszxOrder findOutOrderByOrderNoAndProduct(String orderNo, Product product) {
        Criteria<JszxOrder> criteria = new Criteria<JszxOrder>(JszxOrder.class);

        if (StringUtils.isNotBlank(orderNo)) {
            criteria.eq("orderNo", orderNo);
        }

        if (product != null) {
            criteria.eq("product", product);
        }


        criteria.eq("status", JszxOrderStatus.PAYED);
        return dao.findUniqueByCriteria(criteria);
    }

    /**
     * 通过订单编号获取已经支付的订单
     * @param orderNo
     * @return
     */
    public List<JszxOrder> findOutOrderByOrderNo(String orderNo, JszxOrderStatus status) {
        Criteria<JszxOrder> criteria = new Criteria<JszxOrder>(JszxOrder.class);

        if (StringUtils.isNotBlank(orderNo)) {
            criteria.eq("orderNo", orderNo);
        }

        if (status != null) {
            criteria.eq("status", status);
        }

        return dao.findByCriteria(criteria);
    }


    /**
     * 保存订单
     * @param jszxOrder
     * @param user
     * @param companyUnit
     */
    public void save(JszxOrder jszxOrder, SysUser user, SysUnit companyUnit) {
        if (!StringUtils.isNotBlank(jszxOrder.getOrderNo())) {
            jszxOrder.setOrderNo(NumberUtil.getRunningNo());
        }
        jszxOrder.setUser(user);
        jszxOrder.setCompanyUnit(companyUnit);
        jszxOrder.setCreateTime(new Date());
        jszxOrder.setUpdateTime(new Date());
        dao.save(jszxOrder);
    }


    /**
     * 更新订单
     * @param jszxOrder
     * @param user
     * @param companyUnit
     */
    public void update(JszxOrder jszxOrder, SysUser user, SysUnit companyUnit) {
        jszxOrder.setUser(user);
        jszxOrder.setCompanyUnit(companyUnit);
        jszxOrder.setUpdateTime(new Date());
        dao.update(jszxOrder);
    }


    public JszxOrder load(Long id) {
        return dao.load(id);
    }

    public List<JszxOrder> getOutOrderList(JszxOrder jszxOrder, Page pageInfo, SysUnit companyUnit, SysUser sysUser, boolean isSiteAdmin, boolean isSupperAdmin) {

        //主查询对象
        //子查询对象
        DetachedCriteria detail = DetachedCriteria.forClass(JszxOrderDetail.class, "detail");

        DetachedCriteria order = DetachedCriteria.forClass(JszxOrder.class, "order");

        order.createCriteria("order.user", "u", JoinType.INNER_JOIN);

        foramtDetachedCriteria(jszxOrder, order, "order");

        if (jszxOrder.getDetailUseStatus() != null) {
            //子查询条件
            detail.add(Restrictions.eq("detail.useStatus", jszxOrder.getDetailUseStatus()));
        }

        if (jszxOrder.getStartUseTime() != null) {
            //子查询条件
            detail.add(Restrictions.ge("detail.startTime", jszxOrder.getStartUseTime()));
        }

        if (jszxOrder.getEndUseTime() != null) {
            //子查询条件
            detail.add(Restrictions.le("detail.startTime", jszxOrder.getEndUseTime()));
        }

        if (jszxOrder.getJszxOrderDetailName() != null) {
            //子查询条件
            detail.add(Restrictions.like("detail.ticketName", jszxOrder.getJszxOrderDetailName(), MatchMode.ANYWHERE));
        }

//        detail.add(Restrictions.eq("order.id", "detail.jszxOrder.id"));
        //加子查询与主查询的主外键关联关系，若有多个条件则适当增加
        detail.add(Property.forName("order.id").eqProperty("detail.jszxOrder.id"));

        order.add(Subqueries.exists(detail.setProjection(Projections.property("detail.id"))));

        // 数据过滤
        if (isSiteAdmin) {
            SysSite sysSite = sysSiteDao.load(sysUser.getSysSite().getId());
            List<SysUnit> units = sysUnitDao.findUnitBySite(sysSite);
            String ids = "";
            for (SysUnit s : units) {
                ids = ids + s.getId().toString() + ",";
            }

            order.add(Restrictions.in("order.companyUnit", units));

        } else if (!isSupperAdmin && !isSiteAdmin) {

            if (sysUser.getSysUnit().getUnitType() != UnitType.department) {
                order.add(Restrictions.eq("order.companyUnit", sysUser.getSysUnit().getCompanyUnit()));
            } else {
                order.add(Restrictions.eq("order.user", sysUser));
            }

        }


        order.addOrder(Order.desc("updateTime"));

        List<JszxOrder> jszxOrders = dao.findByCriteria(order, pageInfo);
        return jszxOrders;

    }

    public List<JszxOrder> getOutOrderListTemp(JszxOrder jszxOrder, Page pageInfo, SysUnit companyUnit, SysUser sysUser, boolean isSiteAdmin, boolean isSupperAdmin) {

        Criteria<JszxOrder> criteria = new Criteria<JszxOrder>(JszxOrder.class);
        foramtCond(jszxOrder, criteria);

        // 数据过滤
        if (isSiteAdmin) {
            SysSite sysSite = sysSiteDao.load(sysUser.getSysSite().getId());
            List<SysUnit> units = sysUnitDao.findUnitBySite(sysSite);
            String ids = "";
            for (SysUnit s : units) {
                ids = ids + s.getId().toString() + ",";
            }

            criteria.in("companyUnit", units);

        } else if (!isSupperAdmin && !isSiteAdmin) {

            if (sysUser.getSysUnit().getUnitType() != UnitType.department) {
                criteria.eq("companyUnit", sysUser.getSysUnit().getCompanyUnit());
            } else {
                criteria.eq("user", sysUser);
            }

//            criteria.ne("supplierUnit", companyUnit);
        }

//        criteria.ne("supplierUnit", companyUnit);
        criteria.orderBy("updateTime", "desc");

        return dao.findByCriteria(criteria, pageInfo);

    }

    public List<JszxOrder> getJxzxOrderListToExcel(JszxOrder jszxOrder, SysUnit companyUnit, SysUser sysUser, boolean isSiteAdmin, boolean isSupperAdmin, boolean isSuplier) {

        //主查询对象
        //子查询对象
        DetachedCriteria detail = DetachedCriteria.forClass(JszxOrderDetail.class, "detail");

        DetachedCriteria order = DetachedCriteria.forClass(JszxOrder.class, "order");

        order.createCriteria("order.user", "u", JoinType.INNER_JOIN);

        foramtDetachedCriteria(jszxOrder, order, "order");

        if (jszxOrder.getDetailUseStatus() != null) {
            //子查询条件
            detail.add(Restrictions.eq("detail.useStatus", jszxOrder.getDetailUseStatus()));
        }

        if (jszxOrder.getStartUseTime() != null) {
            //子查询条件
            detail.add(Restrictions.ge("detail.startTime", jszxOrder.getStartUseTime()));
        }

        if (jszxOrder.getEndUseTime() != null) {
            //子查询条件
            detail.add(Restrictions.le("detail.startTime", jszxOrder.getEndUseTime()));
        }

        if (jszxOrder.getJszxOrderDetailName() != null) {
            //子查询条件
            detail.add(Restrictions.like("detail.ticketName", jszxOrder.getJszxOrderDetailName(), MatchMode.ANYWHERE));
        }

//        detail.add(Restrictions.eq("order.id", "detail.jszxOrder.id"));
        //加子查询与主查询的主外键关联关系，若有多个条件则适当增加
        detail.add(Property.forName("order.id").eqProperty("detail.jszxOrder.id"));

        order.add(Subqueries.exists(detail.setProjection(Projections.property("detail.id"))));

        // 数据过滤
        if (isSiteAdmin) {
            SysSite sysSite = sysSiteDao.load(sysUser.getSysSite().getId());
            List<SysUnit> units = sysUnitDao.findUnitBySite(sysSite);
            String ids = "";
            for (SysUnit s : units) {
                ids = ids + s.getId().toString() + ",";
            }
            order.add(Restrictions.in("order.companyUnit", units));
        } else if (!isSupperAdmin && !isSiteAdmin) {
            if (isSuplier) {
                order.add(Restrictions.eq("order.supplierUnit", sysUser.getSysUnit().getCompanyUnit()));
            } else {
                order.add(Restrictions.eq("order.companyUnit", sysUser.getSysUnit().getCompanyUnit()));
            }

        }

        order.addOrder(Order.desc("updateTime"));

        List<JszxOrder> jszxOrders = dao.findByCriteria(order);
        return jszxOrders;

    }

    public List<JszxOrder> getJxzxOrderListToExcelTemp(JszxOrder jszxOrder, SysUnit companyUnit, SysUser sysUser, boolean isSiteAdmin, boolean isSupperAdmin) {

        Criteria<JszxOrder> criteria = new Criteria<JszxOrder>(JszxOrder.class);
        foramtCond(jszxOrder, criteria);

        // 数据过滤
        if (isSiteAdmin) {
            SysSite sysSite = sysSiteDao.load(sysUser.getSysSite().getId());
            List<SysUnit> units = sysUnitDao.findUnitBySite(sysSite);
            String ids = "";
            for (SysUnit s : units) {
                ids = ids + s.getId().toString() + ",";
            }

            criteria.in("companyUnit", units);

        } else if (!isSupperAdmin && !isSiteAdmin) {
            criteria.eq("companyUnit", sysUser.getSysUnit().getCompanyUnit());
        }

        criteria.orderBy("updateTime", "desc");
        return dao.findByCriteria(criteria);

    }


    public void deleteOutOrder(JszxOrder jszxOrder) {
        dao.delete(jszxOrder);
    }


    public void foramtCond(JszxOrder jszxOrder, Criteria<JszxOrder> criteria) {

        DetachedCriteria dc = criteria.createCriteria("product", "p", JoinType.INNER_JOIN);

        if (jszxOrder.getProName() != null) {
//            dc.add(Restrictions.like("p.name", jszxOrder.getProduct().getName(), MatchMode.ANYWHERE));
            criteria.like("proName", jszxOrder.getProName(), MatchMode.ANYWHERE);
        }

//        if (jszxOrder.getProduct().getProType() != null) {
//            dc.add(Restrictions.eq("p.proType", jszxOrder.getProduct().getProType()));
//        }

        if (jszxOrder.getStatus() != null) {
            criteria.eq("status", jszxOrder.getStatus());
        }

        if (jszxOrder.getProType() != null) {
            criteria.eq("proType", jszxOrder.getProType());
        }

        if (jszxOrder.getContact() != null) {
            criteria.like("contact", jszxOrder.getContact(), MatchMode.ANYWHERE);
        }

        if (jszxOrder.getPhone() != null) {
            criteria.like("phone", jszxOrder.getPhone(), MatchMode.ANYWHERE);
        }

        if (jszxOrder.getStatus() != null) {
            criteria.eq("status", jszxOrder.getStatus());
        }

    }
    public void foramtDetachedCriteria(JszxOrder jszxOrder, DetachedCriteria detachedCriteria, String alias) {


        if (StringUtils.isNotBlank(jszxOrder.getProName())) {
            detachedCriteria.add(Restrictions.like(alias + ".proName", jszxOrder.getProName(), MatchMode.ANYWHERE));
        }

        if (StringUtils.isNotBlank(jszxOrder.getOrderNo())) {
            detachedCriteria.add(Restrictions.like(alias + ".orderNo", jszxOrder.getOrderNo(), MatchMode.ANYWHERE));
        }
        if (jszxOrder.getUser() != null && StringUtils.isNotBlank(jszxOrder.getUser().getAccount())) {
            detachedCriteria.add(Restrictions.like("u.account", jszxOrder.getUser().getAccount(), MatchMode.ANYWHERE));
        }

        if (jszxOrder.getStatus() != null) {
            detachedCriteria.add(Restrictions.eq(alias + ".status", jszxOrder.getStatus()));
        }

        if (jszxOrder.getProType() != null) {
            detachedCriteria.add(Restrictions.eq(alias + ".proType", jszxOrder.getProType()));
        }

        if (StringUtils.isNotBlank(jszxOrder.getContact())) {
            detachedCriteria.add(Restrictions.like(alias + ".contact", jszxOrder.getContact(), MatchMode.ANYWHERE));
        }

        if (StringUtils.isNotBlank(jszxOrder.getPhone())) {
            detachedCriteria.add(Restrictions.like(alias + ".phone", jszxOrder.getPhone(), MatchMode.ANYWHERE));
        }

        if (jszxOrder.getStartCreateTime() != null) {
            detachedCriteria.add(Restrictions.ge(alias + ".createTime", jszxOrder.getStartCreateTime()));
        }

        if (jszxOrder.getEndCreateTime() != null) {
            detachedCriteria.add(Restrictions.le(alias + ".createTime", jszxOrder.getEndCreateTime()));
        }

        if (jszxOrder.getStatus() != null) {
            detachedCriteria.add(Restrictions.eq(alias + ".status", jszxOrder.getStatus()));
        }

    }

    public List<JszxOrder> getJszxOrderListByNoCanceled(SourceType jszx, JszxOrderStatus status) {
//        Page page = new Page(1, 100);
        Criteria<JszxOrder> criteria = new Criteria<JszxOrder>(JszxOrder.class);
        criteria.eq("sourceType", jszx);
        criteria.ne("status", status);
        return dao.findByCriteria(criteria);
    }


    public List<JszxOrder> findSalesProductList(Long productId, JszxOrder jszxOrder, SysUnit companyUnit, SysUser sysUser, Boolean isSiteAdmin, Boolean isSupperAdmin) {

        Criteria<JszxOrder> criteria = new Criteria<JszxOrder>(JszxOrder.class);

        criteria.createCriteria("product", "p", JoinType.LEFT_OUTER_JOIN);
        criteria.createCriteria("p.companyUnit", "pUnit", JoinType.LEFT_OUTER_JOIN);
        criteria.createCriteria("pUnit.sysSite", "pSite", JoinType.LEFT_OUTER_JOIN);
        criteria.createCriteria("companyUnit", "jUnit", JoinType.LEFT_OUTER_JOIN);

        // 数据过滤
        if (isSiteAdmin) {
            SysSite sysSite = sysSiteDao.load(sysUser.getSysSite().getId());
            criteria.eq("pSite.id", sysSite.getId());
        } else if (!isSupperAdmin && !isSiteAdmin) {
            criteria.eq("pUnit.id", sysUser.getSysUnit().getCompanyUnit().getId());
        }

        if (jszxOrder.getCompanyName() != null) {
            criteria.like("jUnit.name", jszxOrder.getCompanyName(), MatchMode.ANYWHERE);
        }

        if (jszxOrder.getProName() != null) {
            criteria.like("p.name", jszxOrder.getProName(), MatchMode.ANYWHERE);
        }


        if (jszxOrder.getProType() != null) {
            criteria.eq("proType", jszxOrder.getProType());
        }
        if (jszxOrder.getStartCreateTime() != null) {
            criteria.ge("createTime", jszxOrder.getStartCreateTime());
        }

        if (jszxOrder.getEndCreateTime() != null) {
            criteria.le("createTime", jszxOrder.getEndCreateTime());
        }

        criteria.eq("p.id", productId);

        return dao.findByCriteria(criteria);
    }

    public List<Long> findSalesProductIdList(JszxOrder jszxOrder, SysUnit companyUnit, SysUser sysUser, Boolean isSiteAdmin, Boolean isSupperAdmin, Page page) {

        StringBuffer sb = new StringBuffer();

        List paramList = new ArrayList();

        List<Long> productIdList = new ArrayList<Long>();
        sb.append("select jord.product.id from JszxOrder jord, Product p");
        sb.append(" WHERE");
        sb.append(" p.id = jord.product.id");

        // 数据过滤
        if (isSiteAdmin) {
            SysSite sysSite = sysSiteDao.load(sysUser.getSysSite().getId());
            List<SysUnit> units = sysUnitDao.findUnitBySite(sysSite);
            if (!units.isEmpty()) {
                sb.append(" and p.companyUnit.sysSite.id =?");
                paramList.add(sysSite.getId());
            }
        } else if (!isSupperAdmin && !isSiteAdmin) {
            sb.append(" and p.companyUnit.id = ?");
            paramList.add(sysUser.getSysUnit().getCompanyUnit().getId());
        }
        if (jszxOrder.getProType() != null) {
            sb.append(" and jord.proType = ?");
            paramList.add(jszxOrder.getProType());
        }
        if (jszxOrder.getStartCreateTime() != null) {
            sb.append(" and jord.createTime >= ?");
            paramList.add(jszxOrder.getStartCreateTime());
        }

        if (jszxOrder.getEndCreateTime() != null) {
            sb.append(" and jord.createTime <= ?");
            paramList.add(jszxOrder.getEndCreateTime());
        }

        if (jszxOrder.getCompanyName() != null) {
            sb.append(" and jord.companyUnit.name like '%'||?||'%'");
            paramList.add(jszxOrder.getCompanyName());
        }

        if (jszxOrder.getProName() != null) {
            sb.append(" and p.name like '%'||?||'%'");
            paramList.add(jszxOrder.getProName());
        }

        sb.append(" GROUP BY");
        sb.append(" jord.proName");
        sb.append(" HAVING count(jord.id) > 0");
        sb.append(" order by count(jord.id) DESC");
        productIdList = dao.findByHQL(sb.toString(), page, paramList.toArray());
        return productIdList;
    }

    public List<SalesEntity> findSalesEntityList(JszxOrder jszxOrder, SysUnit companyUnit, SysUser sysUser, Boolean isSiteAdmin, Boolean isSupperAdmin, Page page) {
        List<SalesEntity> salesEntityList = new ArrayList<SalesEntity>();
        List<Long> proIdList = new ArrayList<Long>();
        proIdList = findSalesProductIdList(jszxOrder, companyUnit, sysUser, isSiteAdmin, isSupperAdmin, page);
        if (proIdList.isEmpty()) {
            return salesEntityList;
        }
        for (Long productId : proIdList) {
            List<JszxOrder> jszxOrders = new ArrayList<JszxOrder>();
            jszxOrders = findSalesProductList(productId, jszxOrder, companyUnit, sysUser, isSiteAdmin, isSupperAdmin);

            if (!jszxOrders.isEmpty()) {
                SalesEntity salesEntity = new SalesEntity();
                String pName = jszxOrders.get(0).getProName();
                Integer count = jszxOrders.size();
                Float actualPrice = 0f;
                for (JszxOrder order : jszxOrders) {
                    if (order.getActualPayPrice() == null) {
                        order.setActualPayPrice(0f);
                    }
                    actualPrice = actualPrice + order.getActualPayPrice();
                }
                salesEntity.setName(pName);
                salesEntity.setCount(count);
                salesEntity.setTotalPrice(actualPrice);
                salesEntity.setProductId(productId);

                salesEntityList.add(salesEntity);
            }
        }
        return salesEntityList;
    }

    public List<Long> findPurchaseProductIdList(JszxOrder jszxOrder, SysUnit companyUnit, SysUser sysUser, Boolean isSiteAdmin, Boolean isSupperAdmin, Page page) {

        StringBuffer sb = new StringBuffer();

        List paramList = new ArrayList();

        List<Long> productIdList = new ArrayList<Long>();
        sb.append("select jord.product.id from JszxOrder jord, Product p");
        sb.append(" WHERE");
        sb.append(" p.id = jord.product.id");

        // 数据过滤
        if (isSiteAdmin) {
            SysSite sysSite = sysSiteDao.load(sysUser.getSysSite().getId());
            List<SysUnit> units = sysUnitDao.findUnitBySite(sysSite);
            if (!units.isEmpty()) {
                sb.append(" and jord.companyUnit.sysSite.id =?");
                paramList.add(sysSite.getId());
            }
        } else if (!isSupperAdmin && !isSiteAdmin) {
            sb.append(" and jord.companyUnit.id = ?");
            paramList.add(sysUser.getSysUnit().getCompanyUnit().getId());
        }
        if (jszxOrder.getProType() != null) {
            sb.append(" and jord.proType = ?");
            paramList.add(jszxOrder.getProType());
        }
        if (jszxOrder.getStartCreateTime() != null) {
            sb.append(" and jord.createTime >= ?");
            paramList.add(jszxOrder.getStartCreateTime());
        }

        if (jszxOrder.getEndCreateTime() != null) {
            sb.append(" and jord.createTime <= ?");
            paramList.add(jszxOrder.getEndCreateTime());
        }

        if (jszxOrder.getCompanyName() != null) {
            sb.append(" and jord.supplierUnit.name like '%'||?||'%'");
            paramList.add(jszxOrder.getCompanyName());
        }

        if (jszxOrder.getProName() != null) {
            sb.append(" and p.name like '%'||?||'%'");
            paramList.add(jszxOrder.getProName());
        }

        sb.append(" GROUP BY");
        sb.append(" jord.proName");
        sb.append(" HAVING count(jord.id) > 0");
        sb.append(" order by count(jord.id) DESC");
        productIdList = dao.findByHQL(sb.toString(), page, paramList.toArray());
        return productIdList;
    }

    public List<PurchaseEntity> findPurchaseEntityList(JszxOrder jszxOrder, SysUnit companyUnit, SysUser sysUser, Boolean isSiteAdmin, Boolean isSupperAdmin, Page page) {
        List<PurchaseEntity> purchaseEntities = new ArrayList<PurchaseEntity>();
        List<Long> proIdList = new ArrayList<Long>();
        proIdList = findPurchaseProductIdList(jszxOrder, companyUnit, sysUser, isSiteAdmin, isSupperAdmin, page);
        if (proIdList.isEmpty()) {
            return purchaseEntities;
        }
        for (Long productId : proIdList) {
            List<JszxOrder> jszxOrders = new ArrayList<JszxOrder>();
            jszxOrders = findPurchaseProductList(productId, jszxOrder, companyUnit, sysUser, isSiteAdmin, isSupperAdmin);
            if (!jszxOrders.isEmpty()) {
                PurchaseEntity purchaseEntity = new PurchaseEntity();
                String pName = jszxOrders.get(0).getProName();
                Integer count = jszxOrders.size();
                Float actualPrice = 0f;
                for (JszxOrder order : jszxOrders) {
                    if (order.getActualPayPrice() == null) {
                        order.setActualPayPrice(0f);
                    }
                    actualPrice = actualPrice + order.getActualPayPrice();
                }
                purchaseEntity.setName(pName);
                purchaseEntity.setCount(count);
                purchaseEntity.setTotalPrice(actualPrice);
                purchaseEntity.setProductId(productId);

                purchaseEntities.add(purchaseEntity);
            }
        }
        return purchaseEntities;
    }

    public List<JszxOrder> findPurchaseProductList(Long productId, JszxOrder jszxOrder, SysUnit companyUnit, SysUser sysUser, Boolean isSiteAdmin, Boolean isSupperAdmin) {

        Criteria<JszxOrder> criteria = new Criteria<JszxOrder>(JszxOrder.class);

        criteria.createCriteria("product", "p", JoinType.LEFT_OUTER_JOIN);
        criteria.createCriteria("p.companyUnit", "pUnit", JoinType.LEFT_OUTER_JOIN);
        criteria.createCriteria("companyUnit", "jUnit", JoinType.LEFT_OUTER_JOIN);
        criteria.createCriteria("supplierUnit", "jSuplierUnit", JoinType.LEFT_OUTER_JOIN);

        criteria.createCriteria("jUnit.sysSite", "jSite", JoinType.LEFT_OUTER_JOIN);

        // 数据过滤
        if (isSiteAdmin) {
            SysSite sysSite = sysSiteDao.load(sysUser.getSysSite().getId());
            criteria.eq("jSite.id", sysSite.getId());
        } else if (!isSupperAdmin && !isSiteAdmin) {
            criteria.eq("jUnit.id", sysUser.getSysUnit().getCompanyUnit().getId());
        }

        if (jszxOrder.getCompanyName() != null) {
            criteria.like("jSuplierUnit.name", jszxOrder.getCompanyName(), MatchMode.ANYWHERE);
        }

        if (jszxOrder.getProName() != null) {
            criteria.like("p.name", jszxOrder.getProName(), MatchMode.ANYWHERE);
        }


        if (jszxOrder.getProType() != null) {
            criteria.eq("proType", jszxOrder.getProType());
        }
        if (jszxOrder.getStartCreateTime() != null) {
            criteria.ge("createTime", jszxOrder.getStartCreateTime());
        }

        if (jszxOrder.getEndCreateTime() != null) {
            criteria.le("createTime", jszxOrder.getEndCreateTime());
        }

        criteria.eq("p.id", productId);

        return dao.findByCriteria(criteria);
    }

    public Map<String, Object> doLoadSaleExcel(JszxOrder jszxOrder, SysUnit companyUnit, SysUser sysUser, Boolean isSiteAdmin, Boolean isSupperAdmin, Page pageInfo) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<SalesEntity> salesEntities =  findSalesEntityList(jszxOrder, companyUnit, sysUser, isSiteAdmin, isSupperAdmin, pageInfo);
        String fileName = null;
        if (salesEntities.isEmpty()) {
            map.put("success", false);
            map.put("info", "数据为空，无法导出数据！");
            return map;
        }
        Workbook wb = new HSSFWorkbook();

        CellStyle cellStyle = wb.createCellStyle();
        Font font = wb.createFont();
        Sheet sheet = null;

        if (jszxOrder.getProType() == ProductType.line) {
            sheet = wb.createSheet("线路销售统计");
            fileName = "line";
        } else {
            sheet = wb.createSheet("门票销售统计");
            fileName = "ticket";
        }
        Row row = sheet.createRow(0);
        //表头
        createFirstCell(wb, row, cellStyle, font, (short) 0, "序号");
        createFirstCell(wb, row, cellStyle, font, (short) 1, "产品名称");
        createFirstCell(wb, row, cellStyle, font, (short) 2, "数量");
        createFirstCell(wb, row, cellStyle, font, (short) 3, "总额");
        for (int i = 1; i < (salesEntities.size() + 1); i++) {
            SalesEntity salesEntity = salesEntities.get(i - 1);
            Row rowFor = sheet.createRow(i);
            createTableCell(wb, rowFor, cellStyle, font, (short) 0, i + "");
            createTableCell(wb, rowFor, cellStyle, font, (short) 1, salesEntity.getName());
            createTableCell(wb, rowFor, cellStyle, font, (short) 2, salesEntity.getCount().toString());
            createTableCell(wb, rowFor, cellStyle, font, (short) 3, salesEntity.getTotalPrice().toString());
        }
        sheet.autoSizeColumn(0); //adjust width of the first column
        sheet.autoSizeColumn(1); //adjust width of the second column
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);
        FileOutputStream fileOut = null;


        String startTime = null;
        if (jszxOrder.getStartCreateTime() != null) {
            startTime = DateUtil.formatDate(jszxOrder.getStartCreateTime(), "yyyyMMdd");
        }
        String endTime = "";
        if (jszxOrder.getEndCreateTime() != null) {
            endTime = DateUtil.formatDate(jszxOrder.getEndCreateTime(), "yyyyMMdd");
        }


        fileName = fileName + "_" + startTime + "-" + endTime + ".xls";

        String staticPath = propertiesManager.getString("IMG_DIR");
        String fn = staticPath + "/tempfile/" + fileName;
        File fileDir = new File(staticPath + "/tempfile/");
        if (!fileDir.exists()) {
            fileDir.mkdir();
        }
        try {

            fileOut = new FileOutputStream(fn);
            wb.write(fileOut);
            fileOut.close();
        } catch (IOException e) {
            System.out.println(e);
        }
        map.put("downloadUrl", "/tempfile/" + fileName);
        map.put("success", true);
        map.put("info", "导出成功！");
        return map;
    }

    public Map<String, Object> doLoadPurchaseExcel(JszxOrder jszxOrder, SysUnit companyUnit, SysUser sysUser, Boolean isSiteAdmin, Boolean isSupperAdmin, Page pageInfo) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<PurchaseEntity> purchaseEntities =  findPurchaseEntityList(jszxOrder, companyUnit, sysUser, isSiteAdmin, isSupperAdmin, pageInfo);
        String fileName = null;
        if (purchaseEntities.isEmpty()) {
            map.put("success", false);
            map.put("info", "数据为空，无法导出数据！");
            return map;
        }
        Workbook wb = new HSSFWorkbook();

        CellStyle cellStyle = wb.createCellStyle();
        Font font = wb.createFont();
        Sheet sheet = null;

        if (jszxOrder.getProType() == ProductType.line) {
            sheet = wb.createSheet("线路销售统计");
            fileName = "line";
        } else {
            sheet = wb.createSheet("门票销售统计");
            fileName = "ticket";
        }
        Row row = sheet.createRow(0);
        //表头
        createFirstCell(wb, row, cellStyle, font, (short) 0, "序号");
        createFirstCell(wb, row, cellStyle, font, (short) 1, "产品名称");
        createFirstCell(wb, row, cellStyle, font, (short) 2, "数量");
        createFirstCell(wb, row, cellStyle, font, (short) 3, "总额");
        for (int i = 1; i < (purchaseEntities.size() + 1); i++) {
            PurchaseEntity purchaseEntity = purchaseEntities.get(i - 1);
            Row rowFor = sheet.createRow(i);
            createTableCell(wb, rowFor, cellStyle, font, (short) 0, i + "");
            createTableCell(wb, rowFor, cellStyle, font, (short) 1, purchaseEntity.getName());
            createTableCell(wb, rowFor, cellStyle, font, (short) 2, purchaseEntity.getCount().toString());
            createTableCell(wb, rowFor, cellStyle, font, (short) 3, purchaseEntity.getTotalPrice().toString());
        }
        sheet.autoSizeColumn(0); //adjust width of the first column
        sheet.autoSizeColumn(1); //adjust width of the second column
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);
        FileOutputStream fileOut = null;


        String startTime = null;
        if (jszxOrder.getStartCreateTime() != null) {
            startTime = DateUtil.formatDate(jszxOrder.getStartCreateTime(), "yyyyMMdd");
        }
        String endTime = "";
        if (jszxOrder.getEndCreateTime() != null) {
            endTime = DateUtil.formatDate(jszxOrder.getEndCreateTime(), "yyyyMMdd");
        }


        fileName = fileName + "_" + startTime + "-" + endTime + ".xls";

        String staticPath = propertiesManager.getString("IMG_DIR");
        String fn = staticPath + "/tempfile/" + fileName;
        File fileDir = new File(staticPath + "/tempfile/");
        if (!fileDir.exists()) {
            fileDir.mkdir();
        }
        try {

            fileOut = new FileOutputStream(fn);
            wb.write(fileOut);
            fileOut.close();
        } catch (IOException e) {
            System.out.println(e);
        }
        map.put("downloadUrl", "/tempfile/" + fileName);
        map.put("success", true);
        map.put("info", "导出成功！");
        return map;
    }
}
