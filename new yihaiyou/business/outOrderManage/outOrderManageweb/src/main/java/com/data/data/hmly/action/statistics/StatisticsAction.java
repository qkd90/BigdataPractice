package com.data.data.hmly.action.statistics;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.outOrder.entity.DatagridEntity.PurchaseEntity;
import com.data.data.hmly.service.outOrder.entity.DatagridEntity.SalesEntity;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.outOrder.JszxOrderService;
import com.data.data.hmly.service.outOrder.entity.JszxOrder;
import com.framework.hibernate.util.Page;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.DateUtils;
import com.zuipin.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by dy on 2016/5/10.
 */
public class StatisticsAction extends FrameBaseAction {

    @Resource
    private JszxOrderService jszxOrderService;

    private String type;
    private SysUnit sysUnit;
    private Integer page = 1;
    private Integer rows = 10;
    private JszxOrder jszxOrder;


    public Result salesStatisManage() {
        return dispatch();
    }
    public Result purchaseStatisManage() {
        return dispatch();
    }


    public Result purchaseDatagrid() {
        Page pageInfo = new Page(page, rows);
        String seaStartTime = (String) getParameter("seaStartTime");
        String seaEndTime = (String) getParameter("seaEndTime");
        String seaUnitName = (String) getParameter("seaUnit");
        String seaProName = (String) getParameter("seaProName");
        List<PurchaseEntity> purchaseEntities = new ArrayList<PurchaseEntity>();
        jszxOrder = new JszxOrder();
        if (StringUtils.isNotBlank(seaStartTime)) {
            Date startTime = DateUtils.getDate(seaStartTime + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
            jszxOrder.setStartCreateTime(startTime);
        }
        if (StringUtils.isNotBlank(seaEndTime)) {
            Date endTime = DateUtils.getDate(seaEndTime + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
            endTime = DateUtils.getEndDay(endTime, 0);
            jszxOrder.setEndCreateTime(endTime);
        }
        if (StringUtils.isNotBlank(seaUnitName)) {
            jszxOrder.setCompanyName(seaUnitName);
        }
        if (StringUtils.isNotBlank(seaProName)) {
            jszxOrder.setProName(seaProName);
        }
        if ("scenic".equals(type)) {
            jszxOrder.setProType(ProductType.scenic);
            purchaseEntities = jszxOrderService.findPurchaseEntityList(jszxOrder, getCompanyUnit(), getLoginUser(), isSiteAdmin(), isSupperAdmin(), pageInfo);
        } else {
            jszxOrder.setProType(ProductType.line);
            purchaseEntities = jszxOrderService.findPurchaseEntityList(jszxOrder, getCompanyUnit(), getLoginUser(), isSiteAdmin(), isSupperAdmin(), pageInfo);
        }
        return datagrid(purchaseEntities, pageInfo.getTotalCount());
    }

    public Result loadPurchaseExcel() {
        Page pageInfo = new Page(page, 1000);
        String seaStartTime = (String) getParameter("seaStartTime");
        String seaEndTime = (String) getParameter("seaEndTime");
        String seaUnitName = (String) getParameter("seaUnit");
        String seaProName = (String) getParameter("seaProName");
        jszxOrder = new JszxOrder();
        if (StringUtils.isNotBlank(seaStartTime)) {
            Date startTime = DateUtils.getDate(seaStartTime + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
            jszxOrder.setStartCreateTime(startTime);
        }
        if (StringUtils.isNotBlank(seaEndTime)) {
            Date endTime = DateUtils.getDate(seaEndTime + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
            endTime = DateUtils.getEndDay(endTime, 0);
            jszxOrder.setEndCreateTime(endTime);
        }
        if (StringUtils.isNotBlank(seaUnitName)) {
            jszxOrder.setCompanyName(seaUnitName);
        }
        if (StringUtils.isNotBlank(seaProName)) {
            jszxOrder.setProName(seaProName);
        }
        if ("scenic".equals(type)) {
            jszxOrder.setProType(ProductType.scenic);
        } else {
            jszxOrder.setProType(ProductType.line);
        }

        Map<String, Object> map = jszxOrderService.doLoadPurchaseExcel(jszxOrder, getCompanyUnit(), getLoginUser(), isSiteAdmin(), isSupperAdmin(), pageInfo);
        if ((boolean) map.get("success")) {
            simpleResult(map, true, "导出成功！");
        } else {
            simpleResult(map, false, "导出失败！");
        }
        return jsonResult(map);

    }

    public Result loadSalesExcel() {
        Page pageInfo = new Page(page, 1000);
        String seaStartTime = (String) getParameter("seaStartTime");
        String seaEndTime = (String) getParameter("seaEndTime");
        String seaUnitName = (String) getParameter("seaUnit");
        String seaProName = (String) getParameter("seaProName");
        List<PurchaseEntity> purchaseEntities = new ArrayList<PurchaseEntity>();
        jszxOrder = new JszxOrder();
        if (StringUtils.isNotBlank(seaStartTime)) {
            Date startTime = DateUtils.getDate(seaStartTime + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
            jszxOrder.setStartCreateTime(startTime);
        }
        if (StringUtils.isNotBlank(seaEndTime)) {
            Date endTime = DateUtils.getDate(seaEndTime + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
            endTime = DateUtils.getEndDay(endTime, 0);
            jszxOrder.setEndCreateTime(endTime);
        }
        if (StringUtils.isNotBlank(seaUnitName)) {
            jszxOrder.setCompanyName(seaUnitName);
        }
        if (StringUtils.isNotBlank(seaProName)) {
            jszxOrder.setProName(seaProName);
        }
        if ("scenic".equals(type)) {
            jszxOrder.setProType(ProductType.scenic);
        } else {
            jszxOrder.setProType(ProductType.line);
        }

        Map<String, Object> map = jszxOrderService.doLoadSaleExcel(jszxOrder, getCompanyUnit(), getLoginUser(), isSiteAdmin(), isSupperAdmin(), pageInfo);
        if ((boolean) map.get("success")) {
            simpleResult(map, true, "导出成功！");
        } else {
            simpleResult(map, false, "导出失败！");
        }
        return jsonResult(map);

    }


    public Result salesDatagrid() {
        Page pageInfo = new Page(page, rows);

        String seaStartTime = (String) getParameter("seaStartTime");
        String seaEndTime = (String) getParameter("seaEndTime");
        String seaUnitName = (String) getParameter("seaUnit");
        String seaProName = (String) getParameter("seaProName");
        List<SalesEntity> salesEntityList = new ArrayList<SalesEntity>();
        jszxOrder = new JszxOrder();
        if (StringUtils.isNotBlank(seaStartTime)) {
            Date startTime = DateUtils.getDate(seaStartTime + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
            jszxOrder.setStartCreateTime(startTime);
        }
        if (StringUtils.isNotBlank(seaEndTime)) {
            Date endTime = DateUtils.getDate(seaEndTime + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
            endTime = DateUtils.getEndDay(endTime, 0);
            jszxOrder.setEndCreateTime(endTime);
        }
        if (StringUtils.isNotBlank(seaUnitName)) {
            jszxOrder.setCompanyName(seaUnitName);
        }
        if (StringUtils.isNotBlank(seaProName)) {
            jszxOrder.setProName(seaProName);
        }
        if ("scenic".equals(type)) {
            jszxOrder.setProType(ProductType.scenic);
            salesEntityList = jszxOrderService.findSalesEntityList(jszxOrder, getCompanyUnit(), getLoginUser(), isSiteAdmin(), isSupperAdmin(), pageInfo);
        } else {
            jszxOrder.setProType(ProductType.line);
            salesEntityList = jszxOrderService.findSalesEntityList(jszxOrder, getCompanyUnit(), getLoginUser(), isSiteAdmin(), isSupperAdmin(), pageInfo);
        }
        return datagrid(salesEntityList, pageInfo.getTotalCount());
    }




    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public SysUnit getSysUnit() {
        return sysUnit;
    }

    public void setSysUnit(SysUnit sysUnit) {
        this.sysUnit = sysUnit;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public JszxOrder getJszxOrder() {
        return jszxOrder;
    }

    public void setJszxOrder(JszxOrder jszxOrder) {
        this.jszxOrder = jszxOrder;
    }
}
