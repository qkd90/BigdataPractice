package cn.trasen.chengying.entity.dataList;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.List;

/**
 * @author rq
 */
@Data
@Document(collection = "SQLDocument")
public class ReturnReadServiceList implements Serializable {

    @Field("Service")
    private String serviceName;
    @Field("Interface")
    private String interfaceName;
    @Field("Command")
    private String command;
    @Field("Arguments")
    private String arguments;
    @Field("Results")
    private String results;
    @Field("Mappings")
    private List<String> mappings;
}