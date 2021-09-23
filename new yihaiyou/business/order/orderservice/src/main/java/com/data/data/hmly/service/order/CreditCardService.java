package com.data.data.hmly.service.order;

import com.data.data.hmly.service.order.dao.CreditCardDao;
import com.data.data.hmly.service.order.entity.CreditCard;
import com.framework.hibernate.util.Criteria;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zzl on 2016/4/27.
 */
@Service
public class CreditCardService {

    @Resource
    private CreditCardDao creditCardDao;

    public CreditCard get(Long id) {
        return creditCardDao.load(id);
    }

    public void save(CreditCard creditCard) {
        creditCardDao.save(creditCard);
    }

    public void update(CreditCard creditCard) {
        creditCardDao.update(creditCard);
    }

    public List<CreditCard> getByUserId(Long userId) {
        Criteria<CreditCard> criteria = new Criteria<>(CreditCard.class);
        criteria.eq("user.id", userId);
        return creditCardDao.findByCriteria(criteria);
    }

}
