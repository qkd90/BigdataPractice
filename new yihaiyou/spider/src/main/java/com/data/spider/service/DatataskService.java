package com.data.spider.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.data.spider.service.dao.DatataskDao;
import com.data.spider.service.pojo.Datatask;
import com.data.spider.service.pojo.DatataskStatus;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;

@Service
public class DatataskService {
    @Autowired
    private DatataskDao datataskDao;

    public synchronized List<Datatask> updateTasks() {

		Page page = new Page(1, 20);
		Criteria<Datatask> c = new Criteria<Datatask>(Datatask.class);
		c.eq("status", DatataskStatus.NEW);
		List<Datatask> tasks = datataskDao.findByCriteriaWithOutCount(c, page); //数据量大时，使用 count会执行缓慢
//        String sql = "SELECT * FROM data_tasks WHERE status  = 'NEW' LIMIT 20;";
//        Object parameters = new Object();
//        List<Etltask> tasks = datataskDao.findBySQL(sql);
        for (Datatask task : tasks) {
            task.setStatus(DatataskStatus.DOING);
        }
        datataskDao.updateAll(tasks);
        return tasks;
    }

    public void updateTask(Datatask task) {
        datataskDao.update(task);
    }

}
