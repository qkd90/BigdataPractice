package com.data.data.hmly.service;

import com.data.data.hmly.service.dao.FerryMemberDao;
import com.data.data.hmly.service.entity.FerryMember;
import com.framework.hibernate.util.Criteria;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by huangpeijie on 2016-11-24,0024.
 */
@Service
public class FerryMemberService {
    @Resource
    private FerryMemberDao ferryMemberDao;

    public void save(FerryMember ferryMember) {
        ferryMemberDao.save(ferryMember);
    }

    public FerryMember get(Long id) {
        return ferryMemberDao.load(id);
    }

    public void update(FerryMember ferryMember) {
        ferryMemberDao.update(ferryMember);
    }

    public void saveOrUpdate(FerryMember ferryMember) {
        ferryMemberDao.saveOrUpdate(ferryMember, ferryMember.getId());
    }

    public FerryMember findByName(String name) {
        Criteria<FerryMember> criteria = new Criteria<FerryMember>(FerryMember.class);
        criteria.eq("name", name);
        return ferryMemberDao.findUniqueByCriteria(criteria);
    }
}
