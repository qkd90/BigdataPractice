package cn.trasen.chengying.service;

import cn.trasen.chengying.entity.configureQuery.ServiceAddressDTO;

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
