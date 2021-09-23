package com.data.data.hmly.service.contract;

import com.data.data.hmly.service.contract.dao.ContractDao;
import com.data.data.hmly.service.contract.entity.Contract;
import com.data.data.hmly.service.contract.entity.nums.ContractStatus;
import com.data.data.hmly.service.entity.SysUnit;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.zuipin.util.DateUtils;
import com.zuipin.util.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dy on 2016/9/1.
 */
@Service
public class ContractService {
    @Resource
    private ContractDao contractDao;

    /**
     * 查询当前时间内有效的合同
     * @return
     */
    public Contract getContractByCompanyB(Long companyId, Date date) {
        Criteria<Contract> criteria = new Criteria<Contract>(Contract.class);
        criteria.eq("partyBunit.id", companyId);
        criteria.eq("status", ContractStatus.UP);
//        Date now = DateUtils.getDate(DateUtils.format(date, "yyyyMMdd"), "yyyyMMdd");
        criteria.le("effectiveTime", date);
        criteria.ge("expirationTime", date);
        List<Contract> contracts = contractDao.findByCriteria(criteria);
        if (contracts.isEmpty()) {
            return null;
        }
        return contracts.get(0);
    }

    public Contract load(Long id) {
        return contractDao.load(id);
    }

    public void save(Contract contract) {
        contract.setCreateTime(new Date());
        contract.setUpdateTime(new Date());
        contractDao.save(contract);
    }

    public void update(Contract contract) {
        contract.setUpdateTime(new Date());
        contractDao.update(contract);
    }

    public List<Contract> getContractList(Contract contract, SysUnit companyUnit, Boolean siteAdmin, Boolean supperAdmin, Page pageInfo) {
        Criteria<Contract> criteria = new Criteria<Contract>(Contract.class);
        doFormCriteria(contract, criteria);
        if (!supperAdmin) {
            criteria.createCriteria("partyAunit", "a", JoinType.INNER_JOIN);
            criteria.createCriteria("partyBunit", "b", JoinType.INNER_JOIN);
            criteria.eq("a.sysSite.id", companyUnit.getSysSite().getId());
            if (!siteAdmin) {
                criteria.or(Restrictions.eq("a.id", companyUnit.getId()), Restrictions.eq("b.id", companyUnit.getId()));
//                criteria.eq("a.id", companyUnit.getId());
            }
        }
        criteria.not("status", ContractStatus.DEL);
        criteria.orderBy("updateTime", "desc");
        return contractDao.findByCriteria(criteria, pageInfo);
    }

    private void doFormCriteria(Contract contract, Criteria<Contract> criteria) {

        if (StringUtils.isNotBlank(contract.getName())) {
            criteria.like("name", contract.getName(), MatchMode.ANYWHERE);
        }

        if (contract.getStatus() != null) {
            criteria.eq("status", contract.getStatus());
        }

        if (StringUtils.isNotBlank(contract.getPartyAnum())) {
            criteria.eq("partyAnum", contract.getPartyAnum());
        }

        if (StringUtils.isNotBlank(contract.getPartyBnum())) {
            criteria.eq("partyBnum", contract.getPartyBnum());
        }

        if (contract.getPartyAunit() != null) {
            if (StringUtils.isNotBlank(contract.getPartyAunit().getName())) {
                criteria.like("partyAunit.name", contract.getPartyAunit().getName(), MatchMode.ANYWHERE);
            }
        }
        if (contract.getPartyBunit() != null) {
            if (StringUtils.isNotBlank(contract.getPartyBunit().getName())) {
                criteria.like("partyBunit.name", contract.getPartyBunit().getName(), MatchMode.ANYWHERE);
            }
        }
        if (StringUtils.isNotBlank(contract.getQrySignTimeStart())) {
            criteria.ge("signTime", DateUtils.toDate(contract.getQrySignTimeStart()));
        }

        if (StringUtils.isNotBlank(contract.getQrySignTimeEnd())) {
            criteria.le("signTime", DateUtils.toDate(contract.getQrySignTimeEnd()));
        }

        if (StringUtils.isNotBlank(contract.getQryExpiTimeStart())) {
            criteria.ge("expirationTime", DateUtils.toDate(contract.getQryExpiTimeStart()));
        }

        if (StringUtils.isNotBlank(contract.getQryExpiTimeEnd())) {
            criteria.le("expirationTime", DateUtils.toDate(contract.getQryExpiTimeEnd()));
        }

    }

    public void delete(List<Contract> contractList) {
        contractDao.deleteAll(contractList);
    }

    public Contract getContractByCompanyB(SysUnit sysUnit) {
        Criteria<Contract> criteria = new Criteria<Contract>(Contract.class);
        criteria.eq("partyBunit.id", sysUnit.getId());
        criteria.eq("status", ContractStatus.UP);
        List<Contract> contracts = contractDao.findByCriteria(criteria);
        if (contracts.isEmpty()) {
            return null;
        }
        return contracts.get(0);
    }

    public Map<String, Object> doCheckHasContractByCompanyB(SysUnit sysUnit, ContractStatus status) {
        Map<String, Object> result = new HashMap<String, Object>();
        Criteria<Contract> criteria = new Criteria<Contract>(Contract.class);
        criteria.eq("partyBunit.id", sysUnit.getId());
//        criteria.eq("status", status);
        List<Contract> contracts = contractDao.findByCriteria(criteria);
        if (contracts.isEmpty()) {
            result.put("isHas", false);
            result.put("reMsg", "暂时没有合同信息，请先完善合同信息。");
            return result;
        }
        Contract contract = contracts.get(0);
        if (contract.getStatus() == ContractStatus.FREEZE) {
            result.put("isHas", false);
            result.put("reMsg", "当前合同已被冻结，请联系平台客服咨询。");
        } else if (contract.getStatus() == ContractStatus.INVALID) {
            result.put("isHas", false);
            result.put("reMsg", "当前合同已无效，请联系平台客服咨询。");
        } else if (contract.getStatus() == ContractStatus.DEL) {
            result.put("isHas", false);
            result.put("reMsg", "当前合同已被删除，请联系平台客服咨询。");
        } else {
            result.put("isHas", true);
        }
        return result;
    }

    public List<Contract> getContractListByStatusAndDate(ContractStatus status, Date date) {
        Criteria<Contract> criteria = new Criteria<Contract>(Contract.class);
        criteria.eq("status", status);
        criteria.lt("expirationTime", date);
        return contractDao.findByCriteria(criteria);
    }


    public void updataAll(List<Contract> tempContractList) {
        contractDao.updateAll(tempContractList);
    }
}
