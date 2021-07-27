package br.com.poaapi.busbaseproject.service;

import br.com.poaapi.busbaseproject.exception.MyApiException;
import br.com.poaapi.busbaseproject.exception.customize.MyException;
import br.com.poaapi.busbaseproject.exception.error.MyError;
import br.com.poaapi.busbaseproject.integration.BusLineIntegration;
import br.com.poaapi.busbaseproject.models.BusLine;
import br.com.poaapi.busbaseproject.repository.BusLineRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
        return repository.findByCode(code).orElseThrow(() -> new MyApiException(MyError.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .name(HttpStatus.NOT_FOUND.name())
                .message(String.format("Line code %s not found", code))
                .exceptionList(new MyException(new IllegalArgumentException("Not found line for code " + code)))
                .appAction("Verify code")
                .userAction("Verify code number and try again")
                .build()));
    }

    public Optional<BusLine> findById(Integer id) {
        return repository.findById(id);
    }

    public Page<BusLine> findAll(Pageable pageable){
        Page<BusLine> page = repository.findAll(pageable);
        if(page.getTotalPages() < page.getNumber() && pageable.isPaged()){
            throw new MyApiException(MyError.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .name(HttpStatus.BAD_REQUEST.name())
                    .message(String.format("The requested page is over the total pages. Total: %d", page.getTotalPages() - 1))
                    .exceptionList(new MyException(new IndexOutOfBoundsException("The number of pages is over the total")))
                    .userAction("Verify page number")
                    .build());
        }
        return page;
    }

    public BusLine saveItem(BusLine busLine){
        return  repository.save(busLine);
    }

    public void saveAll(List<BusLine> allBusLine, BusLineIntegration integration, ThreadNotification notification, Semaphore semaphore){
        allBusLine.forEach(i -> {
            if(this.find(i.getId())) {
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

    public void delete(BusLine busLine){
        repository.delete(busLine);
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

    private boolean find(Integer id){
        return repository.findAllById(Collections.singleton(id)).isEmpty();
    }

}
