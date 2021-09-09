package com.filatov.calc.config;

import com.filatov.calc.service.soap.CalcSoapClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class SoapClientConfiguration {
    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.filatov.calc.model.wsdl");
        return marshaller;
    }

    @Bean
    public CalcSoapClient calcSoapClient(
            Jaxb2Marshaller marshaller,
            @Value("${app.soap.url}") String soapUrl,
            @Value("${app.soap.action-callback-base-url}") String soapActionCallbackBaseUrl) {
        CalcSoapClient client = new CalcSoapClient(soapUrl, soapActionCallbackBaseUrl);
        client.setDefaultUri(soapUrl);
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }
}
