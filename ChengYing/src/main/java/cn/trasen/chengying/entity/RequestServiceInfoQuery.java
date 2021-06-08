package cn.trasen.chengying.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author rq
 */
@Data
public class RequestServiceInfoQuery {
    /**
     * 服务名称。微服务接口的ServiceName字段
     */
    @JsonProperty("ServiceName")
    @NotNull(message = "服务名称不能为空")
    @Size(min = 1, max = 50, message = "服务名称长度范围是1-50个字符")
    private String serviceName;
    /**
     * 接口名称。微服务接口的InterfaceName字段
     */
    @JsonProperty("InterfaceName")
    @NotNull(message = "服务名称不能为空")
    @Size(min = 1, max = 50, message = "服务名称长度范围是1-50个字符")
    private String interfaceName;
    /**
     * 文档类型（Argument 表示入参，Result 表示出参）
     */
    @JsonProperty("DocumentType")
    private String documentType;
}
