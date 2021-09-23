package com.data.spider.service.tb;

import com.data.spider.service.dao.TbScenicInfoDao;
import com.data.spider.service.pojo.TbStation;
import com.data.spider.service.pojo.tb.TbScenicInfo;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TbScenicInfoService {
    @Autowired
    private TbScenicInfoDao tbScenicInfoDao;

    public void update(TbScenicInfo dis) {
        tbScenicInfoDao.update(dis);
    }

    public List<TbScenicInfo> gets(int size,Criteria<TbScenicInfo> c) {
        Page page = new Page(1, size);

        List<TbScenicInfo> dis = tbScenicInfoDao.findByCriteriaWithOutCount(c, page);
        return dis;
    }

    public void save(TbScenicInfo tbScenicInfo) {
        tbScenicInfoDao.save(tbScenicInfo);
    }
}
