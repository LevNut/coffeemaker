Feature: Make a coffee for a customer
  A customer can purchase the beverage due to their desire.

  Scenario: Purchase Beverage with right amount of money
    Given a recipe for customer
    When I paid the menu in a recipe for 75
    Then I receive the right amount of change back for "Coffee" with 25

  Scenario: Purchase Beverage with same amount of money
    Given a recipe for customer
    When I paid the menu in a recipe for 75
    Then I receive the right amount of change back for "Mocha" with 75

  Scenario: Purchase Beverage with negative amount of money
    Given a recipe for customer
    When I paid the menu in a recipe for -100
    Then I receive the right amount of change back for "Hot Chocolate" with -100

  Scenario: Purchase Beverage that is not listed
    Given a recipe for customer
    When I paid the menu in a recipe for 20
    Then I receive the right amount of change back for "Coca Cola" with 20