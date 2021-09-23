package com.data.data.hmly.service.common;

import com.data.data.hmly.service.SysUnitService;
import com.data.data.hmly.service.SysUserService;
import com.data.data.hmly.service.common.dao.ProductValidateCodeDao;
import com.data.data.hmly.service.common.dao.ProductValidateRecordDao;
import com.data.data.hmly.service.common.entity.ProductValidateCode;
import com.data.data.hmly.service.common.entity.ProductValidateRecord;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.common.util.DateUtils;
import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.SysUnitDetail;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.entity.UserType;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.google.common.collect.Lists;
import com.zuipin.util.PropertiesManager;
import com.zuipin.util.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.hibernate.criterion.Order;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guoshijie on 2015/11/9.
 */
@Service
@Deprecated
public class ProductValidateCodeService {
	@Resource
	private ProductValidateCodeDao productValidateCodeDao;
	@Resource
	private ProductValidateRecordDao productValidateRecordDao;

	@Resource
	private SysUnitService sysUnitService;

	@Resource
	private SysUserService sysUserService;

	@Resource
	private PropertiesManager propertiesManager;

	public void saveProductValidateRecord(ProductValidateRecord productValidateRecord) {
		productValidateRecordDao.save(productValidateRecord);
	}

	/**
	 * 查询验票记录
	 * @param pvr
	 * @param pageInfo
	 * @return
	 */
	public List<ProductValidateRecord> findValidateRecords(ProductValidateRecord pvr, Page pageInfo) {
		return productValidateRecordDao.findValidateRecords(pvr, pageInfo);
	}

	public ProductValidateCode getPvCode(String ticketNo) {
		Criteria<ProductValidateCode>  criteria = new Criteria<ProductValidateCode>(ProductValidateCode.class);
		criteria.eq("ticketNo", ticketNo);
		return productValidateCodeDao.findUniqueByCriteria(criteria);
	}

    public List<ProductValidateCode> getByOrderId(Long orderId) {
        Criteria<ProductValidateCode>  criteria = new Criteria<ProductValidateCode>(ProductValidateCode.class);
        criteria.eq("orderId", orderId);
        return productValidateCodeDao.findByCriteria(criteria);
    }

	public ProductValidateCode getValidateByProductCode(ProductValidateCode productValidateCode) {
		Criteria<ProductValidateCode>  criteria = new Criteria<ProductValidateCode>(ProductValidateCode.class);
		criteria.eq("ticketNo", productValidateCode.getTicketNo());
		criteria.eq("orderId", productValidateCode.getOrderId());
		return productValidateCodeDao.findUniqueByCriteria(criteria);
	}

	public ProductValidateCode getById(Long id) {
		return productValidateCodeDao.load(id);
	}

	public ProductValidateCode validateByCode(String validateCode, Long scenicId) {
		
		Criteria<ProductValidateCode>  criteria = new Criteria<ProductValidateCode>(ProductValidateCode.class);
		if (scenicId != null) {
//			criteria.createCriteria("product", "tp", JoinType.LEFT_OUTER_JOIN);
//			criteria.eq("tp.proType", ProductType.scenic);
//			criteria.eq("tp.scenicInfo.id", scenicId);
			criteria.eq("scenicId", scenicId);
		}
		criteria.eq("code", validateCode);
		criteria.ne("used", 1);
		criteria.ne("used", -1);
		
		ProductValidateCode productValidateCode = productValidateCodeDao.findUniqueByCriteria(criteria);
		
		return productValidateCode;
	}

	public void update(ProductValidateCode productValidateCode) {
		productValidateCodeDao.update(productValidateCode);
	}

	public List<ProductValidateCode> findValidatesByPids(
			List<Long> pIds, String isUsed, Page pageInfo) {
		
		
		Criteria<ProductValidateCode> criteria = new Criteria<ProductValidateCode>(ProductValidateCode.class);
		
		criteria.in("product.id", pIds);
		if ("1".equals(isUsed) && isUsed != null) {
			criteria.eq("used", 1);
		}
		criteria.orderBy("updateTime", "DESC");
		return productValidateCodeDao.findByCriteria(criteria, pageInfo);
	}

	public ProductValidateCode findValidateCodeByOrderNo(String ticketNo) {
		Criteria<ProductValidateCode> criteria = new Criteria<ProductValidateCode>(ProductValidateCode.class);
		criteria.eq("ticketNo", ticketNo);
		criteria.eq("used", 0);
		return productValidateCodeDao.findByCriteria(criteria).get(0);
	}


