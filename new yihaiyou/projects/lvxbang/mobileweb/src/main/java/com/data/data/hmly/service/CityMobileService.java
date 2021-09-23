package com.data.data.hmly.service;

import com.data.data.hmly.action.mobile.response.CityResponse;
import com.data.data.hmly.service.entity.TbArea;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by huangpeijie on 2016-04-26,0026.
 */
@Service
public class CityMobileService {
    public Map<String, List<CityResponse>> sortAreasList(List<TbArea> sortAreas) {
        String letter = "hot,A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z";
        List<String> letters = Lists.newArrayList(letter.split(","));
        Map<String, List<CityResponse>> map = Maps.newLinkedHashMap();
        for (String s : letters) {
            map.put(s, new ArrayList<CityResponse>());
        }
        for (TbArea a : sortAreas) {
            if (a.getPinyin() != null) {
                String first = a.getPinyin().substring(0, 1).toUpperCase();
                List<CityResponse> list = map.get(first);
                list.add(new CityResponse(a));
                map.put(first, list);
            }
        }
        return map;
    }
}
