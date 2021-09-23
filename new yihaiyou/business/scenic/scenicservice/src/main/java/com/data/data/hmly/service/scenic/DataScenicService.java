package com.data.data.hmly.service.scenic;

import com.data.data.hmly.service.scenic.dao.DataScenicDao;
import com.data.data.hmly.service.scenic.entity.DataScenic;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DataScenicService {

    Logger logger = Logger.getLogger(DataScenicService.class);

    @Resource
    private DataScenicDao dataScenicDao;

    public List<DataScenic> listAll() {
        return dataScenicDao.findAll();
    }

    public DataScenic get(Long id) {
        return dataScenicDao.load(id);
    }

    public void save(DataScenic dataScenic) {
        dataScenicDao.save(dataScenic);
    }


}
