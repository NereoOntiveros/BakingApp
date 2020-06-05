package com.example.bakingapp.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingapp.R;
import com.example.bakingapp.model.Recipe;
import com.example.bakingapp.model.RecipeStep;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeActivity extends AppCompatActivity implements StepListAdapter.StepAdapterOnClickHandler {

    //@BindView(R.id.tv_ingredients)
    //TextView mIngredients;
    @BindView(R.id.rv_steps)
    RecyclerView mStepsListRV;
    private Recipe mRecipe;
    private StepListAdapter mStepsAdapter;
    private boolean mTwoPane = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_activity);
        ButterKnife.bind(this);
        Intent intentThatStartedThisActivity = getIntent();

        if(intentThatStartedThisActivity!=null){
            if(intentThatStartedThisActivity.hasExtra("parcel_data")){
                mRecipe =getIntent().getParcelableExtra("parcel_data");

                if(findViewById(R.id.detail_container)!=null){
                    mTwoPane=true;
                }

                setUpRecyclerView();
                loadSteps(mRecipe.getSteps());




            }
        }



    }

    private void setUpRecyclerView(){
        mStepsAdapter = new StepListAdapter(this);
        mStepsListRV.setAdapter(mStepsAdapter);
        mStepsListRV.setLayoutManager(new LinearLayoutManager(this));
        mStepsListRV.setHasFixedSize(true);
    }

    private void loadSteps(ArrayList<RecipeStep> stepsData){
        mStepsAdapter.setStepsData(stepsData);
    }


    @Override
    public void onClick(RecipeStep selectedStep) {

        if(mTwoPane){
            DetailFragment fragment = DetailFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_container,fragment)
                    .addToBackStack(null)
                    .commit();

        }else {
            Context context = this;
            Class destinationClass = DetailActivity.class;
            Intent intentToStartRecipeActivity = new Intent(context,destinationClass);
            intentToStartRecipeActivity.putExtra("parcel_data",selectedStep);
            startActivity(intentToStartRecipeActivity);
        }



    }
}
