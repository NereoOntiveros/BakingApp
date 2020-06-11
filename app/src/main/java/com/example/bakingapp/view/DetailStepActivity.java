package com.example.bakingapp.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.bakingapp.R;
import com.example.bakingapp.model.RecipeStep;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * This activity represents a single step  of the recipe
 * */

public class DetailStepActivity extends AppCompatActivity {

    private RecipeStep mSelectedStep;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stepdetail);
        ButterKnife.bind(this);
        Intent intentThatStartedThisActivity = getIntent();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(intentThatStartedThisActivity!=null){
            if(intentThatStartedThisActivity.hasExtra("parcel_data")){
                mSelectedStep =getIntent().getParcelableExtra("parcel_data");
                getSupportActionBar().setTitle(mSelectedStep.getShortDescription());
                mToolbar.setNavigationOnClickListener(v -> finish());
            }
        }

        if(savedInstanceState == null){
            DetailStepFragment fragment = DetailStepFragment.newInstance( mSelectedStep);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.step_detail_container,fragment)
                    .commit();
        }




    }
}