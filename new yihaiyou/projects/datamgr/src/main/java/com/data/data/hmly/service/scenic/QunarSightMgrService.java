package com.data.data.hmly.service.scenic;

import com.data.data.hmly.service.base.service.BaseService;
import com.data.data.hmly.service.scenic.dao.QunarSightDao;
import com.data.data.hmly.service.scenic.entity.QunarSight;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

@Service
public class QunarSightMgrService extends BaseService<QunarSight> {

   @Resource
   private QunarSightDao qunarSightDao;


    public void updateSight(Long id, Long scenicId) {
        QunarSight qunarSight = qunarSightDao.load(id);
        qunarSight.setScenicId(scenicId);
        qunarSight.setModifyTime(new Date());
        qunarSightDao.update(qunarSight);
    }

    public void updateOldSight(QunarSight qunarSight){
        qunarSightDao.update(qunarSight);
    }
    public QunarSight selById(Long id) {
       return qunarSightDao.load(id);
    }

    @Override
    public DataAccess<QunarSight> getDao() {
        return qunarSightDao;
    }

    @Override
    public Criteria<QunarSight> makeCriteria(Map<String, Object> paramMap, Criteria<QunarSight> c) {
        if(paramMap.containsKey("id") && !"".equals(paramMap.get("id"))){c.eq("id",paramMap.get("id"));}
        if(paramMap.containsKey("scenicId") && !"".equals(paramMap.get("scenicId"))){c.eq("scenicId",paramMap.get("scenicId"));}
        if(paramMap.containsKey("type") && Integer.parseInt(paramMap.get("type").toString()) == 1){
            c.add(Restrictions.and(Restrictions.ne("scenicId",0),Restrictions.isNotNull("scenicId")));
        }
        if(paramMap.containsKey("type") && Integer.parseInt(paramMap.get("type").toString()) == -1){
            c.add(Restrictions.or(Restrictions.eq("scenicId", 0), Restrictions.isNull("scenicId")));
        }
        if(paramMap.containsKey("name") && !"".equals(paramMap.get("name"))){c.eq("name",paramMap.get("name"));}
        if(paramMap.containsKey("likeName") && !"".equals(paramMap.get("likeName"))){c.like("likeName",paramMap.get("likeName").toString());}
        if(paramMap.containsKey("namePinyin") && !"".equals(paramMap.get("namePinyin"))){c.eq("namePinyin",paramMap.get("namePinyin"));}
        if(paramMap.containsKey("address") && !"".equals(paramMap.get("address"))){c.eq("address",paramMap.get("address"));}
        if(paramMap.containsKey("city") && !"".equals(paramMap.get("city"))){c.eq("city",paramMap.get("city"));}
        if(paramMap.containsKey("province") && !"".equals(paramMap.get("province"))){c.eq("id",paramMap.get("province"));}
        if(paramMap.containsKey("cityName") && !"".equals(paramMap.get("cityName"))){
            c.add(Restrictions.or(Restrictions.like("province",paramMap.get("cityName").toString()), Restrictions.like("city",paramMap.get("cityName").toString())));
        }
        if(paramMap.containsKey("country") && !"".equals(paramMap.get("country"))){c.eq("country",paramMap.get("country"));}
        if(paramMap.containsKey("areaNamePatch") && !"".equals(paramMap.get("areaNamePatch"))){c.like("areaNamePatch", paramMap.get("areaNamePatch").toString());}
        if(paramMap.containsKey("modifyTime") && !"".equals(paramMap.get("modifyTime"))){c.eq("modifyTime",paramMap.get("modifyTime"));}
        if(paramMap.containsKey("createTime") && !"".equals(paramMap.get("createTime"))){c.eq("createTime",paramMap.get("createTime"));}
        if(paramMap.containsKey("relationed") && Long.parseLong(paramMap.get("relationed").toString()) == -1){c.gt("scenicId",0L);}
        if(paramMap.containsKey("relationed") && Long.parseLong(paramMap.get("relationed").toString()) == 1){
            c.add(Restrictions.or(Restrictions.eq("scenicId",0L),Restrictions.isNull("scenicId")));
        }
        if(paramMap.containsKey("ids")){
            String[] ids = (String[]) paramMap.get("ids");
            if (ids.length > 0){
                c.in("id",ids);
            }
        }
        if(paramMap.containsKey("scenicIds")){
            String[] scenicIds = (String[]) paramMap.get("ids");
            if (scenicIds.length > 0){
                c.in("scenicId",scenicIds);
            }
        }
        return c;
    }
}
