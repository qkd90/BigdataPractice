package com.data.data.hmly.service.contract;

import com.data.data.hmly.service.contract.entity.Contract;
import com.data.data.hmly.service.contract.entity.nums.ContractStatus;
import com.google.common.collect.Lists;
import com.zuipin.util.DateUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by dy on 2016/10/25.
 */
@Component
public class ContractQuartz {

    @Resource
    private ContractService contractService;


    public void doCheckAndUpdateContract() {

        List<Contract> contractList = contractService.getContractListByStatusAndDate(ContractStatus.UP, DateUtils.getStartDay(new Date(), 0));

        List<Contract> tempContractList = Lists.newArrayList();
        for (Contract contract : contractList) {
            if (contract.getStatus() == ContractStatus.UP) {
                contract.setStatus(ContractStatus.INVALID);
                tempContractList.add(contract);
            }
        }

        contractService.updataAll(tempContractList);
    }



}
