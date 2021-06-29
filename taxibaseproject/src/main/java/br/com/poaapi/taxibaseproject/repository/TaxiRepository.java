package br.com.poaapi.taxibaseproject.repository;

import br.com.poaapi.taxibaseproject.model.Taxi;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TaxiRepository extends JpaRepository<Taxi, Integer> {

    List<Taxi> filterByName(String name);

    Taxi save(Taxi taxi);

}
