package com.data.data.hmly.service.line;

import com.data.data.hmly.service.common.util.DateUtils;
import com.data.data.hmly.service.line.dao.LinetypepriceDao;
import com.data.data.hmly.service.line.dao.LinetypepricedateDao;
import com.data.data.hmly.service.line.entity.Line;
import com.data.data.hmly.service.line.entity.Linetypeprice;
import com.data.data.hmly.service.line.entity.Linetypepricedate;
import com.framework.hibernate.util.Criteria;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class LinetypepriceService {
	@Resource
	private LinetypepriceDao linetypepriceDao;
	@Resource
	private LinetypepricedateDao linetypepricedateDao;

	@Resource
	private LinetypepricedateService linetypepricedateService;


	public void save(Linetypeprice linetypeprice) {
		linetypepriceDao.save(linetypeprice);
	}

	public void update(Linetypeprice linetypeprice) {
		linetypepriceDao.update(linetypeprice);
	}

	public Float getMinLineAdultPrice(Line line) {


		Date startTime = new Date();

		List<Linetypeprice> linetypeprices = getTypePriceList(line);

		Float adultPrice = 0f;


		for (Linetypeprice typeprice : linetypeprices) {

			adultPrice = linetypepricedateService.getMinAdultPrice(line, typeprice, startTime);

		}

		return adultPrice;

	}

	public Float getMinLineChildPrice(Line line) {


		Date startTime = new Date();

		List<Linetypeprice> linetypeprices = getTypePriceList(line);
		Float childPrice = 0f;
		for (Linetypeprice typeprice : linetypeprices) {
			childPrice = linetypepricedateService.getMinChildPrice(line, typeprice, startTime);
		}
		return childPrice;

	}

	public List<Linetypeprice> getTypePriceList(Line line) {

		return linetypepriceDao.getTypePriceList(line);

	}


	/**
	 * 保存列表报价和报价时间
	 * @author caiys
	 * @date 2015年10月21日 上午11:34:06
	 * @param linetypeprice
	 * @param linetypepricedates
	 */
	public void saveLinePrice(Linetypeprice linetypeprice, List<Linetypepricedate> linetypepricedates) {
		linetypepriceDao.saveOrUpdate(linetypeprice, linetypeprice.getId());
		if (linetypepricedates.size() > 0) {
			if (linetypeprice.getId() != null) {	// 如果是更新，则先删除该时间范围内的时间价格，然后再做新增
				Date dateStart = null;
				Date dateEnd = null;
				dateStart = DateUtils.add(new Date(), Calendar.DAY_OF_MONTH, 1);
                dateStart = DateUtils.getDateBeginTime(dateStart);  // 编辑天+1
//				for (Linetypepricedate pd : linetypepricedates) {
//					if (dateStart == null || dateStart.after(pd.getDay())) {
//						dateStart = pd.getDay();
//					}
//					if (dateEnd == null || dateEnd.before(pd.getDay())) {
//						dateEnd = pd.getDay();
//					}
//				}
				linetypepricedateDao.delByPriceIdAndDay(linetypeprice.getId(), dateStart, dateEnd);
			}
			for (Linetypepricedate linetypepricedate : linetypepricedates) {
				linetypepricedate.setLinetypeprice(linetypeprice);
			}
			linetypepricedateDao.save(linetypepricedates);
		}
	}
	
	/**
	 * 线路类别报价
	 * @author caiys
	 * @date 2015年10月21日 下午2:26:46
	 * @param linetypeprice
	 * @return
	 */
	public List<Linetypeprice> findLinetypepriceList(Linetypeprice linetypeprice) {
		return linetypepriceDao.findLinetypepriceList(linetypeprice);
	}
	
	/**
	 * 获取类别报价中最小优惠价，日期范围第二天和下个月最后一天
	 * @author caiys
	 * @date 2015年10月21日 下午3:50:34
	 * @param linetypepriceId
	 * @param dateStart
	 * @param dateEnd
	 * @return
	 */
	public Float findMinValue(Long linetypepriceId, Date dateStart, Date dateEnd, String prop) {
		return linetypepriceDao.findMinValue(linetypepriceId, dateStart, dateEnd, prop);
	}
	
	
	public Linetypeprice getLinetypeprice(Long id) {
		return linetypepriceDao.load(id);
	}

    public Linetypeprice findFullById(Long id) {
        Criteria<Linetypeprice> criteria = new Criteria<Linetypeprice>(Linetypeprice.class);
        criteria.createCriteria("line", "line");
        criteria.createCriteria("linetypepricedate", "linetypepricedate");
        criteria.eq("id", id);
        return linetypepriceDao.findUniqueByCriteria(criteria);
    }

	public void delTypePriceById(Long priceId) {
		Linetypeprice linetypeprice = linetypepriceDao.load(priceId);
		linetypepricedateDao.delByPriceIdAndDay(priceId, null, null);
		linetypepriceDao.delete(linetypeprice);
	}
}
