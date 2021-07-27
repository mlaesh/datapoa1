package br.com.poaapi.busbaseproject.exception.handler;

import br.com.poaapi.busbaseproject.exception.MyApiException;
import br.com.poaapi.busbaseproject.exception.customize.MyException;
import br.com.poaapi.busbaseproject.exception.error.MyError;
import br.com.poaapi.busbaseproject.exception.error.ResponseError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class MyHandler {

    private static final String EN_US = "en-US";
    private static final String LOG_MESSAGE = "Exception: {}";

    @ExceptionHandler(MyApiException.class)
    public ResponseEntity<ResponseError> apiException(MyApiException exception, HttpServletRequest request){
        log.error(LOG_MESSAGE,exception.getMessage(), exception);
        return ResponseEntity.status(exception.getErrors().get(0).getStatus())
                .body(new ResponseError(request.getRequestURI(), EN_US, exception.getErrors()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseError> exception(Exception exception, HttpServletRequest request) {
        MyApiException apiException = new MyApiException(MyError.builder()
                .message(exception.getMessage())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .name(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .exceptionList(new MyException(exception))
                .userAction("Contact us.")
                .appAction("Contact support at contact@suport.com")
                .build()
        );
        log.error(LOG_MESSAGE,exception.getMessage(), exception);
        return ResponseEntity.status(apiException.getErrors().get(0).getStatus())
                .body(new ResponseError(request.getRequestURI(), EN_US, apiException.getErrors()));
    }

}
