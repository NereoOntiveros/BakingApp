package com.example.bakingapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.bakingapp.R;
import com.example.bakingapp.model.Recipe;

import butterknife.ButterKnife;

public class IngredientsListActivity extends AppCompatActivity {

    private Recipe selectedRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients_list);

        ButterKnife.bind(this);

        Intent intentThatStartedThisActivity = getIntent();

        if(intentThatStartedThisActivity!=null){
            if(intentThatStartedThisActivity.hasExtra("parcel_data")){
                selectedRecipe =getIntent().getParcelableExtra("parcel_data");

            }
        }

        if(savedInstanceState == null){
            IngredientsListFragment fragment = IngredientsListFragment.newInstance( selectedRecipe);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.ingredients_list_container,fragment)
                    .commit();
        }
    }
}