package com.example.chengying.service.configureQuery;

import com.example.chengying.entity.configureQuery.SQLAddressDTO;

/**
 * @author rq
 */
public interface SQLAddress {
    /**
     * fetch data by rule id
     *
     * @return Result
     */
    SQLAddressDTO findSQLAddress();
}
