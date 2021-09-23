package com.data.data.hmly.action.ticket;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.common.ProValidCodeService;
import com.data.data.hmly.service.common.ProductValidateCodeService;
import com.data.data.hmly.service.common.entity.ProValidCode;
import com.data.data.hmly.service.common.entity.ProductValidateCode;
import com.data.data.hmly.service.ticket.entity.enmus.TicketType;
import com.framework.hibernate.util.Page;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.JsonFilter;
import net.sf.json.JsonConfig;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by dy on 2016/12/17.
 */
public class TicketValidateCodeAction extends FrameBaseAction {

    @Resource
    private ProductValidateCodeService productValidateCodeService;
    @Resource
    private ProValidCodeService proValidCodeService;

    private Integer page = 1;
    private Integer rows = 10;
    private String ticketType;
    private ProductValidateCode productValidateCode = new ProductValidateCode();
    private ProValidCode proValidCode = new ProValidCode();


    public Result getValidateCodeList() {
        Page pageInfo = new Page(page, rows);
        List<ProValidCode> productValidateCodeList = proValidCodeService.getYhyValidateCodeList(proValidCode, ticketType, pageInfo);
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig("validateUser");
        return datagrid(productValidateCodeList, pageInfo.getTotalCount(), jsonConfig);
    }

    // /ticket/ticketValidateCode/ticketValidateCodeManage.jhtml
    public Result ticketValidateCodeManage() {
        ticketType = "ticket";
        return dispatch("/WEB-INF/jsp/ticket/ticket/validateCode.jsp");
    }
    // /ticket/ticketValidateCode/ticketValidateCodeManage.jhtml
    public Result sailboatValidateCodeManage() {
        ticketType = "sailboat";
        return dispatch("/WEB-INF/jsp/ticket/ticket/validateCode.jsp");
    }

    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
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

    public ProductValidateCode getProductValidateCode() {
        return productValidateCode;
    }

    public void setProductValidateCode(ProductValidateCode productValidateCode) {
        this.productValidateCode = productValidateCode;
    }

    public ProValidCode getProValidCode() {
        return proValidCode;
    }

    public void setProValidCode(ProValidCode proValidCode) {
        this.proValidCode = proValidCode;
    }
}
