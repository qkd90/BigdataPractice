package com.hmlyinfo.app.soutu.pay.allin.service;

import com.hmlyinfo.app.soutu.pay.allin.domain.PaymentResult;
import com.hmlyinfo.app.soutu.pay.allin.mapper.PaymentResultMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentResultService extends BaseService<PaymentResult, Long> {

    @Autowired
    private PaymentResultMapper<PaymentResult> mapper;

    @Override
    public BaseMapper<PaymentResult> getMapper() {
        return mapper;
    }

    @Override
    public String getKey() {
        return "id";
    }

}
