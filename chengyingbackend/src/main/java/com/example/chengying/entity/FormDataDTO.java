package com.example.chengying.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author rq
 */
@Data
public class FormDataDTO {

    private String subtype;
    private String type;
    @Field("id")
    private String id;
    private String name;
    private String graph;

}
