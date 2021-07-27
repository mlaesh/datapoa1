package br.com.poaapi.busbaseproject.exception.error;

import br.com.poaapi.busbaseproject.exception.customize.MyException;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.io.Serializable;
import java.util.List;

@ApiModel
@Builder @Data
public class MyError implements Serializable {

    @ApiModelProperty(example = "NOT_FOUND")
    private String name;
    @ApiModelProperty(example = "Not found")
    private String message;
    @ApiModelProperty(example = "404")
    private Integer status;
    @ApiModelProperty(example = "E-mail us to help")
    private String appAction;
    @ApiModelProperty(example = "Verify fields")
    private String userAction;
    @Singular("exceptionList")
    private List<MyException> exceptionList;

}
