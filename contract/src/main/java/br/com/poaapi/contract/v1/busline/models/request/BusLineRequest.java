package br.com.poaapi.contract.v1.busline.models.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusLineRequest {

    @ApiModelProperty
    private Integer id;
    @ApiModelProperty
    private String code;
    @ApiModelProperty
    private String name;
    @Singular
    private List<Double[]> coordinates;

}
