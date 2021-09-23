package com.data.data.hmly.service;

import com.data.data.hmly.service.dao.BankCardDao;
import com.data.data.hmly.service.entity.BankCard;
import com.framework.hibernate.util.Criteria;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by huangpeijie on 2017-03-03,0003.
 */
@Service
public class BankCardService {
    @Resource
    private BankCardDao bankCardDao;

    public void save(BankCard bankCard) {
        bankCardDao.save(bankCard);
    }

    public BankCard get(Long id) {
        return bankCardDao.load(id);
    }

    public void update(BankCard bankCard) {
        bankCardDao.update(bankCard);
    }

    public List<BankCard> getByUser(Long userId) {
        Criteria<BankCard> criteria = new Criteria<>(BankCard.class);
        criteria.eq("user.id", userId);
        criteria.eq("inUse", true);
        return bankCardDao.findByCriteria(criteria);
    }
}
