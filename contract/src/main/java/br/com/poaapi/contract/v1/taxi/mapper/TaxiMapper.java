package br.com.poaapi.contract.v1.taxi.mapper;

import br.com.poaapi.contract.v1.taxi.models.request.TaxiRequest;
import br.com.poaapi.contract.v1.taxi.models.response.ListTaxiResponse;
import br.com.poaapi.contract.v1.taxi.models.response.TaxiResponse;
import br.com.poaapi.taxibaseproject.model.Taxi;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaxiMapper {

    private TaxiMapper() {}

    public static Taxi mapToRequest(TaxiRequest taxiRequest){
        return Taxi.builder()
                .id(taxiRequest.getId())
                .name(taxiRequest.getName())
                .lat(taxiRequest.getLat())
                .lng(taxiRequest.getLng())
                .build();
    }

    public static TaxiResponse mapToResponse(Taxi taxi){
        return TaxiResponse.builder()
                .id(taxi.getId())
                .name(taxi.getName())
                .lat(taxi.getLat())
                .lng(taxi.getLng())
                .build();
    }

    public static ListTaxiResponse mapToListResponse(List<Taxi> taxis){
        ListTaxiResponse.ListTaxiResponseBuilder builder = ListTaxiResponse.builder();
        taxis.forEach(i -> builder.taxiResponse(mapToResponse(i)));
        return builder.build();
    }

}
