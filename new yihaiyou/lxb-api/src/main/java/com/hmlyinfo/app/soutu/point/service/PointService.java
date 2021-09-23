package com.hmlyinfo.app.soutu.point.service;

import com.hmlyinfo.app.soutu.point.domain.Point;
import com.hmlyinfo.app.soutu.point.mapper.PointMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guoshijie on 2014/7/14.
 */
@Service
public class PointService extends BaseService<Point, Long> {

    @Autowired
    PointMapper<Point> mapper;

    @Override
    public BaseMapper<Point> getMapper() {
        return mapper;
    }

    @Override
    public String getKey() {
        return null;
    }

    /**
     * 增加积分
     *
     * @param userId
     * @param points
     */
    public void add(Long userId, int points) {
        Point point = info(userId);
        if (point != null) {
            point.setPoint(points);
            update(point);
        } else {
            point = new Point();
            point.setPoint(points);
            point.setUserId(userId);
            insert(point);
        }
    }

    public Point info(Long userId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId.toString());
        List<Point> list = list(params);
        if (!list.isEmpty() && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
}
