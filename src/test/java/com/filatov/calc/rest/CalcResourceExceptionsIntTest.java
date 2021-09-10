package com.filatov.calc.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.filatov.calc.CalcApplication;
import com.filatov.calc.model.CalcOperation;
import com.filatov.calc.rest.exception.ErrorResponse;
import com.filatov.calc.service.soap.CalcSoapClient;
import com.filatov.calc.util.StringHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(classes = CalcApplication.class)
class CalcResourceExceptionsIntTest {
    public static final int REST_RETURN_VALUE = 123;
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CalcSoapClient calcSoapClient;

    @BeforeEach
    void setUp() {
        when(calcSoapClient.doOperation(ArgumentMatchers.any(CalcOperation.class), anyInt(), anyInt()))
                .thenReturn(REST_RETURN_VALUE);
    }

    @Autowired
    private ObjectMapper objectMapper;

    @ParameterizedTest
    @CsvSource({
            "a,             1",
            "1,             b",
            "a,             b",
    })
    void notValidArgument(String  leftArgument, String rightArgument) throws Exception {
        final MvcResult mvcResult = this.mockMvc.perform(get(String.format("/do/%s/%s/%s", CalcOperation.add, leftArgument, rightArgument)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String bodyString = mvcResult.getResponse().getContentAsString();
        final ErrorResponse errorResponse = objectMapper.readValue(bodyString, ErrorResponse.class);
        assertThat(errorResponse.getMessage(), equalTo("Bad request. Try another one!"));
        assertThat(errorResponse.getDetails(), hasItem(String.format("%s operand must be integer number",
                getBadArgumentName(leftArgument, rightArgument))));
        assertThat(errorResponse.getDetails(), hasItem("Read Open-Api documentation on /api or /api.yaml or you may see swagger page on /swagger.html path"));
    }

    private String getBadArgumentName(String leftArgument, String rightArgument) {
        String badArgumentName = !StringHelper.isInteger(leftArgument) ? "Left" :
                (!StringHelper.isInteger(rightArgument) ? "Right" : "");
        if(badArgumentName.isEmpty())
            throw new RuntimeException("Bad input of test. Check arguments!");
        return badArgumentName;
    }
}
