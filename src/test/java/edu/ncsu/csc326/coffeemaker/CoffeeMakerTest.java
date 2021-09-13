/*
 * Copyright (c) 2009,  Sarah Heckman, Laurie Williams, Dright Ho
 * All Rights Reserved.
 *
 * Permission has been explicitly granted to the University of Minnesota
 * Software Engineering Center to use and distribute this source for
 * educational purposes, including delivering online education through
 * Coursera or other entities.
 *
 * No warranty is given regarding this software, including warranties as
 * to the correctness or completeness of this software, including
 * fitness for purpose.
 *
 *
 * Modifications
 * 20171114 - Ian De Silva - Updated to comply with JUnit 4 and to adhere to
 * 							 coding standards.  Added test documentation.
 */
package edu.ncsu.csc326.coffeemaker;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc326.coffeemaker.exceptions.InventoryException;
import edu.ncsu.csc326.coffeemaker.exceptions.RecipeException;

/**
 * Unit tests for CoffeeMaker class.
 *
 * @author Sarah Heckman
 */
public class CoffeeMakerTest {

	/**
	 * The object under test.
	 */
	private CoffeeMaker coffeeMaker;
	private CoffeeMaker mockCoffeeMaker;
	private RecipeBook stubRecipeBook;

	// Sample recipes to use in testing.
	private Recipe recipe1;
	private Recipe recipe2;
	private Recipe recipe3;
	private Recipe recipe4;

	/**
	 * Initializes some recipes to test with and the {@link CoffeeMaker}
	 * object we wish to test.
	 *
	 * @throws RecipeException  if there was an error parsing the ingredient
	 * 		amount when setting up the recipe.
	 */
	@Before
	public void setUp() throws RecipeException {
		coffeeMaker = new CoffeeMaker();

		stubRecipeBook = mock(RecipeBook.class);
		mockCoffeeMaker = new CoffeeMaker(stubRecipeBook, new Inventory());

		//Set up for all recipes
		recipe1 = createRecipe("Coffee", "0", "3", "1", "1", "50");
		recipe2 = createRecipe("Mocha", "20", "3", "1", "1", "75");
		recipe3 = createRecipe("Latte", "0", "3", "3", "1", "100");
		recipe4 = createRecipe("Hot Chocolate", "4", "0", "1", "1", "65");

	}
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


	// ------------------------------------------------------------------------------------

	/**
	 * Test adding more than 3 recipes
	 */
	@Test
	public void testAddRecipe() {
		assertTrue(coffeeMaker.addRecipe(recipe1));
		assertTrue(coffeeMaker.addRecipe(recipe2));
		assertTrue(coffeeMaker.addRecipe(recipe3));
		assertFalse(coffeeMaker.addRecipe(recipe4));
	}

	/**
	 * Test adding same recipe
	 */
	@Test
	public void testAddSameRecipe() {
		assertTrue(coffeeMaker.addRecipe(recipe1));
		assertFalse(coffeeMaker.addRecipe(recipe1));
	}

	/**
	 * Test deleting a recipe and check whether is it null
	 */
	@Test
	public void testDeleteRecipe() {
		assertNull(coffeeMaker.getRecipes()[0]);
		coffeeMaker.addRecipe(recipe1);
		assertEquals(recipe1, coffeeMaker.getRecipes()[0]);
		coffeeMaker.deleteRecipe(0);
		assertNull(coffeeMaker.getRecipes()[0]);
	}


	/**
	 * Test deleting non-existed recipe
	 */
	@Test
	public void testDeleteNonExistedRecipe() {
		assertNull(coffeeMaker.deleteRecipe(0));
	}


	/**
	 * Test editing a recipe2 using recipe1
	 */
	@Test
	public void testEditRecipe() {
		coffeeMaker.addRecipe(recipe1);
		coffeeMaker.addRecipe(recipe2);
		coffeeMaker.editRecipe(1, recipe1);
		assertEquals(coffeeMaker.getRecipes()[0].getName(), coffeeMaker.getRecipes()[1].getName());
	}


	/**
	 * Test editing non-existed recipe
	 */
	@Test
	public void testEditNonExistedRecipe() {
		assertNull(coffeeMaker.editRecipe(0, recipe3));
	}


