package com.nw.prime.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PrimeNumberErrorResponse extends PrimeNumberResponse{
    private ErrorDetails errorDetails;
}
