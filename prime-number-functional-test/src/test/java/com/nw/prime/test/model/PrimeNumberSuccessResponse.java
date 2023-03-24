package com.nw.prime.test.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrimeNumberSuccessResponse extends PrimeNumberResponse{
    private BigInteger initial;
    private List<Integer> primes;
}
