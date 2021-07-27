package br.com.poaapi.busbaseproject.exception;

import br.com.poaapi.busbaseproject.exception.error.MyError;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public class MyApiException extends RuntimeException {

    @Getter
    private final List<MyError> errors;

    public MyApiException(MyError... errors){
        super(errors[0].getMessage());
        this.errors = Arrays.asList(errors);
    }
}
