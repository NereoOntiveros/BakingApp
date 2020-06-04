package com.example.bakingapp.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingapp.R;
import com.example.bakingapp.model.Recipe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder>{

    private ArrayList<Recipe> recipesData;
    private final RecipeAdapterOnClickHandler mClickHandler;

    public interface RecipeAdapterOnClickHandler{
        void onClick(Recipe selectedRecipe);
    }

    public RecipeListAdapter(RecipeAdapterOnClickHandler clickHandler){
        mClickHandler = clickHandler;
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.recipename_tv)
        TextView recipeItemView;

        public RecipeViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Recipe selectedRecipe = recipesData.get(adapterPosition);
            mClickHandler.onClick(selectedRecipe);
        }
    }


    @NonNull
    @Override
    public RecipeListAdapter.RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context= parent.getContext();
        int layoutIdForListItem = R.layout.recipelist_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem,parent, false);
        return new RecipeViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecipeListAdapter.RecipeViewHolder holder, int position) {

        Recipe aRecipe = recipesData.get(position);
        holder.recipeItemView.setText(aRecipe.getName());


    }

    @Override
    public int getItemCount() {
        if(recipesData==null)
            return 0;
        return recipesData.size();

    }

    public void setRecipesData(ArrayList<Recipe> recipesData){
        this.recipesData=recipesData;
        notifyDataSetChanged();
    }

}

