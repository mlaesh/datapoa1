package br.com.poaapi.contract.v1.taxi.models.response;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.List;

@Data @Builder
public class ListTaxiResponse {

    @Singular
    private List<TaxiResponse> taxiResponses;

}
