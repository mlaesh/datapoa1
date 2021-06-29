package br.com.poaapi.contract.v1.busline.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItineraryResponse {

    @ApiModelProperty
    private Integer id;

    @ApiModelProperty
    @JsonProperty("codigo")
    private String code;

    @ApiModelProperty
    @JsonProperty("nome")
    private String name;

    @JsonProperty("coordenadas")
    private List<Double[]> coordinates;

}
