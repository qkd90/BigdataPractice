package com.data.spider.service.tb;

import com.data.spider.service.dao.TbBusdisDao;
import com.data.spider.service.pojo.tb.TbBusDis;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TbBusDisService {
    @Autowired
    private TbBusdisDao tbBusdisDao;

//    public synchronized List<Datatask> updateTasks() {
//
//		Page page = new Page(1, 20);
//		Criteria<Datatask> c = new Criteria<Datatask>(Datatask.class);
//		c.eq("status", DatataskStatus.NEW);
//		List<Datatask> tasks = datataskDao.findByCriteriaWithOutCount(c, page); //数据量大时，使用 count会执行缓慢
////        String sql = "SELECT * FROM data_tasks WHERE status  = 'NEW' LIMIT 20;";
////        Object parameters = new Object();
////        List<Etltask> tasks = datataskDao.findBySQL(sql);
//        for (Datatask task : tasks) {
//            task.setStatus(DatataskStatus.DOING);
//        }
//        datataskDao.updateAll(tasks);
//        return tasks;
//    }

    public void updateDis(TbBusDis dis) {
        tbBusdisDao.update(dis);
    }
    public List<TbBusDis>  getDis(long id) {
        Page page = new Page(1, 1);
        Criteria<TbBusDis> c = new Criteria<TbBusDis>(TbBusDis.class);
        c.eq("id", id);
        List<TbBusDis> dis = tbBusdisDao.findByCriteriaWithOutCount(c, page); //数据量大时，使用 count会执行缓慢
        return dis;
    }


}
