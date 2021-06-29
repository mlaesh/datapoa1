package br.com.poaapi.busbaseproject.service;

import br.com.poaapi.busbaseproject.integration.BusLineIntegration;
import br.com.poaapi.busbaseproject.models.BusLine;
import br.com.poaapi.busbaseproject.repository.BusLineRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.List;
import java.util.concurrent.Semaphore;

@Service @Slf4j
@AllArgsConstructor
public class BusLineService extends JdbcDaoSupport {

    private final BusLineRepository repository;

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    private void initialize() {
        this.setDataSource(dataSource);
    }


    public BusLine findByCode(String code){
        return repository.findByCode(code).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public BusLine findById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Page<BusLine> findAll(Pageable pageable){
        Page<BusLine> page = repository.findAll(pageable);
        if(page.getTotalPages() < page.getNumber() && pageable.isPaged()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return page;
    }

    public BusLine saveItem(BusLine busLine){
        return  repository.save(busLine);
    }

    public void saveAll(List<BusLine> allBusLine, BusLineIntegration integration, ThreadNotification notification, Semaphore semaphore){
        allBusLine.forEach(i -> {
            if(this.find(i.getId()) == null){
                try {
                    semaphore.acquire();
                    notification.saveThread(integration, repository, semaphore, i);
                } catch (InterruptedException e){
                    Thread.currentThread().interrupt();
                }
            } else {
                log.info(String.format("Line %d already exists", i.getId()));
            }
        });
    }

    public boolean delete(BusLine busLine){
        try {
            repository.delete(busLine);
        } catch (IllegalArgumentException e){
            return false;
        }
        return true;
    }

    public BusLine toUpdate(BusLine busLine, BusLine line){
        if(busLine.getName() != null){
            line.setName(busLine.getName().toUpperCase());
        }
        if(busLine.getCode() != null){
            line.setCode(busLine.getCode().toUpperCase());
        }
        List<Double[]> itinerary = busLine.getCoordinates();
        if(itinerary != null && !itinerary.isEmpty()){
            line.setCoordinates(itinerary);
        }
        return line;
    }

    private BusLine find(Integer id){
        return repository.getById(id);
    }

}
