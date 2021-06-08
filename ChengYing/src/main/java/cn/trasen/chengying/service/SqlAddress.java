package cn.trasen.chengying.service;

import cn.trasen.chengying.entity.configureQuery.SQLAddressDTO;

/**
 * @author rq
 */
public interface SqlAddress {
    /**
     * fetch data by rule id
     *
     * @return Result
     */
    SQLAddressDTO findSqlAddress();
}
