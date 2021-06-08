package cn.trasen.chengying.entity.singleData;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author rq
 */
@Data
public class RequestWriteGraph {
    /**
     * 项目中文名
     */
    @JsonProperty("name")
    @NotNull(message = "id不能为空")
    @Size(min = 1, max = 150, message = "id长度范围是1-150个字符")
    private String name;
    /**
     * 服务名称。微服务接口的ServiceName字段
     */
    @JsonProperty("id")
    @NotNull(message = "id不能为空")
    @Size(min = 1, max = 150, message = "id长度范围是1-150个字符")
    private String id;
    /**
     * 服务名称。微服务接口的ServiceName字段
     */
    @JsonProperty("type")
    @NotNull(message = "type不能为空")
    @Size(min = 1, max = 150, message = "type长度范围是1-150个字符")
    private String type;
    /**
     * 服务名称。微服务接口的ServiceName字段
     */
    @JsonProperty("subtype")
    @NotNull(message = "subtype不能为空")
    private String subtype;
    /**
     * 服务名称。微服务接口的ServiceName字段
     */
    @JsonProperty("graph")
    @NotNull(message = "Object不能为空")
    private Object graph;

}