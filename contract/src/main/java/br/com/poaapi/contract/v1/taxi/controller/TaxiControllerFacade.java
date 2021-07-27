package br.com.poaapi.contract.v1.taxi.controller;

import br.com.poaapi.contract.v1.taxi.mapper.TaxiMapper;
import br.com.poaapi.contract.v1.taxi.models.request.TaxiRequest;
import br.com.poaapi.contract.v1.taxi.models.response.ListTaxiResponse;
import br.com.poaapi.contract.v1.taxi.models.response.TaxiResponse;
import br.com.poaapi.taxibaseproject.facade.TaxiFacade;
import br.com.poaapi.taxibaseproject.model.Taxi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"Taxi", "v1"}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequestMapping("api/taxi/v1/taxi_stand")
@AllArgsConstructor
public class TaxiControllerFacade {

    private final TaxiFacade facade;

    @ApiOperation(value = "Get all taxi stands")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public ListTaxiResponse findAll(){
        List<Taxi> taxis = facade.findAll();
        return TaxiMapper.mapToListResponse(taxis);
    }

    @ApiOperation(value = "Filter taxi stands by name", notes = "Get all taxi stands that contains the provided name")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/filter-by-name/{name}")
    public ListTaxiResponse filterByName(@PathVariable @ApiParam String name){
        List<Taxi> taxis = facade.filterByName(name);
        return TaxiMapper.mapToListResponse(taxis);
    }

    @ApiOperation(value = "Get taxi stand by id")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public TaxiResponse findById(@PathVariable @ApiParam Integer id){
        return TaxiMapper.mapToResponse(facade.findById(id));
    }

    @ApiOperation(value = "Update taxi stand")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping
    public TaxiResponse update(@RequestBody TaxiRequest taxiRequest){
        Taxi taxi = TaxiMapper.mapToRequest(taxiRequest);
        return TaxiMapper.mapToResponse(facade.update(taxi));
    }

    @ApiOperation(value = "Save taxi stand")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public TaxiResponse save(@RequestBody TaxiRequest request){
        Taxi taxi = TaxiMapper.mapToRequest(request);
        return TaxiMapper.mapToResponse(facade.save(taxi));
    }

    @ApiOperation(value = "Delete taxi stand")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        facade.delete(id);
    }

}
