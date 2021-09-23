package com.data.data.hmly.service.traffic;

import com.data.data.hmly.service.traffic.entity.Traffic;

import java.util.List;

/**
 * Created by Sane on 16/1/29.
 */
public class TrafficStatic {
    private List<Traffic> traffics;
    private long time;

    public TrafficStatic(List<Traffic> traffics, long time) {
        this.traffics = traffics;
        this.time = time;
    }

    public List<Traffic> getTraffics() {
        return traffics;
    }

    public void setTraffics(List<Traffic> traffics) {
        this.traffics = traffics;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
