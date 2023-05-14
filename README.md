# AppRecipes
App for searching recipes online and saving them locally.

## Design made in Figma
![recipes_app_design_v1.png](recipes_app_design_v1.png)

## Setup
To use this app, you need to provide your own Spoonacular API key (https://spoonacular.com/food-api).
In `network` package, create `ApiKeys.kt` and an object with value `SPOONACULAR_API_KEY`.
```
object ApiKeys {
  const val SPOONACULAR_API_KEY: String = *put your key here*
}
```
## Screenshots
![screenshots/Saved_screen.jpg](screenshots/Saved_screen.jpg)
![screenshots/Online_recipes_screen.jpg](screenshots/Online_recipes_screen.jpg)
![screenshots/Saved_recipe.jpg](screenshots/Saved_recipe.jpg)
![screenshots/Online_recipe.jpg](screenshots/Online_recipe.jpg)
![screenshots/Add_new_recipe.jpg](screenshots/Add_new_recipe.jpg)
![screenshots/Edit_recipe.jpg](screenshots/Edit_recipe.jpg)
