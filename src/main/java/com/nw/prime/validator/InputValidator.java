package com.nw.prime.validator;

import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;

@Slf4j
public class InputValidator {
    public static boolean isNumberLessThanSmallestPrime(BigInteger number){
        return number.intValueExact() < 2;
    }

    public static boolean isNumberGreaterThanMaxIntegerValue(BigInteger number) {
        try {
            int intValue = number.intValueExact();
            return intValue > Integer.MAX_VALUE - 2;
        } catch (ArithmeticException e) {
            log.error("Number is greater than max int size");
            return true;
        }
    }
}
