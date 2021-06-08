package cn.trasen.chengying.entity.pagedesigner;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * @author rq
 */
@Data
@Document(collection = "Pages")
public class ReturnWritePage implements Serializable {
    @Field("_id")
    private String id;
    private String type;
    private String name;
    private String data;
}
