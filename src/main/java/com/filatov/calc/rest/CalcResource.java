package com.filatov.calc.rest;

import com.filatov.calc.model.CalcOperation;
import com.filatov.calc.service.soap.CalcSoapClient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestController
@RequestMapping("/do")
public class CalcResource {
    private final CalcSoapClient calcSoapClient;

    public CalcResource(CalcSoapClient calcSoapClient) {
        this.calcSoapClient = calcSoapClient;
    }

    @Operation(summary = "Execute one of available calculator's operation")
    @GetMapping("/{operation}/{intA}/{intB}")
    Integer doOperation(
            @Parameter(description = "Calculator's operation") @PathVariable CalcOperation operation,
            @Parameter(description = "left operand") @PathVariable Integer intA,
            @Parameter(description = "right operand") @PathVariable Integer intB) {
        return calcSoapClient.doOperation(operation, intA, intB);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public void handleException() {

    }


}
