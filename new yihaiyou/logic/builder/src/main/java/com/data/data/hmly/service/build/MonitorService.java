package com.data.data.hmly.service.build;

import com.data.data.hmly.service.build.builder.DestinationBuilder;
import com.data.data.hmly.service.build.builder.ScenicBuilder;
import com.data.data.hmly.service.build.response.MonitorResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Jonathan.Guo
 */
@Service
public class MonitorService {

    @Resource
    private ScenicBuilder scenicBuilder;
    @Resource
    private DestinationBuilder destinationBuilder;

    public MonitorResponse monitorScenic() {
        return scenicBuilder.monitor();
    }

    public void resetScenicDetail() {
        scenicBuilder.resetStatus();
    }

    public MonitorResponse monitorDestination() {
        return destinationBuilder.monitor();
    }

    public void resetDestination() {
        destinationBuilder.resetStatus();
    }
}
