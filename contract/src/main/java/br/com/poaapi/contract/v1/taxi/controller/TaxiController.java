package br.com.poaapi.contract.v1.taxi.controller;

import br.com.poaapi.contract.v1.taxi.controller.facade.TaxiControllerFacade;
import br.com.poaapi.contract.v1.taxi.models.request.TaxiRequest;
import br.com.poaapi.contract.v1.taxi.models.response.ListTaxiResponse;
import br.com.poaapi.contract.v1.taxi.models.response.TaxiResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/taxi/v1/taxi_stand")
@AllArgsConstructor
public class TaxiController {

    private final TaxiControllerFacade controllerFacade;

    @ApiOperation(value = "Get all taxi stands")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public ListTaxiResponse findAll(){
        return controllerFacade.findAll();
    }

    @ApiOperation(value = "Get taxi stand by id")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public TaxiResponse findById(@PathVariable @ApiParam Integer id){
        return controllerFacade.findById(id);
    }

    @ApiOperation(value = "Filter taxi stands by name", notes = "Get all taxi stands that contains the provided name")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/filter-by-name/{name}")
    public ListTaxiResponse filterByName(@PathVariable @ApiParam String name){
        return controllerFacade.filterByName(name);
    }

    @ApiOperation(value = "Save taxi stand")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public TaxiResponse save(@RequestBody TaxiRequest request){
        return controllerFacade.save(request);
    }

    @ApiOperation(value = "Update taxi stand")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping
    public TaxiResponse update(TaxiRequest taxiRequest){
        return controllerFacade.update(taxiRequest);
    }

    @ApiOperation(value = "Delete taxi stand")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        controllerFacade.delete(id);
    }

}
