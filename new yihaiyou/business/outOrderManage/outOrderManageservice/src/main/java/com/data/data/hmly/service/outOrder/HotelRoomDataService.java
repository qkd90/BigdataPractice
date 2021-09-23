package com.data.data.hmly.service.outOrder;

import com.data.data.hmly.service.hotel.entity.Hotel;
import com.data.data.hmly.service.hotel.entity.HotelPrice;
import com.data.data.hmly.service.order.entity.HotelRoomData;
import com.data.data.hmly.service.order.entity.OrderDetail;
import com.data.data.hmly.service.order.entity.OrderTourist;
import com.zuipin.util.DateUtils;
import com.zuipin.util.PropertiesManager;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2016/9/13.
 */
@Service
public class HotelRoomDataService {

    @Resource
    private PropertiesManager propertiesManager;
    @Resource
    private JszxOrderService jszxOrderService;


    public Map<String, Object> getHotelRoomDataTable(List<HotelRoomData> hotelRoomDataList, Map<String, Object> result, Integer days) {

        Workbook wb = new HSSFWorkbook();
        CellStyle cellStyle = wb.createCellStyle();
        Font font = wb.createFont();
        Sheet sheet = wb.createSheet("酒店房态数据");
        Row row = sheet.createRow(0);
        // 表头
        jszxOrderService.createFirstCell(wb, row, cellStyle, font, (short) 0, "酒店名称");
        jszxOrderService.createFirstCell(wb, row, cellStyle, font, (short) 1, "房型名称");
        jszxOrderService.createFirstCell(wb, row, cellStyle, font, (short) 2, "预订数量");
        jszxOrderService.createFirstCell(wb, row, cellStyle, font, (short) 3, "客户信息");
        jszxOrderService.createFirstCell(wb, row, cellStyle, font, (short) 4, "联系方式");
        jszxOrderService.createFirstCell(wb, row, cellStyle, font, (short) 5, "入住日期");
        jszxOrderService.createFirstCell(wb, row, cellStyle, font, (short) 6, "退房日期");
        jszxOrderService.createFirstCell(wb, row, cellStyle, font, (short) 7, "订单状态");

        for (HotelRoomData hotelRoomData : hotelRoomDataList) {
            Hotel hotel = hotelRoomData.getHotel();
            HotelPrice hotelPrice = hotelRoomData.getHotelPrice();
            List<OrderDetail> orderDetailList = hotelRoomData.getOrderDetailList();
            if (orderDetailList.isEmpty()) {
                // 空订单详情, 跳过, 不导出 (// 后续可以修改为导出空记录用于提示)
                continue;
            }
            // 创建表格数据
            this.createTableData(hotelRoomData, sheet, wb, cellStyle, font);
            // 处理表格文件
            FileOutputStream fileOutputStream = null;
            Date nowDate = new Date();
            Calendar endCalendar = Calendar.getInstance();
            endCalendar.setTime(nowDate);
            endCalendar.add(Calendar.DAY_OF_YEAR, days);
            Date endDate = endCalendar.getTime();
            String fileName = "hotel_room_data_" + DateUtils.format(nowDate, "yyyy-MM-dd") + "_" + DateUtils.format(endDate, "yyyy-MM-dd") + ".xls";
            String baseFilePath = propertiesManager.getString("IMG_DIR");
            String filePath = baseFilePath + "/tempfile/" + fileName;
            File file = new File(baseFilePath + "/tempfile/");
            if (!file.exists()) {
                file.mkdir();
            }
            try {
                fileOutputStream = new FileOutputStream(filePath);
                wb.write(fileOutputStream);
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
                result.put("success", false);
                result.put("msg", "文件读写错误!");
            }
            result.put("success", true);
            result.put("fileUrl", "/tempfile/" + fileName);
            result.put("msg", "导出成功!");
        }
        return result;
    }

    /**
     * 创建表格数据
     * @param hotelRoomData
     * @param wb
     * @param cellStyle
     * @param font
     */
    private void createTableData(HotelRoomData hotelRoomData, Sheet sheet, Workbook wb, CellStyle cellStyle, Font font) {
        Hotel hotel = hotelRoomData.getHotel();
        HotelPrice hotelPrice = hotelRoomData.getHotelPrice();
        List<OrderDetail> orderDetailList = hotelRoomData.getOrderDetailList();
        int mergeCount = 0;
        int j = 1; // 行数记录
        for (OrderDetail orderDetail : orderDetailList) {
            List<OrderTourist> orderTouristList = orderDetail.getOrderTouristList();
            mergeCount = orderTouristList.size();
            for (OrderTourist orderTourist : orderTouristList) {
                Row dataRow = sheet.createRow(j);
                // 数据行记录
                // 酒店名称
                jszxOrderService.createTableCell(wb, dataRow, cellStyle, font, (short) 0, hotel.getName());
                // 房型名称
                jszxOrderService.createTableCell(wb, dataRow, cellStyle, font, (short) 1, hotelPrice.getRoomName());
                // 预订数量
                jszxOrderService.createTableCell(wb, dataRow, cellStyle, font, (short) 2, Integer.toString(orderDetail.getNum()));
                // 客户信息
                jszxOrderService.createTableCell(wb, dataRow, cellStyle, font, (short) 3, orderTourist.getName() + "(" + orderTourist.getIdNumber() + ")");
                // 联系方式
                jszxOrderService.createTableCell(wb, dataRow, cellStyle, font, (short) 4, orderTourist.getTel());
                // 入住日期
                jszxOrderService.createTableCell(wb, dataRow, cellStyle, font, (short) 5, DateUtils.format(orderDetail.getPlayDate(), "yyyy-MM-dd"));
                // 退房日期
                jszxOrderService.createTableCell(wb, dataRow, cellStyle, font, (short) 6, DateUtils.format(orderDetail.getLeaveDate(), "yyyy-MM-dd"));
                // 订单状态
                jszxOrderService.createTableCell(wb, dataRow, cellStyle, font, (short) 7, orderDetail.getStatus().getDescription());
                // 行数加一
                j++;
            }
            // 单元格合并处理
            if (mergeCount > 1) {
                sheet.addMergedRegion(new CellRangeAddress(j - mergeCount, j - 1 , 0, 0));
                sheet.addMergedRegion(new CellRangeAddress(j - mergeCount, j - 1 , 1, 1));
                sheet.addMergedRegion(new CellRangeAddress(j - mergeCount, j - 1 , 2, 2));
                sheet.addMergedRegion(new CellRangeAddress(j - mergeCount, j - 1 , 5, 5));
                sheet.addMergedRegion(new CellRangeAddress(j - mergeCount, j - 1 , 6, 6));
                sheet.addMergedRegion(new CellRangeAddress(j - mergeCount, j - 1 , 7, 7));
            }
        }
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);
        sheet.autoSizeColumn(4);
        sheet.autoSizeColumn(5);
        sheet.autoSizeColumn(6);
        sheet.autoSizeColumn(7);
    }
}