	public List<ProductValidateCode> findValidateCodesByOrderNo(List<String> ticketNos) {

		Criteria<ProductValidateCode> criteria = new Criteria<ProductValidateCode>(ProductValidateCode.class);
		criteria.in("ticketNo", ticketNos);
//		criteria.eq("used", 0);
		return productValidateCodeDao.findByCriteria(criteria);

	}

	public void saveAll(List<ProductValidateCode> list) {
		productValidateCodeDao.save(list);
	}

	public void save(ProductValidateCode productValidateCode) {
		productValidateCodeDao.save(productValidateCode);
	}

	public List<ProductValidateCode> findValidatesList(Page pageInfo, ProductValidateCode searchParams, SysUnit sysUnit, Boolean isSupperAdmin, Boolean isSiteAdmin) {
		Criteria<ProductValidateCode> criteria = new Criteria<ProductValidateCode>(ProductValidateCode.class);
		criteria.createCriteria("product", "tp", JoinType.LEFT_OUTER_JOIN);
		criteria.eq("tp.proType", ProductType.scenic);
		// 数据过滤
		if (!isSupperAdmin) {
			if (!isSiteAdmin) {		// 非站点管理员-查询相关景点验票
				if (searchParams.getScenicId() != null) {
					criteria.eq("tp.scenicInfo.id", searchParams.getScenicId());
				}
			} else {	// 站点管理员-查询该站点下验票
				criteria.createCriteria("tp.companyUnit", "cu", JoinType.INNER_JOIN);
				criteria.eq("cu.sysSite.id", sysUnit.getSysSite().getId());
			}
		}
		
		if (!StringUtils.isEmpty(searchParams.getTicketName())) {
			criteria.like("tp.name", "%" + searchParams.getTicketName() + "%");
		}

		if (!StringUtils.isEmpty(searchParams.getOrderNo())) {
			criteria.eq("orderNo", searchParams.getOrderNo());
		}

		if (searchParams.getUpdateTimeStart() != null) {
			criteria.ge("createTime", searchParams.getUpdateTimeStart());
		}

		if (searchParams.getUpdateTimeEnd() != null) {
			criteria.le("createTime", searchParams.getUpdateTimeEnd());
		}

		if (searchParams.getUsed() != null) {
			criteria.eq("used", searchParams.getUsed());
		}

		criteria.orderBy("createTime", "DESC");

		return productValidateCodeDao.findByCriteria(criteria, pageInfo);
	}

	/**
	 * 验票记录查询
	 * @param pageInfo
	 * @param pvc
	 * @param user
	 * @param isSupperAdmin
	 * @param isSiteAdmin
	 * @return
	 */
	public List<ProductValidateCode> findCheckRecord(Page pageInfo, ProductValidateCode pvc, SysUser user, Boolean isSupperAdmin, Boolean isSiteAdmin) {
		Criteria<ProductValidateCode> criteria = new Criteria<ProductValidateCode>(ProductValidateCode.class);
		criteria.createCriteria("product", "tp", JoinType.LEFT_OUTER_JOIN);
		criteria.eq("tp.proType", ProductType.scenic);
		// 数据过滤
		if (!isSupperAdmin) {
			if (!isSiteAdmin) {		// 非站点管理员-查询相关景点验票
				criteria.eq("tp.scenicInfo.id", user.getSysUnit().getCompanyUnit().getSysUnitDetail().getScenicid());
			} else {	// 站点管理员-查询该站点下验票
				criteria.createCriteria("tp.companyUnit", "cu", JoinType.INNER_JOIN);
				criteria.eq("cu.sysSite.id", user.getSysUnit().getCompanyUnit().getSysSite().getId());
			}
		}
		// 是否使用
		if (pvc.getUsed() != null) {
			criteria.eq("used", pvc.getUsed());
		}
		if (pvc.getUpdateTimeStart() != null) {
			criteria.ge("updateTime", pvc.getUpdateTimeStart());
		}
		if (pvc.getUpdateTimeEnd() != null) {
			criteria.le("updateTime", pvc.getUpdateTimeEnd());
		}
		criteria.orderBy("updateTime", "DESC");
		return productValidateCodeDao.findByCriteria(criteria, pageInfo);
	}

