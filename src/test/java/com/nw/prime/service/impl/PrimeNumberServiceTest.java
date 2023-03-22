package com.nw.prime.service.impl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

class PrimeNumberServiceTest {

    private static PrimeNumberService primeNumberService;

    @BeforeAll
    static void setUp() {
        primeNumberService = new PrimeNumberService();
    }

    @Test
    void getPrimeNumbersUnder10() {
        List<Integer> primeNumbers = primeNumberService.getPrimeNumbers(11);
        Assertions.assertThat(primeNumbers).containsAll(List.of(2, 3, 5, 7, 11));
    }

    @Test
    void getPrimeNumbersForALargeNumber() {
        List<Integer> primeNumbers = primeNumberService.getPrimeNumbers(1000000);
        Assertions.assertThat(primeNumbers).containsAll(List.of(2, 3, 5, 7));
    }
}