package br.com.poaapi.busbaseproject.exception.error;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@ApiModel
@Builder @Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseError implements Serializable {

    @ApiModelProperty
    private String namespace;
    @ApiModelProperty
    private String language;
    @Singular
    private List<MyError> errors;

}
