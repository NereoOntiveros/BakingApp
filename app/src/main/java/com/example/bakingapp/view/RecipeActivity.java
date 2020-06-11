package com.example.bakingapp.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingapp.R;
import com.example.bakingapp.model.Recipe;
import com.example.bakingapp.model.RecipeStep;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeActivity extends AppCompatActivity implements StepListAdapter.StepAdapterOnClickHandler {


    @BindView(R.id.rv_steps)
    RecyclerView mStepsListRV;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private Recipe mRecipe;
    private StepListAdapter mStepsAdapter;
    private boolean mTwoPane = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_activity);
        ButterKnife.bind(this);
        Intent intentThatStartedThisActivity = getIntent();
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(intentThatStartedThisActivity!=null){
            if(intentThatStartedThisActivity.hasExtra("parcel_data")){
                mRecipe =getIntent().getParcelableExtra("parcel_data");
                getSupportActionBar().setTitle(mRecipe.getName());
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

        if(selectedStep.getId()!=null){
            if(mTwoPane){
                DetailStepFragment fragment = DetailStepFragment.newInstance( selectedStep);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.detail_container,fragment)
                        .addToBackStack(null)
                        .commit();

            }else {
                Context context = this;
                Class destinationClass = DetailStepActivity.class;
                Intent intentToStartRecipeActivity = new Intent(context,destinationClass);
                intentToStartRecipeActivity.putExtra("parcel_data",selectedStep);
                startActivity(intentToStartRecipeActivity);
            }
        }else {
            if(mTwoPane){
                IngredientsListFragment fragment = IngredientsListFragment.newInstance(mRecipe);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.detail_container,fragment)
                        .addToBackStack(null)
                        .commit();
            }else {
                Context context = this;
                Class destinationClass = IngredientsListActivity.class;
                Intent intentToStartIngredientsListActivity = new Intent(context,destinationClass);
                intentToStartIngredientsListActivity.putExtra("parcel_data",mRecipe);
                startActivity(intentToStartIngredientsListActivity);

            }
        }






    }
}
