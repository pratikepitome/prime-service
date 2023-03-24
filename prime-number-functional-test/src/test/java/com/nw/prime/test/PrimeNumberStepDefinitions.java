package com.nw.prime.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nw.prime.test.model.PrimeNumberErrorResponse;
import com.nw.prime.test.model.PrimeNumberSuccessResponse;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.SneakyThrows;
import org.junit.Assert;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class PrimeNumberStepDefinitions {

    private final ObjectMapper mapper = new ObjectMapper();
    private String pathParam;
    private HttpResponse<String> httpResponse;

    @Given("The number is {}")
    public void the_number_is(String number) {
        pathParam = number;
    }

    @When("API is called")
    public void api_is_called() throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder(new URI("http://localhost:8080/prime/" + pathParam))
                .GET()
                .build();

        HttpClient client = HttpClient.newHttpClient();
        httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());

    }

    @SneakyThrows
    @Then("API should return status {}")
    public void api_should_return_status(int status) {
        Assert.assertEquals(status, httpResponse.statusCode());
    }

    @SneakyThrows
    @And("API should return prime numbers as below")
    public void api_should_return(DataTable data) {
        PrimeNumberSuccessResponse primeNumberResponse = mapper.readValue(httpResponse.body(), PrimeNumberSuccessResponse.class);
        List<List<String>> list = data.asLists();
        for(int i =0; i< list.size(); i++){
            Assert.assertEquals((int) primeNumberResponse.getPrimes().get(i), Integer.parseInt(list.get(i).get(0)));
        }
    }

    @SneakyThrows
    @And("API should return error message as below")
    public void api_should_return_error_message(DataTable data) {
        PrimeNumberErrorResponse primeNumberErrorResponse = mapper.readValue(httpResponse.body(), PrimeNumberErrorResponse.class);
        List<List<String>> list = data.asLists();
        Assert.assertTrue(primeNumberErrorResponse.getErrorDetails().getErrorMessage().contains(list.get(0).get(0)));
    }
}
