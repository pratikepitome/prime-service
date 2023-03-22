package com.nw.prime.controller;

import com.nw.prime.service.impl.PrimeNumberService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ExtendWith(SpringExtension.class)
class PrimeNumberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private PrimeNumberController primeNumberController;

    @MockBean
    private PrimeNumberService primeNumberService;

    @Test
    @SneakyThrows
    void testIfControllerReturns200ResponseForAValidInput() {
        when(primeNumberService.getPrimeNumbers(10)).thenReturn(getPrimeNumberResponse());
        MvcResult result = mockMvc.perform(
                        get("/prime/10")
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        Assertions.assertEquals(200, result.getResponse().getStatus());
        Assertions.assertEquals("{\"initial\":10,\"primes\":[2,3,5,7]}", result.getResponse().getContentAsString());
    }

    @Test
    @SneakyThrows
    void testIfControllerReturns400IfInputIsANotNumber() {
        when(primeNumberService.getPrimeNumbers(10)).thenReturn(getPrimeNumberResponse());
        MvcResult result = mockMvc.perform(
                        get("/prime/abc"))
                .andExpect(status().is4xxClientError()).andReturn();

        Assertions.assertEquals(400, result.getResponse().getStatus());
        Assertions.assertTrue(result.getResponse().getContentAsString().contains("java.lang.NumberFormatException: For input string: \\\"abc\\\"\""));
    }

    @Test
    @SneakyThrows
    void testIfControllerReturns400IfInputIsInvalid() {
        when(primeNumberService.getPrimeNumbers(10)).thenReturn(getPrimeNumberResponse());
        MvcResult result = mockMvc.perform(
                        get("/prime/0"))
                .andExpect(status().is4xxClientError()).andReturn();

        Assertions.assertEquals(400, result.getResponse().getStatus());
        Assertions.assertTrue(result.getResponse().getContentAsString().contains("Input number is not valid for prime number calculation"));
    }

    @Test
    @SneakyThrows
    void testIfControllerReturns500IfInputIsHuge() {
        when(primeNumberService.getPrimeNumbers(10)).thenReturn(getPrimeNumberResponse());
        int max_value = Integer.MAX_VALUE;
        MvcResult result = mockMvc.perform(
                        get("/prime/" + max_value))
                .andExpect(status().is4xxClientError()).andReturn();

        Assertions.assertEquals(400, result.getResponse().getStatus());
        Assertions.assertTrue(result.getResponse().getContentAsString().contains("Input number is not valid for prime number calculation"));
    }

    private List<Integer> getPrimeNumberResponse() {
        return List.of(2, 3, 5, 7);
    }

}