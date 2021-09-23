package com.data.spider.service.tb;

import com.data.spider.service.dao.TbdisDao;
import com.data.spider.service.pojo.tb.TbDis;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TbDisService {
    @Autowired
    private TbdisDao tbdisDao;

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

    public void updateDis(TbDis dis) {
        tbdisDao.update(dis);
    }
    public List<TbDis>  getDis(long id) {
        Page page = new Page(1, 1);
        Criteria<TbDis> c = new Criteria<TbDis>(TbDis.class);
        c.eq("id", id);
        List<TbDis> dis = tbdisDao.findByCriteriaWithOutCount(c, page); //数据量大时，使用 count会执行缓慢
        return dis;
    }

    public List<TbDis> find(Criteria<TbDis> criteria, Page page) {
        return tbdisDao.findByCriteriaWithOutCount(criteria, page);
    }


}
