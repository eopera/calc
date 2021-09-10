package com.filatov.calc.service;

import com.filatov.calc.model.CalcOperation;
import com.filatov.calc.model.wsdl.AddResponse;
import com.filatov.calc.service.soap.CalcSoapClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
public class SoapClientIntTest {

    @Autowired
    private  CalcSoapClient client;

    @ParameterizedTest
    @EnumSource(CalcOperation.class)
    void testOperations(CalcOperation calcOperation){
        assertDoesNotThrow(() -> client.doOperation(calcOperation, 10, 5));
    }
}
