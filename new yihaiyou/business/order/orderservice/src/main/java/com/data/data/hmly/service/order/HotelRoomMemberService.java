package com.data.data.hmly.service.order;

import com.data.data.hmly.service.order.dao.HotelRoomMemberDao;
import com.data.data.hmly.service.order.entity.HotelRoomMember;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zzl on 2016/12/9.
 */
@Service
public class HotelRoomMemberService {

    @Resource
    private HotelRoomMemberDao hotelRoomMemberDao;

    public void save(HotelRoomMember hotelRoomMember) {
        hotelRoomMemberDao.save(hotelRoomMember);
    }

    public void saveAll(List<HotelRoomMember> memberList) {
        hotelRoomMemberDao.save(memberList);
    }

    public void delete(HotelRoomMember hotelRoomMember) {
        hotelRoomMemberDao.delete(hotelRoomMember);
    }

    public void delete(Long id) {
        hotelRoomMemberDao.delete(id, HotelRoomMember.class);
    }

    public void update(HotelRoomMember hotelRoomMember) {
        hotelRoomMemberDao.update(hotelRoomMember);
    }

    public void updateAll(List<HotelRoomMember> memberList) {
        hotelRoomMemberDao.updateAll(memberList);
    }
}
