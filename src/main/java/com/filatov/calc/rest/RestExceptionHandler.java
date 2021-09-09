package com.filatov.calc.rest;

import com.filatov.calc.model.CalcOperation;
import com.filatov.calc.service.soap.CalcSoapClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.ws.soap.SoapFaultDetail;
import org.springframework.ws.soap.SoapFaultDetailElement;
import org.springframework.ws.soap.client.SoapFaultClientException;

import javax.servlet.http.HttpServletRequest;
import javax.xml.transform.Source;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Stream;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private final CalcSoapClient calcSoapClient;

    public RestExceptionHandler(CalcSoapClient calcSoapClient) {
        this.calcSoapClient = calcSoapClient;
    }

    @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public String handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex, WebRequest request) {
        String message;
        if(ex.getName().equals("operation")){
            message = "Operation should be one of values: " + Arrays.toString(CalcOperation.values());
        }
        else if(ex.getName().startsWith("intA") || ex.getName().startsWith("intB")){
            message = (ex.getName().equals("intA") ? "Left" : "Right") + " operand must be integer number";
        }
        else {
            message = "Bad request";
        }
        return message;

//        ApiError apiError =
//                new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
//        return new ResponseEntity<Object>(
//                apiError, new HttpHeaders(), apiError.getStatus());
    }
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({ SoapFaultClientException.class })
    public String handleSoapFault(SoapFaultClientException ex, WebRequest webRequest){
        return "Some exception was on SOAP server. Details:\n" + ex.getFaultStringOrReason();
    }
}
