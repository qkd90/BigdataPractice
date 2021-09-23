package com.data.data.hmly.action.ticket;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.common.ProductimageService;
import com.data.data.hmly.service.common.entity.Product;
import com.data.data.hmly.service.common.entity.Productimage;
import com.data.data.hmly.service.common.util.UUIDUtil;
import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.ticket.TicketDatepriceService;
import com.data.data.hmly.service.ticket.TicketPriceService;
import com.data.data.hmly.service.ticket.TicketPriceTypeExtendService;
import com.data.data.hmly.service.ticket.TicketService;
import com.data.data.hmly.service.ticket.entity.Ticket;
import com.data.data.hmly.service.ticket.entity.TicketDateprice;
import com.data.data.hmly.service.ticket.entity.TicketPrice;
import com.data.data.hmly.service.ticket.entity.TicketPriceTypeExtend;
import com.data.data.hmly.service.ticket.entity.enmus.TicketPriceStatus;
import com.data.data.hmly.service.ticket.entity.enmus.TicketType;
import com.framework.hibernate.util.Page;
import com.framework.struts.AjaxCheck;
import com.google.common.collect.Lists;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.DateUtils;
import com.zuipin.util.JsonFilter;
import com.zuipin.util.NumberUtil;
import com.zuipin.util.QiniuUtil;
import com.zuipin.util.StringUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;
import org.hibernate.Hibernate;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TicketPriceAction extends FrameBaseAction {

	
	private TicketPrice ticketPrice;
	private TicketDateprice ticketDatePrice = new TicketDateprice();
	private Ticket ticket = new Ticket();
	@Resource
	private TicketService ticketService;
	
	@Resource
	private TicketPriceService priceService;

	@Resource
	private ProductimageService productimageService;

	@Resource
	private TicketDatepriceService datepriceService;

	@Resource
	private TicketPriceTypeExtendService typeExtendService;
	
	Map<String, Object> map = new HashMap<String, Object>();
	
	private Long ticketPriceId;
	private Long ticketId;

	private List<Productimage> productimages = new ArrayList<Productimage>();

	private Integer			page				= 1;
	private Integer			rows				= 10;
	
	
	private List<TickettypepriceForm> 	typePriceDate;

	public Result savePriceTypeImages() {
		List<Productimage> tempProimages = new ArrayList<Productimage>();

		if (ticketPriceId != null) {
			productimageService.doDelByTargetId(ticketPriceId);
		}

		if (!productimages.isEmpty()) {

			for (Productimage productimage : productimages) {
				productimage.setUserId(getLoginUser().getId());
				productimage.setCompanyUnitId(getCompanyUnit().getId());
				productimage.setCreateTime(new Date());
				tempProimages.add(productimage);
			}
			productimageService.saveAll(tempProimages);
		}
		simpleResult(map, true, "");
		return jsonResult(map);
	}

	public Result updatePriceTypeImages() {
		final HttpServletRequest request = getRequest();
		MultiPartRequestWrapper multiPartRequestWrapper = (MultiPartRequestWrapper) request;
		Map<String, Object> result = new HashMap<String, Object>();
		File file = null;
		String suffix = "";
		String name = "";
		String imgDesc = "";
		String section = "";
		String path = "";
		Integer width = 0;
		Integer height = 0;
		Integer fileQuenuLength = multiPartRequestWrapper.getFiles("file").length;
		if (fileQuenuLength != 1) {
			throw new IndexOutOfBoundsException("没有需要上传的文件或队列中文件个数太多! find: " + fileQuenuLength + "allowed: 1");
		} else {
			file = multiPartRequestWrapper.getFiles("file")[0];
		}
		if (com.zuipin.util.StringUtils.isNotBlank(multiPartRequestWrapper.getParameter("name"))) {
			name = multiPartRequestWrapper.getParameter("name");
			imgDesc = name.substring(0, name.lastIndexOf("."));
			suffix = name.substring(name.lastIndexOf("."));
		} else {
			throw new RuntimeException("图片缺少文件名, 无法存储! 请检查!");
		}
		if (com.zuipin.util.StringUtils.isBlank(suffix)) {
			throw new RuntimeException("图片缺少格式, 无法存储! 请检查!");
		}
		if (com.zuipin.util.StringUtils.isNotBlank(multiPartRequestWrapper.getParameter("section"))) {
			section = multiPartRequestWrapper.getParameter("section");
		} else {
			throw new RuntimeException("图片存储路径缺少类别名称, 无法存储, 请检查!");
		}
		String id = UUIDUtil.getUUID();
		path = section + id + suffix;
		try {
			BufferedImage bufferedImage = ImageIO.read(file);
			if (bufferedImage != null) {
				width = bufferedImage.getWidth();
				height = bufferedImage.getHeight();
			} else {
				throw new RuntimeException("无法读取图片! 需要重新上传!");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		QiniuUtil.upload(file, path);
		result.put("path", path);
		result.put("width", width);
		result.put("height", height);
		result.put("name", name);
		result.put("imgDesc", imgDesc);
		result.put("id", id);
		return json(JSONObject.fromObject(result));
	}

	public Result getPriceTypeImagesList() {



		Productimage productimage = new Productimage();

		Product product = new Product();
		productimage.setProduct(ticketPrice.getTicket());
		productimage.setTargetId(ticketPrice.getId());

		List<Productimage> productimages = productimageService.findProductimage(productimage, null);

		JsonConfig jsonConfig = JsonFilter.getFilterConfig("product");
		map.put("productImages", productimages);
		return datagrid(productimages, jsonConfig);
	}

	public Result priceTypeImagesPage() {

		if (ticketId != null) {
			ticket = ticketService.loadTicket(ticketId);
		}
		if (ticketPriceId != null) {
			ticketPriceId = ticketPriceId;
		}
		return dispatch("/WEB-INF/jsp/ticket/sailboat/priceTypeImagesPage.jsp");
	}

	public Result getPriceTypeExtendList() {

		List<TicketPriceTypeExtend> knowOrderList = Lists.newArrayList();
		List<TicketPriceTypeExtend> feeDescList = Lists.newArrayList();
		List<TicketPriceTypeExtend> refundDescList = Lists.newArrayList();

		if (ticketPriceId != null) {
			ticketPrice = priceService.getPrice(ticketPriceId);
			List<TicketPriceTypeExtend> ticketPriceTypeExtends = ticketPrice.getTicketPriceTypeExtends();

			for (TicketPriceTypeExtend extend : ticketPriceTypeExtends) {
				extend.setTicketPrice(null);
				if ("预订须知".equals(extend.getFirstTitle())) {
					knowOrderList.add(extend);
				}
				if ("费用说明".equals(extend.getFirstTitle())) {
					feeDescList.add(extend);
				}
				if ("退款说明".equals(extend.getFirstTitle())) {
					refundDescList.add(extend);
				}
			}
		}
		map.put("knowOrderList", knowOrderList);
		map.put("feeDescList", feeDescList);
		map.put("refundDescList", refundDescList);
		JsonConfig jsonConfig = JsonFilter.getFilterConfig("ticketPrice");
		return json(JSONObject.fromObject(map), jsonConfig);
	}

	@AjaxCheck
	public Result listHotelPriceForLine() {
		// 参数
		String scenicIdStr = (String) getParameter("scenicId");
		String ticketNameStr = (String) getParameter("ticketName");
		String linedaysIdStr = (String) getParameter("linedaysId");
		TicketPrice ticketPrice = new TicketPrice();
		ticketPrice.setLinedaysId(Long.valueOf(linedaysIdStr));
		if (StringUtils.isNotBlank(scenicIdStr)) {
			ticketPrice.setScenicId(Long.valueOf(scenicIdStr));
		}
		if (StringUtils.isNotBlank(ticketNameStr)) {
			ticketPrice.setTicketName(ticketNameStr);
		}

		Page pageInfo = new Page(page, rows);
		List<TicketPrice> list = priceService.listTicketPriceForLine(ticketPrice, pageInfo, getLoginUser(), isSiteAdmin(), isSupperAdmin());

		JsonConfig jsonConfig = JsonFilter.getIncludeConfig();
		return datagrid(list, pageInfo.getTotalCount(), jsonConfig);
	}


	public Result getDatePricelist() {
		
		Page pageInfo = new Page(page, rows);
		String prop = (String) getParameter("prop");
		Date[] dates = DateUtils.getRangeDate();
		
		String agent = (String) getParameter("agent");
		
		if (ticketId != null) {
			ticket = ticketService.findTicketById(ticketId);
		}
		List<TicketPrice> ticketPrices = new ArrayList<TicketPrice>();
		List<TicketPrice> topTicketPrices = new ArrayList<TicketPrice>();
		if ("T".equals(agent)) {
			ticketPrices = priceService.findTicketList(ticketId, pageInfo);
			topTicketPrices = priceService.findTicketList(ticket.getTopProduct().getId(), pageInfo);
			for (TicketPrice topT:topTicketPrices) {
				ticketPrices.add(topT);
			}
			
		} else {
			ticketPrices = priceService.findTicketList(ticketId, pageInfo);
		}
		
		
//		List<TicketMinData> minDateprices = priceService.findMinPrice(ticketPrices, dates[0], dates[1], prop);
//		List<TicketMinPrice> ticketMinPrices = new ArrayList<TicketMinPrice>();
//
//		for (TicketPrice tp : ticketPrices) {
//
//			TicketMinPrice minPrice = new TicketMinPrice();
//			minPrice.setTicketId(tp.getTicket().getId());
//			minPrice.setId(tp.getId());
//			minPrice.setName(tp.getName());
//			minPrice.setType(tp.getType());
//			minPrice.setStatus(tp.getStatus());
//			for (TicketMinData td : minDateprices) {
//				if (td != null) {
//					if (td.getId() == tp.getId()) {
//						minPrice.setDiscountPrice(td.getMinPriPrice());
//						minPrice.setRebate(td.getMinRebate());
//					}
//				} else {
//					minPrice.setDiscountPrice(tp.getDiscountPrice());
//					minPrice.setRebate(tp.getCommission());
//				}
//			}
//			ticketMinPrices.add(minPrice);
//		}

		JsonConfig jsonConfig = JsonFilter.getIncludeConfig("ticket");

		return datagrid(ticketPrices, pageInfo.getTotalCount(), jsonConfig);
		
}
	
	
	public Result delPrice() {
		
		String priceIdStr = (String) getParameter("priceId");
		
		if (priceIdStr != null) {
			priceService.delPrice(priceIdStr);
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		
		simpleResult(map, true, "");
		return jsonResult(map);
	}
	
	public Result  edit() {
		String dataJsonStr = (String) getParameter("dataJson");
		List<Float> minPriceList = new ArrayList<Float>();
		List<Float> minRebateList = new ArrayList<Float>();
		List<Float> minMarketPriceList = new ArrayList<Float>();

		if (ticketPrice.getId() == null) {
            Ticket ticket = ticketPrice.getTicket();
            if (ticket.getId() != null) {
                ticket = ticketService.findFullById(ticket.getId());
                Hibernate.initialize(ticket);
                ticketPrice.setTicket(ticket);
            }
			ticketPrice.setScore(NumberUtil.getRandomScore());
            ticketPrice.setAddTime(new Date());
			ticketPrice.setModifyTime(new Date());
            User user = getLoginUser();
            ticketPrice.setUserid(user.getId());
            ticketPrice.setStatus(TicketPriceStatus.UP);

            priceService.save(ticketPrice);


            List<TicketDateprice> dateprices = new ArrayList<TicketDateprice>();


			dateprices = (List<TicketDateprice>) getTicketDatePriceFormJsonObject(dataJsonStr, ticketPrice).get("datePriceList");
			for (TicketDateprice dateprice : dateprices) {
				minRebateList.add(dateprice.getPrice());
				minPriceList.add(dateprice.getPriPrice());
				minMarketPriceList.add(dateprice.getMaketPrice());
			}

			ticketPrice.setDiscountPrice((Float) getTicketDatePriceFormJsonObject(dataJsonStr, ticketPrice).get("minPriPrice"));
			ticketPrice.setMaketPrice((Float) getTicketDatePriceFormJsonObject(dataJsonStr, ticketPrice).get("minMarketPrice"));
			ticketPrice.setPrice((Float) getTicketDatePriceFormJsonObject(dataJsonStr, ticketPrice).get("minPrice"));

            datepriceService.save(dateprices, ticketPrice);

			if (ticket.getTicketType() == TicketType.sailboat || ticket.getTicketType() == TicketType.yacht) {
				List<TicketPriceTypeExtend> tempTicketPriceTypeList = Lists.newArrayList();

				for (TicketPriceTypeExtend extend : ticketPrice.getTicketPriceTypeExtends()) {
					extend.setTicketPrice(ticketPrice);
					extend.setCreateTime(new Date());
					tempTicketPriceTypeList.add(extend);
				}

				typeExtendService.saveAll(tempTicketPriceTypeList);
			}

        } else {

			TicketPrice tPrice = priceService.findFullById(ticketPrice.getId());
			typeExtendService.delExtendByPriceTypeId(tPrice.getId());

            Ticket ticket = tPrice.getTicket();
            Hibernate.initialize(ticket);
            ticketPrice.setTicket(ticket);
			tPrice.setGetTicket(ticketPrice.getGetTicket());
			if (ticketPrice.getDiscountPrice() != null) {
				tPrice.setDiscountPrice(ticketPrice.getDiscountPrice());
			}
			if (ticketPrice.getCommission() != null) {
				tPrice.setCommission(ticketPrice.getCommission());
			}

			if (tPrice.getScore() == null || tPrice.getScore() == 0) {
				tPrice.setScore(NumberUtil.getRandomScore());
			}
			tPrice.setIsConditionRefund(ticketPrice.getIsConditionRefund());
			tPrice.setIsTodayValid(ticketPrice.getIsTodayValid());
			tPrice.setName(ticketPrice.getName());
			tPrice.setOrderKnow(ticketPrice.getOrderKnow());
			tPrice.setType(ticketPrice.getType());

			datepriceService.delAllByprice(tPrice);
			List<TicketDateprice> dateprices = new ArrayList<TicketDateprice>();
			dateprices = (List<TicketDateprice>) getTicketDatePriceFormJsonObject(dataJsonStr, ticketPrice).get("datePriceList");
			for (TicketDateprice dateprice : dateprices) {
				minRebateList.add(dateprice.getPrice());
				minPriceList.add(dateprice.getPriPrice());
				minMarketPriceList.add(dateprice.getMaketPrice());
			}
			tPrice.setDiscountPrice((Float) getTicketDatePriceFormJsonObject(dataJsonStr, ticketPrice).get("minPriPrice"));
			tPrice.setMaketPrice((Float) getTicketDatePriceFormJsonObject(dataJsonStr, ticketPrice).get("minMarketPrice"));
			tPrice.setPrice((Float) getTicketDatePriceFormJsonObject(dataJsonStr, ticketPrice).get("minPrice"));



			if (ticket.getTicketType() == TicketType.sailboat || ticket.getTicketType() == TicketType.yacht) {
				List<TicketPriceTypeExtend> tempTicketPriceTypeList = Lists.newArrayList();
				for (TicketPriceTypeExtend extend : ticketPrice.getTicketPriceTypeExtends()) {
					extend.setCreateTime(new Date());
					tempTicketPriceTypeList.add(extend);
				}
				typeExtendService.saveAll(tempTicketPriceTypeList);
			}
			tPrice.setModifyTime(new Date());
			datepriceService.save(dateprices, tPrice);
		}

		if (ticketPrice.getId() == null) {
			simpleResult(map, false, "");
			return jsonResult(map);
		}
		map.put("ticketId", ticketPrice.getTicket().getId());
		map.put("ticketPriceId", ticketPrice.getId());
		simpleResult(map, true, "");
		return jsonResult(map);
	}

	public Map<String, Object> getTicketDatePriceFormJsonObject(String dataJsonStr, TicketPrice ticketPrice) {

		Map<String, Object> tempMap = new HashMap<String, Object>();

		List<Float> minPriceList = new ArrayList<Float>();
		List<Float> minRebateList = new ArrayList<Float>();
		List<Float> minMarketPriceList = new ArrayList<Float>();

		List<TicketDateprice> dateprices = new ArrayList<TicketDateprice>();
		if (StringUtils.isNotBlank(dataJsonStr)) {
			JSONArray jsonArray = JSONArray.fromObject(dataJsonStr);
			for (Object o : jsonArray) {
				JSONObject object = JSONObject.fromObject(o);
				TicketDateprice dateprice = new TicketDateprice();

				Float price = 0f;
				Float priPrice = 0f;
				Float marketPrice = 0f;
				if (object.get("price") != null) {
					price = Float.parseFloat(object.get("price").toString());
				}
				if (object.get("priPrice") != null) {
					priPrice = Float.parseFloat(object.get("priPrice").toString());
				}
				if (object.get("inventory") != null) {
					Integer inventory = Integer.parseInt(object.get("inventory").toString());
					dateprice.setInventory(inventory);
				}
				if (object.get("marketPrice") != null) {
					marketPrice =  Float.parseFloat(object.get("marketPrice").toString());
				}
				dateprice.setPrice(price);
				minRebateList.add(price);
				dateprice.setPriPrice(priPrice);
				minPriceList.add(priPrice);
				dateprice.setMaketPrice(marketPrice);
				minMarketPriceList.add(marketPrice);
				dateprice.setPrice(priPrice);
				dateprice.setHuiDate(DateUtils.toDate(object.get("huiDate").toString()));
				dateprice.setTicketPriceId(ticketPrice);
				dateprices.add(dateprice);
			}
		}

		if (minRebateList.isEmpty()) {
			tempMap.put("minPrice", 0f);
		} else {
			tempMap.put("minPrice", Collections.min(minRebateList));
		}

		if (minPriceList.isEmpty()) {
			tempMap.put("minPriPrice", 0f);
		} else {
			tempMap.put("minPriPrice", Collections.min(minPriceList));
		}

		if (minMarketPriceList.isEmpty()) {
			tempMap.put("minMarketPrice", 0f);
		} else {
			tempMap.put("minMarketPrice", Collections.min(minMarketPriceList));
		}
		tempMap.put("datePriceList", dateprices);

		return tempMap;

	}

	public Result isHasPrice() {
		
		String tId = (String) getParameter("ticId");
		int counts = 0;
		if (tId != null) {
			Long ticketId = Long.parseLong(tId);
			counts = priceService.getCountsByTid(ticketId);
		}
		
		map.clear();
		
		if (counts > 0 ) {
			map.put("tId", ticket.getId());
			simpleResult(map, false, "");
		} else {
			map.put("tId", null);
			simpleResult(map, true, "");
		}
		
		return jsonResult(map);
	}
	
	

	public Long getTicketPriceId() {
		return ticketPriceId;
	}

	public void setTicketPriceId(Long ticketPriceId) {
		this.ticketPriceId = ticketPriceId;
	}

	public Long getTicketId() {
		return ticketId;
	}

	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}


	public List<TickettypepriceForm> getTypePriceDate() {
		return typePriceDate;
	}


	public void setTypePriceDate(List<TickettypepriceForm> typePriceDate) {
		this.typePriceDate = typePriceDate;
	}


	public TicketPrice getTicketPrice() {
		return ticketPrice;
	}

	public void setTicketPrice(TicketPrice ticketPrice) {
		this.ticketPrice = ticketPrice;
	}

	public TicketDateprice getTicketDatePrice() {
		return ticketDatePrice;
	}

	public void setTicketDatePrice(TicketDateprice ticketDatePrice) {
		this.ticketDatePrice = ticketDatePrice;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	public List<Productimage> getProductimages() {
		return productimages;
	}

	public void setProductimages(List<Productimage> productimages) {
		this.productimages = productimages;
	}
}
