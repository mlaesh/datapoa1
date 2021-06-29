package br.com.poaapi.contract.v1.busline.models.response;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.List;

@Data @Builder
public class ListBusLineResponse {

    @Singular
    private List<BusLineResponse> busLineResponses;

}
