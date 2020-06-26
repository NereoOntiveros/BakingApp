package com.example.bakingapp.utils;

import android.util.Log;

import com.example.bakingapp.model.Ingredient;
import com.example.bakingapp.model.Recipe;
import com.example.bakingapp.model.RecipeStep;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public final class JsonUtils {

    public static ArrayList<Recipe> getRecipesList(String responseFromHttpUrl) throws JSONException {

        JSONArray responseArray =new JSONArray(responseFromHttpUrl);


        ArrayList<Recipe> recipesList = new ArrayList<>();

        try{
            //loops the JSONArray to parse every JSONObject into a Recipe object
            for (int i = 0; i<responseArray.length();i++){
                JSONObject jsonRecipe = responseArray.getJSONObject(i);
                Recipe recipe = new Recipe();
                ArrayList<RecipeStep> stepArrayList= new ArrayList<>();
                ArrayList<Ingredient>ingredientsArrayList = new ArrayList<>();
                JSONArray ingredientsJSONArray = jsonRecipe.getJSONArray("ingredients");
                JSONArray stepsJSONArray = jsonRecipe.getJSONArray("steps");

                //loops the jsonarray of ingredients to parse every JSONobject to an Ingredient
                //and add it to an ArrayList of Ingredients
                for (int j = 0;j<ingredientsJSONArray.length();j++){
                    JSONObject jsonIngredient =ingredientsJSONArray.getJSONObject(j);

                    Ingredient ingredient = new Ingredient();
                    //builds the Ingredient object
                    ingredient.setIngredient(jsonIngredient.getString("ingredient"));
                    ingredient.setMeasure(jsonIngredient.getString("measure"));
                    ingredient.setQuantity(jsonIngredient.getInt("quantity"));

                    ingredientsArrayList.add(ingredient);
                }

                //loops the jsonarray of steps to parse every JSONobject to a RecipeStep
                //and add it to an ArrayList of RecipeStep

                for (int k = 0;k<stepsJSONArray.length();k++){
                    JSONObject jsonStep = stepsJSONArray.getJSONObject(k);
                    RecipeStep step = new RecipeStep();
                    //builds the RecipeStep object
                    step.setId(jsonStep.getInt("id"));
                    step.setDescription(jsonStep.getString("description"));
                    step.setShortDescription(jsonStep.getString("shortDescription"));
                    step.setVideoUrl(jsonStep.getString("videoURL"));
                    step.setThumbnailURL(jsonStep.getString("thumbnailURL"));

                    stepArrayList.add(step);
                }

                //builds the Recipe object
                recipe.setId(jsonRecipe.getInt("id"));
                recipe.setName(jsonRecipe.getString("name"));
                recipe.setIngredients(ingredientsArrayList);
                recipe.setSteps(stepArrayList);
                recipe.setServings(jsonRecipe.getInt("servings"));

                //Then add it to the list of recipes
                recipesList.add(recipe);
            }

        }catch (JSONException e){
            e.printStackTrace();
        }

        return recipesList;
    }
}
