package br.com.poaapi.contract.v1.taxi.models.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaxiRequest {

    private Integer id;
    @ApiModelProperty
    private String name;
    @ApiModelProperty
    private String lat;
    @ApiModelProperty
    private String lng;

    @Override
    public String toString() {
        final String convert = "TaxiRequest[name='%s', lat='%s', lng='%s']";
        return String.format(convert, this.name, this.lat, this.lng);
    }
}