	public ProductValidateCode checkVliadateCode(String code, ProductValidateCode productValidateCode) {
		Criteria<ProductValidateCode> criteria = new Criteria<ProductValidateCode>(ProductValidateCode.class);
		criteria.eq("code", code);
		criteria.eq("used", 0);
		if (productValidateCode.getScenicId() != null) {
			criteria.eq("scenicId", productValidateCode.getScenicId());
		}
		if (productValidateCode.getSupplierId() != null) {
			criteria.eq("supplierId", productValidateCode.getSupplierId());
		}
//		criteria.createCriteria("product", "tp", JoinType.LEFT_OUTER_JOIN);
//		criteria.eq("tp.proType", ProductType.scenic);
//		criteria.eq("tp.companyUnit", productValidateCode.getProduct().getCompanyUnit());
		ProductValidateCode resultPvCode = productValidateCodeDao.findUniqueByCriteria(criteria);
		if (resultPvCode != null) {
			return resultPvCode;
		} else {
			return null;
		}
	}




	public List<ProductValidateCode> getValidateInfoList(ProductValidateCode productValidateCode) {
		Criteria<ProductValidateCode> criteria = new Criteria<ProductValidateCode>(ProductValidateCode.class);
		if (productValidateCode.getOrderId() != null) {
			criteria.eq("orderId", productValidateCode.getOrderId());
		}
		if (productValidateCode.getUsed() != null) {
			criteria.eq("used", productValidateCode.getUsed());
		}
		return productValidateCodeDao.findByCriteria(criteria);
	}

	public List<ProductValidateRecord> findValidateRecodsInfoList(List<ProductValidateCode> codeList) {
		Criteria<ProductValidateRecord> criteria = new Criteria<ProductValidateRecord>(ProductValidateRecord.class);
		criteria.in("productValidateCode", codeList);
		criteria.orderBy(Order.desc("validateTime"));
		return productValidateRecordDao.findByCriteria(criteria);
	}

	public Map<String, Object> doLoadExcel(Date startTime, Date endTime,
							  SysUnit companyUnit, SysUser loginUser, SysUnitDetail unitDetail,
							  Boolean supperAdmin, Boolean siteAdmin, boolean isSuplier) {
		List<ProductValidateRecord> productValidateCodes = findValidateCodeExcelList(startTime, endTime, loginUser,
				companyUnit, unitDetail,
				supperAdmin, siteAdmin, isSuplier);

		Map<String, Object> map = new HashMap<String, Object>();

		if (productValidateCodes.isEmpty()) {
			map.put("success", false);
			map.put("info", "当前没有可导出的数据！");
			return map;
		}

		Workbook wb = new HSSFWorkbook();

		CellStyle cellStyleFirst = wb.createCellStyle();
		Font fontFirst = wb.createFont();

		CellStyle cellStyle = wb.createCellStyle();
		Font font = wb.createFont();


		Sheet sheet = wb.createSheet("门票验证列表");
		Row row = sheet.createRow(0);
		//表头
		createFirstCell(wb, row, cellStyleFirst, fontFirst, (short) 0, "订单编号");
		createFirstCell(wb, row, cellStyleFirst, fontFirst, (short) 1, "产品名称+票种类型");
		createFirstCell(wb, row, cellStyleFirst, fontFirst, (short) 2, "销售商");
		createFirstCell(wb, row, cellStyleFirst, fontFirst, (short) 3, "订单用户");
		createFirstCell(wb, row, cellStyleFirst, fontFirst, (short) 4, "手机号");
		createFirstCell(wb, row, cellStyleFirst, fontFirst, (short) 5, "下单时间");
		createFirstCell(wb, row, cellStyleFirst, fontFirst, (short) 6, "验证票数");
//		createFirstCell(wb, row, cellStyleFirst, fontFirst, (short) 7, "验证码");
		createFirstCell(wb, row, cellStyleFirst, fontFirst, (short) 7, "验证时间");
		createFirstCell(wb, row, cellStyleFirst, fontFirst, (short) 8, "操作人");

		for (int i = 0; i < productValidateCodes.size(); i++) {
			ProductValidateRecord productValidateRecord = productValidateCodes.get(i);
			ProductValidateCode productValidateCode = productValidateRecord.getProductValidateCode();

			Row rowFor = sheet.createRow(i + 1);
			//订单编号
			createTableCell(wb, rowFor, cellStyle, font, (short) 0, productValidateCode.getOrderNo());
			//产品名称+票种类型
			createTableCell(wb, rowFor, cellStyle, font, (short) 1, productValidateRecord.getProductName());
			//销售商
			createTableCell(wb, rowFor, cellStyle, font, (short) 2, productValidateCode.getProduct().getUser().getUserName());
			//订单用户
			createTableCell(wb, rowFor, cellStyle, font, (short) 3, productValidateRecord.getBuyerName());
			//手机号
			createTableCell(wb, rowFor, cellStyle, font, (short) 4, productValidateRecord.getBuyerMobile());
			//下单时间
			String createTimeStr = DateUtils.format(productValidateCode.getCreateTime(), "yyyy-MM-dd HH:mm");
			createTableCell(wb, rowFor, cellStyle, font, (short) 5, createTimeStr);
			//验证票数
			createTableCell(wb, rowFor, cellStyle, font, (short) 6, productValidateRecord.getValidateCount().toString());
			//验证时间
			createTableCell(wb, rowFor, cellStyle, font, (short) 7, DateUtils.format(productValidateRecord.getValidateTime(), "yyyy-MM-dd HH:mm"));
			//操作人
			createTableCell(wb, rowFor, cellStyle, font, (short) 8, sysUserService.load(productValidateRecord.getValidateBy()).getAccount());

		}
		sheet.autoSizeColumn(0); //adjust width of the first column
		sheet.autoSizeColumn(1); //adjust width of the second column
		sheet.autoSizeColumn(2);
		sheet.autoSizeColumn(3);
		sheet.autoSizeColumn(4);
		sheet.autoSizeColumn(5);
		sheet.autoSizeColumn(6);
		sheet.autoSizeColumn(7);
		sheet.autoSizeColumn(8);


		FileOutputStream fileOut = null;
		String fileName = new Date().getTime() + ".xls";
		String staticPath = propertiesManager.getString("IMG_DIR");
        File fileDir = new File(staticPath + "/tempfile/");
        if (!fileDir.exists()) {
            fileDir.mkdir();
        }
		String fn = staticPath + "/tempfile/" + fileName;
		try {

			fileOut = new FileOutputStream(fn);
			wb.write(fileOut);
			fileOut.close();
		} catch (IOException e) {
			System.out.println("++++++++++++++++++++++++" + e);
		}
		map.put("downloadPath", "/tempfile/" + fileName);
		map.put("success", true);
		map.put("info", "导出成功！");
		return map;
	}

