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
public class ReturnFindPage implements Serializable {
    @Field("id")
    private String id;
    private String name;
    private String label;
    private String text;
    private String icon;
    private Boolean draggable;
    private Object attr;
    private Object events;
    private Object style;
    private Object children;
}
