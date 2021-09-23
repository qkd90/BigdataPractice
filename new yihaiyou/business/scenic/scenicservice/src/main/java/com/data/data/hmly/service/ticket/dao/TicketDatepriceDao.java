package com.data.data.hmly.service.ticket.dao;

import com.data.data.hmly.service.ticket.entity.TicketDateprice;
import com.data.data.hmly.service.ticket.entity.TicketMinData;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class TicketDatepriceDao extends DataAccess<TicketDateprice> {

	public List<TicketDateprice> findTicketList(
			TicketDateprice ticketDateprice, Page pageInfo) {
		
		Criteria<TicketDateprice> criteria = new Criteria<TicketDateprice>(TicketDateprice.class);
		
		criteria.eq("ticketPriceId", ticketDateprice.getId());
		
		return findByCriteria(criteria, pageInfo);
	}

    public Float findMinPriceByScenic(Long scenicInfoId, Date startDate) {
        Criteria<TicketDateprice> criteria = new Criteria<TicketDateprice>(TicketDateprice.class);
        DetachedCriteria ticketPriceCriteria = criteria.createCriteria("ticketPriceId", "ticketPrice");
        DetachedCriteria tiketCriteria = ticketPriceCriteria.createCriteria("ticket", "ticket");
        DetachedCriteria scenicCriteria = tiketCriteria.createCriteria("scenicInfo", "scenicInfo");
        scenicCriteria.add(Restrictions.eq("id", scenicInfoId));
        criteria.setProjection(Projections.min("priPrice"));
        if (startDate != null) {
            criteria.ge("huiDate", startDate);
        }
        return (Float) findUniqueValue(criteria);
    }

    public TicketMinData findMinPrice(Long tickettypepriceId,
			Date dateStart, Date dateEnd, String prop) {
		
		TicketMinData ticketMinData = null;
		
		Criteria<TicketDateprice> criteria = new Criteria<TicketDateprice>(TicketDateprice.class);
		criteria.eq("ticketPriceId.id", tickettypepriceId);
		if (dateStart != null) {
			criteria.ge("huiDate", dateStart);
		}
		if (dateEnd != null) {
			criteria.le("huiDate", dateEnd);
		}

		criteria.orderBy("rebate", "ASC");
		List<TicketDateprice> dateprices = findByCriteria(criteria);
		
		if (dateprices.size() > 0) {
			
			
			
//			dateprice = compareValue(dateprices);
			
			ticketMinData = compareValue(dateprices);
		}
		
		return ticketMinData;
	}
	
	public TicketMinData compareValue(List<TicketDateprice> dateprices) {
		
		TicketMinData minData = new TicketMinData();
		
		TicketDateprice dateprice = null;
		TicketDateprice datepriceReMax = dateprices.get(0);
		TicketDateprice datepriceReMin = dateprices.get(0);
       for (int i = 0; i < dateprices.size(); i++) {          
    	   if (datepriceReMax.getRebate() != null && dateprices.get(i).getRebate() != null) {
    		   if (datepriceReMax.getRebate() > dateprices.get(i).getRebate()) {
              	 datepriceReMin = dateprices.get(i);
               }
    	   } else {
    		   datepriceReMin.setRebate(0F); 
    	   }
             
    	   if (datepriceReMax.getRebate() != null && dateprices.get(i).getRebate() != null) {
    		   if (datepriceReMin.getRebate() < dateprices.get(i).getRebate()) {
              	 datepriceReMax = dateprices.get(i);
               }
    	   } else {
    		   datepriceReMax.setRebate(0F); 
    	   }
            
          }  
       
       
       
       TicketDateprice datepricePrMax = dateprices.get(0);
       TicketDateprice datepricePrMin = dateprices.get(0);
       
       for (int i = 0; i < dateprices.size(); i++) {
    	   
    	   if (datepriceReMax.getPriPrice() != null && dateprices.get(i).getPriPrice() != null) {
    		   if (datepricePrMax.getPriPrice() > dateprices.get(i).getPriPrice()) {
            	   datepricePrMin = dateprices.get(i);
               }
    	   } else {
    		   datepricePrMin.setPriPrice(0F);
    	   }
    	   
    	   if (datepriceReMax.getPriPrice() != null && dateprices.get(i).getPriPrice() != null) {
    		   if (datepricePrMin.getPriPrice() < dateprices.get(i).getPriPrice()) {
            	   datepricePrMax = dateprices.get(i);
               }
    	   } else {
    		   datepricePrMax.setPriPrice(0F);
    	   }
           
        }

		TicketDateprice datepriceMarMax = dateprices.get(0);
		TicketDateprice datepriceMarMin = dateprices.get(0);

		for (int i = 0; i < dateprices.size(); i++) {

			if (datepriceMarMax.getMaketPrice() != null && dateprices.get(i).getMaketPrice() != null) {
				if (datepriceMarMax.getMaketPrice() > dateprices.get(i).getMaketPrice()) {
					datepriceMarMin = dateprices.get(i);
				}
			} else {
				datepriceMarMin.setMaketPrice(0F);
			}

			if (datepriceReMax.getPriPrice() != null && dateprices.get(i).getPriPrice() != null) {
				if (datepricePrMin.getPriPrice() < dateprices.get(i).getPriPrice()) {
					datepricePrMax = dateprices.get(i);
				}
			} else {
				datepricePrMax.setPriPrice(0F);
			}

		}

		minData.setId(datepricePrMin.getTicketPriceId().getId());
       minData.setMinPriPrice(datepricePrMin.getPriPrice());
       minData.setMinRebate(datepriceReMin.getRebate());
       
       
//       str = datepricePrMin.getPriPrice().toString() + "," +datepriceReMin.getRebate().toString();
       
		return minData;
	}


	@Override
	public void save(Object entity) {
		// TODO Auto-generated method stub
		super.save(entity);
	}


	@Override
	public void update(Object entity) {
		// TODO Auto-generated method stub
		super.update(entity);
	}

	@Override
	public void save(List<?> objs) {
		// TODO Auto-generated method stub
		super.save(objs);
	}

	public void saevAll(List<TicketDateprice> datepriceList) {
		super.save(datepriceList);
	}

	public void insert(Object o) {
        super.save(o);
    }

}
