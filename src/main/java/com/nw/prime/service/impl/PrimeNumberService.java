package com.nw.prime.service.impl;

import com.nw.prime.service.NumberService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Service
public class PrimeNumberService implements NumberService {

    /**
     * This method generates prime number smaller than given number
     * @param number received inout from controller
     * @return LIst<Integer></> of prime numbers
     */
    @Override
    public List<Integer> getPrimeNumbers(int number) {
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
}
