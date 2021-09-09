package com.filatov.calc.config;

import com.filatov.calc.service.soap.CalcSoapClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class SoapClientConfiguration {
    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        // this package must match the package in the <generatePackage> specified in
        // pom.xml
        marshaller.setContextPath("com.filatov.calc.model.wsdl"); //нехорошо, что строчка здесь
        return marshaller;
    }

    @Bean
    public CalcSoapClient calcSoapClient(Jaxb2Marshaller marshaller) {
        CalcSoapClient client = new CalcSoapClient();
        client.setDefaultUri("http://www.dneonline.com/calculator.asmx");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }

}
