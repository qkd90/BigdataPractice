package com.data.data.hmly.service.contract;

import com.data.data.hmly.service.contract.dao.ContractAppendiceDao;
import com.data.data.hmly.service.contract.entity.Contract;
import com.data.data.hmly.service.contract.entity.ContractAppendices;
import com.framework.hibernate.util.Criteria;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by dy on 2016/11/15.
 */
@Service
public class ContractAppendiceService {
    @Resource
    private ContractAppendiceDao appendiceDao;


    public List<ContractAppendices> getContractAppendiceList(ContractAppendices appendices) {

        Criteria<ContractAppendices> criteria = new Criteria<ContractAppendices>(ContractAppendices.class);
        if (appendices == null) {
            return appendiceDao.findByCriteria(criteria);
        }
        if (appendices.getContract() != null && appendices.getContract().getId() != null) {
            criteria.eq("contract.id", appendices.getContract().getId());
        }
        if (appendices.getType() != null) {
            criteria.eq("type", appendices.getType());
        }
        return appendiceDao.findByCriteria(criteria);
    }

    public void save(List<ContractAppendices> appendiceses, Contract contract) {
        for (ContractAppendices appendicese : appendiceses) {
            appendicese.setContract(contract);
            appendicese.setCreateTime(new Date());
            appendicese.setUpdateTime(new Date());
            appendiceDao.save(appendicese);
        }
    }

    public void deleteAll(ContractAppendices appendices) {
        List<ContractAppendices> appendiceses = getContractAppendiceList(appendices);
        if (!appendiceses.isEmpty()) {
            appendiceDao.deleteAll(appendiceses);
        }
    }

}
