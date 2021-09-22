Feature: Check Inventory Ingredients
  The amount of left ingredients that used in coffee maker must be appropriate.

  Scenario: Check the used ingredient for a recipe
    Given a recipe for customer
    When I paid for the menu called "Latte" with 120
    Then The material must used appropriately due to the selected recipe

  Scenario: Adding coffee to the inventory
    Given a recipe for customer
    When I add 10 more coffee to the inventory
    Then Amount of coffee must be correctly added

  Scenario: Adding milk to the inventory
    Given a recipe for customer
    When I add 10 more milk to the inventory
    Then Amount of milk must be correctly added

  Scenario: Adding sugar to the inventory
    Given a recipe for customer
    When I add 10 more sugar to the inventory
    Then Amount of sugar must be correctly added

  Scenario: Adding chocolate to the inventory
    Given a recipe for customer
    When I add 10 more chocolate to the inventory
    Then Amount of chocolate must be correctly added