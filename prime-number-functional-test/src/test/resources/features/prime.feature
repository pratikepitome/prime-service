Feature: Test the prime numbers API
  Scenario: 1- API returns prime numbers smaller and equal to given number
    Given The number is 10
    When API is called
    Then API should return status 200
    And API should return prime numbers as below
    |2|
    |3|
    |5|
    |7|

  Scenario: 2- API returns Bad_Request Response for a not valid number
    Given The number is abc
    When API is called
    Then API should return status 400
    And API should return error message as below
    |Failed to convert value|

  Scenario: 2- API returns Internal Server Error Response for a very large number
    Given The number is 100000000
    When API is called
    Then API should return status 500
    And API should return error message as below
      |Could not process request as number is too large|

