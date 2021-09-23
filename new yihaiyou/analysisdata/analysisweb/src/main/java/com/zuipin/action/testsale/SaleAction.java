package com.zuipin.action.testsale;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.Result;
import com.zuipin.action.JxmallAction;
import com.zuipin.pojo.Month;
import com.zuipin.pojo.Myorder;
import com.zuipin.pojo.Myorderdetail;
import com.zuipin.service.dao.MonthDao;
import com.zuipin.service.dao.MyorderDao;
import com.zuipin.service.dao.MyorderdetailDao;

public class SaleAction extends JxmallAction {
	
	/**
	 * 
	 */
	private static final long		serialVersionUID	= 3441688618247120468L;
	
	@Autowired
	private MonthDao				monthDao;
	@Autowired
	private MyorderdetailDao		myorderdetailDao;
	@Autowired
	private MyorderDao				myorderDao;
	
	private HashMap<Month, Float>	monthLeft			= new HashMap<Month, Float>();
	private Random					ran					= new Random();
	/**
	 * 每单数量
	 */
	private List<Integer>			itemNum				= new ArrayList<Integer>();
	private Long					orderid				= 0l;
	
	public Result test() {
		for (int i = 3; i <= 10; i++) {
			itemNum.add(i);
		}
		
		List<Month> months = monthDao.findAll();
		WeightTreeInfo<Month> weightTreeInfo = new WeightTreeInfo<Month>();
		for (Month month : months) {
			weightTreeInfo.putNode(month, month.getNum().intValue() < 1 ? 1 : month.getNum().intValue());
			monthLeft.put(month, month.getNum());
		}
		do {
			int saleItem = itemNum.get(ran.nextInt(itemNum.size()));
			List<Integer> itemNum = new ArrayList<Integer>();
			int left = saleItem;
			do {
				int a = ran.nextInt(left) + 1;
				itemNum.add(a);
				left -= a;
			} while (left != 0);
			HashMap<Month, Float> items = new HashMap<Month, Float>();
			Boolean rebuild = true;
			for (Integer b : itemNum) {
				Month month = getMonth(weightTreeInfo);
				Float leftNum = monthLeft.get(month);
				Float increNum = 0f;
				if (leftNum >= b) {
					increNum = b.floatValue();
					monthLeft.put(month, leftNum - b);
				} else {
					increNum = leftNum;
					monthLeft.remove(month);
					rebuild = true;
				}
				
				if (items.containsKey(month)) {
					items.put(month, items.get(month) + increNum);
				} else {
					items.put(month, increNum);
				}
			}
			orderid += 1;
			List<Myorderdetail> details = new ArrayList<Myorderdetail>();
			Float totalAmt = 0f;
			for (Entry<Month, Float> entry : items.entrySet()) {
				Myorderdetail detail = new Myorderdetail();
				detail.setNum(entry.getValue());
				detail.setOrderid(orderid);
				detail.setPrice(entry.getKey().getPrice());
				detail.setProid(entry.getKey().getProid());
				details.add(detail);
				totalAmt += detail.getNum() * detail.getPrice();
			}
			Myorder myorder = new Myorder();
			myorder.setId(orderid);
			myorder.setTotlamt(totalAmt);
			myorderDao.save(myorder);
			myorderdetailDao.save(details);
			if (rebuild) {
				weightTreeInfo.getNodes().clear();
				for (Entry<Month, Float> entry : monthLeft.entrySet()) {
					weightTreeInfo.putNode(entry.getKey(), entry.getValue().intValue() < 1 ? 1 : entry.getValue().intValue());
				}
			}
		} while (monthLeft.size() > 0);
		
		return text("success");
	}
	
	private Month getMonth(WeightTreeInfo<Month> weightTreeInfo) {
		Month month = null;
		do {
			month = weightTreeInfo.getNode();
			if (!monthLeft.containsKey(month)) {
				month = null;
			}
		} while (month == null);
		return month;
	}
	
}
