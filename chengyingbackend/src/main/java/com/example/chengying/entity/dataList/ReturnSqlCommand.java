package com.example.chengying.entity.dataList;

import lombok.Data;

import java.io.Serializable;

/**
 * @author rq
 */
@Data
public class ReturnSqlCommand  implements Serializable {
    private String service;
    private String command;
}
