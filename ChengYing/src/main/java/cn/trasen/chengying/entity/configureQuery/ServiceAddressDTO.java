package cn.trasen.chengying.entity.configureQuery;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;


/**
 * @author rq
 */
@Data
@Document(collection = "ServiceAddress")
public class ServiceAddressDTO implements Serializable {

    @Field("id")
    private String id;

    private String description;

    private String address;
}

