package com.data.data.hmly.service.hotel;

import com.data.data.hmly.service.common.entity.enums.ProductStatus;
import com.data.data.hmly.service.hotel.dao.HotelRoomDao;
import com.data.data.hmly.service.hotel.entity.HotelPrice;
import com.data.data.hmly.service.hotel.entity.HotelRoom;
import com.data.data.hmly.service.hotel.entity.enums.HotelRoomStatus;
import com.framework.hibernate.util.Criteria;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zzl on 2016/11/23.
 */
@Service
public class HotelRoomService {

    @Resource
    private HotelRoomDao hotelRoomDao;

    public HotelRoom load(Long id) {
        return hotelRoomDao.load(id);
    }

    public HotelRoom get(Long id) {
        return hotelRoomDao.get(HotelRoom.class, id);
    }

    public void save(HotelRoom hotelRoom) {
        hotelRoomDao.save(hotelRoom);
    }

    public void saveAll(List<HotelRoom> hotelRooms) {
        hotelRoomDao.save(hotelRooms);
    }

    public void updata(HotelRoom hotelRoom) {
        hotelRoomDao.update(hotelRoom);
    }

    public void updateAll(List<HotelRoom> hotelRooms) {
        hotelRoomDao.updateAll(hotelRooms);
    }

    public String getRoomNumbers(Long hotelPriceId, ProductStatus status) {
        Criteria<HotelRoom> criteria = new Criteria<HotelRoom>(HotelRoom.class);
        criteria.eq("hotelPriceId", hotelPriceId);
        if (status != null) {
            criteria.eq("status", status);
        }
        List<HotelRoom> hotelRoomList = hotelRoomDao.findByCriteria(criteria);
        String roomNums = "";
        for (HotelRoom hotelRoom : hotelRoomList) {
            roomNums += hotelRoom.getRoomNum() + ",";
        }
        if (roomNums.length() > 0 ) {
            return roomNums.substring(0, roomNums.length() - 1);
        } else {
            return roomNums;
        }
    }

    public String getAvailableRoomNumbers(Long hotelPriceId) {
        Criteria<HotelRoom> criteria = new Criteria<HotelRoom>(HotelRoom.class);
        criteria.eq("hotelPriceId", hotelPriceId);
        criteria.eq("roomStatus", HotelRoomStatus.AVAILABLE);
        List<HotelRoom> hotelRoomList =  hotelRoomDao.findByCriteria(criteria);
        String roomNums = "";
        for (HotelRoom hotelRoom : hotelRoomList) {
            roomNums += hotelRoom.getRoomNum() + ",";
        }
        if (roomNums.length() > 0 ) {
            return roomNums.substring(0, roomNums.length() - 1);
        } else {
            return roomNums;
        }
    }

    public List<HotelRoom> getRoomList(Long hotelPriceId) {
        Criteria<HotelRoom> criteria = new Criteria<HotelRoom>(HotelRoom.class);
        criteria.eq("hotelPriceId", hotelPriceId);
        return hotelRoomDao.findByCriteria(criteria);
    }


    public String getRoomNumbers(Long hotelId, Long hotelPriceId) {
        List<HotelRoom> hotelRoomList = getRoomList(hotelId, hotelPriceId);
        String roomNums = "";
        for (HotelRoom hotelRoom : hotelRoomList) {
            roomNums += hotelRoom.getRoomNum() + ",";
        }
        if (roomNums.length() > 0 ) {
            return roomNums.substring(0, roomNums.length() - 1);
        } else {
            return roomNums;
        }
    }

    public String getRoomNums(List<HotelRoom> roomList) {
        String roomNums = "";
        for (HotelRoom hotelRoom : roomList) {
            roomNums += hotelRoom.getRoomNum() + ",";
        }
        if (roomNums.length() > 0 ) {
            return roomNums.substring(0, roomNums.length() - 1);
        } else {
            return roomNums;
        }
    }

    public List<HotelRoom> getRoomList(Long hotelId, Long hotelPriceId) {
        Criteria<HotelRoom> criteria = new Criteria<HotelRoom>(HotelRoom.class);
        criteria.eq("hotelPriceId", hotelPriceId);
        criteria.eq("hotelId", hotelId);
        return hotelRoomDao.findByCriteria(criteria);
    }


