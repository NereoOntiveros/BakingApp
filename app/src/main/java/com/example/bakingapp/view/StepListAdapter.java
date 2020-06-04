package com.example.bakingapp.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingapp.R;
import com.example.bakingapp.model.RecipeStep;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepListAdapter extends RecyclerView.Adapter<StepListAdapter.StepViewHolder> {

    private ArrayList<RecipeStep>stepsData;
    private final StepAdapterOnClickHandler mClickHandler;

    public StepListAdapter(StepAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    public interface StepAdapterOnClickHandler{
        void onClick(RecipeStep selectedStep);
    }


    public class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_step_name)
        TextView stepItemView;

        public StepViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            RecipeStep selectedStep = stepsData.get(adapterPosition);
            mClickHandler.onClick(selectedStep);
        }
    }

    @NonNull
    @Override
    public StepListAdapter.StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context= parent.getContext();
        int layoutIdForListItem = R.layout.steplist_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem,parent, false);
        return new StepListAdapter.StepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepListAdapter.StepViewHolder holder, int position) {
        if (position == 0) {
            holder.stepItemView.setText("Recipe Ingredients");
        } else {
            RecipeStep aStep = stepsData.get(position - 1);
            holder.stepItemView.setText(aStep.getShortDescription());
        }

    }

    @Override
    public int getItemCount() {

        if (stepsData==null)
            return 0;
        return stepsData.size() + 1;

    }

    public void setStepsData(ArrayList<RecipeStep> stepsData){
        this.stepsData = stepsData;
        notifyDataSetChanged();
    }
}