	/**
	 * Test adding default inventory to coffeeMaker
	 */
	@Test
	public void testAddInventory() throws InventoryException {
		coffeeMaker.addInventory("0","0","0","0");
		assertEquals("Coffee: 15\nMilk: 15\nSugar: 15\nChocolate: 15\n", coffeeMaker.checkInventory());

		coffeeMaker.addInventory("5","0","0","0");
		assertEquals("Coffee: 20\nMilk: 15\nSugar: 15\nChocolate: 15\n", coffeeMaker.checkInventory());

		coffeeMaker.addInventory("0","5","0","0");
		assertEquals("Coffee: 20\nMilk: 20\nSugar: 15\nChocolate: 15\n", coffeeMaker.checkInventory());

		coffeeMaker.addInventory("0","0","5","0");
		assertEquals("Coffee: 20\nMilk: 20\nSugar: 20\nChocolate: 15\n", coffeeMaker.checkInventory());

		coffeeMaker.addInventory("0","0","0","5");
		assertEquals("Coffee: 20\nMilk: 20\nSugar: 20\nChocolate: 20\n", coffeeMaker.checkInventory());
	}


	/**
	 * Test adding negative integer setup in inventory
	 * @throws InventoryException  if there was an error parsing the quantity
	 * 		to a positive integer.
	 */
	@Test(expected = InventoryException.class)
	public void testAddInventoryByNegativeCoffeeInteger() throws InventoryException {
		coffeeMaker.addInventory("-5","0","0","0");
		assertEquals("Coffee: 10\nMilk: 15\nSugar: 15\nChocolate: 15\n", coffeeMaker.checkInventory());
	}


	/**
	 * Test adding negative integer setup in inventory
	 * @throws InventoryException  if there was an error parsing the quantity
	 * 		to a positive integer.
	 */
	@Test(expected = InventoryException.class)
	public void testAddInventoryByNegativeMilkInteger() throws InventoryException {
		coffeeMaker.addInventory("0","-5","0","0");
		assertEquals("Coffee: 15\nMilk: 10\nSugar: 15\nChocolate: 15\n", coffeeMaker.checkInventory());
	}


	/**
	 * Test adding negative integer setup in inventory
	 * @throws InventoryException  if there was an error parsing the quantity
	 * 		to a positive integer.
	 */
	@Test(expected = InventoryException.class)
	public void testAddInventoryByNegativeSugarInteger() throws InventoryException {
		coffeeMaker.addInventory("0","0","-5","0");
		assertEquals("Coffee: 15\nMilk: 15\nSugar: 10\nChocolate: 15\n", coffeeMaker.checkInventory());
	}


	/**
	 * Test adding negative integer setup in inventory
	 * @throws InventoryException  if there was an error parsing the quantity
	 * 		to a positive integer.
	 */
	@Test(expected = InventoryException.class)
	public void testAddInventoryByNegativeChocolateInteger() throws InventoryException {
		coffeeMaker.addInventory("0","0","0","-5");
		assertEquals("Coffee: 15\nMilk: 15\nSugar: 15\nChocolate: 10\n", coffeeMaker.checkInventory());

	}


	/**
	 * Test adding string to the setup in inventory
	 * @throws InventoryException  if there was an error parsing the quantity
	 * 		to a positive integer.
	 */
	@Test(expected = InventoryException.class)
	public void testAddInventoryCoffeeByString() throws InventoryException {
		coffeeMaker.addInventory("test","0","0","0");
		assertEquals("Coffee: 15test\nMilk: 15\nSugar: 15\nChocolate: 15\n", coffeeMaker.checkInventory());
	}


	/**
	 * Test adding string to the setup in inventory
	 * @throws InventoryException  if there was an error parsing the quantity
	 * 		to a positive integer.
	 */
	@Test(expected = InventoryException.class)
	public void testAddInventoryMilkByString() throws InventoryException {
		coffeeMaker.addInventory("0","test","0","0");
		assertEquals("Coffee: 15test\nMilk: 15\nSugar: 15\nChocolate: 15\n", coffeeMaker.checkInventory());
	}

