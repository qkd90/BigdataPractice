package com.data.data.hmly.service.lvxbang.response;

import com.data.data.hmly.service.lvxbang.request.TripNode;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author Jonathan.Guo
 */
public class PlanOptimizeResponse {
    public Long id;
    public List<TripNode> removedNode = Lists.newArrayList();
    public List<TripNode> addNodes = Lists.newArrayList();
    public List<PlanOptimizeDayResponse> data = Lists.newArrayList();
    public Boolean success;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<TripNode> getRemovedNode() {
        return removedNode;
    }

    public void setRemovedNode(List<TripNode> removedNode) {
        this.removedNode = removedNode;
    }

    public List<TripNode> getAddNodes() {
        return addNodes;
    }

    public void setAddNodes(List<TripNode> addNodes) {
        this.addNodes = addNodes;
    }

    public List<PlanOptimizeDayResponse> getData() {
        return data;
    }

    public void setData(List<PlanOptimizeDayResponse> data) {
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
