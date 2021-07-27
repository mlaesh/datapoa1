package br.com.poaapi.busbaseproject.facade;

import br.com.poaapi.busbaseproject.exception.BusLineNotFoundException;
import br.com.poaapi.busbaseproject.exception.MyApiException;
import br.com.poaapi.busbaseproject.exception.customize.MyException;
import br.com.poaapi.busbaseproject.exception.error.MyError;
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
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
        Optional<BusLine> line = service.findById(id);
        if (line.isEmpty()){
            throw notFound("No line with id " +id, "Not registered id",
                    "Verify before requesting", "Verify the provided id and try again");
        }
        return line.get();
    }

    public Page<BusLine> findAll(Pageable pageable){
        return service.findAll(pageable);
    }

    public BusLine saveItem(BusLine busLine){
        if(service.findById(busLine.getId()).isEmpty()){
            return service.saveItem(busLine);
        } else {
            throw new MyApiException(MyError.builder()
                    .status(HttpStatus.METHOD_NOT_ALLOWED.value())
                    .name(HttpStatus.METHOD_NOT_ALLOWED.name())
                    .message("Try to use PUT method")
                    .exceptionList(new MyException(new HttpRequestMethodNotSupportedException("Use PUT method")))
                    .appAction("Redirect endpoint")
                    .userAction("Contact us")
                    .build());
        }
    }

    @EventListener(ApplicationReadyEvent.class)
    public void saveAll(){
        List<BusLine> all = BusLineIntegration.allLines(restTemplateBuilder.build(), objectMapper);
        service.saveAll(all, integration, new ThreadNotification(), new Semaphore(2));
    }

    public void delete(Integer id){
        Optional<BusLine> del = service.findById(id);
        if(del.isEmpty()){
            throw invalidData("No line with id " + id, "Invalid id","Verify provided data", "Verify id and try again");
        }
        del.ifPresent(service::delete);
    }

    public BusLine update(BusLine busLine){
        BusLine up = service.toUpdate(busLine, findById(busLine.getId()));
        return service.saveItem(up);
    }

    private MyApiException notFound(String exceptionMessage, String errorMessage, String appAction, String userAction) {
        return new MyApiException(MyError.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(exceptionMessage)
                .name(HttpStatus.NOT_FOUND.name())
                .exceptionList(new MyException(new BusLineNotFoundException(errorMessage)))
                .appAction(appAction)
                .userAction(userAction)
                .build());
    }

    private MyApiException invalidData(String exceptionMessage, String errorMessage, String appAction, String userAction) {
        return new MyApiException(MyError.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(exceptionMessage)
                .name(HttpStatus.BAD_REQUEST.name())
                .exceptionList(new MyException(new IllegalArgumentException(errorMessage)))
                .appAction(appAction)
                .userAction(userAction)
                .build());
    }

}
