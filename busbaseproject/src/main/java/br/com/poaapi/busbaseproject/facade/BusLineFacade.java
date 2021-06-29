package br.com.poaapi.busbaseproject.facade;

import br.com.poaapi.busbaseproject.integration.BusLineIntegration;
import br.com.poaapi.busbaseproject.models.BusLine;
import br.com.poaapi.busbaseproject.service.BusLineService;
import br.com.poaapi.busbaseproject.service.ThreadNotification;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.concurrent.Semaphore;

@Service
@EnableScheduling
@RequiredArgsConstructor
public class BusLineFacade {

    private final RestTemplateBuilder restTemplateBuilder;
    private final ObjectMapper objectMapper;
    private final BusLineService service;
    private final BusLineIntegration integration;

    public BusLine findByCode(String code){
        return service.findByCode(code);
    }

    public BusLine findById(Integer id){
        return service.findById(id);
    }

    public Page<BusLine> findAll(Pageable pageable){
        return service.findAll(pageable);
    }

    public BusLine saveItem(BusLine busLine){
        if(service.findById(busLine.getId()) == null){
            return service.saveItem(busLine);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_MODIFIED);
        }
    }

    @EventListener(ApplicationReadyEvent.class)
    public void saveAll(){
        List<BusLine> all = integration.allLines(restTemplateBuilder.build(), objectMapper);
        service.saveAll(all, integration, new ThreadNotification(), new Semaphore(2));
    }

    public void delete(Integer id){
        BusLine del = service.findById(id);
        if(!service.delete(del)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public BusLine update(BusLine busLine){
        BusLine up = service.toUpdate(busLine, findById(busLine.getId()));
        return service.saveItem(up);
    }

}
