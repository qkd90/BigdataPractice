package com.data.data.hmly.service.line;

import com.data.data.hmly.service.line.dao.LineContactDao;
import com.data.data.hmly.service.line.dao.LineDepartureDao;
import com.data.data.hmly.service.line.dao.LineDepartureInfoDao;
import com.data.data.hmly.service.line.entity.LineContact;
import com.framework.hibernate.util.Criteria;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by dy on 2016/6/21.
 */

@Service
public class LineContactService {

    @Resource
    private LineContactDao lineContactDao;

    public void delContact(LineContact lineContact) {
        lineContactDao.delete(lineContact);
    }

    public void saveLineContact(LineContact lineContact) {
        lineContact.setCreateTime(new Date());
        lineContact.setUpdateTime(new Date());
        lineContactDao.save(lineContact);
    }

    public List<LineContact> getLineContactList(LineContact lineContact) {

        Criteria<LineContact> criteria = new Criteria<LineContact>(LineContact.class);
        if (lineContact.getLine().getId() != null) {
            criteria.eq("line.id", lineContact.getLine().getId());
        }
        return lineContactDao.findByCriteria(criteria);
    }
}
