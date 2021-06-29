package br.com.poaapi.taxibaseproject.facade;

import br.com.poaapi.taxibaseproject.model.Taxi;
import br.com.poaapi.taxibaseproject.service.TaxiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaxiFacade {

    private final TaxiService service;

    public List<Taxi> findAll(){
        return service.findAll();
    }

    public Taxi findById(Integer id){
        return service.findById(id);
    }

    public List<Taxi> filterByName(String name){
        if (service.filterByName(name) == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return service.filterByName(name);
    }

    public void delete(Integer id){
        Taxi taxiDeleted = service.findById(id);
        if(!service.delete(taxiDeleted)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public Taxi update(Taxi taxi){
        final String[] params = { taxi.getName().toUpperCase(), taxi.getLat(), taxi.getLng() };
        return service.update(taxi, params);
    }

    public Taxi save(Taxi taxi){
        return service.save(taxi);
    }

}
