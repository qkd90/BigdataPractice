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
public class ReturnGraph implements Serializable {
    @Field("code")
    private Integer code;

    @Field("data")
    private Object data;

    @Field("success")
    private Boolean success;
}

