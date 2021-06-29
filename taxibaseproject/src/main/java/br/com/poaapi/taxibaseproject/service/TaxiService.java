package br.com.poaapi.taxibaseproject.service;

import br.com.poaapi.taxibaseproject.model.Taxi;
import br.com.poaapi.taxibaseproject.repository.TaxiRepositoryImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TaxiService {

    private TaxiRepositoryImpl taxiRepository;

    public List<Taxi> findAll(){
        return taxiRepository.findAll();
    }

    public Taxi findById(Integer id){
        return taxiRepository.findById(id);
    }

    public List<Taxi> filterByName(String name){
        return taxiRepository.filterByName(name.toUpperCase());
    }

    public boolean delete(Taxi taxi) {
        try {
            taxiRepository.delete(taxi);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    public Taxi update(Taxi taxi, String[] params){
        taxiRepository.update(taxi, params);
        return taxi;
    }

    public Taxi save(Taxi taxi){
        return taxiRepository.save(taxi);
    }

}
