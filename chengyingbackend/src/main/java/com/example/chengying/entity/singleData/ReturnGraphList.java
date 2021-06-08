package com.example.chengying.entity.singleData;



import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * @author rq
 */
@Data
@Document(collection = "Graph")
public class ReturnGraphList implements Serializable {

    @Field("data.subtype")
    private String subtype;
    @Field("data.type")
    private String type;
    @Field("data.id")
    private String id;
    @Field("data.name")
    private String name;

}

