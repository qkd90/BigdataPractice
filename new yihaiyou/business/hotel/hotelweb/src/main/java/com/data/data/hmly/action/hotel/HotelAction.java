package com.data.data.hmly.action.hotel;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.action.hotel.vo.HotelPriceCalendarVo;
import com.data.data.hmly.enums.TargetType;
import com.data.data.hmly.service.LabelItemService;
import com.data.data.hmly.service.LabelService;
import com.data.data.hmly.service.TbAreaService;
import com.data.data.hmly.service.common.ProductimageService;
import com.data.data.hmly.service.common.entity.Productimage;
import com.data.data.hmly.service.common.entity.enums.ProductSource;
import com.data.data.hmly.service.common.entity.enums.ProductStatus;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.common.entity.enums.ShowStatus;
import com.data.data.hmly.service.entity.Label;
import com.data.data.hmly.service.entity.LabelItem;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.hotel.HotelAmenitiesService;
import com.data.data.hmly.service.hotel.HotelExtendService;
import com.data.data.hmly.service.hotel.HotelPriceService;
import com.data.data.hmly.service.hotel.HotelRoomService;
import com.data.data.hmly.service.hotel.HotelService;
import com.data.data.hmly.service.hotel.entity.Hotel;
import com.data.data.hmly.service.hotel.entity.HotelExtend;
import com.data.data.hmly.service.hotel.entity.HotelPrice;
import com.data.data.hmly.service.hotel.entity.HotelPriceCalendar;
import com.data.data.hmly.service.hotel.entity.enums.PriceStatus;
import com.framework.hibernate.util.Page;
import com.framework.struts.AjaxCheck;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.BeanUtils;
import com.zuipin.util.DateUtils;
import com.zuipin.util.JsonFilter;
import com.zuipin.util.PropertiesManager;
import com.zuipin.util.QiniuUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 线路
 * @author caiys
 * @date 2015年10月13日 下午4:12:38
 */
