package br.com.poaapi.taxibaseproject.utils;

import br.com.poaapi.taxibaseproject.model.Taxi;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Util {

    private List<Taxi> taxis = new ArrayList<>();

    public List<Taxi> getByName(String contains){
        return taxis.stream().filter(i -> i.getName().contains(contains)).collect(Collectors.toList());
    }

}
