package com.data.data.hmly.service;

import com.data.data.hmly.service.dao.UserExinfoDao;
import com.data.data.hmly.service.entity.UserExinfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by vacuity on 15/12/14.
 */

@Service
public class UserExinfoService {


    @Resource
    private UserExinfoDao userExinfoDao;

    public UserExinfo get(Long id) {
        return userExinfoDao.get(id);
    }

    public UserExinfo getByUserId(Long userId) {
        return userExinfoDao.getByUserId(userId);
    }
    public List<UserExinfo> getByTelephone(String telephone) {
        return userExinfoDao.getByTelephone(telephone);
    }

    public Integer countByTelephone(String telephone) {
        return userExinfoDao.countByTelephone(telephone);
    }

    public void save(UserExinfo userExinfo) {
        userExinfoDao.saveOrUpdate(userExinfo, userExinfo.getId());
    }
}
