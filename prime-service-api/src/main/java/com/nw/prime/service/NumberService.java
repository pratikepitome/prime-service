package com.nw.prime.service;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public interface NumberService {
    List<Integer> getPrimeNumbers(int number, String algo) throws ExecutionException, InterruptedException, TimeoutException;
}
