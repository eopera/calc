package com.filatov.calc.service.soap;

import com.filatov.calc.model.CalcOperation;
import com.filatov.calc.model.wsdl.*;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

public class CalcSoapClient extends WebServiceGatewaySupport {

    private final String soapUrl;
    private final String soapActionCallbackBaseUrl;

    public CalcSoapClient(String soapUrl, String soapActionCallbackBaseUrl) {

        this.soapUrl = soapUrl;
        this.soapActionCallbackBaseUrl = soapActionCallbackBaseUrl;
    }

    public Integer doOperation(CalcOperation operation, int a, int b) {
        switch (operation){
            case Add:
                final Add add = new Add();
                add.setIntA(a);
                add.setIntB(b);
                return sendAndReceive(AddResponse.class, add).getAddResult();
            case Divide:
                final Divide divide = new Divide();
                divide.setIntA(a);
                divide.setIntB(b);
                return sendAndReceive(DivideResponse.class, divide).getDivideResult();
            case Multiply:
                final Multiply multiply = new Multiply();
                multiply.setIntA(a);
                multiply.setIntB(b);
                return sendAndReceive(MultiplyResponse.class, multiply).getMultiplyResult();
            case Subtract:
                final Subtract subtract = new Subtract();
                subtract.setIntA(a);
                subtract.setIntB(b);
                return sendAndReceive(SubtractResponse.class, subtract).getSubtractResult();
            default:
                throw new UnsupportedOperationException();
        }
    }

    @SuppressWarnings("unchecked")
    private <T,D> T sendAndReceive(Class<T> responseClass, D requestPayload){
        return (T) getWebServiceTemplate()
                .marshalSendAndReceive(soapUrl, requestPayload, new SoapActionCallback(
                        soapActionCallbackBaseUrl + requestPayload.getClass().getSimpleName()
                ));
    }
}
