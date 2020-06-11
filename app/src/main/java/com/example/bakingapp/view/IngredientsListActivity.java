package com.example.bakingapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;

import com.example.bakingapp.R;
import com.example.bakingapp.model.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientsListActivity extends AppCompatActivity {

    private Recipe selectedRecipe;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients_list);

        ButterKnife.bind(this);

        Intent intentThatStartedThisActivity = getIntent();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Ingredients list");

        if(intentThatStartedThisActivity!=null){
            if(intentThatStartedThisActivity.hasExtra("parcel_data")){
                selectedRecipe =getIntent().getParcelableExtra("parcel_data");
                mToolbar.setNavigationOnClickListener(v -> finish());
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