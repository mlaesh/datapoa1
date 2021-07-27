package br.com.poaapi.taxibaseproject.exception.customize;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
@AllArgsConstructor
public class MyException implements Serializable {

    @ApiModelProperty
    private String id;
    @ApiModelProperty(example = "Not Found")
    private String message;

    public MyException(Exception e) {
        this.id = e.toString();
        this.message = e.getMessage();
    }

}
