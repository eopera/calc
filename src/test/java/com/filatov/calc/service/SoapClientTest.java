package com.filatov.calc.service;

import com.filatov.calc.service.soap.CalcSoapClient;
import com.filatov.calc.wsdl.AddResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest
public class SoapClientTest {

    @Autowired
    private  CalcSoapClient client;

    @Test
    void request(){
        final AddResponse addResponse = client.doAdd(1, 2);
        assertThat(addResponse.getAddResult(), equalTo(3));
    }
}
