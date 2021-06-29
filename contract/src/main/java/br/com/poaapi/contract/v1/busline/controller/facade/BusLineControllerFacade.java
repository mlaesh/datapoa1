package br.com.poaapi.contract.v1.busline.controller.facade;

import br.com.poaapi.busbaseproject.facade.BusLineFacade;
import br.com.poaapi.busbaseproject.models.BusLine;
import br.com.poaapi.contract.v1.busline.mapper.BusLineMapper;
import br.com.poaapi.contract.v1.busline.models.request.BusLineRequest;
import br.com.poaapi.contract.v1.busline.models.response.BusLineResponse;
import br.com.poaapi.contract.v1.busline.models.response.ItineraryResponse;
import br.com.poaapi.contract.v1.busline.models.response.ListBusLineResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class BusLineControllerFacade {

    private final BusLineFacade facade;

    public ItineraryResponse findByCode(String code){
        return BusLineMapper.mapToItinerary(facade.findByCode(code));
    }

    public ItineraryResponse findById(Integer id){
        return BusLineMapper.mapToItinerary(facade.findById(id));
    }

    public Page<BusLineResponse> findAll(Pageable pageable){
        return facade.findAll(pageable).map(BusLineMapper::mapToBusLineResponse);
    }

    public BusLineResponse saveItem(BusLineRequest busLineRequest){
        BusLine line = BusLineMapper.mapToLine(busLineRequest);
        return BusLineMapper.mapToBusLineResponse(facade.saveItem(line));
    }

    public void delete(Integer id){
        facade.delete(id);
    }

    public BusLineResponse update(BusLineRequest busLineRequest){
        BusLine busLine = BusLineMapper.mapToLine(busLineRequest);
        return BusLineMapper.mapToBusLineResponse(facade.update(busLine));
    }

}
