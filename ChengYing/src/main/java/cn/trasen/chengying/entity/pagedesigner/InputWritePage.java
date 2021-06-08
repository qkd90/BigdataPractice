package cn.trasen.chengying.entity.pagedesigner;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author rq
 */
@Data
public class InputWritePage {
    /**
     *
     */
    @JsonProperty("name")
    @NotNull(message = "name不能为空")
    @Size(min = 1, max = 150, message = "name长度范围是1-150个字符")
    private String name;

    @JsonProperty("data")
    @NotNull(message = "data不能为空")
    private Object data;

    @JsonProperty("type")
    @NotNull(message = "type不能为空")
    @Size(min = 1, max = 150, message = "type长度范围是1-150个字符")
    private String type;

    private String id;

}


