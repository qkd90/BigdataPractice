package cn.trasen.chengying.entity.pagedesigner;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author rq
 */
@Data
public class InputEvents {
    /**
     *传入的文件内容
     */
    @JsonProperty("code")
    @NotNull(message = "code")
    private String code;

    private String id;
}