    public void doHandleRoomNum(String[] roomNumbers, HotelPrice hotelPrice) {
        // delete old CHECKING hotel room
        String sql = "delete from hotel_room where hotel_price_id=? and status=?";
        hotelRoomDao.updateBySQL(sql, hotelPrice.getId(), "UP_CHECKING");
        List<HotelRoom> hotelRoomList = new ArrayList<HotelRoom>();
        for (String num : roomNumbers) {
            HotelRoom hotelRoom = new HotelRoom();
            hotelRoom.setHotelId(hotelPrice.getHotel().getId());
            hotelRoom.setHotelPriceId(hotelPrice.getPriceId());
            hotelRoom.setRoomNum(num);
            hotelRoom.setStatus(ProductStatus.UP_CHECKING);
            hotelRoom.setRoomStatus(HotelRoomStatus.AVAILABLE);
            hotelRoom.setCreateTime(new Date());
            hotelRoomList.add(hotelRoom);
        }
        hotelRoomDao.save(hotelRoomList);
    }

    public void updateRoomStatus(Long hotelPriceId, String roomNum, HotelRoomStatus targetStatus) {
        String sql = "update hotel_room set room_status=? where hotel_price_id=? and room_num=?";
        hotelRoomDao.updateBySQL(sql, targetStatus.toString(), hotelPriceId, roomNum);
    }

    public void doDelHotelRoomNum(HotelPrice hotelPrice) {
        String sql = "delete from hotel_room where hotel_price_id=?";
        hotelRoomDao.updateBySQL(sql, hotelPrice.getId());
    }
    public void doDelHotelRoomNum(Long hotelId, Long hotelPriceId) {
        List<HotelRoom> hotelRoomList = getRoomList(hotelId, hotelPriceId);
        hotelRoomDao.deleteAll(hotelRoomList);
    }

    /**
     * 获取房型数据
     * @param hotelPrice
     * @return
     */
    public List<HotelRoom> getHotelRoomList(HotelPrice hotelPrice) {
        Criteria<HotelRoom> criteria = new Criteria<HotelRoom>(HotelRoom.class);
        if (hotelPrice != null && hotelPrice.getId() != null) {
            criteria.eq("hotelPriceId", hotelPrice.getId());
        }
        return hotelRoomDao.findByCriteria(criteria);
    }

    /**
     * get checking hotellist and update hotelroom's status up
     */
    public void doSaveHotelRooms(String[] roomNumbers, HotelPrice hotelPrice) {
        List<HotelRoom> hotelRoomList = new ArrayList<HotelRoom>();
        for (String num : roomNumbers) {
            HotelRoom hotelRoom = new HotelRoom();
            hotelRoom.setHotelId(hotelPrice.getHotel().getId());
            hotelRoom.setHotelPriceId(hotelPrice.getPriceId());
            hotelRoom.setRoomNum(num);
            hotelRoom.setRoomStatus(HotelRoomStatus.AVAILABLE);
            hotelRoom.setCreateTime(new Date());
            hotelRoomList.add(hotelRoom);
        }
        hotelRoomDao.save(hotelRoomList);
    }

    /**
     * delete hotelroom's status is not checking
     * @param hotelPrice
     */
    public void doDelNeCheckingRooms(HotelPrice hotelPrice) {
        List<HotelRoom> hotelRoomList = getHotelRoomList(hotelPrice);  //get hotelroom list which hotelroom'status is not checking
        hotelRoomDao.deleteAll(hotelRoomList);
    }

    public void doCheckHotelRoomsUp(HotelPrice hotelPrice) {
        List<HotelRoom> hotelRoomList = getHotelRoomList(hotelPrice);
        for (HotelRoom hotelRoom : hotelRoomList) {
            hotelRoom.setHotelId(hotelPrice.getHotel().getId());
            hotelRoom.setHotelPriceId(hotelPrice.getOriginId());
            hotelRoom.setRoomStatus(HotelRoomStatus.AVAILABLE);
            hotelRoomDao.update(hotelRoom);
        }
    }
}
