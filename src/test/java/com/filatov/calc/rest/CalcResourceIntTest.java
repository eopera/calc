package com.filatov.calc.rest;

import com.filatov.calc.model.CalcOperation;
import com.filatov.calc.service.ApiDocUrlsService;
import com.filatov.calc.service.soap.CalcSoapClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CalcResource.class)
class CalcResourceIntTest {
    public static final int REST_RETURN_VALUE = 123;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CalcSoapClient calcSoapClient;

    @MockBean
    private ApiDocUrlsService apiDocUrlsService;

    @BeforeEach
    void setUp() {
        when(calcSoapClient.doOperation(ArgumentMatchers.any(CalcOperation.class), anyInt(), anyInt()))
                .thenReturn(REST_RETURN_VALUE);
    }

    @ParameterizedTest
    @EnumSource(CalcOperation.class)
    void some(CalcOperation calcOperation) throws Exception {
        this.mockMvc.perform(get("/do/" + calcOperation + "/4/2")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(String.valueOf(REST_RETURN_VALUE))));
    }
}
