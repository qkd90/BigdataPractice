package com.example.chengying.service.configureQuery;

import com.example.chengying.entity.configureQuery.ServiceAddressDTO;

/**
 * @author rq
 */
public interface ServiceAddress {
     /**
      * fetch data by rule id
      *
      * @return Result
      */
     ServiceAddressDTO findServiceAddress();
}
