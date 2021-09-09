package com.filatov.calc.rest.exception;

import com.filatov.calc.model.CalcOperation;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.ws.soap.client.SoapFaultClientException;

import java.util.Arrays;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private final String seeApiDocMessage;

    public RestExceptionHandler(
                                 @Value("${springdoc.api-docs.path}")  String springDocPath,
                                 @Value("${springdoc.swagger-ui.path}") String swaggerUiPath) {
        this.seeApiDocMessage = setSeeApiDocMessage(springDocPath, swaggerUiPath);
    }

    private String setSeeApiDocMessage(@NonNull String springDocPath, @NonNull String swaggerUiPath) {
        return "Read Open-Api documentation on " + springDocPath + " or " +
                springDocPath + ".yaml" +
                " or you may see swagger page on " + swaggerUiPath + " path";
    }

    @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex, WebRequest request) {
        String detail;
        if(ex.getName().equals("operation")){
            detail = "Operation should be one of: " + Arrays.toString(CalcOperation.values());
        }
        else if(ex.getName().startsWith("intA") || ex.getName().startsWith("intB")){
            detail = (ex.getName().equals("intA") ? "Left" : "Right") + " operand must be integer number";
        }
        else {
            detail = "Bad request";
        }
        return ErrorResponse.builder()
                .message("Bad request. Try another one!")
                .detail(detail)
                .detail(seeApiDocMessage)
                .build();
    }
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({ SoapFaultClientException.class })
    public ErrorResponse handleSoapFault(SoapFaultClientException ex, WebRequest webRequest){
        return ErrorResponse.builder()
                .message("Some exception was on SOAP server")
                .detail(ex.getFaultStringOrReason())
                .build();
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
                                                                   HttpStatus status, WebRequest request) {
        String errorMessage = "No such url was found: " + ex.getRequestURL();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.builder()
                .message(errorMessage)
                .detail(seeApiDocMessage)
                .build());
    }


}
