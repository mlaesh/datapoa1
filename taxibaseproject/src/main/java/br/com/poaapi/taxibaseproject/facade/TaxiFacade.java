package br.com.poaapi.taxibaseproject.facade;

import br.com.poaapi.taxibaseproject.exception.MyApiException;
import br.com.poaapi.taxibaseproject.exception.TaxiStandNotFoundException;
import br.com.poaapi.taxibaseproject.exception.customize.MyException;
import br.com.poaapi.taxibaseproject.exception.error.MyError;
import br.com.poaapi.taxibaseproject.model.Taxi;
import br.com.poaapi.taxibaseproject.service.TaxiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaxiFacade {

    private final TaxiService service;

    public List<Taxi> findAll(){
        return service.findAll();
    }

    public Taxi findById(Integer id){
        Optional<Taxi> taxi = service.findById(id);
        if (taxi.isEmpty()){
            throw notFound("No taxi stand with id " +id, "Not registered id",
                    "Verify before requesting", "Verify the provided id and try again");
        }
        return taxi.get();
    }

    public List<Taxi> filterByName(String name){
        if (service.filterByName(name) == null){
            throw invalidData("No taxi stand with name " + name, "Invalid name",
                    "Verify name", "Verify and try again");
        }
        return service.filterByName(name);
    }

    public void delete(Integer id){
        Optional<Taxi> taxiDeleted = service.findById(id);
        if(taxiDeleted.isEmpty()){
            throw invalidData("No taxi stand with id " + id, "Invalid id",
                    "Verify provided data", "Verify id and try again");
        }
        taxiDeleted.ifPresent(service::delete);
    }

    public Taxi update(Taxi taxi){
        Taxi up = service.toUpdate(taxi, findById(taxi.getId()));
        return service.saveItem(up);
    }

    public Taxi save(Taxi taxi){
        if(service.findById(taxi.getId()).isEmpty()){
            return service.saveItem(taxi);
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

    private MyApiException notFound(String exceptionMessage, String errorMessage, String appAction, String userAction) {
        return new MyApiException(MyError.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(exceptionMessage)
                .name(HttpStatus.NOT_FOUND.name())
                .exceptionList(new MyException(new TaxiStandNotFoundException(errorMessage)))
                .appAction(appAction)
                .userAction(userAction)
                .build());
    }

}
