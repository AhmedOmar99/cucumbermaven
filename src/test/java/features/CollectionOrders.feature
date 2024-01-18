
Feature: Collection orders
  Scenario: Change partial flag of CO
    Given User is authorized with credentials
    When user send the request to change the value of partial flag
    Then partial flag should changed
