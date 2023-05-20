package com.sergei.apprecipes

import com.sergei.apprecipes.network.Ingredient
import com.sergei.apprecipes.network.prepareSpoonacularInstructions
import junit.framework.TestCase.assertTrue
import org.junit.Test

class NetworkUnitTest {
    @Test
    fun checkHtmlResponseInstructionsConversion() {
        assertTrue(prepareSpoonacularInstructions("<ol>Recipe</ol>") == "Recipe")
    }

    @Test
    fun checkHtmlResponseIngredientsConversion() {
        assertTrue(
            prepareRecipeOnlineIngredients(
                listOf(
                    Ingredient("One"),
                    Ingredient("Two")
                )
            ) == "One\nTwo\n"
        )
    }
}