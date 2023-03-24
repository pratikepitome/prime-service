package com.nw.prime.test.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrimeNumberErrorResponse extends PrimeNumberResponse{
    private ErrorDetails errorDetails;
}
