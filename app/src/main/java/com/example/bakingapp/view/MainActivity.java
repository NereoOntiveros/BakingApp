package com.example.bakingapp.view;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingapp.R;
import com.example.bakingapp.model.Recipe;
import com.example.bakingapp.utils.JsonUtils;
import com.example.bakingapp.utils.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements RecipeListAdapter.RecipeAdapterOnClickHandler {

    //use of Butterknife library
    @BindView(R.id.rv_recipes)
    RecyclerView mRecyclerview;
    @BindView(R.id.tv_error_connection_display)
    TextView mErrorConnection;
    @BindView(R.id.tv_error_message_display)
    TextView mErrorDisplay;
    @BindView(R.id.pb_loading_indicator)
    ProgressBar mLoadingIndicator;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private RecipeListAdapter mRecipesAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if(thereIsConnection()){
            setUpRecyclerView();
            loadRecipes();
        }else {
            showNetworkConnectionErrorMessage();
        }

        setSupportActionBar(mToolbar);

    }

    private void setUpRecyclerView(){
        mRecipesAdapter = new RecipeListAdapter(this);
        mRecyclerview.setAdapter(mRecipesAdapter);

        if(findViewById(R.id.tablet_layout)==null){
            mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        }else {
            mRecyclerview.setLayoutManager(new GridLayoutManager(this,2));
        }
        mRecyclerview.setHasFixedSize(true);


    }

    private void loadRecipes(){
        showRecipesDataView();
        URL url = NetworkUtils.buildUrl();
        new FetchRecipesTask().execute(url);
    }

    private boolean thereIsConnection() {


        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();

    }

    private void showRecipesDataView(){
        mErrorDisplay.setVisibility(View.INVISIBLE);
        mRecyclerview.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage(){
        mRecyclerview.setVisibility(View.INVISIBLE);
        mErrorDisplay.setVisibility(View.VISIBLE);
    }

    private void showNetworkConnectionErrorMessage(){

        mErrorConnection.setVisibility(View.VISIBLE);
    }




    public class FetchRecipesTask extends AsyncTask<URL, Void, ArrayList<Recipe>>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<Recipe> doInBackground(URL... params) {
            if (params.length == 0){
                return null;
            }

            URL url = params[0];

            try{
                String responseFromHttpUrl = NetworkUtils.getResponseFromHttpUrl(url);
                ArrayList<Recipe> recipesList = JsonUtils.getRecipesList(responseFromHttpUrl);
                return recipesList;
            }catch (IOException | JSONException e){
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Recipe> recipesList) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if(recipesList!=null){
                showRecipesDataView();
                mRecipesAdapter.setRecipesData(recipesList);
            }else {
                showErrorMessage();
            }

        }
    }

    @Override
    public void onClick(Recipe selectedRecipe) {
        Context context = this;
        Class destinationClass = RecipeActivity.class;
        Intent intentToStartRecipeActivity = new Intent(context,destinationClass);
        intentToStartRecipeActivity.putExtra("parcel_data",selectedRecipe);
        startActivity(intentToStartRecipeActivity);
    }
}
