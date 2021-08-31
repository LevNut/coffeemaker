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
	private Inventory inventory;

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


	/**
	 * Given a coffee maker with the default inventory
	 * When we add inventory with malformed quantities (i.e., a negative
	 * quantity and a non-numeric string)
	 * Then we get an inventory exception
	 *
	 * @throws InventoryException  if there was an error parsing the quanity
	 * 		to a positive integer.
	 */
	@Test(expected = InventoryException.class)
	public void testAddInventoryException() throws InventoryException {
		coffeeMaker.addInventory("4", "-1", "asdf", "3");
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
	public void testAddInventoryByNegativeInteger() throws InventoryException {
		coffeeMaker.addInventory("-5","0","0","0");
		assertEquals("Coffee: 10\nMilk: 15\nSugar: 15\nChocolate: 15\n", coffeeMaker.checkInventory());

		coffeeMaker.addInventory("0","-5","0","0");
		assertEquals("Coffee: 10\nMilk: 10\nSugar: 15\nChocolate: 15\n", coffeeMaker.checkInventory());

		coffeeMaker.addInventory("0","0","-5","0");
		assertEquals("Coffee: 10\nMilk: 10\nSugar: 10\nChocolate: 15\n", coffeeMaker.checkInventory());

		coffeeMaker.addInventory("0","0","0","-5");
		assertEquals("Coffee: 10\nMilk: 10\nSugar: 10\nChocolate: 10\n", coffeeMaker.checkInventory());
	}


	/**
	 * Test adding string to the setup in inventory
	 * @throws InventoryException  if there was an error parsing the quantity
	 * 		to a positive integer.
	 */
	@Test(expected = InventoryException.class)
	public void testAddInventoryByString() throws InventoryException {
		coffeeMaker.addInventory("test","0","0","0");
		assertEquals("Coffee: 15test\nMilk: 15\nSugar: 15\nChocolate: 15\n", coffeeMaker.checkInventory());
	}

	/**
	 * Test the default purchasing the beverage
	 */
	@Test
	public void testPurchaseBeverage() {
		coffeeMaker.addRecipe(recipe2);
		coffeeMaker.addRecipe(recipe3);
		coffeeMaker.addRecipe(recipe4);
		// 75 100 65
		assertEquals(coffeeMaker.makeCoffee(0, 0), 0);
		// coffeeMaker.makeCoffee(0, -10);
		coffeeMaker.makeCoffee(0, 65);



	}

	/**
	 * Test checking the spent ingredients
	 */
	@Test
	public void testCheckIngredient() {
		inventory = new Inventory();
		inventory.useIngredients(recipe3);

		assertEquals(inventory.getChocolate(), 15);
		assertEquals(inventory.getCoffee(), 12);
		assertEquals(inventory.getMilk(), 12);
		assertEquals(inventory.getSugar(), 14);
	}
}
