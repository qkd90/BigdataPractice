//package com.data.data.hmly.action.scemanager;
//
//import com.data.data.hmly.action.FrameBaseAction;
//import com.data.data.hmly.service.SysSiteService;
//import com.data.data.hmly.service.SysUnitDetailService;
//import com.data.data.hmly.service.SysUnitService;
//import com.data.data.hmly.service.SysUserService;
//import com.data.data.hmly.service.TbAreaService;
//import com.data.data.hmly.service.UserService;
//import com.data.data.hmly.service.common.ProductValidateCodeService;
//import com.data.data.hmly.service.common.entity.ProductValidateCode;
//import com.data.data.hmly.service.common.entity.ProductValidateRecord;
//import com.data.data.hmly.service.entity.BusinessModel;
//import com.data.data.hmly.service.entity.BusinessScope;
//import com.data.data.hmly.service.entity.BusinessType;
//import com.data.data.hmly.service.entity.SupplierType;
//import com.data.data.hmly.service.entity.SysUnit;
//import com.data.data.hmly.service.entity.SysUnitDetail;
//import com.data.data.hmly.service.entity.SysUser;
//import com.data.data.hmly.service.entity.TbArea;
//import com.data.data.hmly.service.entity.User;
//import com.data.data.hmly.service.entity.UserStatus;
//import com.data.data.hmly.service.outOrder.JszxOrderDetailService;
//import com.data.data.hmly.service.outOrder.JszxOrderService;
//import com.data.data.hmly.service.outOrder.entity.JszxOrder;
//import com.data.data.hmly.service.outOrder.entity.JszxOrderDetail;
//import com.data.data.hmly.service.outOrder.entity.enums.JszxOrderDetailStatus;
//import com.data.data.hmly.service.scenic.ScenicInfoService;
//import com.data.data.hmly.service.scenic.entity.ScenicInfo;
//import com.data.data.hmly.service.ticket.TicketService;
//import com.data.data.hmly.util.Encryption;
//import com.framework.hibernate.util.Page;
//import com.framework.struts.AjaxCheck;
//import com.opensymphony.xwork2.ModelDriven;
//import com.opensymphony.xwork2.Result;
//import com.zuipin.util.DateUtils;
//import com.zuipin.util.FileUtils;
//import com.zuipin.util.JsonFilter;
//import com.zuipin.util.PropertiesManager;
//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;
//import net.sf.json.JsonConfig;
//import org.apache.commons.lang3.StringUtils;
//
//import javax.annotation.Resource;
//import java.io.File;
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
///**
// * 线路
// * @author caiys
// * @date 2015年10月13日 下午4:12:38
// */
//public class ScemanagerAction extends FrameBaseAction implements ModelDriven<SceManager> {
//
//	/**
//	 *
//	 */
//	private static final long serialVersionUID = 4395061833017783442L;
//	@Resource
//	private TbAreaService 				tbAreaService;
//	@Resource
//	private ScenicInfoService scenicInfoService;
//	@Resource
//	private SysSiteService sysSiteService;
//	@Resource
//	private SysUnitService sysUnitService;
//	@Resource
//	private SysUnitDetailService detailService;
//	@Resource
//	private UserService userService;
//	@Resource
//	private SysUserService sysUserService;
//
//	@Resource
//	private JszxOrderService jszxOrderService;
//
//	@Resource
//	private JszxOrderDetailService jszxOrderDetailService;
//
//	@Resource
//	private ProductValidateCodeService validateCodeService;
//	@Resource
//	private TicketService ticketService;
//	@Resource
//	private PropertiesManager propertiesManager;
//
//
//
//	private Integer			page				= 1;
//	private Integer			rows				= 2;
//	private SysUnit 		sysUnit;
//
//	private File resource;
//	private String resourceFileName;
//	private String resourceContentType;	// image/jpeg
//	private String oldFilePath;	// 旧图片路径
//	private String folder;		// 图片目录
//
//
//
//	private SceManager sceManager = new SceManager();
//	// 返回数据
//	Map<String, Object>	map					= new HashMap<String, Object>();
//
//
//	/**
//	 * 功能描述：验票端验票详情查看
//	 * @return
//	 */
//	public Result getTicketValidataInfo() {
//		String productValidateId = (String) getParameter("productValidateId");
//		List<ProductValidateRecord> validateRecords = new ArrayList<ProductValidateRecord>();
//		if (StringUtils.isNotBlank(productValidateId)) {
//			List<ProductValidateCode> codeList = new ArrayList<ProductValidateCode>();
//			codeList.add(validateCodeService.getById(Long.parseLong(productValidateId)));
//			validateRecords = validateCodeService.findValidateRecodsInfoList(codeList);
//			for (ProductValidateRecord productValidateRecord : validateRecords) {
//				productValidateRecord.setValidateByName(sysUserService.load(productValidateRecord.getValidateBy()).getAccount());
//			}
//		}
//		return datagrid(validateRecords);
//	}
//
//
//
//	public Result testExcel() throws IOException {
//
//		String startTimeStr = (String) getParameter("startTimeStr");
//		String endTimeStr = (String) getParameter("endTimeStr");
//		String isSuplierStr = (String) getParameter("isSuplier");
//		boolean isSuplier = false;
//		if (StringUtils.isNotBlank(isSuplierStr)) {
//			isSuplier = true;
//		}
//		Date startTime = null;
//		if (StringUtils.isNotBlank(startTimeStr)){
//			startTime = DateUtils.getDate(startTimeStr + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
//		}
//		Date endTime = null;
//		if (StringUtils.isNotBlank(endTimeStr)){
//			endTime = DateUtils.getDate(endTimeStr + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
//			endTime = DateUtils.getEndDay(endTime, 0);
//		}
//		SysUnitDetail unitDetail = detailService.findDetailByUid(getCompanyUnit().getId());
//
//		map = validateCodeService.doLoadExcel(startTime, endTime, getCompanyUnit(), getLoginUser(), unitDetail, isSupperAdmin(), isSiteAdmin(), isSuplier);
//
//		if ((boolean) map.get("success")) {
//			simpleResult(map, true, "导出成功！");
//		} else {
//			simpleResult(map, false, "导出成功！");
//		}
//
//
//		return jsonResult(map);
//	}
//
//
//	/*
//	 * 验证门票
//	 *
//	 */
//	public Result validateCode() {
//
//		return dispatch();
//	}
//
//
//	public Result validateList() {
//		String ticNameid = (String) getParameter("ticNameid");
//		String orderNum = (String) getParameter("orderNum");
//		String seaStartTime = (String) getParameter("seaStartTime");
//		String seaEndTime = (String) getParameter("seaEndTime");
//		String isUsed = (String) getParameter("isUsed");
//		Long userId = getLoginUser().getId();
//		Page pageInfo = new Page(page, rows);
//		SysUnit sysUnit = getCompanyUnit();
//		sysUnit.setSysSite(getSite());
//
//		ProductValidateCode searchParam = new ProductValidateCode();
//
//
//		if (StringUtils.isNotBlank(seaStartTime)) {
//			Date startTime = DateUtils.getDate(seaStartTime, "yyyy-MM-dd HH:mm:ss");
//			searchParam.setUpdateTimeStart(startTime);
//		}
//
//		if (StringUtils.isNotBlank(seaEndTime)) {
//			Date endTime = DateUtils.getDate(seaEndTime, "yyyy-MM-dd HH:mm:ss");
//			searchParam.setUpdateTimeEnd(endTime);
//		}
//
//		if (StringUtils.isNotBlank(ticNameid)) {
//			searchParam.setTicketName(ticNameid);
//		}
//
//		if (StringUtils.isNotBlank(orderNum)) {
//			searchParam.setOrderNo(orderNum);
//		}
//
//		if (StringUtils.isNotBlank(isUsed)) {
//			searchParam.setUsed(Integer.parseInt(isUsed));
//		}
//
//
//		SysUnitDetail unitDetail = detailService.findDetailByUid(sysUnit.getId());
//
//		if (unitDetail != null && unitDetail.getScenicid() != null) {
//			searchParam.setScenicId(unitDetail.getScenicid());
//		}
//
//		List<SceValiData> validate = new ArrayList<SceValiData>();
//
//		List<ProductValidateCode> validateCodes = validateCodeService.findValidatesList(pageInfo, searchParam, sysUnit, isSupperAdmin(), isSiteAdmin());
//		for (ProductValidateCode productValidateCode : validateCodes) {
//			SceValiData data = new SceValiData();
//			data.setName(productValidateCode.getTicketName());
//			data.setOrderNo(productValidateCode.getOrderNo());
//			data.setSupUserName(productValidateCode.getProduct().getUser().getUserName());
//			data.setId(productValidateCode.getId());
//			data.setBuyerName(productValidateCode.getBuyerName());
//			data.setBuyerMobile(productValidateCode.getBuyerMobile());
//			data.setCreateTime(productValidateCode.getCreateTime());
//			data.setCode(productValidateCode.getCode());
//			data.setOrderCount(productValidateCode.getOrderCount());
//			data.setOrderInitCount(productValidateCode.getOrderInitCount());
//			data.setRefundCount(productValidateCode.getRefundCount());
//			if (productValidateCode.getUsed() == 1) {
//				data.setUsed(1);
//				data.setUsedStr("已验证");
//			} else if (productValidateCode.getUsed() == 0) {
//				data.setUsed(0);
//				data.setUsedStr("未验证");
//			} else {
//				data.setUsed(-1);
//				data.setUsedStr("验证码无效");
//			}
//			if (productValidateCode.getValidateBy() != null) {
//				data.setVaName(productValidateCode.getValidateBy().getUserName());
//			} else {
//				data.setVaName("------");
//			}
//			validate.add(data);
//		}
//
//		return datagrid(validate, pageInfo.getTotalCount());
//	}
//	/**
//	 * 门票重复性验证
//	 * 获取门票名称和数量信息
//	 * @return
//	 */
//	public Result checkVliCode() {
//		String validateCode = (String) getParameter("validateCode");
//		String validateCountStr = (String) getParameter("validateCount");
//		Long scenicId = null;
//		JSONObject json = null;
//		String content = "";
//		if (StringUtils.isEmpty(validateCode)) {
//			content = "验证码无效！";
//			simpleResult(map, false, content);
//			return jsonResult(map);
//		}
//		if (getCompanyUnit().getSysUnitDetail() != null) {
//			scenicId = getCompanyUnit().getSysUnitDetail().getScenicid();
//		}
//		ProductValidateCode productValidateCode = validateCodeService.validateByCode(validateCode, scenicId);
//		if (productValidateCode == null || productValidateCode.getUsed() == 1 || productValidateCode.getUsed() == -1) {
//			content = "验证码无效！";
//			simpleResult(map, false, content);
//			return jsonResult(map);
//		}
//		// 检查验票数量是否合法
//        int validateCount = productValidateCode.getOrderCount();
//        if (StringUtils.isNotBlank(validateCountStr)) {
//            validateCount = Integer.valueOf(validateCountStr);
//            if (validateCount > productValidateCode.getOrderCount()) {
//                content = "超过可验票数量(" + productValidateCode.getOrderCount() + "张)！";
//                simpleResult(map, false, content);
//                return jsonResult(map);
//            }
//        }
//
//		JszxOrder jszxOrder = jszxOrderService.load(productValidateCode.getOrderId());
//		JszxOrderDetail jszxOrderDetail = jszxOrderDetailService.getOrderDetailByOrderNo(productValidateCode.getTicketNo(), jszxOrder);
//		Long endLong = com.zuipin.util.DateUtils.getDateDiffLong(jszxOrderDetail.getEndTime(), new Date());
//		if (endLong < 0 ) {
//			content = "此验证码已经过期，请重新购买！";
//			simpleResult(map, false, content);
//			return jsonResult(map);
//		}
//		map.put("orderNo", productValidateCode.getTicketNo());
//		map.put("productId", productValidateCode.getProduct().getId());
//		map.put("orderCount", productValidateCode.getOrderCount());
//        map.put("validateCount", validateCount);
//		map.put("ticketName", productValidateCode.getProduct().getName());
//		simpleResult(map, true, content);
//		return jsonResult(map);
//	}
//
//	/**
//	 * 门票验证操作
//	 * @return
//	 */
//	public Result valCode() {
//		String validateCode = (String) getParameter("validateCode");
//        String validateCountStr = (String) getParameter("validateCount");
//
//		String content = "";
//		if (!StringUtils.isEmpty(validateCode)) {
//			Long scenicId = null;
//			if (getCompanyUnit().getSysUnitDetail() != null) {
//				scenicId = getCompanyUnit().getSysUnitDetail().getScenicid();
//			}
//			ProductValidateCode productValidateCode = validateCodeService.validateByCode(validateCode, scenicId);
//			if (productValidateCode == null || productValidateCode.getUsed() == 1 || productValidateCode.getUsed() == -1) {
//				content = "验证码无效！";
//                simpleResult(map, false, content);
//                JSONObject json = JSONObject.fromObject(map);
//                return json(json);
//            }
//            // 检查验票数量是否合法
//            int validateCount = productValidateCode.getOrderCount();
//            if (StringUtils.isNotBlank(validateCountStr)) {
//                validateCount = Integer.valueOf(validateCountStr);
//                if (validateCount > productValidateCode.getOrderCount()) {
//                    content = "超过可验票数量(" + productValidateCode.getOrderCount() + "张)！";
//                    simpleResult(map, false, content);
//                    return jsonResult(map);
//                }
//            }
//            productValidateCode.setOrderCount(productValidateCode.getOrderCount() - validateCount);     // 可验票数减少
//            productValidateCode.syncUsed(null); // 有效期之前已经判断，此处暂时省略
//            productValidateCode.setValidateBy(getLoginUser());
//            productValidateCode.setUpdateTime(new Date());
//            if (productValidateCode.getTicketNo() != null) {
//                JszxOrderDetailStatus status = JszxOrderDetailStatus.USED;
//                if (productValidateCode.getUsed() == 0) {   // 与验证码状态一致
//                    status = JszxOrderDetailStatus.UNUSED;
//                }
//                jszxOrderDetailService.saveValidateSuccessOrderDetail(productValidateCode.getTicketNo(), validateCount, productValidateCode.getOrderId(), status);
//            }
//            validateCodeService.update(productValidateCode);
//            // 保存验票记录
//            ProductValidateRecord pvr = new ProductValidateRecord();
//            pvr.setProductValidateCode(productValidateCode);
//            pvr.setProductId(productValidateCode.getProduct().getId());
//            pvr.setProductName(productValidateCode.getTicketName());
//            pvr.setScenicId(productValidateCode.getScenicId());
//            pvr.setValidateCount(validateCount);
//            pvr.setValidateBy(getLoginUser().getId());
//            pvr.setValidateTime(new Date());
//            pvr.setBuyerName(productValidateCode.getBuyerName());
//            pvr.setBuyerMobile(productValidateCode.getBuyerMobile());
//            validateCodeService.saveProductValidateRecord(pvr);
//            content = "验证成功！";
//            simpleResult(map, true, content);
//		} else {
//			content = "请输入验证码!";
//			simpleResult(map, false, content);
//		}
//		JSONObject json = JSONObject.fromObject(map);
//		return json(json);
//	}
//
//
//	public Result scemanage() {
//
//		return dispatch();
//	}
//
//	public Result addScemanage() {
//		return dispatch();
//	}
//
//	@AjaxCheck
//	public Result upload() {
//		if (checkFileType()) {
//			String suffix = resourceFileName.substring(resourceFileName.lastIndexOf("."));
//			String newFileName = System.currentTimeMillis() + suffix;
//			String staticPath = propertiesManager.getString("IMG_DIR");
//			String newFilePath = StringUtils.defaultString(folder) + newFileName;
//			FileUtils.copy(resource, staticPath + newFilePath);
//			// 删除旧图片
//			if (StringUtils.isNotBlank(oldFilePath)) {
//				try {
//					FileUtils.deleteFile(staticPath + oldFilePath);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//			map.put("url", newFilePath);
//			simpleResult(map, true, "");
//			return jsonResult(map);
//		} else {
//			simpleResult(map, false, "图片格式不正确");
//			return jsonResult(map);
//		}
//	}
//
//	public Result addNewSite() {
//
//		return null;
//	}
//
//	public Result lockStatus() {
//		String ids = (String) getParameter("ids");
//		if (StringUtils.isNotBlank(ids)) {
//			String[] idArray = ids.split(",");
//			for (String userId : idArray) {
//				SysUser user = sysUserService.load(Long.parseLong(userId));
//				user.setStatus(UserStatus.lock);
//				user.setIsUse(false);
//				user.setUpdateTime(new Date());
//				userService.update(user);
//			}
//		}
//		simpleResult(map, true, "");
//		return jsonResult(map);
//	}
//
//	public Result editStatus() {
//		String ids = (String) getParameter("ids");
//		if (StringUtils.isNotBlank(ids)) {
//			String[] idArray = ids.split(",");
//			for (String userId : idArray) {
//				SysUser user = sysUserService.load(Long.parseLong(userId));
//				user.setStatus(UserStatus.activity);
//				user.setIsUse(true);
//				user.setUpdateTime(new Date());
//				userService.update(user);
//			}
//		}
//		simpleResult(map, true, "");
//		return jsonResult(map);
//	}
//
//	public Result editPassword() {
//
//		String userId = (String) getParameter("userId");
//
//		if (!StringUtils.isEmpty(userId)) {
//			User user = userService.get(Long.parseLong(userId));
//			user.setPassword(Encryption.encry("123456"));
//			userService.update(user);
//		}
//
//		simpleResult(map, true, "");
//
//		return jsonResult(map);
//	}
//
//
//	public Result addSceManager() {
//
//		String sitename = (String) getParameter("sitename");
//		String scenicid = (String) getParameter("scenicid");
//		String name = (String) getParameter("name");
////		String siteurl = (String) getParameter("siteurl");
//		String areaStr = (String) getParameter("area");
//		String filePath = (String) getParameter("filePath");
//
//		TbArea area = new TbArea();
//		if (!StringUtils.isEmpty(areaStr)) {
//			area = tbAreaService.getArea(Long.parseLong(areaStr));
//		}
//
//		String address = (String) getParameter("address");
//		String supplierType = (String) getParameter("supplierType");
//		String businessScope = (String) getParameter("businessScope");
//		String businessType = (String) getParameter("businessType");
//		String telphone = (String) getParameter("telphone");
//		String fax = (String) getParameter("fax");
//		String mainBody = (String) getParameter("mainBody");
//		String businessModel = (String) getParameter("businessModel");
//		String partnerChannel = (String) getParameter("partnerChannel");
//		String partnerUrl = (String) getParameter("partnerUrl");
//		String partnerAdvantage = (String) getParameter("partnerAdvantage");
//
//
//		SysUnitDetail sysUnitDetail = new SysUnitDetail();
//		SysUnit sysUnit = new SysUnit();
//
//		sysUnitDetail.setSupplierType(SupplierType.valueOf(supplierType));
//		sysUnitDetail.setBusinessScope(BusinessScope.valueOf(businessScope));
//		sysUnitDetail.setBusinessType(BusinessType.valueOf(businessType));
//		sysUnitDetail.setBusinessModel(BusinessModel.valueOf(businessModel));
//		sysUnitDetail.setTelphone(telphone);
//		sysUnitDetail.setFax(fax);
//		sysUnitDetail.setMainBody(mainBody);
//		sysUnitDetail.setPartnerAdvantage(partnerAdvantage);
//		sysUnitDetail.setPartnerChannel(partnerChannel);
//		sysUnitDetail.setPartnerUrl(partnerUrl);
//		sysUnitDetail.setLogoImgPath(filePath);
//		ScenicInfo scenic = null;
//		if (!StringUtils.isEmpty(scenicid)) {
//			scenic = scenicInfoService.get(Long.parseLong(scenicid));
//			sysUnitDetail.setScenicid(scenic.getId());
//		}
//
//
//		sysUnit.setSysSite(getSite());
//		sysUnit.setName(name);
//		sysUnit.setDelFlag(false);
//		sysUnit.setAddress(address);
//		sysUnit.setArea(area);
//
//		sysSiteService.addSecAcc(sysUnit, sysUnitDetail);
//
//		simpleResult(map, true, "");
//
//		return jsonResult(map);
//	}
//
//	/**
//	 * 重新上传时异步删除旧图片
//	 * @author caiys
//	 * @date 2015年10月30日 上午9:01:22
//	 * @return
//	 */
//	@AjaxCheck
//	public Result delFile() {
//		// 删除旧图片
//		if (StringUtils.isNotBlank(oldFilePath)) {
//			String staticPath = propertiesManager.getString("IMG_DIR");
////			staticPath = staticPath + "/static";
//			try {
//				FileUtils.deleteFile(staticPath + oldFilePath);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		simpleResult(map, true, "");
//		return jsonResult(map);
//	}
//
//	/**
//	 * 通过关键字获取景点列表
//	 *
//	 * @return
//	 * @throws UnsupportedEncodingException
//	 */
//	public Result getScenicList() throws UnsupportedEncodingException {
//
//		String keyword = (String) getParameter("name_startsWith");
//		if (StringUtils.isBlank(keyword)) {
//			return text("关键字不能为空");
//		}
//		String maxRowsStr = (String) getParameter("maxRows");
//		if (StringUtils.isBlank(maxRowsStr)) {
//			maxRowsStr = "20";
//		}
//		Integer maxRows = Integer.valueOf(maxRowsStr);
//
//		Page page = new Page(1, maxRows);
//		List<ScenicInfo> list = scenicInfoService.listByKeyword(keyword, null, page);
//
//		JsonConfig jsonConfig = JsonFilter.getIncludeConfig();
//		JSONArray json = JSONArray.fromObject(list, jsonConfig);
////		JSONArray json = JSONArray.fromObject(list);
//		return json(json);
//	}
//
//	public Result dataList() {
//
//		Page pageInfo2 = new Page(page, rows);
//
//		String sceId = (String) getParameter("sceId");
//		String cityIdStr = (String) getParameter("cityId");
//		String status = (String) getParameter("status");
//
//		List<SceManager> sceManagerList  = new ArrayList<SceManager>();
//		List<SysUser> users = userService.getUserList(sceId, status, cityIdStr, pageInfo2, getCompanyUnit(), isSupperAdmin(), isSiteAdmin());
//		for (SysUser user : users) {
//			SceManager manager = new SceManager();
//			manager.setId(user.getId());
//			manager.setAccount(user.getAccount());
//			manager.setCreatedTime(user.getCreatedTime());
//			TbArea area = user.getSysUnit().getCompanyUnit().getArea();
//			manager.setCity(area.getName());
//			manager.setStatus(user.getStatus().toString());
//
//			ScenicInfo info = scenicInfoService.get(user.getSysUnit().getCompanyUnit().getSysUnitDetail().getScenicid());
//			if (info != null) {
//				manager.setSceName(info.getName());
//			}
//
//			sceManagerList.add(manager);
//		}
//		return datagrid(sceManagerList, pageInfo2.getTotalCount());
//	}
//
//	public Result getCityLoader() throws UnsupportedEncodingException {
//
//		Object p = getParameter("name_startsWith");
//		String keyword = p.toString();
//		keyword = new String(keyword.getBytes("ISO-8859-1"), "UTF-8");
//		if (keyword == null) {
//			return text("没有keyword");
//		}
//		List<TbArea> list = tbAreaService.getAreaByName(keyword.toString());
//
//		JsonConfig jsonConfig = JsonFilter.getIncludeConfig();
//		JSONArray json = JSONArray.fromObject(list, jsonConfig);
////		JSONArray json = JSONArray.fromObject(list);
//
//		return json(json);
//	}
//
//
//	/**
//	 * 获取省
//	 * @return
//	 * @throws UnsupportedEncodingException
//	 */
//	public Result getProvince() throws UnsupportedEncodingException {
//
//		Object p = getParameter("name_startsWith");
//		String keyword = p.toString();
//		keyword = new String(keyword.getBytes("ISO-8859-1"), "UTF-8");
//		if (keyword == null) {
//			return text("没有keyword");
//		}
//		List<TbArea> list = tbAreaService.getProByFather(keyword.toString());
//		JsonConfig jsonConfig = JsonFilter.getIncludeConfig();
//		JSONArray json = JSONArray.fromObject(list, jsonConfig);
////		JSONArray json = JSONArray.fromObject(list);
//		return json(json);
//	}
//
//	/**
//	 * 验证景点帐号名称的唯一性
//	 * @return
//	 */
//	public Result checkAccountIsValidate() {
//
//		String inputName = (String) getParameter("newName");
//		if (StringUtils.isNotBlank(inputName)) {
//			int unitCount = sysUnitService.getCountUnitByName(inputName);
//			int userCount = sysUserService.getCountUserByAccount(inputName);
//			if (unitCount > 0 && userCount > 0) {
//				simpleResult(map, false, "");
//				return jsonResult(map);
//			}
//		}
//		simpleResult(map, true, "");
//		return jsonResult(map);
//	}
//
//
//	/**
//	 * 获取市
//	 * @return
//	 * @throws UnsupportedEncodingException
//	 */
//	public Result getCity() throws UnsupportedEncodingException {
//
//		Object p = getParameter("name_startsWith");
//		String keyword = p.toString();
//		keyword = new String(keyword.getBytes("ISO-8859-1"), "UTF-8");
//		if (keyword == null) {
//			return text("没有keyword");
//		}
//		List<TbArea> list = tbAreaService.getCityByPro(keyword.toString(), 2);
//
//		JsonConfig jsonConfig = JsonFilter.getIncludeConfig();
//		JSONArray json = JSONArray.fromObject(list, jsonConfig);
////		JSONArray json = JSONArray.fromObject(list);
//		return json(json);
//	}
//
//	/**
//	 * 通过获取的citycode找到该景点的所属区域
//	 * @return
//	 * @throws UnsupportedEncodingException
//	 */
//	public Result getAreaByCitycode() throws UnsupportedEncodingException {
//
//		Object p = getParameter("cityCode");
//		String keyword = p.toString();
//		keyword = new String(keyword.getBytes("ISO-8859-1"), "UTF-8");
//		if (keyword == null) {
//			return text("没有keyword");
//		}
//		TbArea tbarea = tbAreaService.getAreaByCityCode(keyword.toString());
//
//		JsonConfig jsonConfig = JsonFilter.getIncludeConfig();
//		JSONObject json = JSONObject.fromObject(tbarea, jsonConfig);
////		JSONObject json = JSONObject.fromObject(tbarea);
//		return json(json);
//	}
//
//	@Override
//	public SceManager getModel() {
//		return sceManager;
//	}
//
//	public SceManager getSceManager() {
//		return sceManager;
//	}
//
//	public void setSceManager(SceManager sceManager) {
//		this.sceManager = sceManager;
//	}
//	/**
//	 * 检查是否是图片格式
//	 * @author caiys
//	 * @date 2015年10月28日 下午3:19:01
//	 * @return
//	 */
//	public boolean checkFileType() {
//		if (StringUtils.isBlank(resourceContentType)) {
//			return false;
//		}
//		return resourceContentType.startsWith("image/");
//	}
//
//
//	public File getResource() {
//		return resource;
//	}
//
//
//	public void setResource(File resource) {
//		this.resource = resource;
//	}
//
//
//	public String getResourceFileName() {
//		return resourceFileName;
//	}
//
//
//	public void setResourceFileName(String resourceFileName) {
//		this.resourceFileName = resourceFileName;
//	}
//
//
//	public String getResourceContentType() {
//		return resourceContentType;
//	}
//
//
//	public void setResourceContentType(String resourceContentType) {
//		this.resourceContentType = resourceContentType;
//	}
//
//
//	public String getOldFilePath() {
//		return oldFilePath;
//	}
//
//
//	public void setOldFilePath(String oldFilePath) {
//		this.oldFilePath = oldFilePath;
//	}
//
//
//	public String getFolder() {
//		return folder;
//	}
//
//
//	public void setFolder(String folder) {
//		this.folder = folder;
//	}
//
//
//	public Integer getPage() {
//		return page;
//	}
//
//
//	public void setPage(Integer page) {
//		this.page = page;
//	}
//
//
//	public Integer getRows() {
//		return rows;
//	}
//
//
//	public void setRows(Integer rows) {
//		this.rows = rows;
//	}
//
//
//}
