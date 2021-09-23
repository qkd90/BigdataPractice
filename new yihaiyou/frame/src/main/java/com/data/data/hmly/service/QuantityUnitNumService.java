package com.data.data.hmly.service;

import com.data.data.hmly.service.dao.QuantityUnitNumDao;
import com.data.data.hmly.service.entity.QuantityUnitNum;
import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.SysUser;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import org.hibernate.criterion.MatchMode;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class QuantityUnitNumService {

    @Resource
    private QuantityUnitNumDao quantityUnitNumDao;


    /**
     * 功能描述：保存
     * @param quantityUnitNum
     */
    public void save(QuantityUnitNum quantityUnitNum) {
        quantityUnitNum.setCreateTime(new Date());
        quantityUnitNum.setUpdateTime(new Date());
        quantityUnitNumDao.save(quantityUnitNum);

    }

    /**
     * 功能描述：更新
     * @param quantityUnitNum
     */
    public void update(QuantityUnitNum quantityUnitNum) {
        QuantityUnitNum newQuantityUnitNum = quantityUnitNumDao.load(quantityUnitNum.getId());
        newQuantityUnitNum.setConditionNum(quantityUnitNum.getConditionNum());
        newQuantityUnitNum.setUpdateTime(new Date());
        quantityUnitNumDao.update(newQuantityUnitNum);

    }


    /**
     * 功能描述：判断当前拱量公司是否已经存在
     * @param suplerUnit
     * @param dealerUnit
     * @return
     */
    public boolean doCheckIdentityCodeExisted(SysUnit suplerUnit, SysUnit dealerUnit) {

        Criteria<QuantityUnitNum> criteria = new Criteria<QuantityUnitNum>(QuantityUnitNum.class);

        criteria.eq("dealerUnit", dealerUnit);
        criteria.eq("suplerUnit", suplerUnit);

        QuantityUnitNum quantityUnitNum = quantityUnitNumDao.findUniqueByCriteria(criteria);

        if (quantityUnitNum == null) {
            return false;
        }
        return true;
    }


    /**
     * 功能描述：查询拱量关系的公司列表
     * @param quantityUnitNum
     * @param page
     * @param loginUser
     * @param companyUnit
     * @param siteAdmin
     * @param supperAdmin
     * @return
     */
    public List<QuantityUnitNum> getQuantityUnitNumList(QuantityUnitNum quantityUnitNum,
        Page page, SysUser loginUser,
        SysUnit companyUnit, Boolean siteAdmin, Boolean supperAdmin) {


        Criteria<QuantityUnitNum> criteria =
                new Criteria<QuantityUnitNum>(QuantityUnitNum.class);




        // 数据过滤
        if (!supperAdmin) {
            criteria.createCriteria("suplerUnit", "u", JoinType.INNER_JOIN);
            criteria.eq("u.sysSite.id", loginUser.getSysSite().getId());
            if (!siteAdmin) {
                criteria.eq("u.id", loginUser.getSysUnit().getCompanyUnit().getId());
            }
        }
        if (quantityUnitNum != null) {
            foramtCond(quantityUnitNum, criteria);
        }

        criteria.orderBy("updateTime", "desc");
        return quantityUnitNumDao.findByCriteria(criteria, page);
    }


    /**
     * 功能描述：查询条件拼接
     *
     * @author zdy
     * @date 2015年10月16日 上午9:10:28
     * @param quantityUnitNum
     * @param criteria
     */
    public void foramtCond(QuantityUnitNum quantityUnitNum, Criteria<QuantityUnitNum> criteria) {

        if (quantityUnitNum.getSuplerUnitName() != null) {
            criteria.like("u.name", quantityUnitNum.getSuplerUnitName(), MatchMode.ANYWHERE);
        }
        if (quantityUnitNum.getDealerUnitName() != null) {
            criteria.createCriteria("dealerUnit", "d", JoinType.INNER_JOIN);
            criteria.like("d.name", quantityUnitNum.getDealerUnitName(), MatchMode.ANYWHERE);
        }
        if (quantityUnitNum.getConditionNumStart() != null) {
            criteria.ge("conditionNum", quantityUnitNum.getConditionNumStart());
        }
        if (quantityUnitNum.getConditionNumEnd() != null) {
            criteria.le("conditionNum", quantityUnitNum.getConditionNumEnd());
        }

    }

    /**
     * 功能描述：删除记录
     */
    public void delQuantityUnitNumById(long id) {
        String hql = "delete from QuantityUnitNum where id=?";
        quantityUnitNumDao.updateByHQL(hql, id);
    }

    /**
     * 功能描述：通过id查询
     */
    public QuantityUnitNum load(Long id) {
        return quantityUnitNumDao.load(id);
    }


    /**
     * 功能描述：根据被拱量公司获取拱量公司
     * @param dealerUnit
     * @return
     */
    public List<SysUnit> getSupplerUintByDealurUnit(SysUnit dealerUnit) {

        Criteria<QuantityUnitNum> criteria =
                new Criteria<QuantityUnitNum>(QuantityUnitNum.class);

        criteria.eq("dealerUnit", dealerUnit);

        List<QuantityUnitNum> quantityUnitNums = quantityUnitNumDao.findByCriteria(criteria);

        List<SysUnit> sysUnits = new ArrayList<SysUnit>();

        for (QuantityUnitNum quantityUnitNum : quantityUnitNums) {
            sysUnits.add(quantityUnitNum.getSuplerUnit());
        }
        return sysUnits;
    }

    /**
     * 功能描述：根据拱量公司和被拱量公司获取拱量关系的拱量条件
     * @param sysUnit
     * @param supplerUnit
     * @return
     */
    public Integer getConditionNumByQuantityUnits(SysUnit sysUnit, SysUnit supplerUnit) {
        Criteria<QuantityUnitNum> criteria = new Criteria<QuantityUnitNum>(QuantityUnitNum.class);
        criteria.eq("dealerUnit", sysUnit);
        criteria.eq("suplerUnit", supplerUnit);

        QuantityUnitNum quantityUnitNum = quantityUnitNumDao.findUniqueByCriteria(criteria);
        if (quantityUnitNum != null) {
            return quantityUnitNum.getConditionNum();
        } else {
            return 0;
        }

    }
}
