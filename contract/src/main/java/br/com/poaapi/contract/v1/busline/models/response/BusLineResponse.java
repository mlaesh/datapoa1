package br.com.poaapi.contract.v1.busline.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder
@AllArgsConstructor
@NoArgsConstructor
public class BusLineResponse {

    private Integer id;
    @JsonProperty("codigo")
    private String code;
    @JsonProperty("nome")
    private String name;

}
