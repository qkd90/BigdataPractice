package com.data.data.hmly.action.line;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.line.LinetypepricedateService;
import com.data.data.hmly.service.line.entity.Linetypepricedate;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.DateUtils;
import net.sf.json.JSONArray;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LinetypepricedateAction extends FrameBaseAction {
	private static final long serialVersionUID = 780567922999778904L;
	@Resource
	private LinetypepricedateService		linetypepricedateService;
	
	// 返回数据
	Map<String, Object>					map					= new HashMap<String, Object>();
	
	
	/**
	 * 获取报价时间列表（日历显示）
	 * @author caiys
	 * @date 2015年10月23日 上午10:57:36
	 * @return
	 */
	public Result findTypePriceDate() {
		// 参数
		String linetypepriceId = (String) getParameter("linetypepriceId");
		String dateStartStr = (String) getParameter("dateStart");
		String dateEndStr = (String) getParameter("dateEnd");
		//String cIndex = (String) getParameter("cIndex");
		Date dateStart = null;
		Date dateEnd = null;
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		if (StringUtils.isNotBlank(linetypepriceId)) {
			if (StringUtils.isNotBlank(dateStartStr)) {
				dateStart = DateUtils.getDate(dateStartStr, "yyyy-MM-dd");
			}
			if (StringUtils.isNotBlank(dateEndStr)) {
				dateEnd = DateUtils.getDate(dateEndStr, "yyyy-MM-dd");
			}
			// 如果起始时间不为空且结束时间为空，则设置结束时间为开始时间当月最后一天
			if (dateStart != null && dateEnd == null) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(dateStart);
				calendar.set(Calendar.DAY_OF_MONTH, 1);
				calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 6);
				calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);
				dateEnd = calendar.getTime();
			}
			// 如果起始时间为空且结束时间不为空，则设置起始时间为结束时间当月的第一天
			if (dateStart == null && dateEnd != null) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(dateEnd);
				calendar.set(Calendar.DAY_OF_MONTH, 1);
				dateStart = calendar.getTime();
			}
			List<Linetypepricedate> linetypepricedates = linetypepricedateService.findTypePriceDate(Long.valueOf(linetypepriceId), dateStart, dateEnd);
			// 返回页面数据格式
			DecimalFormat df = new DecimalFormat("0.00");
			for (Linetypepricedate pd : linetypepricedates) {	// {id:vid,discountPrice:discountPrice,title:'优惠价¥'+discountPrice,start:LineUtil.dateToString(date,'yyyy-MM-dd')}
				if (pd.getDiscountPrice() != null) {
					Map<String, String> map = new HashMap<String, String>();
					Date day = pd.getDay();
                    String title = "a.成人" + df.format(pd.getDiscountPrice());
					map.put("id", "1" + day.getTime());
					map.put("discountPrice", String.valueOf(pd.getDiscountPrice()));
					map.put("title", title);
					map.put("start", DateUtils.format(day, "yyyy-MM-dd"));
					list.add(map);
				}
				if (pd.getMarketPrice() != null) {
					Map<String, String> map = new HashMap<String, String>();
					Date day = pd.getDay();
					String title = "b.成人市价" + df.format(pd.getMarketPrice());
					map.put("id", "2" + day.getTime());
					map.put("marketPrice", String.valueOf(pd.getMarketPrice()));
					map.put("title", title);
					map.put("start", DateUtils.format(day, "yyyy-MM-dd"));
					list.add(map);
				}
				if (pd.getRebate() != null) {
					Map<String, String> map = new HashMap<String, String>();
					Date day = pd.getDay();
					String title = "c.成人C端" + df.format(pd.getRebate());
					map.put("id", "3" + day.getTime());
					map.put("rebate", String.valueOf(pd.getRebate()));
					map.put("title", title);
					map.put("start", DateUtils.format(day, "yyyy-MM-dd"));
					list.add(map);
				}
				if (pd.getChildPrice() != null) {	// {id:vid,childPrice:childPrice,title:'儿童价¥'+childPrice,start:LineUtil.dateToString(date,'yyyy-MM-dd')}
					Map<String, String> map = new HashMap<String, String>();
					Date day = pd.getDay();
                    String title = "d.儿童" + df.format(pd.getChildPrice());
					map.put("id", "4" + day.getTime());
					map.put("childPrice", String.valueOf(pd.getChildPrice()));
					map.put("title", title);
					map.put("start", DateUtils.format(day, "yyyy-MM-dd"));
					list.add(map);
				}

				if (pd.getChildMarketPrice() != null) {
					Map<String, String> map = new HashMap<String, String>();
					Date day = pd.getDay();
					String title = "e.儿童市价" + df.format(pd.getChildMarketPrice());
					map.put("id", "5" + day.getTime());
					map.put("childMarketPrice", String.valueOf(pd.getChildMarketPrice()));
					map.put("title", title);
					map.put("start", DateUtils.format(day, "yyyy-MM-dd"));
					list.add(map);
				}

				if (pd.getChildRebate() != null) {
					Map<String, String> map = new HashMap<String, String>();
					Date day = pd.getDay();
					String title = "f.儿童C端" + df.format(pd.getChildPrice());
					map.put("id", "6" + day.getTime());
					map.put("childRebate", String.valueOf(pd.getChildRebate()));
					map.put("title", title);
					map.put("start", DateUtils.format(day, "yyyy-MM-dd"));
					list.add(map);
				}

				if (pd.getOasiaHotel() != null) {	// {id:vid,childPrice:childPrice,title:'儿童价¥'+childPrice,start:LineUtil.dateToString(date,'yyyy-MM-dd')}
					Map<String, String> map = new HashMap<String, String>();
					Date day = pd.getDay();
					map.put("id", "7" + day.getTime());
					map.put("oasiaHotel", String.valueOf(pd.getOasiaHotel()));
					map.put("title", "g.单房差" + df.format(pd.getOasiaHotel()));
					map.put("start", DateUtils.format(day, "yyyy-MM-dd"));
					list.add(map);
				}
				if (pd.getInventory() != null) {	// {id:vid,childPrice:childPrice,title:'儿童价¥'+childPrice,start:LineUtil.dateToString(date,'yyyy-MM-dd')}
					Map<String, String> map = new HashMap<String, String>();
					Date day = pd.getDay();
					map.put("id", "8" + day.getTime());
					map.put("inventory", String.valueOf(pd.getInventory()));
					map.put("title", "h.库存" + String.valueOf(pd.getInventory()));
					map.put("start", DateUtils.format(day, "yyyy-MM-dd"));
					list.add(map);
				}
			}		
		}

		JSONArray json = JSONArray.fromObject(list);
		return json(json);
	}
	
}
