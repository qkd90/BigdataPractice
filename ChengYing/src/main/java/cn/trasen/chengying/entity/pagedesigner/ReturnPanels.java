package cn.trasen.chengying.entity.pagedesigner;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * @author rq
 */
@Data
@Document(collection = "Components")
public class ReturnPanels implements Serializable {
    @Field("_id")
    private String id;
    private String name;
    private Object object;
}