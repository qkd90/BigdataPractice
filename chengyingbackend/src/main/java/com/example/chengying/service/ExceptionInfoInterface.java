package com.example.chengying.service;

/**
 * @author rq
 */
public interface ExceptionInfoInterface {
    /**
     * fetch data by rule id
     * @return int
     */
    int getCode();
    /**
     * fetch data by rule id
     * @return String
     */
    String getType();
    /**
     * fetch data by rule id
     * @return String
     */
    String getSubtype();
    /**
     * fetch data by rule id
     * @return String
     */
    String getDetail();
}
