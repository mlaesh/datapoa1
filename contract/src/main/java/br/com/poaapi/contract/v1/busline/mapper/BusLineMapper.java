package br.com.poaapi.contract.v1.busline.mapper;

import br.com.poaapi.busbaseproject.models.BusLine;
import br.com.poaapi.contract.v1.busline.models.request.BusLineRequest;
import br.com.poaapi.contract.v1.busline.models.response.BusLineResponse;
import br.com.poaapi.contract.v1.busline.models.response.ItineraryResponse;
import br.com.poaapi.contract.v1.busline.models.response.ListBusLineResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusLineMapper {

    private BusLineMapper() {}

    public static BusLineResponse mapToBusLineResponse(BusLine busLine){
        return BusLineResponse.builder()
                .id(busLine.getId())
                .name(busLine.getName())
                .code(busLine.getCode())
                .build();
    }

    public static ListBusLineResponse mapToListResponse(List<BusLine> busLines){
        ListBusLineResponse.ListBusLineResponseBuilder builder = ListBusLineResponse.builder();
        busLines.forEach(i -> builder.busLineResponse(mapToBusLineResponse(i)));
        return builder.build();
    }

    public static ItineraryResponse mapToItinerary(BusLine busLine){
        return ItineraryResponse.builder()
                .name(busLine.getName())
                .id(busLine.getId())
                .code(busLine.getCode())
                .coordinates(busLine.getCoordinates())
                .build();
    }

    public static BusLine mapToLine(BusLineRequest busLineRequest){
        return BusLine.builder()
                .id(busLineRequest.getId())
                .name(busLineRequest.getName())
                .code(busLineRequest.getCode())
                .coordinates(busLineRequest.getCoordinates())
                .build();
    }

}
