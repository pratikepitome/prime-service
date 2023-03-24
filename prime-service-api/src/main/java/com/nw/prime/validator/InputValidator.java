package com.nw.prime.validator;

import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;

@Slf4j
public class InputValidator {
    /**
     * Method to check if provided input in request is less than 2
     * @param number input received from controller request
     * @return boolean value
     */
    public static boolean isNumberLessThanSmallestPrime(BigInteger number){
        return number.intValueExact() < 2;
    }

    /**
     * Method to check if provided input in request is greater than Integer max value
     * @param number input received from controller request
     * @return boolean value
     */
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
