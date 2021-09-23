package com.data.data.hmly.action.contract;

import com.alibaba.fastjson.JSONObject;
import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.common.ProductService;
import com.data.data.hmly.service.common.entity.Product;
import com.data.data.hmly.service.contract.ContractAppendiceService;
import com.data.data.hmly.service.contract.ContractService;
import com.data.data.hmly.service.contract.entity.Contract;
import com.data.data.hmly.service.contract.entity.ContractAppendices;
import com.data.data.hmly.service.contract.entity.nums.ContractStatus;
import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.SysUser;
import com.framework.hibernate.util.Page;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.DateUtils;
import com.zuipin.util.JsonFilter;
import com.zuipin.util.NumberUtil;
import com.zuipin.util.StringUtils;
import net.sf.json.JsonConfig;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.*;

/**
 * Created by dy on 2016/9/1.
 */
public class ContractAction extends FrameBaseAction {

    @Resource
    private ContractService contractService;
    @Resource
    private ContractAppendiceService appendiceService;

    @Resource
    private ProductService productService;

    private Contract contract = new Contract();
    private Map<String, Object> map = new HashMap<String, Object>();
    private Integer page = 1;
    private Integer pageSize = 10;
    private Long proId;




    public Result getContractAppendiceList() {
        ContractAppendices contractAppendices = new ContractAppendices();
        contractAppendices.setContract(contract);
        List<ContractAppendices> appendiceses = appendiceService.getContractAppendiceList(contractAppendices);
        if (appendiceses.isEmpty()) {
            simpleResult(map, false, "");
            return jsonResult(map);
        }
        map.put("appendiceList", appendiceses);
        simpleResult(map, true, "");
        return json(net.sf.json.JSONObject.fromObject(map, JsonFilter.getIncludeConfig()));
    }

    public Result isHasContract() {

        if (proId != null) {
            Product product = productService.get(proId);
            SysUnit sysUnit = product.getCompanyUnit();
            map = contractService.doCheckHasContractByCompanyB(sysUnit, ContractStatus.UP);
        } else {
            result.put("isHas", false);
            result.put("reMsg", "当前产品无效。");
        }
        return jsonResult(map);
    }

    public Result viewContract() {
        if (!isSupperAdmin() && !isSiteAdmin()) {
            SysUnit sysUnit = getCompanyUnit();
            SysUser sysUser = getLoginUser();
            contract = contractService.getContractByCompanyB(sysUnit);
        }
        return dispatch();
    }

    public Result del() {
        String idStrs = (String) getParameter("idStrs");
        String[] idStrArr = idStrs.split(",");
        List<Contract> contractList = new ArrayList<Contract>();
        for (String idstr : idStrArr) {
            Contract tempContract = new Contract();
            tempContract.setId(Long.parseLong(idstr));
            contractList.add(tempContract);
        }
        contractService.delete(contractList);
        simpleResult(map, true, "");
        return jsonResult(map);
    }

    public Result list() {
        List<Contract> contractList = new ArrayList<Contract>();
        Page pageInfo = new Page(page, pageSize);
        contractList = contractService.getContractList(contract, getCompanyUnit(), isSiteAdmin(), isSupperAdmin(), pageInfo);
        String[] includeConfig = new String[]{"partyBunit","partyAunit"};
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig(includeConfig);
        return datagrid(contractList, pageInfo.getTotalCount(), jsonConfig);
    }

    public Result saveOrUpdate() throws ParseException {
        if (contract.getId() == null) {
            if (StringUtils.isNotBlank(contract.getEffectiveTimeStr())) {
                contract.setEffectiveTime(DateUtils.parseShortTime(contract.getEffectiveTimeStr()));
            }
            if (StringUtils.isNotBlank(contract.getSignTimeStr())) {
                contract.setSignTime(DateUtils.parseShortTime(contract.getSignTimeStr()));
            }
            if (StringUtils.isNotBlank(contract.getExpirationTimeStr())) {
                contract.setExpirationTime(DateUtils.parseShortTime(contract.getExpirationTimeStr()));
            }
            contract.setUser(getLoginUser());
            contract.setStatus(ContractStatus.UP);
            List<ContractAppendices> appendiceses = contract.getAppendicesList();
            contractService.save(contract);
            appendiceService.save(appendiceses, contract);
            simpleResult(map, true, "");
        } else {
            Contract tempContract = contractService.load(contract.getId());
            tempContract.setName(contract.getName());
            if (StringUtils.isNotBlank(contract.getEffectiveTimeStr())) {
                tempContract.setEffectiveTime(DateUtils.parseShortTime(contract.getEffectiveTimeStr()));
            }
            if (StringUtils.isNotBlank(contract.getSignTimeStr())) {
                tempContract.setSignTime(DateUtils.parseShortTime(contract.getSignTimeStr()));
            }
            if (StringUtils.isNotBlank(contract.getExpirationTimeStr())) {
                tempContract.setExpirationTime(DateUtils.parseShortTime(contract.getExpirationTimeStr()));
            }
            ContractAppendices appendices = new ContractAppendices();
            appendices.setContract(tempContract);
            appendiceService.deleteAll(appendices);
            tempContract.setPartyAnum(contract.getPartyAnum());
            tempContract.setPartyBnum(contract.getPartyBnum());
            tempContract.setSettlementType(contract.getSettlementType());
            tempContract.setSettlementValue(contract.getSettlementValue());
            tempContract.setValuationModels(contract.getValuationModels());
            tempContract.setValuationValue(contract.getValuationValue());
            tempContract.setSettlementType(contract.getSettlementType());
            tempContract.setStatus(contract.getStatus());
            tempContract.setContent(contract.getContent());
            tempContract.setPartyAunit(contract.getPartyAunit());
            tempContract.setPartyBunit(contract.getPartyBunit());
            contract.setStatus(ContractStatus.UP);
            contractService.update(tempContract);
            appendiceService.save(contract.getAppendicesList(), tempContract);
            simpleResult(map, true, "");
        }
        return jsonResult(map);
    }

    public Result editContract() {
        if (contract.getId() != null) {
            contract = contractService.load(contract.getId());
        } else {
            String partyAnum = "A";
            String partyBnum = "B";
            partyAnum += NumberUtil.getRunningNo();
            partyBnum += NumberUtil.getRunningNo();
            contract.setPartyAnum(partyAnum);
            contract.setPartyBnum(partyBnum);
        }
        return dispatch();
    }

//  /contract/contract/contractManage.jhtml
    public Result contractManage() {
        return dispatch();
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public Long getProId() {
        return proId;
    }

    public void setProId(Long proId) {
        this.proId = proId;
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
