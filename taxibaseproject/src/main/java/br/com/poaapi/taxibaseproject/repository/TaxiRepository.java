package br.com.poaapi.taxibaseproject.repository;

import br.com.poaapi.taxibaseproject.model.Taxi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaxiRepository extends JpaRepository<Taxi, Integer> {

    List<Taxi> filterByName(String name);

}