	public List<ProductValidateRecord> findValidateCodeExcelList(Date startTime, Date endTime,
																 SysUser loginUser, SysUnit companyUnit, SysUnitDetail unitDetail,
																 Boolean supperAdmin, Boolean siteAdmin, Boolean isSuplier) {

		String hql = "select pvdr from ProductValidateCode pvdc, ProductValidateRecord pvdr, JszxOrder jord where pvdc.id = pvdr.productValidateCode.id" +
				" and jord.id=pvdc.orderId";

		StringBuffer sb = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		if (!supperAdmin) {
			if (!siteAdmin) {		// 非站点管理员-查询相关景点验票
				if (unitDetail != null) {
					if (loginUser.getUserType() == UserType.ScenicManage) {
						sb.append(" and pvdc.scenicId=");
						sb.append(unitDetail.getScenicid());
					} else {
						if (isSuplier) {
							sb.append(" and jord.supplierUnit.id=");
							sb.append(companyUnit.getId());
						} else {
							sb.append(" and jord.companyUnit.id=");
							sb.append(companyUnit.getId());
						}

					}
				}
			} else {	// 站点管理员-查询该站点下验票
				sb.append(" and pvdc.product.companyUnit.sysSite.id=");
				sb.append(companyUnit.getSysSite().getId());
			}
		}

		if (startTime != null) {
			sb.append(" and pvdr.validateTime>=?");
			params.add(startTime);
		}
		if (endTime != null) {
			sb.append(" and pvdr.validateTime<=?");
			params.add(endTime);
		}

		sb.append(" order by pvdc.updateTime");

		hql += sb.toString();

		List<ProductValidateRecord> productValidateCodes = productValidateCodeDao.findByHQL(hql, params.toArray());

		return productValidateCodes;
	}

	/**
	 * 创建表格头体列
	 * @param wb
	 * @param row
	 * @param column
	 * @param value
	 */
	public void createFirstCell(Workbook wb, Row row, CellStyle style, Font font, short column, String value) {
		Cell cell = row.createCell(column);
		cell.setCellValue(value);
		CellStyle cellStyle = getFirstStyle(style, font);
		cell.setCellStyle(cellStyle);
	}

	/**
	 * 创建表格主体列
	 * @param wb
	 * @param row
	 * @param column
	 * @param value
	 */
	public void createTableCell(Workbook wb, Row row, CellStyle style, Font font, short column, String value) {
		Cell cell = row.createCell(column);
		cell.setCellValue(value);
		CellStyle cellStyle = getTableStyle(style, font);
		cell.setCellStyle(cellStyle);
	}

