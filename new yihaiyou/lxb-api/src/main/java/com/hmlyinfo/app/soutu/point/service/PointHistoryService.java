package com.hmlyinfo.app.soutu.point.service;

import com.hmlyinfo.app.soutu.point.domain.PointHistory;
import com.hmlyinfo.app.soutu.point.mapper.PointHistoryMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guoshijie on 2014/7/15.
 */
@Service
public class PointHistoryService extends BaseService<PointHistory, Long> {

    @Autowired
    PointHistoryMapper<PointHistory> mapper;

    @Override
    public BaseMapper<PointHistory> getMapper() {
        return mapper;
    }

    @Override
    public String getKey() {
        return null;
    }

    public PointHistory info(Long userId, PointHistory.PointType type) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        params.put("type", type);
        List<PointHistory> list = mapper.list(params);
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 添加积分记录
     *
     * @param userId
     * @param type
     * @return 可以添加的积分数量，-1:已经达到当前添加上限
     */
    public int addPointHistory(Long userId, PointHistory.PointType type) {
        PointHistory pointHistory = info(userId, type);
        if (pointHistory == null) {
            pointHistory = new PointHistory();
            pointHistory.setUserId(userId);
            pointHistory.setPoint(type.getPoint());
            pointHistory.setInterval(type.getInterval());
            pointHistory.setPointType(type.getValue());
            pointHistory.setModifyTime(new Date());
        }
        if (pointHistory.getInterval() == -1 && pointHistory.getCount() == 1) {
            return -1;
        }
        if (!DateUtils.isSameDay(new Date(), pointHistory.getModifyTime())) {
            pointHistory.setCount(0);
        }
        if (pointHistory.getCount() == pointHistory.getInterval()) {
            return -1;
        }
        pointHistory.setCount(pointHistory.getCount() + 1);
        updatePointHistory(pointHistory);
        return pointHistory.getPoint();
    }

    public void updatePointHistory(PointHistory pointHistory) {
        pointHistory.setModifyTime(new Date());
        if (pointHistory.getId() == null) {
            mapper.insert(pointHistory);
        } else {
            mapper.update(pointHistory);
        }
    }
}
