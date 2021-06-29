package br.com.poaapi.contract.v1.taxi.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter @Builder
public class TaxiResponse {

    private Integer id;

    @ApiModelProperty
    @JsonProperty("name")
    private String name;

    @ApiModelProperty
    @JsonProperty("lat")
    private String lat;

    @ApiModelProperty
    @JsonProperty("lng")
    private String lng;

    @Override
    public String toString() {
        final String convert = "TaxiResponse[name='%s', lat='%s', lng='%s']";
        return String.format(convert, this.name, this.lat, this.lng);
    }

}
