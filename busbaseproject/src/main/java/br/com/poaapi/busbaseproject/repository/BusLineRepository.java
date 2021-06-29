package br.com.poaapi.busbaseproject.repository;

import br.com.poaapi.busbaseproject.models.BusLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BusLineRepository extends JpaRepository<BusLine, Integer> {

    Optional<BusLine> findByCode(String code);

}
