package com.data.data.hmly.service.scenic;

import com.data.data.hmly.service.base.service.BaseService;
import com.data.data.hmly.service.scenic.dao.ScenicOtherDao;
import com.data.data.hmly.service.scenic.entity.ScenicOther;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2015/11/27.
 */
@Service
public class ScenicOtherMgrService extends BaseService<ScenicOther>{

    @Resource
    private ScenicOtherDao scenicOtherDao;

    public ScenicOther getByScenicId(Long id) {
        ScenicOther scenicOther = scenicOtherDao.load(id);
        if(scenicOther == null){
            return new ScenicOther();
        }
        return scenicOther;
    }

    public void updateWithDataScenic(ScenicOther scenicOther) {
        scenicOtherDao.update(scenicOther);
    }

    public ScenicOther selById(Long id) {
        return scenicOtherDao.load(id);
    }

    //scenic_extend id 与 scenic id 一致对应
    public ScenicOther selByScenicId(Long id) {
        return scenicOtherDao.load(id);
    }

    @Override
    public DataAccess<ScenicOther> getDao() {
        return scenicOtherDao;
    }

    @Override
    public Criteria<ScenicOther> makeCriteria(Map<String, Object> paramMap, Criteria<ScenicOther> c) {
        DetachedCriteria dc_scenic = c.createCriteria("scenicInfo", "s");
        if(paramMap.containsKey("id") && Long.parseLong(paramMap.get("id").toString()) > 0){
            dc_scenic.add(Restrictions.eq("id",Long.parseLong(paramMap.get("id").toString())));
        }
        if(paramMap.containsKey("ctripId") && Integer.parseInt(paramMap.get("ctripId").toString()) > 0){
            c.eq("ctripId",Integer.parseInt(paramMap.get("ctripId").toString()));
        }
        return c;
    }
}
