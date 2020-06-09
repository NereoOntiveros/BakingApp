package com.example.bakingapp.view;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.bakingapp.R;
import com.example.bakingapp.model.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IngredientsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IngredientsListFragment extends Fragment {

    private Recipe selectedRecipe;
    @BindView(R.id.rv_ingredients)
    RecyclerView mIngredientsListRV;


    public IngredientsListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment IngredientsListFragment.
     */

    public static IngredientsListFragment newInstance(Recipe selectedRecipe) {
        IngredientsListFragment fragment = new IngredientsListFragment();

        //set the bundle arguments for the fragment
        Bundle arguments = new Bundle();
        arguments.putParcelable("SELECTED_RECIPE",selectedRecipe);
        fragment.setArguments(arguments);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments().containsKey("SELECTED_RECIPE")){
            //load the content specified by the fragment arguments
            selectedRecipe = getArguments().getParcelable("SELECTED_RECIPE");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_ingredients_list, container, false);
        ButterKnife.bind(this,rootView);

        Context context = getContext();

        //create the adapter for the recyclerview
        IngredientsListAdapter adapter = new IngredientsListAdapter();
        adapter.setIngredientsData(selectedRecipe.getIngredients());

        //create the layout for the recyclerview
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);

        //Set the recyclerview
        mIngredientsListRV.addItemDecoration(new DividerItemDecoration(context,DividerItemDecoration.VERTICAL));
        mIngredientsListRV.setLayoutManager(linearLayoutManager);
        mIngredientsListRV.setAdapter(adapter);
        mIngredientsListRV.setHasFixedSize(true);


        return rootView;
    }
}