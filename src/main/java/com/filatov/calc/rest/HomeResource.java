package com.filatov.calc.rest;

import com.filatov.calc.service.ApiDocUrlsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HomeResource {
    private final ApiDocUrlsService apiDocUrlsService;

    public HomeResource(ApiDocUrlsService apiDocUrlsService) {
        this.apiDocUrlsService = apiDocUrlsService;
    }
    @GetMapping("/")
    String home(){
        return "Hello on calc adapter for SOAP Web service!\n" +
                this.apiDocUrlsService.getWhereToSeeApiDoc();
    }
}
