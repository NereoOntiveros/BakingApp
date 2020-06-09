package com.example.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Ingredient implements Parcelable{

    private Integer quantity;
    private String measure;
    private String ingredient_name;

    public Ingredient(){}

    protected Ingredient(Parcel in) {
        quantity=in.readInt();
        measure=in.readString();
        ingredient_name=in.readString();
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public void setIngredient(String ingredient) {
        this.ingredient_name = ingredient;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getIngredient() {
        return ingredient_name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(quantity);
        dest.writeString(measure);
        dest.writeString(ingredient_name);
    }
}
