package com.hmlyinfo.app.soutu.scenic.service;

import com.hmlyinfo.app.soutu.common.service.WeatherService;
import com.hmlyinfo.app.soutu.scenic.domain.Destination;
import com.hmlyinfo.app.soutu.scenic.domain.DestinationWeather;
import com.hmlyinfo.app.soutu.scenic.domain.HdMapLevel;
import com.hmlyinfo.app.soutu.scenic.mapper.DestinationMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guoshijie on 2014/7/15.
 */
@Service
public class DestinationService extends BaseService<Destination, Long> {

    @Autowired
    DestinationMapper mapper;
    @Autowired
    private ScenicInfoService scenicInfoService;
    @Autowired
    private WeatherService weatherService;
    @Autowired
    private DestinationWeatherService destinationWeatherService;
    @Autowired
    HdMapLevelService hdMapLevelService;


    @Override
    public BaseMapper<Destination> getMapper() {
        return mapper;
    }

    @Override
    public String getKey() {
        return "id";
    }

    public List<Destination> listLike(Map<String, Object> params) {
        return mapper.list(params);
    }

    /**
     * 目的地详细信息
     * <ul>
     * <li>必选：目的地ID或景点ID{id&scenicId}</li>
     * </ul>
     *
     * @return DestinationMap
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> detailList(Map<String, Object> paramMap) {
        Map<String, Object> scenicMap = (Map<String, Object>) scenicInfoService.info(Long.valueOf(paramMap.get("scenicId").toString()));
        Destination destination = mapper.selByIds(paramMap);
        if (paramMap.get("date") != null) {
            String dateStr = paramMap.get("date").toString();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date date = null;
            try {
                date = simpleDateFormat.parse(dateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            destination.setWeather(getWeather(date, destination, scenicMap));
        }
        scenicMap.put("destination", destination);

        Map<String, Object> levelParams = new HashMap<String, Object>();
        levelParams.put("destinationId", destination.getId());
        List<HdMapLevel> levelList = hdMapLevelService.list(levelParams);
        Map<Integer, Object> levelMap = new HashMap<Integer, Object>();
        for (HdMapLevel hdMapLevel : levelList) {
            levelMap.put(hdMapLevel.getLevel(), hdMapLevel);
        }
        scenicMap.put("levels", levelMap);
        return scenicMap;
    }

    private String getWeather(Date date, Destination destination, Map<String,Object> scenicMap) {
        if (date == null) {
            return "暂无天气信息";

        }
        String result = weatherService.getWeather(destination.getName(),scenicMap, date);
        if (result != null) {
            return result;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("destinationId", destination.getId());
        params.put("month", calendar.get(Calendar.MONTH) + 1);
        List<DestinationWeather> list = destinationWeatherService.list(params);
        if (list.isEmpty()) {
            return "暂无天气信息";
        }
        return list.get(0).getWeather();
    }
}
