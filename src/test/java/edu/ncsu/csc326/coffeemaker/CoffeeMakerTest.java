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

		//Set up for r1
		recipe1 = new Recipe();
		recipe1.setName("Coffee");
		recipe1.setAmtChocolate("0");
		recipe1.setAmtCoffee("3");
		recipe1.setAmtMilk("1");
		recipe1.setAmtSugar("1");
		recipe1.setPrice("50");

		//Set up for r2
		recipe2 = new Recipe();
		recipe2.setName("Mocha");
		recipe2.setAmtChocolate("20");
		recipe2.setAmtCoffee("3");
		recipe2.setAmtMilk("1");
		recipe2.setAmtSugar("1");
		recipe2.setPrice("75");

		//Set up for r3
		recipe3 = new Recipe();
		recipe3.setName("Latte");
		recipe3.setAmtChocolate("0");
		recipe3.setAmtCoffee("3");
		recipe3.setAmtMilk("3");
		recipe3.setAmtSugar("1");
		recipe3.setPrice("100");

		//Set up for r4
		recipe4 = new Recipe();
		recipe4.setName("Hot Chocolate");
		recipe4.setAmtChocolate("4");
		recipe4.setAmtCoffee("0");
		recipe4.setAmtMilk("1");
		recipe4.setAmtSugar("1");
		recipe4.setPrice("65");



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
	public void testAddInventoryByMilkString() throws InventoryException {
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
		coffeeMaker.addRecipe(recipe1);
		assertEquals(25, coffeeMaker.makeCoffee(0, 75));
	}

	/**
	 * Test the negative purchasing the beverage
	 */
	@Test (expected = Exception.class)
	public void testMakeCoffeeByNegativeInteger() {
		coffeeMaker.addRecipe(recipe3);
		coffeeMaker.makeCoffee(0, -100);
	}

	/**
	 * Test whether the given money is acceptable
	 */
	@Test
	public void testMakeCoffeeWithoutRightAmountOfMoney() {
		coffeeMaker.addRecipe(recipe2);
		assertEquals(coffeeMaker.makeCoffee(0, 50), 50);

	}

	/**
	 * Test making a coffee without enough ingredients
	 */
	@Test
	public void testMakeCoffeeWithoutIngredients() throws RecipeException {
		Recipe testCoffeeIng = createRecipe("Not Enough Beverage Ingredients", "50", "50", "50", "50", "20");
		coffeeMaker.addRecipe(testCoffeeIng);
		assertEquals(coffeeMaker.makeCoffee(0, 20), 20);

	}

	/**
	 * Test giving no recipe to make coffee
	 */
	@Test
	public void testMakeCoffeeWithoutRecipe() {
		assertEquals(coffeeMaker.makeCoffee(0, 50), 50);
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
}
