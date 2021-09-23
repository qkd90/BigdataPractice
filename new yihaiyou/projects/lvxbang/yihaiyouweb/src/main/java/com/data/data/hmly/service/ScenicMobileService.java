package com.data.data.hmly.service;

import com.data.data.hmly.action.yihaiyou.response.ScenicResponse;
import com.data.data.hmly.action.yihaiyou.response.ScenicSimpleResponse;
import com.data.data.hmly.service.scenic.ScenicInfoService;
import com.data.data.hmly.service.scenic.entity.ScenicInfo;
import com.framework.hibernate.util.Page;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by huangpeijie on 2016-04-26,0026.
 */
@Service
public class ScenicMobileService {
    @Resource
    ScenicInfoService scenicInfoService;

    public ScenicResponse scenicDetail(Long id) {
        ScenicInfo scenicInfo = scenicInfoService.get(id);
        ScenicInfo childScenic = new ScenicInfo();
        childScenic.setFather(scenicInfo);
        List<ScenicInfo> childrenScenicInfo = scenicInfoService.list(childScenic, new Page(0, 3), "ranking", "asc");
        ScenicResponse response = new ScenicResponse(scenicInfo);
        List<ScenicSimpleResponse> child = Lists.transform(childrenScenicInfo, new Function<ScenicInfo, ScenicSimpleResponse>() {
            @Override
            public ScenicSimpleResponse apply(ScenicInfo input) {
                return new ScenicSimpleResponse(input);
            }
        });
        response.setChild(child);
        return response;
    }
}
