package com.example.bakingapp.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingapp.R;
import com.example.bakingapp.model.Ingredient;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientsListAdapter extends RecyclerView.Adapter<IngredientsListAdapter.IngredientViewHolder>{

    private ArrayList<Ingredient> ingredientsData;

    public IngredientsListAdapter(){

    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //Inflates the view and pass it to the viewholder to create it. Then returns it.
        Context context= parent.getContext();
        int layoutIdForListItem = R.layout.ingredientlist_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem,parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        if(ingredientsData==null)
            return 0;
        return ingredientsData.size();
    }

    public void setIngredientsData(ArrayList<Ingredient> ingredientsData){
        this.ingredientsData=ingredientsData;
        notifyDataSetChanged();
    }



    public class IngredientViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_ingredient_name)
        TextView ingredientName;
        @BindView(R.id.tv_ingredient_measure)
        TextView ingredientMeasure;
        @BindView(R.id.tv_ingredient_quantity)
        TextView ingredientQuantity;

        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        @SuppressLint("SetTextI18n")
        void bind(int position){
            Ingredient ingredient = ingredientsData.get(position);
            String text1 = this.itemView.getContext().getString(R.string.ingredient_name);
            String text2 = this.itemView.getContext().getString(R.string.ingredient_measure);
            String text3 = this.itemView.getContext().getString(R.string.ingredient_quantity);
            ingredientName.setText(String.format("%s  %s",text1, ingredient.getIngredient()));
            ingredientMeasure.setText(String.format("%s  %s", text2, ingredient.getMeasure()));
            ingredientQuantity.setText(String.format("%s  %s", text3, ingredient.getQuantity()));

        }
    }


}
