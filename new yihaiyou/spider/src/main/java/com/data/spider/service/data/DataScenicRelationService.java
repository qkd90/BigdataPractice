package com.data.spider.service.data;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.data.spider.service.dao.DataScenicRelationDao;
import com.data.spider.service.pojo.tb.DataScenicRelation;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;

@Service
public class DataScenicRelationService {
    @Autowired
    private DataScenicRelationDao dataScenicRelationDao;

    public void update(DataScenicRelation dis) {
    	dataScenicRelationDao.update(dis);
    }

    public List<DataScenicRelation> gets(int size,Criteria<DataScenicRelation> c) {
        Page page = new Page(1, size);
        List<DataScenicRelation> dis = dataScenicRelationDao.findByCriteriaWithOutCount(c, page);
        return dis;
    }


}