public class HotelAction extends FrameBaseAction  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4395061833017783442L;
	@Resource
	private TbAreaService 				tbAreaService;
	@Resource
	private HotelService hotelService;

	@Resource
	private HotelExtendService hotelExtendService;

	@Resource
	private HotelPriceService hotelPriceService;

	@Resource
	private HotelRoomService hotelRoomService;

	@Resource
	private HotelAmenitiesService amenitiesService;

	@Resource
	private LabelItemService labelItemService;
	@Resource
	private LabelService labelService;
	@Resource
	private ProductimageService productimageService;
	@Resource
	private PropertiesManager propertiesManager;



	private Integer			page				= 1;
	private Integer			rows				= 10;
	private Map<String, Object> map = new HashMap<String, Object>();
	private List<Hotel> hotelList = new ArrayList<Hotel>();
	private List<Long> ids = new ArrayList<Long>();

	private Hotel hotel = new Hotel();
	private HotelPrice hotelPrice = new HotelPrice();
	private TbArea area = new TbArea();


	private File resource;
	private String resourceFileName;
	private String resourceContentType;	// image/jpeg
	private String oldFilePath;	// 旧图片路径
	private String folder;		// 图片目录
	private String dateStartStr;
	private String dateEndStr;
	private String productId;
	private String winIndex; // 面板索引
	private Long typePriceId;
	private Integer showOrder;
	private String sort;
	private String order;


	private String images;

	private List<Productimage> productimages = new ArrayList<Productimage>();
	private List<HotelPriceCalendarVo> priceCalendarVos = new ArrayList<HotelPriceCalendarVo>();




	public Result saveTypePriceDate() {
		if (typePriceId != null) {
			HotelPrice sourceHotelPrice = hotelPriceService.get(typePriceId);
			hotel = sourceHotelPrice.getHotel();
			List<HotelPriceCalendar> priceCalendarList = new ArrayList<HotelPriceCalendar>();
			for (HotelPriceCalendarVo priceCalendarVo : priceCalendarVos) {
				HotelPriceCalendar priceCalendar = new HotelPriceCalendar();
				priceCalendar.setHotelPrice(sourceHotelPrice);
				priceCalendar.setHotelId(hotel.getId());
				priceCalendar.setDate(DateUtils.getDate(priceCalendarVo.getDate(), "yyyy-MM-dd"));
				priceCalendar.setMember(priceCalendarVo.getMember());
				priceCalendar.setCost(priceCalendarVo.getCost());
				priceCalendar.setInventory(priceCalendarVo.getInventory());
				priceCalendar.setStatus(true);
				priceCalendar.setCreateTime(new Date());
				priceCalendarList.add(priceCalendar);
			}
			// delete old price calendar data
			hotelPriceService.delPriceDate(sourceHotelPrice);
			// save new price calendar data
			hotelPriceService.saveCalendarList(priceCalendarList);
			// update hotel price info
			Date start = hotelPriceService.getMinDateByPrice(sourceHotelPrice);
			Date end = hotelPriceService.getMaxDateByPrice(sourceHotelPrice);
			Float price = hotelPriceService.findMinPriceByType(hotel.getId(), sourceHotelPrice, start);
			sourceHotelPrice.setStart(start);
			sourceHotelPrice.setEnd(end);
			sourceHotelPrice.setPrice(price);
			sourceHotelPrice.setModifyTime(new Date());
			hotelPriceService.update(sourceHotelPrice);
			hotelService.indexHotel(sourceHotelPrice.getHotel());
			simpleResult(map, true, "");
		} else {
			simpleResult(map, false, "操作失败");
		}

		return json(JSONObject.fromObject(map));
	}


	public Result viewPrcieCalendar() {
		typePriceId = typePriceId;
		return dispatch("/WEB-INF/jsp/hotel/hotel/viewPriceCalendar.jsp");
	}


	/**
	 * 撤销房型
	 * @return
	 */
	public Result doHotelPriceInfoReturn() {

		if (typePriceId == null) {
			simpleResult(map, false, "");
			return json(JSONObject.fromObject(map));
		}
		hotelPrice = hotelPriceService.get(typePriceId);

		HotelPrice sourceHotelPrice = hotelPriceService.get(hotelPrice.getOriginId());

		// update origin data
		sourceHotelPrice.setShowStatus(ShowStatus.SHOW);
		sourceHotelPrice.setStatus(PriceStatus.DOWN);
		hotelPriceService.update(sourceHotelPrice);
		// handle product image
		// delete UP_CHECKING image
		productimageService.delImages(hotelPrice.getHotel().getId(), hotelPrice.getId(), "hotel/hotelRoom/");
		hotelRoomService.doDelHotelRoomNum(hotelPrice.getHotel().getId(), hotelPrice.getId());
		hotelPriceService.delete(hotelPrice);
		simpleResult(map, true, "");
		return jsonResult(map);
	}


	/**
	 * 删除房型
	 * @return
	 */
	public Result doHotelPriceDel() {

		if (typePriceId == null) {
			simpleResult(map, false, "");
			return json(JSONObject.fromObject(map));
		}
		hotelPrice = hotelPriceService.get(typePriceId);
		hotelPrice.setShowStatus(ShowStatus.HIDE_FOR_CHECK);
		hotelPrice.setStatus(PriceStatus.DEL);
		hotelPriceService.update(hotelPrice);
		simpleResult(map, true, "");
		return jsonResult(map);
	}

	public Result doHotelInfoReturn() {

		if (!com.zuipin.util.StringUtils.isNotBlank(productId)) {
			simpleResult(map, false, "");
			return json(JSONObject.fromObject(map));
		}
		hotel = hotelService.get(Long.parseLong(productId));
		HotelExtend tempExtend = hotel.getExtend();
		hotelExtendService.delete(tempExtend);
		hotelService.delete(hotel);
		// update origin data
		Hotel sourceHotel = hotelService.get(hotel.getOriginId());
		sourceHotel.setShowStatus(ShowStatus.SHOW);
		sourceHotel.setStatus(ProductStatus.DOWN);
		hotelService.update(sourceHotel);
		// handle product image
		// delete UP_CHECKING image
		productimageService.delImages(hotel.getId(), hotel.getId(), "hotel/hotelDesc/");
		simpleResult(map, true, "");
		return jsonResult(map);
	}

	public Result doHotelInfoDel() {

		if (!com.zuipin.util.StringUtils.isNotBlank(productId)) {
			simpleResult(map, false, "");
			return json(JSONObject.fromObject(map));
		}
		hotel = hotelService.get(Long.parseLong(productId));
		hotel.setShowStatus(ShowStatus.HIDE_FOR_CHECK);
		hotel.setStatus(ProductStatus.DEL);
		hotelService.update(hotel);
		hotelPriceService.deleteTempHotelPriceList(hotel);	//删除所有房型临时数据
		hotelPriceService.doHandleHotelPriceAll(hotel, PriceStatus.DEL);	//删除所有的房型
		simpleResult(map, true, "");
		return jsonResult(map);
	}


	/**
	 * 平台编辑民宿
	 * @return
	 */
	public Result saveCheckHotelInfo() {
		if (hotel.getId() != null) {
			Hotel oldHotel = hotelService.get(hotel.getId());
			HotelExtend hotelExtend = hotelExtendService.getByHotel(hotel.getId());

			BeanUtils.copyProperties(hotel, oldHotel, false, null, "id", "hotelPriceList", "labelItems", "productimage", "commentList");
			BeanUtils.copyProperties(hotel.getExtend(), hotelExtend, false, null, "id");


			hotelService.update(oldHotel);
			hotelExtendService.update(hotelExtend);
			productimageService.delImages(hotel.getId(), hotel.getId(), "hotel/hotelDesc/");	//删除民宿图片
			for (Productimage productimage : productimages) {
				productimage.setId(null);
				productimage.setProduct(hotel);
				productimage.setTargetId(hotel.getId());
				productimage.setCreateTime(new Date());
				productimage.setCompanyUnitId(oldHotel.getCompanyUnit().getId());
			}
			productimageService.saveAll(productimages);
			simpleResult(map, true, "");
		} else {
			simpleResult(map, false, "");
		}
		return json(JSONObject.fromObject(map));
	}

	public Result saveCheckHotelPriceInfo() {
		if (hotelPrice.getId() != null) {

			String roomNum = hotelPrice.getRoomNum();

			HotelPrice oldHotelPrice = hotelPriceService.get(hotelPrice.getId());

			hotel = hotelService.get(oldHotelPrice.getHotel().getId());

			BeanUtils.copyProperties(hotelPrice, oldHotelPrice, false, null, "id");

			oldHotelPrice.setModifyTime(new Date());
			// update temp hotel price
			hotelPriceService.update(oldHotelPrice);

			hotelRoomService.doDelHotelRoomNum(oldHotelPrice);	//删除房型原始数据对应的房间号
//			if (com.zuipin.util.StringUtils.isNotBlank(roomNum)) {
				// handle room numbers
			hotelRoomService.doSaveHotelRooms(roomNum.split(","), oldHotelPrice);	//重新新增房间号
//			}
			productimageService.delImages(hotelPrice.getHotel().getId(), hotelPrice.getId(), "hotel/hotelRoom/");	//删除民宿图片
			for (Productimage productimage : productimages) {
				productimage.setProduct(hotelPrice.getHotel());
				productimage.setTargetId(hotelPrice.getId());
				productimage.setCreateTime(new Date());
				productimage.setCompanyUnitId(hotel.getCompanyUnit().getId());
				productimageService.saveProductimage(productimage);
			}
			simpleResult(map, true, "");
		} else {
			simpleResult(map, false, "");
		}
		return json(JSONObject.fromObject(map));
	}


	@AjaxCheck
	public Result checkingSearch() throws ParseException {

		String qryStartTime = (String) getParameter("qryStartTime");

		String qryEndTime = (String) getParameter("qryEndTime");

		Page pageInfo = new Page(page, rows);
		if (com.zuipin.util.StringUtils.isNotBlank(qryStartTime)) {
			hotel.setQryStartTime(DateUtils.parseShortTime(qryStartTime));
		}
		if (com.zuipin.util.StringUtils.isNotBlank(qryEndTime)) {
			hotel.setQryEndTime(DateUtils.parseShortTime(qryEndTime));
		}
		List<Hotel> cruiseShips = hotelService.getCheckingList(hotel, getLoginUser(), pageInfo, isSiteAdmin());
		JsonConfig jsonConfig = JsonFilter.getIncludeConfig();
		return datagrid(cruiseShips, pageInfo.getTotalCount(), jsonConfig);
	}


	public Result checkingManage() {
		return dispatch();
	}


	public Result getDatePriceList() {

		Date date = DateUtils.getStartDay(new Date(), 0);

		List<HotelPriceCalendar> calendars = new ArrayList<HotelPriceCalendar>();

		if (typePriceId != null) {
			calendars = hotelPriceService.getCalendarList(typePriceId, date);
		}
		String[] includeConfig = new String[]{};
		JsonConfig jsonConfig = JsonFilter.getIncludeConfig(null, includeConfig);
		JSONArray jsonArray = JSONArray.fromObject(calendars, jsonConfig);
		return json(jsonArray);
	}

	public Result selectDatePrice() {
		if (typePriceId != null) {
			typePriceId = typePriceId;
		}
		return dispatch();
	}


	public Result viewDetail() {
		if (StringUtils.isNotBlank(productId)) {
			hotel = hotelService.get(Long.parseLong(productId));
			area = tbAreaService.getArea(hotel.getCityId());
			if (hotel.getExtend() != null && hotel.getExtend().getAddress() != null) {
				hotel.getExtend().setAddress(area.getFullPath() + "  " + hotel.getExtend().getAddress());
			}

			String theme = hotel.getTheme();

			if (StringUtils.isNotBlank(theme)) {
				theme = amenitiesService.getAmenities(amenitiesService.getListByIds(theme));
				hotel.setTheme(theme);
			}
			String facilities = hotel.getFacilities();
			if (StringUtils.isNotBlank(facilities)) {
				facilities = amenitiesService.getAmenities(amenitiesService.getListByIds(facilities));
				hotel.setFacilities(facilities);
			}
			String generalAmenities = hotel.getGeneralAmenities();
			if (StringUtils.isNotBlank(generalAmenities)) {
				generalAmenities = amenitiesService.getAmenities(amenitiesService.getListByIds(generalAmenities));
				hotel.setGeneralAmenities(generalAmenities);
			}

			String recreationAmenities = hotel.getRecreationAmenities();
			if (StringUtils.isNotBlank(recreationAmenities)) {
				recreationAmenities = amenitiesService.getAmenities(amenitiesService.getListByIds(recreationAmenities));
				hotel.setRecreationAmenities(recreationAmenities);
			}

			String serviceAmenities = hotel.getServiceAmenities();
			if (StringUtils.isNotBlank(serviceAmenities)) {
				serviceAmenities = amenitiesService.getAmenities(amenitiesService.getListByIds(serviceAmenities));
				hotel.setServiceAmenities(serviceAmenities);
			}
			productimages = productimageService.findImagesByProductId(hotel.getId());

			for (Productimage productimage : productimages) {

				String path = productimage.getPath();

				String folder = path.substring(0, path.lastIndexOf("/"));

				if ("/hotel/hotelDesc".equals(folder)) {
					path = path.substring(1, path.length());
					path = QiniuUtil.URL + path;
					productimage.setPath(path);
				}
			}

		}
		return dispatch();
	}

	public Result quickPub() {

		String idStr = (String) getParameter("idStrs");
		if (StringUtils.isNotBlank(idStr)) {
			hotelService.doChangeStatus(fmtIds(idStr), ProductStatus.UP);
			simpleResult(map, true, "");
		} else {
			simpleResult(map, false, "");
		}
		return jsonResult(map);
	}

	public Result doSetHide() {
		Hotel tempHotel = hotelService.get(hotel.getId());
		if (hotel.getStatus() != null) {
			tempHotel.setStatus(hotel.getStatus());
		}
		tempHotel.setRepeatFlag(hotel.getRepeatFlag());
		hotelService.update(tempHotel);
//			hotelService.doChangeStatus(fmtIds(idStr), ProductStatus.DOWN);
//			hotelService.delLabelItems(fmtIds(idStr));
			simpleResult(map, true, "");
		return jsonResult(map);

	}

	public Result doDel() {

		String idStr = (String) getParameter("idStrs");
		if (StringUtils.isNotBlank(idStr)) {
			hotelService.doChangeStatus(fmtIds(idStr), ProductStatus.DEL);
			hotelService.delLabelItems(fmtIds(idStr));
			simpleResult(map, true, "");
		} else {
			simpleResult(map, false, "");
		}
		return jsonResult(map);
	}

	public List<Long> fmtIds(String idStr) {

		String[] idStrs = idStr.split(",");

		for (String id : idStrs) {
			ids.add(Long.parseLong(id));
		}
		return ids;
	}

	public  Result saveHotelImages() {

		String imageUrl = (String) getParameter("imageUrl");

		List<Productimage> productimages = new ArrayList<Productimage>();

		if (StringUtils.isBlank(productId)) {
			simpleResult(map, false, "获取订单失败！");
			return jsonResult(map);

		}

		hotel = hotelService.get(Long.parseLong(productId));

		if (StringUtils.isBlank(imageUrl)) {
			simpleResult(map, false, "图片相册获取失败！");
			return jsonResult(map);
		}

		if (StringUtils.isNotBlank(imageUrl)) {
			String[] urls = imageUrl.split(",");
			for (String str : urls) {
				Productimage productimage = new Productimage();
				str = str.substring(str.indexOf(QiniuUtil.URL) + 1, str.length());
				productimage.setPath(str);
				if (folder != null) {
					productimage.setChildFolder(folder);
				}
				productimage.setProType(ProductType.hotel);
				productimage.setCoverFlag(false);
				productimage.setUserId(getLoginUser().getId());
				productimage.setCompanyUnitId(getLoginUser().getSysUnit().getCompanyUnit().getId());
				productimage.setCreateTime(new Date());
				productimages.add(productimage);
			}
		}
		productimageService.delProImages(hotel.getId());
		productimageService.saveAll(productimages);
		simpleResult(map, true, "");
		return jsonResult(map);
	}

	public Result doSubChecking() {
		String idStr = (String) getParameter("idStrs");
		if (StringUtils.isNotBlank(idStr)) {
			hotelService.doChangeStatus(fmtIds(idStr), ProductStatus.UP_CHECKING);
			simpleResult(map, true, "");
		} else {
			simpleResult(map, false, "");
		}
		return jsonResult(map);
	}


	public Result saveHotel() {
		String imageUrl = (String) getParameter("imageUrls");
		List<Productimage> productimages = new ArrayList<Productimage>();
		if (hotel.getId() == null) {
			hotel.setTopProduct(hotel); // 默认原线路是自己
			hotel.setStatus(ProductStatus.DOWN);
			hotel.setProType(ProductType.hotel);
			hotel.setScore(radomScore().floatValue());
			hotel.setUser(getLoginUser());
			hotel.setSupplier(getLoginUser());
			hotel.setCompanyUnit(getCompanyUnit());
			hotel.setCreateTime(new Date());
			hotel.setUpdateTime(new Date());
			if (hotel.getBrandId() == null) {
				hotel.setBrandId(0);
			}
			if (hotel.getGroupId() == null) {
				hotel.setGroupId(0);
			}
			hotel.setTopProduct(hotel);
			hotel.setUser(getLoginUser());
			hotel.setUpdateTime(new Date());
			hotelService.saveHotel(hotel);
		} else {
			Hotel newHotel = hotelService.get(hotel.getId());
			HotelExtend hotelExtend = hotelExtendService.getByHotel(newHotel.getId());

			hotelExtend.setAddress(hotel.getExtend().getAddress());
			hotelExtend.setDescription(hotel.getExtend().getDescription());
			hotelExtend.setLatitude(hotel.getExtend().getLatitude());
			hotelExtend.setLongitude(hotel.getExtend().getLongitude());
			newHotel.setExtend(hotelExtend);
			newHotel.setName(hotel.getName());
			newHotel.setStatus(ProductStatus.DOWN);
			newHotel.setSupplier(getLoginUser());
			newHotel.setStar(hotel.getStar());
			newHotel.setNeedConfirm(hotel.getNeedConfirm());
			newHotel.setCover(hotel.getCover());
			newHotel.setFacilities(hotel.getFacilities());
			newHotel.setGeneralAmenities(hotel.getGeneralAmenities());
			newHotel.setRanking(hotel.getRanking());
			newHotel.setRecreationAmenities(hotel.getRecreationAmenities());
			newHotel.setServiceAmenities(hotel.getServiceAmenities());
			newHotel.setTheme(hotel.getTheme());
			newHotel.setTopProduct(newHotel);
			newHotel.setShortDesc(hotel.getShortDesc());
			newHotel.setProvienceId(hotel.getProvienceId());
			newHotel.setCityId(hotel.getCityId());
			newHotel.setOrderConfirm(hotel.getOrderConfirm());
			newHotel.setUpdateTime(new Date());

			hotelService.updateHotel(newHotel);
		}

		if (StringUtils.isNotBlank(imageUrl)) {
			String[] urls = imageUrl.split(",");
			for (String str : urls) {
				Productimage productimage = new Productimage();
				int urlLength = QiniuUtil.URL.length();
				str = str.substring(urlLength - 1, str.length());
				productimage.setPath(str);
				productimage.setProduct(hotel);
				productimage.setProType(ProductType.hotel);
				productimage.setCoverFlag(false);
				productimage.setUserId(getLoginUser().getId());
				productimage.setCompanyUnitId(getLoginUser().getSysUnit().getCompanyUnit().getId());
				productimage.setCreateTime(new Date());
				productimages.add(productimage);
			}
		}

		productimageService.delProImages(hotel.getId());
		productimageService.saveAll(productimages);

		if (hotel.getId() != null) {
			map.put("id", hotel.getId());
			simpleResult(map, true, "保存成功！");
		} else {
			simpleResult(map, false, "保存失败！");
		}
		return jsonResult(map);
	}

	public Integer radomScore() {
		Random r = new Random();
		return r.nextInt(100);

	}

	public Result addStep1() {
		return dispatch();
	}

	public Result addStep2() {
		// 起始时间 - 当前时间第二天
		Date dateStart = DateUtils.add(new Date(), Calendar.DAY_OF_MONTH, 1);
		dateStartStr = DateUtils.format(dateStart, "yyyy-MM-dd");
		// 结束时间 - 当前时间次月最后一天
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateStart);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 6);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);
		dateEndStr = DateUtils.format(calendar.getTime(), "yyyy-MM-dd");

		if (StringUtils.isNotBlank(productId)) {
			hotel = hotelService.get(Long.parseLong(productId));
			hotelPrice.setHotel(hotel);
		}
		if (typePriceId != null) {
			hotelPrice = hotelPriceService.get(typePriceId);
		}
		return dispatch();
	}

	public Result addStep3() {

		if (StringUtils.isNotBlank(productId)) {
			hotel = hotelService.get(Long.parseLong(productId));

			Float minPrice = hotelPriceService.findHotelMinPrice(hotel);

			if (minPrice != null && minPrice > 0) {
				hotel.setPrice(minPrice);
			}
			hotel.setStatus(ProductStatus.DOWN);
			hotelService.updateHotel(hotel);
		}

		return dispatch();
	}

	public Result addStep21() {
		if (StringUtils.isNotBlank(productId)) {
			hotel = hotelService.get(Long.parseLong(productId));
		}
		return dispatch();
	}

	public Result addWizard() {
		return dispatch();
	}

	public Result editWizard() {
		winIndex = (String) getParameter("winIndex");
		productId = (String) getParameter("productId");
		return dispatch();
	}

	public Result editStep1() {
		if (StringUtils.isNotBlank(productId)) {
			hotel = hotelService.get(Long.parseLong(productId));
			List<Productimage> productimages = productimageService.findImagesByProductId(hotel.getId());
			int i = 0;
			images = "";
			if (hotel.getSource() != null) {
				for (Productimage productimage : productimages) {
					if (i < productimages.size() - 1) {
						images += productimage.getPath() + ",";
					} else {
						images += productimage.getPath();
					}
					i++;
				}
			} else {
				for (Productimage productimage : productimages) {
					String path = productimage.getPath();
					path = path.substring(1, path.length());
					if (i < productimages.size() - 1) {
						images += QiniuUtil.URL + path + ",";
					} else {
						images += QiniuUtil.URL + path;
					}
					i++;
				}
			}

		}
		return dispatch();
	}
	public Result editStep21() {
		if (StringUtils.isNotBlank(productId)) {
			hotel = hotelService.get(Long.parseLong(productId));
		}
		return dispatch();
	}


	public Result editStep2() {
		// 起始时间 - 当前时间第二天
		Date dateStart = DateUtils.add(new Date(), Calendar.DAY_OF_MONTH, 1);
		dateStartStr = DateUtils.format(dateStart, "yyyy-MM-dd");
		// 结束时间 - 当前时间次月最后一天
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateStart);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 6);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);
		dateEndStr = DateUtils.format(calendar.getTime(), "yyyy-MM-dd");

		if (StringUtils.isNotBlank(productId)) {
			hotel = hotelService.get(Long.parseLong(productId));
			hotelPrice.setHotel(hotel);
		}
		if (typePriceId != null) {
			hotelPrice = hotelPriceService.get(typePriceId);
		}
		return dispatch();
	}

	public Result editStep3() {

		if (StringUtils.isNotBlank(productId)) {
			hotel = hotelService.get(Long.parseLong(productId));

			Float minPrice = hotelPriceService.findHotelMinPrice(hotel);

			if (minPrice != null && minPrice > 0) {
				hotel.setPrice(minPrice);
			}

			hotel.setStatus(ProductStatus.DOWN);
			hotelService.updateHotel(hotel);
		}

		return dispatch();
	}


	public Result getAreaList() {

		List<TbArea> tbAreas = new ArrayList<TbArea>();
		if (area.getId() != null) {
			area.setName("市辖区");
			tbAreas = tbAreaService.findAreaListByFather(area);

		}
		JsonConfig jsonConfig = JsonFilter.getIncludeConfig();
		JSONArray json = JSONArray.fromObject(tbAreas, jsonConfig);
		return json(json);
	}

	public Result getAreaById() {
		if (area.getId() != null) {
			area = tbAreaService.getArea(area.getId());
		}
		JsonConfig jsonConfig = JsonFilter.getIncludeConfig();
		JSONObject jsonObject = JSONObject.fromObject(area, jsonConfig);
		return json(jsonObject);
	}

	/**
	 * 上传图片
	 * @return
	 */
	public Result uploadImg() {
		if (checkFileType()) {
			String suffix = resourceFileName.substring(resourceFileName.lastIndexOf("."));
			String newFileName = System.currentTimeMillis() + suffix;
			String newFilePath = StringUtils.defaultString(folder) + newFileName;
			QiniuUtil.upload(resource, newFilePath);
			map.put("url", QiniuUtil.URL + newFilePath);
			simpleResult(map, true, "");
			return jsonResult(map);
		} else {
			simpleResult(map, false, "图片格式不正确");
			return jsonResult(map);
		}
	}

	/**
	 * 上传图片
	 * @return
	 */
	public Result uploadPics() {

		final HttpServletRequest request = getRequest();
		MultiPartRequestWrapper multiPartRequestWrapper = (MultiPartRequestWrapper) request;
		File file = multiPartRequestWrapper.getFiles("file")[0];
		//没有获取到文件,或者无法读取文件或者文件容量太大等问题
		if (file == null || file.length() > 10000000) {
			simpleResult(map, false, "没有获取到文件,或者无法读取文件或者文件容量太大等问题");
			return jsonResult(map);
		}
		//二级栏目类别为空问题
		if (folder == null || "".equals(folder)) {
			simpleResult(map, false, "二级栏目类别为空问题");
			return jsonResult(map);
		}
		//获得上传的原始文件名称
		String filename = multiPartRequestWrapper.getParameter("name");

		String imgIndex = multiPartRequestWrapper.getParameter("id");


		if (filename != null) {
			filename.startsWith("image/");
			String suffix = filename.substring(filename.lastIndexOf("."));
			String newFileName = System.currentTimeMillis() + suffix;
			String newFilePath = StringUtils.defaultString(folder) + newFileName;
			QiniuUtil.upload(file, newFilePath);
			map.put("imgIndex", imgIndex);
			map.put("url", QiniuUtil.URL + newFilePath);
			simpleResult(map, true, "");
			return jsonResult(map);
		} else {
			simpleResult(map, false, "图片格式不正确");
			return jsonResult(map);
		}
	}

	public Result imgsView() {
		String folder = (String) getParameter("folder");
		Productimage pi = new Productimage();
		pi.setChildFolder(folder);
		SysUser user = getLoginUser();
		List<Productimage> productimages = productimageService.findProductimage(pi, user);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (Productimage productimage : productimages) {
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("is_dir", false);
			m.put("has_file", false);
			m.put("is_photo", true);
			String fileExt = productimage.getPath().substring(productimage.getPath().lastIndexOf(".") + 1).toLowerCase();
			m.put("filetype", fileExt);
			String filename = productimage.getPath().substring(productimage.getPath().lastIndexOf("/") + 1).toLowerCase();
			m.put("filename", filename);
			m.put("datetime", DateUtils.format(productimage.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
			list.add(m);
		}
		map.put("current_dir_path", QiniuUtil.URL + folder);
		map.put("current_url", QiniuUtil.URL + folder);
		map.put("total_count", list.size());
		map.put("file_list", list);
		return jsonResult(map);
	}

	/**
	 * 检查是否是图片格式
	 * @author caiys
	 * @date 2015年10月28日 下午3:19:01
	 * @return
	 */
	public boolean checkFileType() {
		if (StringUtils.isBlank(resourceContentType)) {
			return false;
		}
		return resourceContentType.startsWith("image/");
	}

	/**
	 * 获取上架的酒店
	 * @return
	 */
	public Result getShowHotelList() {
		Page pageInfo = new Page(page, rows);

		String source = (String) getParameter("source");
		String name = (String) getParameter("name");

		String star = (String) getParameter("star");

		String qryStartTime = (String) getParameter("qryStartTime");

		String qryEndTime = (String) getParameter("qryEndTime");


		if (StringUtils.isNotBlank(source)) {
			hotel.setSource(ProductSource.valueOf(source));
		}

		if (StringUtils.isNotBlank(name)) {
			hotel.setName(name);
		}

		if (StringUtils.isNotBlank(star)) {
			hotel.setStar(Integer.parseInt(star));
		}

		if (StringUtils.isNotBlank(qryStartTime)) {
			Date startTime = DateUtils.getDate(qryStartTime, "yyyy-MM-dd HH:mm:ss");
			hotel.setQryStartTime(startTime);
		}

		if (StringUtils.isNotBlank(qryEndTime)) {
			Date endTime = DateUtils.getDate(qryEndTime, "yyyy-MM-dd HH:mm:ss");
			hotel.setQryEndTime(endTime);
		}

		hotelList = hotelService.getShowHotelList(hotel, pageInfo, isSupperAdmin(), isSiteAdmin(), getLoginUser());
		JsonConfig jsonConfig = JsonFilter.getIncludeConfig("extend");
		return datagrid(hotelList, pageInfo.getTotalCount(), jsonConfig);
	}


	public Result getHotelImages() {
		if (com.zuipin.util.StringUtils.isNotBlank(productId)) {
			hotel = hotelService.get(Long.parseLong(productId));
			if (typePriceId != null) {
				productimages = productimageService.findAllImagesByProIdAadTarId(hotel.getId(), typePriceId, "hotel/hotelRoom/");
			} else {
				productimages = productimageService.findAllImagesByProIdAadTarId(hotel.getId(), hotel.getId(), "hotel/hotelDesc/");
			}
			map.put("imageList", productimages);
			simpleResult(map, true, "");
		} else {
			simpleResult(map, false, "");
		}
		return json(JSONObject.fromObject(map, JsonFilter.getIncludeConfig()));
	}


	/**
	 * 民宿类型下架操作
	 * @return
	 */
	public Result doPriceTypeDown() {
		if (typePriceId == null) {
			simpleResult(map, false, "");
			return json(JSONObject.fromObject(map));
		}
		hotelPrice = hotelPriceService.get(typePriceId);
		hotelPrice.setStatus(PriceStatus.DOWN);
		hotelPrice.setShowStatus(ShowStatus.SHOW);
		hotelPrice.setModifyTime(new Date());
		hotelPriceService.update(hotelPrice);
		simpleResult(map, true, "");
		return json(JSONObject.fromObject(map));
	}

	/**
	 * 民宿房型基本信息上架拒绝操作
	 * @return
	 */
	public Result doRefuseHotelPriceTypeInfo() {
		if (typePriceId == null) {
			simpleResult(map, false, "");
			return json(JSONObject.fromObject(map));
		}
		String content = (String) getParameter("content");
		hotelPrice = hotelPriceService.get(typePriceId);
		hotelPrice.setStatus(PriceStatus.REFUSE);
//		hotelPrice.setShowStatus(ShowStatus.HIDE_FOR_CHECK);
//		hotelPrice.setModifyTime(new Date());
		hotelPrice.setAuditBy(getLoginUser().getId());
		hotelPrice.setAuditReason(content);
		hotelPrice.setAuditTime(new Date());
		hotelPriceService.update(hotelPrice);

		simpleResult(map, true, "");
		return json(JSONObject.fromObject(map));
	}

	/**
	 * 民宿基本信息上架拒绝操作
	 * @return
	 */
	public Result doRefuseHotelInfoUp() {
		if (!com.zuipin.util.StringUtils.isNotBlank(productId)) {
			simpleResult(map, false, "");
			return json(JSONObject.fromObject(map));
		}
		String content = (String) getParameter("content");
		hotel = hotelService.get(Long.parseLong(productId));
		hotel.setStatus(ProductStatus.REFUSE);
//		hotel.setShowStatus(ShowStatus.HIDE_FOR_CHECK);
		hotel.setAuditBy(getLoginUser().getId());
		hotel.setAuditTime(new Date());
		hotel.setAuditReason(content);
		hotelService.update(hotel);

		simpleResult(map, true, "");
		return json(JSONObject.fromObject(map));
	}

	/**
	 * 民宿拒绝下架操作
	 * @return
	 */
	public Result doRefuseHotelInfoDown() {
		if (!com.zuipin.util.StringUtils.isNotBlank(productId)) {
			simpleResult(map, false, "");
			return json(JSONObject.fromObject(map));
		}
		String content = (String) getParameter("content");
		hotel = hotelService.get(Long.parseLong(productId));
		hotel.setStatus(ProductStatus.UP);
		hotel.setShowStatus(ShowStatus.SHOW);
		hotel.setAuditBy(getLoginUser().getId());
		hotel.setAuditTime(new Date());
		hotel.setAuditReason(content);
		hotelService.update(hotel);

		simpleResult(map, true, "");
		return json(JSONObject.fromObject(map));
	}



	/**
	 * 房型审核上架通过操作
	 * @return
	 */
	public Result doCheckHotelPriceInfoUp() {

		if (typePriceId == null) {
			simpleResult(map, false, "");
			return json(JSONObject.fromObject(map));
		}
		hotelPrice = hotelPriceService.get(typePriceId);
		if (hotelPrice.getOriginId() == null) {
			hotelPrice.setStatus(PriceStatus.UP);
			hotelPrice.setShowStatus(ShowStatus.SHOW);
//			hotelPrice.setModifyTime(new Date());
			hotelPrice.setAuditTime(new Date());
			hotelPrice.setAuditBy(getLoginUser().getId());
			hotelPriceService.update(hotelPrice);	//update hotelprice

			hotel = hotelService.get(hotelPrice.getHotel().getId());
			hotelService.indexHotel(hotel);		//索引民宿信息

//			hotelRoomService.doCheckHotelRoomsUp(hotelPrice);	//handle hotelroom'lsit

//			productimages = productimageService.findAllImagesByProIdAadTarId(hotelPrice.getHotel().getId(), hotelPrice.getId());	//找到新增房型数据图片
//			productimageService.doCheckImagesStatus(productimages, hotelPrice.getHotel(), hotelPrice.getId());	//设置新增房型数据图片
		} else {
			HotelPrice orginHotelPrice = hotelPriceService.get(hotelPrice.getOriginId());
			BeanUtils.copyProperties(hotelPrice, orginHotelPrice, false, null, "id", "originId");
			orginHotelPrice.setStatus(PriceStatus.UP);
			orginHotelPrice.setShowStatus(ShowStatus.SHOW);
//			orginHotelPrice.setModifyTime(new Date());
			orginHotelPrice.setAuditTime(new Date());
			orginHotelPrice.setAuditBy(getLoginUser().getId());

			hotelPriceService.update(orginHotelPrice);	//update orginal hotelprice

			hotelRoomService.doDelHotelRoomNum(orginHotelPrice.getHotel().getId(), orginHotelPrice.getId());	//删除原始房型房间号
			hotelRoomService.doCheckHotelRoomsUp(hotelPrice);    //把临时房型房间号复制给原始房型房间号
			hotelPriceService.delete(hotelPrice);	//delete temproray hotelprice

			hotel = hotelService.get(hotelPrice.getHotel().getId());
			hotelService.indexHotel(hotel);		//索引民宿信息

			productimageService.delImages(orginHotelPrice.getHotel().getId(), orginHotelPrice.getId(), "hotel/hotelRoom/"); 	//删除原始数据房型图片
			productimages = productimageService.findAllImagesByProIdAadTarId(orginHotelPrice.getHotel().getId(), hotelPrice.getId(), "hotel/hotelRoom/");	//获取房型临时数据图片
			productimageService.doCheckImagesStatus(productimages, orginHotelPrice.getHotel(), orginHotelPrice.getId());	//把房型临时数据图片设为房型原始数据图片
		}
		simpleResult(map, true, "");
		return json(JSONObject.fromObject(map));

	}

	/**
	 * 民宿上架审核通过操作
	 * @return
	 */
	public Result doCheckHotelInfoUp() {
		if (!com.zuipin.util.StringUtils.isNotBlank(productId)) {
			simpleResult(map, false, "");
			return json(JSONObject.fromObject(map));
		}
		hotel = hotelService.get(Long.parseLong(productId));
		if (hotel.getOriginId() == null) {
			hotel.setStatus(ProductStatus.UP);
//			hotel.setUpdateTime(new Date());
			hotel.setShowStatus(ShowStatus.SHOW);
			hotel.setAuditBy(getLoginUser().getId());
			hotel.setAuditTime(new Date());
			hotel.setAuditReason("新增上架审核成功");
			hotelService.update(hotel);
			productimages = productimageService.findAllImagesByProIdAadTarId(hotel.getId(), hotel.getId(), "hotel/hotelDesc/");
			productimageService.doCheckImagesStatus(productimages, hotel, hotel.getId());

		} else {
			Hotel orginHotel = hotelService.get(hotel.getOriginId());
			HotelExtend hotelExtend = hotel.getExtend();
			HotelExtend orginHotelExtend = orginHotel.getExtend();
			BeanUtils.copyProperties(hotel, orginHotel, false, null, "id", "originId");
			BeanUtils.copyProperties(hotelExtend, orginHotelExtend, false, null, "id", "originId");
			orginHotel.setStatus(ProductStatus.UP);
			orginHotel.setShowStatus(ShowStatus.SHOW);
//			orginHotel.setUpdateTime(new Date());
			orginHotel.setAuditBy(getLoginUser().getId());
			orginHotel.setAuditTime(new Date());
			orginHotel.setAuditReason("编辑上架审核成功");
			hotelService.delete(hotel); //del temporary hotel

			hotelExtendService.delete(hotelExtend);	//del temporary hotelextend
			hotelService.update(orginHotel);	//update original hotel
			hotelExtendService.update(orginHotelExtend);	//update original hotelextend

			productimageService.delImages(orginHotel.getId(), orginHotel.getId(), "hotel/hotelDesc/");	//删除民宿原始数据图片
			productimages = productimageService.findAllImagesByProIdAadTarId(hotel.getId(), hotel.getId(), "hotel/hotelDesc/");	//找出民宿临时数据图片
			productimageService.doCheckImagesStatus(productimages, orginHotel, orginHotel.getId());	//把临时数据图片设为原始数据图片
		}
		simpleResult(map, true, "");
		return json(JSONObject.fromObject(map));
	}

	public Result doEditProductShowOrder() {

		if (com.zuipin.util.StringUtils.isBlank(productId)) {
			simpleResult(map, false, "无效ID！");
			return jsonResult(map);
		}
//		if (showOrder == null) {
//			simpleResult(map, false, "无效序号！");
//			return jsonResult(map);
//		}
		hotel = hotelService.get(Long.parseLong(productId));
		hotel.setShowOrder(showOrder);
		hotelService.update(hotel);
		simpleResult(map, true, "操作成功！");
		return jsonResult(map);

	}

	public Result doEditTypeShowOrder() {

		if (typePriceId == null) {
			simpleResult(map, false, "无效ID！");
			return jsonResult(map);
		}
//		if (showOrder == null) {
//			simpleResult(map, false, "无效序号！");
//			return jsonResult(map);
//		}
		hotelPrice = hotelPriceService.get(typePriceId);
		hotelPrice.setShowOrder(showOrder);
		hotelPriceService.update(hotelPrice);
		simpleResult(map, true, "操作成功！");
		return jsonResult(map);

	}

	public Result hotelImages() {
		if (com.zuipin.util.StringUtils.isNotBlank(productId)) {
			productimages = productimageService.findAllImagesByProIdAadTarId(Long.parseLong(productId), null, null, "showOrder", "asc");
		}
		return dispatch();
	}

	public Result saveProductImageSort() {
		if (ids.isEmpty()) {
			simpleResult(map, false, "操作失败，无数据提交！");
			return jsonResult(map);
		}
		productimageService.saveProductImageSort(ids);
		simpleResult(map, true, "操作成功！");
		return jsonResult(map);

	}

	/**
	 * 民宿下架审核通过操作
	 * @return
	 */
	public Result doCheckHotelInfoDown() {
		if (!com.zuipin.util.StringUtils.isNotBlank(productId)) {
			simpleResult(map, false, "");
			return json(JSONObject.fromObject(map));
		}
		hotel = hotelService.get(Long.parseLong(productId));
		hotel.setShowStatus(ShowStatus.SHOW);
		hotel.setStatus(ProductStatus.DOWN);
//		hotel.setUpdateTime(new Date());
		hotel.setAuditBy(getLoginUser().getId());
		hotel.setAuditTime(new Date());
		hotel.setAuditReason("下架审核成功");
		hotelService.update(hotel);

		hotelPriceService.deleteTempHotelPriceList(hotel);	//删除所有房型临时数据
		hotelPriceService.doHandleHotelPriceAll(hotel, PriceStatus.DOWN);	//下架所有房型

		simpleResult(map, true, "");
		return json(JSONObject.fromObject(map));
	}

	/**
	 * 民宿基本信息审核编辑详情
	 * @return
	 */
	public Result checkHotelInfo() {
		if (com.zuipin.util.StringUtils.isNotBlank(productId)) {
			hotel = hotelService.get(Long.parseLong(productId));
		}
		return dispatch();
	}

	/**
	 * 民宿房型基本信息审核编辑详情
	 * @return
	 */
	public Result checkHotelPriceTypeInfo() {
		if (typePriceId != null) {
			hotelPrice = hotelPriceService.get(typePriceId);
			hotelPrice.setRoomNum(hotelRoomService.getRoomNumbers(hotelPrice.getHotel().getId(), hotelPrice.getId()));
		}
		return dispatch();
	}

	/**
	 * 获取房型列表
	 * @return
	 */
	public Result getHotelPriceList() {
		Page pageInfo = new Page(page, rows);
		if (com.zuipin.util.StringUtils.isBlank(sort)) {
			sort = "modifyTime";
		}
		if (com.zuipin.util.StringUtils.isBlank(order)) {
			order = "desc";
		}
		List<HotelPrice> hotelPriceList = hotelPriceService.getHotelPriceList(hotelPrice, pageInfo, sort, order);
		List<HotelPrice> resultPriceList = Lists.newArrayList();
		for (HotelPrice price : hotelPriceList) {
			price.setRoomNum(hotelRoomService.getRoomNumbers(price.getHotel().getId(), price.getId()));
			resultPriceList.add(price);
		}
		return datagrid(resultPriceList, pageInfo.getTotalCount());
	}

	/**
	 * 获取所有的酒店
	 * @return
	 */
	public Result getAllHotelList() {
		Page pageInfo = new Page(page, rows);

		String qryStartTime = (String) getParameter("qryStartTime");
		String qryEndTime = (String) getParameter("qryEndTime");

		if (StringUtils.isNotBlank(qryStartTime)) {
			Date startTime = DateUtils.getDate(qryStartTime, "yyyy-MM-dd HH:mm:ss");
			hotel.setQryStartTime(startTime);
		}

		if (StringUtils.isNotBlank(qryEndTime)) {
			Date endTime = DateUtils.getDate(qryEndTime, "yyyy-MM-dd HH:mm:ss");
			hotel.setQryEndTime(endTime);
		}

		if (com.zuipin.util.StringUtils.isBlank(sort)) {
			sort = "showOrder";
		}
		if (com.zuipin.util.StringUtils.isBlank(order)) {
			order = "asc";
		}

		hotelList = hotelService.getAllHotelList(hotel, pageInfo, isSupperAdmin(), isSiteAdmin(), getLoginUser(), sort, order);

		List<Hotel> resultHotelList = Lists.transform(hotelList, new Function<Hotel, Hotel>() {
			@Override
			public Hotel apply(Hotel hotel) {
				if (hotel.getProductimage() != null && hotel.getProductimage().size() > 0) {
					hotel.setImageTotalCount(hotel.getProductimage().size());
				}
				return hotel;
			}
		});
		JsonConfig jsonConfig = JsonFilter.getIncludeConfig("extend", "supplier");
		return datagrid(resultHotelList, pageInfo.getTotalCount(), jsonConfig);
	}

	public Result manage() {
		return dispatch();
	}
	
	public Result hotelList() {
		Page pageInfo = new Page(page, rows);
		String name = (String) getParameter("name");
		String cityId = (String) getParameter("cityId");
		String type = (String) getParameter("type");
		String labelId = (String) getParameter("labelId");
		String tagIds = (String) getParameter("tagIds");
		TbArea area = null;
		Hotel info = null;
		if (StringUtils.isNotBlank(cityId)) {
			area = tbAreaService.getArea(Long.parseLong(cityId));
		}
		
		if (StringUtils.isNotBlank(name)) {
			info = new Hotel();
			info.setName(name);
		}
		List<Hotel> scenics = new ArrayList<Hotel>();
		List<HotelLabel> scenicLabels = new ArrayList<HotelLabel>();
		if ("HOTEL".equals(type)) {
			scenics = hotelService.getHotelLabels(info, area, tagIds, pageInfo);
			for (Hotel sInfo : scenics) {
				HotelLabel slabel = new HotelLabel();
				slabel.setId(sInfo.getId());
				slabel.setName(sInfo.getName());
				if (sInfo.getCityId() != null) {
					slabel.setCityId(sInfo.getCityId());
					slabel.setCityName(tbAreaService.getArea(sInfo.getCityId()).getName());
				}
				
				List<LabelItem> items = labelItemService.findItemByTargId(sInfo.getId(), TargetType.HOTEL);
				
				List<String> labelNames = new ArrayList<String>();
				List<Integer> itemSorts = new ArrayList<Integer>();
				List<Long> itemIds = new ArrayList<Long>();
				List<Long> labIds = new ArrayList<Long>();
				for (LabelItem it : items) {
					if ((it.getTargetId()).equals(sInfo.getId())) {
						if (StringUtils.isNotBlank(labelId)) {
							Long lId = Long.parseLong(labelId);
							List<Label> labels = labelService.findLabelsByParent(lId);
							if (labels.size() > 0) {
								for (Label la : labels){
									if ((la.getId()).equals(it.getLabel().getId())) {
										slabel.setSort(it.getOrder());
										itemSorts.add(it.getOrder());
										labelNames.add(it.getLabel().getName());
										itemIds.add(it.getId());
										labIds.add(it.getLabel().getId());
									}
								}
								
							} else {
								if ((lId).equals(it.getLabel().getId())) {
									slabel.setSort(it.getOrder());
									itemSorts.add(it.getOrder());
									labelNames.add(it.getLabel().getName());
									itemIds.add(it.getId());
									labIds.add(it.getLabel().getId());
								}
							}
							
						} else {
							slabel.setSort(it.getOrder());
							itemSorts.add(it.getOrder());
							labelNames.add(it.getLabel().getName());
							itemIds.add(it.getId());
							labIds.add(it.getLabel().getId());
						}
					}
					
				}
				
				slabel.setLabelNames(labelNames);
				slabel.setItemSort(itemSorts);
				slabel.setLabelItems(itemIds);
				slabel.setLabelIds(labIds);
				
				
				scenicLabels.add(slabel);
			}
		}
		return datagrid(scenicLabels, pageInfo.getTotalCount());
	}

	public Result yhyHotelList() {
		Page pageInfo = new Page(page, rows);
		String name = (String) getParameter("name");
		String labelId = (String) getParameter("labelId");
		String type = (String) getParameter("type");
		String tagIds = (String) getParameter("tagIds");
		TbArea area = tbAreaService.getArea(350200L);
		Hotel info = null;
		if (StringUtils.isNotBlank(name)) {
			info = new Hotel();
			info.setName(name);
		}
		List<Hotel> scenics = new ArrayList<Hotel>();
		List<HotelLabel> scenicLabels = new ArrayList<HotelLabel>();
		if ("HOTEL".equals(type)) {
			scenics = hotelService.getHotelLabels(info, area, tagIds, pageInfo);
			for (Hotel sInfo : scenics) {
				HotelLabel slabel = new HotelLabel();
				slabel.setId(sInfo.getId());
				slabel.setName(sInfo.getName());
				if (sInfo.getCityId() != null) {
					slabel.setCityId(sInfo.getCityId());
					slabel.setCityName(tbAreaService.getArea(sInfo.getCityId()).getName());
				}

				List<LabelItem> items = labelItemService.findItemByTargId(sInfo.getId(), TargetType.HOTEL);

				List<String> labelNames = new ArrayList<String>();
				List<Integer> itemSorts = new ArrayList<Integer>();
				List<Long> itemIds = new ArrayList<Long>();
				List<Long> labIds = new ArrayList<Long>();
				for (LabelItem it : items) {
					if ((it.getTargetId()).equals(sInfo.getId())) {
						if (StringUtils.isNotBlank(labelId)) {
							Long lId = Long.parseLong(labelId);
							List<Label> labels = labelService.findLabelsByParent(lId);
							if (labels.size() > 0) {
								for (Label la : labels){
									if ((la.getId()).equals(it.getLabel().getId())) {
										slabel.setSort(it.getOrder());
										itemSorts.add(it.getOrder());
										labelNames.add(it.getLabel().getName());
										itemIds.add(it.getId());
										labIds.add(it.getLabel().getId());
									}
								}

							} else {
								if ((lId).equals(it.getLabel().getId())) {
									slabel.setSort(it.getOrder());
									itemSorts.add(it.getOrder());
									labelNames.add(it.getLabel().getName());
									itemIds.add(it.getId());
									labIds.add(it.getLabel().getId());
								}
							}

						} else {
							slabel.setSort(it.getOrder());
							itemSorts.add(it.getOrder());
							labelNames.add(it.getLabel().getName());
							itemIds.add(it.getId());
							labIds.add(it.getLabel().getId());
						}
					}

				}

				slabel.setLabelNames(labelNames);
				slabel.setItemSort(itemSorts);
				slabel.setLabelItems(itemIds);
				slabel.setLabelIds(labIds);


				scenicLabels.add(slabel);
			}
		}
		return datagrid(scenicLabels, pageInfo.getTotalCount());
	}

    public Result listCoordinates() {
        final HttpServletRequest request = getRequest();
        String cityCode = request.getParameter("cityCode");
        String rankingLimit = request.getParameter("rankingLimit");
        Map<String, Object> result = new HashMap<String, Object>();
        List<Map<String, String>> resultList = hotelService.listCoordinates(cityCode, rankingLimit);
        result.put("data", resultList);
        return json(JSONObject.fromObject(result));
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

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	public File getResource() {
		return resource;
	}

	public void setResource(File resource) {
		this.resource = resource;
	}

	public String getResourceFileName() {
		return resourceFileName;
	}

	public void setResourceFileName(String resourceFileName) {
		this.resourceFileName = resourceFileName;
	}

	public String getResourceContentType() {
		return resourceContentType;
	}

	public void setResourceContentType(String resourceContentType) {
		this.resourceContentType = resourceContentType;
	}

	public String getOldFilePath() {
		return oldFilePath;
	}

	public void setOldFilePath(String oldFilePath) {
		this.oldFilePath = oldFilePath;
	}

	public String getFolder() {
		return folder;
	}

	public void setFolder(String folder) {
		this.folder = folder;
	}

	public TbArea getArea() {
		return area;
	}

	public void setArea(TbArea area) {
		this.area = area;
	}

	public String getDateStartStr() {
		return dateStartStr;
	}

	public void setDateStartStr(String dateStartStr) {
		this.dateStartStr = dateStartStr;
	}

	public String getDateEndStr() {
		return dateEndStr;
	}

	public void setDateEndStr(String dateEndStr) {
		this.dateEndStr = dateEndStr;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Long getTypePriceId() {
		return typePriceId;
	}

	public void setTypePriceId(Long typePriceId) {
		this.typePriceId = typePriceId;
	}

	public HotelPrice getHotelPrice() {
		return hotelPrice;
	}

	public void setHotelPrice(HotelPrice hotelPrice) {
		this.hotelPrice = hotelPrice;
	}

	public String getWinIndex() {
		return winIndex;
	}

	public void setWinIndex(String winIndex) {
		this.winIndex = winIndex;
	}

	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public List<Productimage> getProductimages() {
		return productimages;
	}

	public void setProductimages(List<Productimage> productimages) {
		this.productimages = productimages;
	}

	public List<HotelPriceCalendarVo> getPriceCalendarVos() {
		return priceCalendarVos;
	}

	public void setPriceCalendarVos(List<HotelPriceCalendarVo> priceCalendarVos) {
		this.priceCalendarVos = priceCalendarVos;
	}

	public Integer getShowOrder() {
		return showOrder;
	}

	public void setShowOrder(Integer showOrder) {
		this.showOrder = showOrder;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
}
