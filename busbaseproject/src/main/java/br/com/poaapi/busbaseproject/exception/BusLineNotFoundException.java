package br.com.poaapi.busbaseproject.exception;

public class BusLineNotFoundException extends RuntimeException {
    public BusLineNotFoundException(String message) {
        super(message);
    }
}
