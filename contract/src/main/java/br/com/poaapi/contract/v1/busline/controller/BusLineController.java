package br.com.poaapi.contract.v1.busline.controller;

import br.com.poaapi.contract.v1.busline.controller.facade.BusLineControllerFacade;
import br.com.poaapi.contract.v1.busline.models.request.BusLineRequest;
import br.com.poaapi.contract.v1.busline.models.response.BusLineResponse;
import br.com.poaapi.contract.v1.busline.models.response.ItineraryResponse;
import br.com.poaapi.contract.v1.busline.models.response.ListBusLineResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"Lines", "v1"}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequestMapping("api/bus_line/v1/lines")
@AllArgsConstructor
public class BusLineController {

    private final BusLineControllerFacade controllerFacade;

    @ApiOperation(value = "Get all lines")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    Page<BusLineResponse> findAll(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size){
        Pageable pageable;
        if(page == null || size == null){
            pageable = Pageable.unpaged();
        } else {
            pageable = PageRequest.of(page, size);
        }
        return controllerFacade.findAll(pageable);
    }

    @ApiOperation(value = "Get line by id")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public ItineraryResponse findById(@PathVariable @ApiParam Integer id){
        return controllerFacade.findById(id);
    }


    @ApiOperation(value = "Find bus by code")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/code/{code}")
    public ItineraryResponse findByCode(@PathVariable @ApiParam String code){
        return controllerFacade.findByCode(code);
    }

    @ApiOperation(value = "Save bus line")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public BusLineResponse saveBusLine(@RequestBody BusLineRequest busLineRequest){
        return controllerFacade.saveItem(busLineRequest);
    }

    @ApiOperation(value = "Update bus line")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping
    public BusLineResponse updateBusLine(@RequestBody BusLineRequest busLineRequest){
        return controllerFacade.update(busLineRequest);
    }

    @ApiOperation(value = "Delete bus line")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping
    public void delete(@PathVariable @ApiParam Integer id){
        controllerFacade.delete(id);
    }

}