	/**
	 * Test adding string to the setup in inventory
	 * @throws InventoryException  if there was an error parsing the quantity
	 * 		to a positive integer.
	 */
	@Test(expected = InventoryException.class)
	public void testAddInventorySugarByString() throws InventoryException {
		coffeeMaker.addInventory("0","0","test","0");
		assertEquals("Coffee: 15test\nMilk: 15\nSugar: 15\nChocolate: 15\n", coffeeMaker.checkInventory());
	}

	/**
	 * Test adding string to the setup in inventory
	 * @throws InventoryException  if there was an error parsing the quantity
	 * 		to a positive integer.
	 */
	@Test(expected = InventoryException.class)
	public void testAddInventoryChocolateByString() throws InventoryException {
		coffeeMaker.addInventory("0","0","0","test");
		assertEquals("Coffee: 15test\nMilk: 15\nSugar: 15\nChocolate: 15\n", coffeeMaker.checkInventory());
	}

	/**
	 * Given a coffee maker with one valid recipe
	 * When we make coffee, selecting the valid recipe and paying more than
	 * 		the coffee costs
	 * Then we get the correct change back.
	 */
	@Test
	public void testMakeCoffee() {
		Recipe[] recipesArr = new Recipe[] {recipe1};
		when(stubRecipeBook.getRecipes()).thenReturn(recipesArr);

		assertEquals(25, mockCoffeeMaker.makeCoffee(0, 75));

		verify(stubRecipeBook, times(4)).getRecipes();
	}

	/**
	 * Test the negative purchasing the beverage
	 */
	@Test
	public void testMakeCoffeeByNegativeInteger() {
		Recipe[] recipesArr = new Recipe[] {recipe3};
		when(stubRecipeBook.getRecipes()).thenReturn(recipesArr);

		assertEquals(-100, mockCoffeeMaker.makeCoffee(0, -100));
		verify(stubRecipeBook, times(2)).getRecipes();
	}

	/**
	 * Test whether the given money is acceptable
	 */
	@Test
	public void testMakeCoffeeWithoutRightAmountOfMoney() {
		Recipe[] recipesArr = new Recipe[] {recipe2};
		when(stubRecipeBook.getRecipes()).thenReturn(recipesArr);

		assertEquals(mockCoffeeMaker.makeCoffee(0, 50), 50);
		verify(stubRecipeBook, times(2)).getRecipes();

	}

	/**
	 * Test making a coffee without enough ingredients
	 */
	@Test
	public void testMakeCoffeeWithoutIngredients() throws RecipeException {
		Recipe testCoffeeIng = createRecipe("Not Enough Beverage Ingredients", "50", "50", "50", "50", "20");
		Recipe[] recipesArr = new Recipe[] {testCoffeeIng};
		when(stubRecipeBook.getRecipes()).thenReturn(recipesArr);

		assertEquals(mockCoffeeMaker.makeCoffee(0, 20), 20);
		verify(stubRecipeBook, times(3)).getRecipes();

	}

	/**
	 * Test giving no recipe to make coffee
	 */
	@Test
	public void testMakeCoffeeWithoutRecipe() {
		assertEquals(50, coffeeMaker.makeCoffee(0, 50));
	}

	/**
	 * Checking the amount of spent ingredients
	 */
	@Test
	public void testCheckIngredient() {
		Inventory inventory = new Inventory();
		inventory.useIngredients(recipe3);

		assertEquals(inventory.getChocolate(), 15);
		assertEquals(inventory.getCoffee(), 12);
		assertEquals(inventory.getMilk(), 12);
		assertEquals(inventory.getSugar(), 14);
	}


	/**
	 * Test if the method makeCoffee() calls getRecipes() and calls addRecipe() or not
	 */
	@Test
	public void testMockMakeCoffeeGetRecipes() {
		Recipe[] recipesArr = new Recipe[] {recipe1, recipe2, recipe3};
		when(stubRecipeBook.getRecipes()).thenReturn(recipesArr);

		assertEquals(0, mockCoffeeMaker.makeCoffee(0, 50));
		verify(stubRecipeBook, times(4)).getRecipes();
		verify(stubRecipeBook, never()).addRecipe(recipe1);
		verify(stubRecipeBook, never()).addRecipe(recipe2);
		verify(stubRecipeBook, never()).addRecipe(recipe3);
	}
}
