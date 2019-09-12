Feature: Executing a sum

  Scenario: As a tester I want to sum 5 + 2
    Given The calculator app is running
    When I add 2 and 5
    And I press the equals button
    Then I can see the result is "7"