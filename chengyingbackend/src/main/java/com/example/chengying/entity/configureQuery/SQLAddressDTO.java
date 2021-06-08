package com.example.chengying.entity.configureQuery;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * @author rq
 */

@Data
@Document(collection = "SQLAddress")
public class SQLAddressDTO implements Serializable {

    @Field("id")
    private String id;
    private String description;
    private String type;
    private String address;
    private String username;
    private String password;
    private String service;
}