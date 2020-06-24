package com.example.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Recipe implements Parcelable {
    private Integer id;
    private String name;
    private ArrayList<Ingredient> ingredients;
    private ArrayList<RecipeStep> steps;
    private Integer servings;



    public Recipe(){}

    protected Recipe(Parcel in) {
        id = in.readInt();
        name=in.readString();
        ingredients=in.readArrayList(Ingredient.class.getClassLoader());
        steps=in.readArrayList(RecipeStep.class.getClassLoader());
        servings=in.readInt();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public void setId (Integer id){this.id=id;}
    public void setName(String name){this.name=name;}
    public void setIngredients(ArrayList<Ingredient> ingredients){this.ingredients=ingredients;}
    public void setSteps(ArrayList<RecipeStep> steps){this.steps=steps;}
    public void setServings(Integer servings) {this.servings = servings;}

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public ArrayList<RecipeStep> getSteps() {
        return steps;
    }

    public Integer getServings() {
        return servings;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeList(ingredients);
        dest.writeList(steps);
        dest.writeInt(servings);
    }
}
