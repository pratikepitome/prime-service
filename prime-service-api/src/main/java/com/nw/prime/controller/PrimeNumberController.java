package com.nw.prime.controller;

import com.nw.prime.model.ErrorDetails;
import com.nw.prime.model.PrimeNumberErrorResponse;
import com.nw.prime.model.PrimeNumberResponse;
import com.nw.prime.model.PrimeNumberSuccessResponse;
import com.nw.prime.service.NumberService;
import com.nw.prime.validator.InputValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/prime")
@Slf4j
public class PrimeNumberController {

    private final NumberService primeNumberService;

    public PrimeNumberController(NumberService primeNumberService) {
        this.primeNumberService = primeNumberService;
    }


    /**
     * Get mapping for endpoint to get prime numbers smaller and equal to given number
     *
     * @param number received as Path param from request
     * @return List of prime numbers
     */
    @Operation(summary = "Get the prime numbers smaller or equal to given input number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get prime numbers",
                    content = {@Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n" +
                                    "  \"Initial\": \"10\",\n" +
                                    "  \"primes\": \"[2,3,5,7]\",\n" +
                                    "}"),
                            schema = @Schema(implementation = PrimeNumberSuccessResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PrimeNumberErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(schema = @Schema(hidden = true)))})
    @GetMapping(value = "/{number}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<PrimeNumberResponse> getPrimeNumbers(@PathVariable(name = "number") BigInteger number,
                                                               @RequestParam(name = "algorithm", required = false) String algo)
            throws ExecutionException, InterruptedException, TimeoutException {
        log.info("Prime Number Details request for number {} using algorithm {}", number, algo);
        if (InputValidator.isNumberGreaterThanMaxIntegerValue(number)
                || InputValidator.isNumberLessThanSmallestPrime(number)) {
            return new ResponseEntity<>(new PrimeNumberErrorResponse(
                    new ErrorDetails("Input number is not valid for prime number calculation"))
                    , HttpStatus.BAD_REQUEST);
        }
        List<Integer> primeNumberList = primeNumberService.getPrimeNumbers(number.intValueExact(), algo);
        PrimeNumberSuccessResponse primeNumberSuccessResponse = new PrimeNumberSuccessResponse(number, primeNumberList);
        return new ResponseEntity<>(primeNumberSuccessResponse, HttpStatus.OK);
    }

}
