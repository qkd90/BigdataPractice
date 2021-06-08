package cn.trasen.chengying.entity.pagedesigner;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author rq
 */
@Data
@Document(collection = "Events")
public class EventsDTO {

    private String id;
    @Field("object")
    private Location location;
}
