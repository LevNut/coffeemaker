package edu.ncsu.csc326.coffeemaker;

import edu.ncsu.csc326.coffeemaker.exceptions.InventoryException;
import edu.ncsu.csc326.coffeemaker.exceptions.RecipeException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.*;

public class CoffeeMakerCucumberTest {
    private CoffeeMaker coffeeMaker;
    private Recipe recipe1;
    private Recipe recipe2;
    private Recipe recipe3;
    private Recipe recipe4;
    private String recipeName;
    private boolean isRecipeExisted;
    private int money;

    private static Recipe createRecipe(String name, String amtChocolate, String amtCoffee, String amtMilk, String amtSugar, String price) throws RecipeException {
        Recipe recipe = new Recipe();
        recipe.setName(name);
        recipe.setAmtChocolate(amtChocolate);
        recipe.setAmtCoffee(amtCoffee);
        recipe.setAmtMilk(amtMilk);
        recipe.setAmtSugar(amtSugar);
        recipe.setPrice(price);

        return recipe;
    }

    private int checkRecipe(String recipe) {
        for (int i = 0; i < coffeeMaker.getRecipes().length; i++) {
            if (coffeeMaker.getRecipes()[i].getName().equals(recipe)) {
                isRecipeExisted = true;
                return i;
            }
        }
        isRecipeExisted = false;
        return 0;
    }

    public void setUpForRecipes() {
        coffeeMaker = new CoffeeMaker();
        recipeName = "";
        isRecipeExisted = false;
        money = 0;
    }

    @Given("a recipe for customer")
    public void aRecipeForCustomer() throws RecipeException {
        setUpForRecipes();
        recipe1 = createRecipe("Coffee", "0", "3", "1", "1", "50");
        recipe2 = createRecipe("Mocha", "20", "3", "1", "1", "75");
        recipe3 = createRecipe("Latte", "0", "3", "3", "1", "100");
        recipe4 = createRecipe("Hot Chocolate", "4", "0", "1", "1", "65");

        coffeeMaker.addRecipe(recipe1);
        coffeeMaker.addRecipe(recipe2);
        coffeeMaker.addRecipe(recipe3);
        coffeeMaker.addRecipe(recipe4);
    }

    // Purchase for a coffee with right amount of money

    @When("I paid the menu in a recipe for {int}")
    public void iPaidTheMenuInARecipeFor(int receivedMoney) {
        money = receivedMoney;
    }

    @Then("I receive the right amount of change back for {string} with {int}")
    public void iReceiveTheRightAmountOfChangeBackForWith(String recipe, int payment) throws Exception {
        int recipeNum = checkRecipe(recipe);
        if (isRecipeExisted) {
            assertEquals(payment, coffeeMaker.makeCoffee(recipeNum, money));
        } else {throw new Exception("There is no recipe in the menu.");}
    }

    // Check the used Ingredient follows each recipe

    @When("I paid for the menu called {string} with {int}")
    public void iPaidForTheMenuCalledWith(String recipe, int payment) throws Exception {
        int recipeNum = checkRecipe(recipe);
        if (isRecipeExisted) {
            assertEquals(20, coffeeMaker.makeCoffee(recipeNum, payment));
        } else {throw new Exception("There is no recipe in the menu.");}

    }

    @Then("The material must used appropriately due to the selected recipe")
    public void theMaterialMustUsedAppropriatelyDueToTheSelectedRecipe() {
        assertEquals("Coffee: 12\nMilk: 12\nSugar: 14\nChocolate: 15\n", coffeeMaker.checkInventory());
    }

    @When("I add {int} more coffee to the inventory")
    public void iAddMoreCoffeeToTheInventory(int amount) throws InventoryException {
        coffeeMaker.addInventory(String.valueOf(amount), "0", "0", "0");
    }

    @Then("Amount of coffee must be correctly added")
    public void amountOfCoffeeMustBeCorrectlyAdded() {
        assertEquals("Coffee: 25\nMilk: 15\nSugar: 15\nChocolate: 25\n", coffeeMaker.checkInventory());
    }

    @When("I add {int} more milk to the inventory")
    public void iAddMoreMilkToTheInventory(int amount) throws InventoryException {
        coffeeMaker.addInventory("0",String.valueOf(amount), "0", "0");
    }

    @Then("Amount of milk must be correctly added")
    public void amountOfMilkMustBeCorrectlyAdded() {
        assertEquals("Coffee: 15\nMilk: 25\nSugar: 15\nChocolate: 15\n", coffeeMaker.checkInventory());
    }

    @When("I add {int} more sugar to the inventory")
    public void iAddMoreSugarToTheInventory(int amount) throws InventoryException {
        coffeeMaker.addInventory("0","0",String.valueOf(amount), "0");
    }

    @Then("Amount of sugar must be correctly added")
    public void amountOfSugarMustBeCorrectlyAdded() {
        assertEquals("Coffee: 15\nMilk: 15\nSugar: 25\nChocolate: 15\n", coffeeMaker.checkInventory());
    }

    @When("I add {int} more chocolate to the inventory")
    public void iAddMoreChocolateToTheInventory(int amount) throws InventoryException {
        coffeeMaker.addInventory("0","0","0", String.valueOf(amount));
    }

    @Then("Amount of chocolate must be correctly added")
    public void amountOfChocolateMustBeCorrectlyAdded() {
        assertEquals("Coffee: 15\nMilk: 15\nSugar: 15\nChocolate: 25\n", coffeeMaker.checkInventory());
    }

    // Check the recipe if its ingredients are correctly used

    @When("I delete one of the recipe called {string} from coffee maker")
    public void iDeleteOneOfTheRecipeCalledFromCoffeeMaker(String recipe) {
        recipeName = recipe;
    }

    @Then("The recipe called Hot Chocolate is completely delete from it")
    public void theRecipeCalledHotChocolateIsCompletelyDeleteFromIt() {
        coffeeMaker.deleteRecipe(checkRecipe(recipeName));
        assertNull(coffeeMaker.getRecipes()[checkRecipe(recipeName)]);
    }

    @When("I edit one of the recipe called {string} from coffee maker")
    public void iEditOneOfTheRecipeCalledFromCoffeeMaker(String recipe) {
        recipeName = coffeeMaker.editRecipe(checkRecipe(recipe), recipe1);
    }


    @Then("The recipe called {string} is completely edit from it")
    public void theRecipeCalledIsCompletelyEditFromIt(String recipe) {
        assertEquals(recipe, recipeName);
    }

}
