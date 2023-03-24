package com.nw.prime.service.impl;

import com.nw.prime.model.PrimeNumberAlgo;
import com.nw.prime.service.NumberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Slf4j
public class PrimeNumberService implements NumberService {

    private final Map<Integer, List<Integer>> primeNumbersCache;

    public PrimeNumberService(Map<Integer, List<Integer>> primeNumbersCache) {
        this.primeNumbersCache = primeNumbersCache;
    }

    /**
     * This method first checks if prime numbers already exist in cache or not
     * If exist then return and if not then calculate them based on provided algo
     *
     * @param number received inout from controller
     * @param algo   optional parameter provided by client
     * @return LIst<Integer></> of prime numbers
     */
    @Override
    public List<Integer> getPrimeNumbers(int number, String algo) throws ExecutionException, InterruptedException, TimeoutException {
        List<Integer> primeNumberList = getPrimeNumberFromCache(number);
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        if (!primeNumberList.isEmpty()) {
            log.info("Prime Number List is available in cache");
        } else {
            log.info("Prime Numbers List is not available in cache. Calculating it.");
            if (algo != null && algo.equalsIgnoreCase(PrimeNumberAlgo.Stream.label)) {
                CompletableFuture<List<Integer>> completableFutureStream = CompletableFuture.supplyAsync(() -> getPrimesUsingStream(number), executorService);
                primeNumberList = completableFutureStream.get(10, TimeUnit.SECONDS);
            } else {
                CompletableFuture<List<Integer>> completableFutureSieve = CompletableFuture.supplyAsync(() -> getPrimeNumbersUsingSieve(number), executorService);
                primeNumberList = completableFutureSieve.get(10, TimeUnit.SECONDS);
            }
            executorService.shutdown();
            primeNumbersCache.put(number, primeNumberList);
        }
        return primeNumberList;
    }

    /**
     * This method generates prime number smaller than given number using Sieve algo
     *
     * @param number received inout from controller
     * @return LIst<Integer></> of prime numbers
     */
    private List<Integer> getPrimeNumbersUsingSieve(int number) {
        log.info("Using sieve algorithm");
        List<Boolean> prime = new ArrayList<>(Collections.nCopies(number + 1, true));
        Collections.fill(prime, true);
        for (int p = 2; p * p <= number; p++) {
            if (prime.get(p)) {
                for (int i = p * 2; i <= number; i += p) {
                    prime.set(i, false);
                }
            }
        }
        List<Integer> primeNumbers = new LinkedList<>();
        for (int i = 2; i <= number; i++) {
            if (prime.get(i)) {
                primeNumbers.add(i);
            }
        }
        return primeNumbers;
    }

    /**
     * This method generates prime number smaller than given number using stream api
     *
     * @param number received from controller request
     * @return LIst<Integer></> of prime numbers
     */
    private List<Integer> getPrimesUsingStream(int number) {
        log.info("Using stream mechanism for calculating primes");
        return IntStream.rangeClosed(2, number).parallel()
                .filter(this::isPrime)
                .boxed()
                .collect(Collectors.toList());
    }

    private boolean isPrime(int number) {
        return BigInteger.valueOf(number).isProbablePrime(100);
    }

    private List<Integer> getPrimeNumberFromCache(int number) {
        return primeNumbersCache.getOrDefault(number, Collections.emptyList());
    }
}
