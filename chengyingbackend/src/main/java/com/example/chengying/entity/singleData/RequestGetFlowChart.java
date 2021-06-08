package com.example.chengying.entity.singleData;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author rq
 */
@Data
public class RequestGetFlowChart {
    /**
     * 服务名称。微服务接口的ServiceName字段
     */
    @JsonProperty("id")
    @NotNull(message = "id不能为空")
    @Size(min = 1, max = 150, message = "id长度范围是1-150个字符")
    private String id;
}


