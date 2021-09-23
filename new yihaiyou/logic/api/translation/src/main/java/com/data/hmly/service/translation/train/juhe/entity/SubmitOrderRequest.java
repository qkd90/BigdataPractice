package com.data.hmly.service.translation.train.juhe.entity;

/**
 * Created by Sane on 16/1/8.
 */
public class SubmitOrderRequest {
    public SubmitOrderRequest(String trainDate, String fromCode, String fromName, String toCode, String toName, String checi) {
        this.trainDate = trainDate;
        this.fromCode = fromCode;
        this.fromName = fromName;
        this.toCode = toCode;
        this.toName = toName;
        this.checi = checi;
    }

    private String trainDate;
    private String fromCode;
    private String fromName;
    private String toCode;
    private String toName;
    private String checi;

    public String getTrainDate() {
        return trainDate;
    }

    public void setTrainDate(String trainDate) {
        this.trainDate = trainDate;
    }

    public String getFromCode() {
        return fromCode;
    }

    public void setFromCode(String fromCode) {
        this.fromCode = fromCode;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getToCode() {
        return toCode;
    }

    public void setToCode(String toCode) {
        this.toCode = toCode;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public String getCheci() {
        return checi;
    }

    public void setCheci(String checi) {
        this.checi = checi;
    }
}
