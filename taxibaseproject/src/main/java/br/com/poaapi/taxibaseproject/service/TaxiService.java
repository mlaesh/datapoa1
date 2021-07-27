package br.com.poaapi.taxibaseproject.service;

import br.com.poaapi.taxibaseproject.integration.TaxiIntegration;
import br.com.poaapi.taxibaseproject.model.Taxi;
import br.com.poaapi.taxibaseproject.repository.TaxiRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Service @Slf4j
@AllArgsConstructor
public class TaxiService extends JdbcDaoSupport {

    private final TaxiRepository repository;

    @Autowired
    private final DataSource dataSource;

    @PostConstruct
    private void initialize(){
        this.setDataSource(dataSource);
        TaxiIntegration.readFile().forEach(this::saveItem);
    }

    public List<Taxi> findAll(){
        return repository.findAll();
    }

    public Optional<Taxi> findById(Integer id){
        return repository.findById(id);
    }

    public List<Taxi> filterByName(String name){
        return repository.filterByName(name.toUpperCase());
    }

    public void delete(Taxi taxi) {
        repository.delete(taxi);
    }

    public Taxi toUpdate(Taxi taxiToUp, Taxi taxi){
        if(taxiToUp.getName() != null){
            taxi.setName(taxiToUp.getName().toUpperCase());
        }
        if(taxiToUp.getLat() != null){
            taxi.setLat(taxiToUp.getLat());
        }
        if(taxiToUp.getLng() != null){
            taxi.setLng(taxiToUp.getLng());
        }
        taxi.setTime(taxiToUp.getTime());
        TaxiIntegration.appendFile(taxi);

        return taxi;
    }

    public Taxi saveItem(Taxi taxi){
        TaxiIntegration.appendFile(taxi);
        return repository.save(taxi);
    }

}
