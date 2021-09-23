package com.data.data.hmly.service.outer;

import com.data.data.hmly.service.outer.dao.OuterMascotBenameDao;
import com.data.data.hmly.service.outer.entity.OuterMascotBename;
import com.framework.hibernate.util.Criteria;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by caiys on 2017/1/6.
 */
@Service
public class OuterMascotBenameService {
    @Resource
    private OuterMascotBenameDao outerMascotBenameDao;

    public void saveOuterMascotBename(OuterMascotBename outerMascotBename) {
        outerMascotBenameDao.save(outerMascotBename);
    }

    public List<OuterMascotBename> list() {
        Criteria<OuterMascotBename> criteria = new Criteria<OuterMascotBename>(OuterMascotBename.class);
        criteria.orderBy("createTime", "asc");
        List<OuterMascotBename> list = outerMascotBenameDao.findByCriteria(criteria);
        return list;
    }
}
