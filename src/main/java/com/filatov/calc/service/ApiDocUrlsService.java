package com.filatov.calc.service;

import lombok.Getter;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Класс для вывода подсказки, где можно посмотреть api
 */
@Service
@Getter
public class ApiDocUrlsService {
    private final String whereToSeeApiDoc;

    public ApiDocUrlsService(@Value("${springdoc.api-docs.path}")  String springDocPath,
                             @Value("${springdoc.swagger-ui.path}") String swaggerUiPath) {
        this.whereToSeeApiDoc = getWhereToSeeApiDoc(springDocPath, swaggerUiPath);

    }

    private String getWhereToSeeApiDoc(@NonNull String springDocPath, @NonNull String swaggerUiPath) {
        return "Read Open-Api documentation on " + springDocPath + " or " +
                springDocPath + ".yaml" +
                " or you may see swagger page on " + swaggerUiPath + " path";
    }
}
