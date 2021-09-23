package com.data.data.hmly.service.traffic;

import com.data.hmly.service.translation.train.Qunar.PassBy;
import com.data.hmly.service.translation.train.QunarTrainService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Sane on 15/12/25.
 */
@Service
public class TrafficQunarTrainService {


    public List<PassBy> getPassBy(String leavePortName, String arrivePortName, String trafficCode, String date) {
        return new QunarTrainService().getPassBy(leavePortName, arrivePortName, trafficCode, date);

    }
}
