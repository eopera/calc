package com.filatov.calc.service.soap;

import com.filatov.calc.model.CalcOperation;
import com.filatov.calc.model.wsdl.*;
import lombok.NonNull;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import static com.filatov.calc.util.StringHelper.toCapital;

/**
 * Класс для вызова методов SOAP веб-сервиса
 * Фиксированный список возможных названий методов см enum {@link CalcOperation}
 */
public class CalcSoapClient extends WebServiceGatewaySupport {

    private final String soapUrl;
    private final String soapActionCallbackBaseUrl;

    public CalcSoapClient(String soapUrl, String soapActionCallbackBaseUrl) {
        this.soapUrl = soapUrl;
        this.soapActionCallbackBaseUrl = soapActionCallbackBaseUrl;
    }

    public int doOperation(@NonNull CalcOperation operation, int a, int b) {
        switch (operation){
            case add:
                final Add add = new Add();
                add.setIntA(a);
                add.setIntB(b);
                return sendAndReceive(AddResponse.class, add).getAddResult();
            case divide:
                final Divide divide = new Divide();
                divide.setIntA(a);
                divide.setIntB(b);
                return sendAndReceive(DivideResponse.class, divide).getDivideResult();
            case multiply:
                final Multiply multiply = new Multiply();
                multiply.setIntA(a);
                multiply.setIntB(b);
                return sendAndReceive(MultiplyResponse.class, multiply).getMultiplyResult();
            case subtract:
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
                        soapActionCallbackBaseUrl + toCapital(requestPayload.getClass().getSimpleName())
                ));
    }
}
