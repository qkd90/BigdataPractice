package com.data.data.hmly.service.build.response;

import com.data.data.hmly.service.build.enums.BuilderStatus;

/**
 * @author Jonathan.Guo
 */
public class MonitorResponse {

    private String name;
    private Long id;
    private BuilderStatus status;
    private int count;
    private Long time;

    public MonitorResponse withName(String name) {
        this.name = name;
        return this;
    }

    public MonitorResponse withId(Long id) {
        this.id = id;
        return this;
    }

    public MonitorResponse withStatus(BuilderStatus status) {
        this.status = status;
        return this;
    }

    public MonitorResponse withCount(int count) {
        this.count = count;
        return this;
    }

    public MonitorResponse withTime(long time) {
        this.time = time;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BuilderStatus getStatus() {
        return status;
    }

    public void setStatus(BuilderStatus status) {
        this.status = status;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
