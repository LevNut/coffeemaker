Feature: Check Recipe function
  The recipe can be modified correctly due to each feature.

  Scenario: Deleting a recipe from the coffee maker
    Given a recipe for customer
    When I delete one of the recipe called "Hot Chocolate" from coffee maker
    Then The recipe called Hot Chocolate is completely delete from it

  Scenario: Editing a recipe from the coffee maker
    Given a recipe for customer
    When I edit one of the recipe called "Hot Chocolate" from coffee maker
    Then The recipe called "Hot Chocolate" is completely edit from it