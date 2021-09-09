package com.filatov.calc.service.soap;

import com.filatov.calc.model.wsdl.Add;
import com.filatov.calc.model.wsdl.AddResponse;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

public class CalcSoapClient extends WebServiceGatewaySupport {
    public AddResponse doAdd(int a, int b) {
        final Add add = new Add();
        add.setIntA(a);
        add.setIntB(b);
        return (AddResponse) getWebServiceTemplate()
                .marshalSendAndReceive("http://www.dneonline.com/calculator.asmx", add, new SoapActionCallback(
                        "http://tempuri.org/Add"
                ));
    }
}
