package com.example.demo.dao;

/**
 * @author rq
 */
public interface Mysql {

    /**
     * 新增一个用户
     *
     * @param content 请求参数
     * @return 字符串
     */
    String save(String content);
}