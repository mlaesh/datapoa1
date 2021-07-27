package br.com.poaapi.taxibaseproject.exception;

public class TaxiStandNotFoundException extends RuntimeException{
    public TaxiStandNotFoundException(String message) {
        super(message);
    }
}
