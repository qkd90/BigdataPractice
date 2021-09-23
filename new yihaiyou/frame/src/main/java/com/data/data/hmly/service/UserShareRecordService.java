package com.data.data.hmly.service;

import com.data.data.hmly.service.dao.UserShareRecordDao;
import com.data.data.hmly.service.entity.ShareType;
import com.data.data.hmly.service.entity.UserShareRecord;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by caiys on 2016/7/21.
 */
@Service
public class UserShareRecordService {
    @Resource
    private UserShareRecordDao userShareRecordDao;

    /**
     * 分享成功时记录，只记录第一次
     * @param userId
     * @param shareType
     */
    public void doShareSuccess(Long userId, ShareType shareType) {
        Long count = userShareRecordDao.countBy(userId, null);
        if (count <= 0) {
            UserShareRecord userShareRecord = new UserShareRecord();
            userShareRecord.setCreatedTime(new Date());
            userShareRecord.setUserId(userId);
            userShareRecord.setShareType(shareType);
            userShareRecordDao.save(userShareRecord);
        }
    }
}