	/**
	 * 表格头体样式
	 * @param style
	 * @param font
	 * @return
	 */
	public CellStyle getFirstStyle(CellStyle style, Font font) {
		font.setFontName("宋体");
		font.setFontHeightInPoints((short) 11); // 设置字体大小
		font.setBoldweight(Font.BOLDWEIGHT_NORMAL);
		style.setAlignment(CellStyle.ALIGN_CENTER); // 左右居中
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER); // 上下居中
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setFillBackgroundColor(IndexedColors.BLUE.getIndex()); //设置背景颜色
		style.setWrapText(true);
		style.setFont(font);
		return style;
	}

	/**
	 * 表格主体样式
	 * @param style
	 * @param font
	 * @return
	 */
	public CellStyle getTableStyle(CellStyle style, Font font) {

		font.setFontName("宋体");
		font.setFontHeightInPoints((short) 11); // 设置字体大小
		font.setBoldweight(Font.BOLDWEIGHT_NORMAL);
		style.setAlignment(CellStyle.ALIGN_LEFT); // 左右居中
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER); // 上下居中
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setWrapText(true);
		style.setFont(font);
		return style;
	}


	public List<ProductValidateCode> getYhyValidateCodeList(ProductValidateCode productValidateCode, String ticketType, Page pageInfo) {
//		select p.id, p.name, pvcd.order_id, pvcd.buyerName, pvcd.buyerMobile, pvcd.supplierName from productvalidatecode pvcd left join torderdetail toad on toad.orderid = pvcd.order_id left join product p on p.id = toad.proId group by pvcd.order_id order by pvcd.createTime desc;

		List param = new ArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append("select p.id as id, p.name as productName, pvcd.orderNo as orderNo, pvcd.order_id as orderId, pvcd.buyerName as buyerName, pvcd.buyerMobile as buyerMobile, pvcd.supplierName as supplierName from productvalidatecode pvcd " +
				"left join torderdetail toad on toad.orderid = pvcd.order_id " +
				"left join product p on p.id = toad.proId");

		String sqlQueryParams = "";
		if (StringUtils.isNotBlank(productValidateCode.getOrderNo())) {
			if (sqlQueryParams.length() > 0) {
				sqlQueryParams += " and pvcd.orderNo like '%" + productValidateCode.getOrderNo() + "%'";
			} else {
				sqlQueryParams += " where pvcd.orderNo like '%" + productValidateCode.getOrderNo() + "%'";
			}
		}

		if (StringUtils.isNotBlank(productValidateCode.getProductName())) {
			if (sqlQueryParams.length() > 0) {
				sqlQueryParams += " and p.name like '%" + productValidateCode.getProductName() + "%'";
			} else {
				sqlQueryParams += " where p.name like '%" + productValidateCode.getProductName() + "%'";
			}
		}

		if (productValidateCode.getUsed() != null) {
			if (sqlQueryParams.length() > 0) {
				sqlQueryParams += " and pvcd.used =?";
			} else {
				sqlQueryParams += " where pvcd.used =?";
			}
			param.add(productValidateCode.getUsed());
		}

		if (sqlQueryParams.length() > 0) {
			sql.append(sqlQueryParams);
		}
		sql.append(" group by pvcd.order_id");
		sql.append(" order by pvcd.createTime desc");
		List<Map<String, Object>> resultMapList = productValidateCodeDao.findEntitiesBySQL4(sql.toString(), pageInfo, param.toArray());
		List<ProductValidateCode> resultList = Lists.newArrayList();

		for (Map<String, Object> map : resultMapList) {
			ProductValidateCode validateCode = new ProductValidateCode();
			if (map.get("orderId") == null) {
				break;
			}
			productValidateCode.setOrderId(Long.parseLong(map.get("orderId").toString()));
			List<ProductValidateCode> productValidateCodeList = getValidateInfoList(productValidateCode);
			validateCode.setProductValidateCodeList(productValidateCodeList);
			if (map.get("productName") != null) {
				validateCode.setProductName(map.get("productName").toString());
			}
			if (map.get("orderId") != null) {
				validateCode.setOrderId(Long.parseLong(map.get("orderId").toString()));
			}
			if (map.get("buyerName") != null) {
				validateCode.setBuyerName((String) map.get("buyerName"));
			}
			if (map.get("buyerMobile") != null) {
				validateCode.setBuyerMobile((String) map.get("buyerMobile"));
			}
			if (map.get("supplierName") != null) {
				validateCode.setSupplierName((String) map.get("supplierName"));
			}
			if (map.get("orderNo") != null) {
				validateCode.setOrderNo((String) map.get("orderNo"));
			}
			resultList.add(validateCode);
		}
		return resultList;
	}
}
