package com.nw.prime.service.impl;

import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class NumberServiceTest {

    private static PrimeNumberService primeNumberService;
    private static Map<Integer, List<Integer>> primeNumbersCache = new HashMap<>();

    @BeforeAll
    static void setUp() {
        primeNumberService = new PrimeNumberService(primeNumbersCache);
        List<Integer> availablePrimes = Arrays.asList(2,3,5,7);
        primeNumbersCache.put(10, availablePrimes);
    }

    @Test
    @SneakyThrows
    void getPrimeNumbersUnder10UsingSieve() {
        List<Integer> primeNumbers = primeNumberService.getPrimeNumbers(11, null);
        Assertions.assertThat(primeNumbers).containsAll(List.of(2, 3, 5, 7, 11));
    }

    @Test
    @SneakyThrows
    void getPrimeNumbersUnder10UsingStream() {
        List<Integer> primeNumbers = primeNumberService.getPrimeNumbers(11, "Stream");
        Assertions.assertThat(primeNumbers).containsAll(List.of(2, 3, 5, 7, 11));
    }

    @Test
    @SneakyThrows
    void checkIfCacheGetsUpdated() {
        List<Integer> primeNumbers = primeNumberService.getPrimeNumbers(12, null);
        Assertions.assertThat(primeNumbersCache).containsKey(12);
    }

    @Test
    @SneakyThrows
    void getPrimeNumbersForALargeNumber() {
        List<Integer> primeNumbers = primeNumberService.getPrimeNumbers(1000000, "stream");
        Assertions.assertThat(primeNumbers).containsAll(List.of(2, 3, 5, 7));
        Assertions.assertThat(primeNumbersCache).containsKey(1000000);
    }
}