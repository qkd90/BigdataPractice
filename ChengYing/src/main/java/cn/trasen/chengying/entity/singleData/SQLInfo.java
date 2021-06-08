package cn.trasen.chengying.entity.singleData;

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
public class SQLInfo implements Serializable {

    @Field("ServiceName")
    private List<String> serviceName;
    /**
     * 接口名称。微服务接口的InterfaceName字段
     */
    @Field("InterfaceName")
    private List<String> interfaceName;
    /**
     * 文档类型（Argument 表示入参，Result 表示出参）
     */
    @Field("Arguments")
    private String arguments;
    @Field("Results")
    private String results;
    @Field("Mappings")
    private List<String> mappings;
}