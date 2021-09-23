package com.data.data.hmly.service.order;

import com.data.data.hmly.service.order.dao.HotelRoomMemberDao;
import com.data.data.hmly.service.order.dao.HotelRoomStateDao;
import com.data.data.hmly.service.order.entity.HotelRoomState;
import com.framework.hibernate.util.Criteria;
import com.zuipin.util.ArrayUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zzl on 2016/12/9.
 */
@Service
public class HotelRoomStateService {

    @Resource
    private HotelRoomStateDao hotelRoomStateDao;
    @Resource
    private HotelRoomMemberDao hotelRoomMemberDao;

    public void save(HotelRoomState hotelRoomState) {
        hotelRoomStateDao.save(hotelRoomState);
    }

    public void saveAll(List<HotelRoomState> roomStateList) {
        hotelRoomStateDao.save(roomStateList);
    }

    public void saveHotelRoomStateList(List<HotelRoomState> roomStateList) {
        hotelRoomStateDao.save(roomStateList);
        for (HotelRoomState hotelRoomState : roomStateList) {
            hotelRoomMemberDao.save(hotelRoomState.getMemberList());
        }
    }

    public void delete(HotelRoomState hotelRoomState) {
        hotelRoomStateDao.delete(hotelRoomState);
    }

    public void delete(Long id) {
        hotelRoomStateDao.delete(id, HotelRoomState.class);
    }

    public void update(HotelRoomState hotelRoomState) {
        hotelRoomStateDao.update(hotelRoomState);
    }

    public void updateAll(List<HotelRoomState> roomStateList) {
        hotelRoomStateDao.updateAll(roomStateList);
    }

    public List<HotelRoomState> findByOrderDetail(Long orderDetailId) {
        Criteria<HotelRoomState> criteria = new Criteria<HotelRoomState>(HotelRoomState.class);
        criteria.createCriteria("orderDetail", "orderDetail");
        criteria.eq("orderDetail.id", orderDetailId);
        return hotelRoomStateDao.findByCriteria(criteria);
    }

    public String getRoomNumsByOrderDetail(Long orderDetailId) {
        List<HotelRoomState> roomStateList = this.findByOrderDetail(orderDetailId);
        List<String> roomNumList = new ArrayList<String>();
        for (HotelRoomState hotelRoomState : roomStateList) {
            if (!roomNumList.contains(hotelRoomState.getRoomNo())) {
                roomNumList.add(hotelRoomState.getRoomNo());
            }
        }
        String roomNums =  roomNumList.toString();
        return roomNums.substring(1, roomNums.length() - 1);
    }

}
