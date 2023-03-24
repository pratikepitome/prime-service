package com.nw.prime.model;

public enum PrimeNumberAlgo {
    SIEVE("Sieve"),
    Stream("Stream");

    public final String label;

    PrimeNumberAlgo(String label) {
        this.label = label;
    }

}
