package com.nw.prime.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigInteger;
import java.util.List;

@Data
@AllArgsConstructor
public class PrimeNumberSuccessResponse extends PrimeNumberResponse{
    private BigInteger initial;
    private List<Integer> primes;
}
