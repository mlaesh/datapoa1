package br.com.poaapi.contract.v1.taxi.controller.facade;

import br.com.poaapi.contract.v1.taxi.mapper.TaxiMapper;
import br.com.poaapi.contract.v1.taxi.models.request.TaxiRequest;
import br.com.poaapi.contract.v1.taxi.models.response.ListTaxiResponse;
import br.com.poaapi.contract.v1.taxi.models.response.TaxiResponse;
import br.com.poaapi.taxibaseproject.facade.TaxiFacade;
import br.com.poaapi.taxibaseproject.model.Taxi;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TaxiControllerFacade {

    private final TaxiFacade facade;

    public ListTaxiResponse filterByName(String name){
        List<Taxi> taxis = facade.filterByName(name);
        return TaxiMapper.mapToListResponse(taxis);
    }

    public ListTaxiResponse findAll(){
        List<Taxi> taxis = facade.findAll();
        return TaxiMapper.mapToListResponse(taxis);
    }

    public TaxiResponse findById(Integer id){
        return TaxiMapper.mapToResponse(facade.findById(id));
    }

    public void delete(Integer id){
        facade.delete(id);
    }

    public TaxiResponse update(TaxiRequest taxiRequest){
        Taxi taxi = TaxiMapper.mapToRequest(taxiRequest);
        return TaxiMapper.mapToResponse(facade.update(taxi));
    }

    public TaxiResponse save(TaxiRequest request){
        Taxi taxi = TaxiMapper.mapToRequest(request);
        return TaxiMapper.mapToResponse(facade.save(taxi));
    }

}
