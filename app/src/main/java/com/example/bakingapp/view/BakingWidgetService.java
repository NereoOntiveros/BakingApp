package com.example.bakingapp.view;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.Toast;

import com.example.bakingapp.R;
import com.example.bakingapp.model.Ingredient;
import com.example.bakingapp.model.Recipe;

import static  com.example.bakingapp.view.BakingAppWidget.selectedRecipe;
import java.util.ArrayList;

public class BakingWidgetService extends RemoteViewsService {

    ArrayList<Ingredient>ingredientArrayList;
    ArrayList<String>remoteViewIngredientsList;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFactory(this.getApplicationContext(),intent);
    }

    class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory{

        Context mContext = null;

        public ListRemoteViewsFactory(Context context,Intent intent){
            mContext = context;
        }

        public ArrayList<String> getIngredientsList(){

            ArrayList<String>stringIngredientsList = new ArrayList<>();

            if(selectedRecipe!=null){
                ingredientArrayList=selectedRecipe.getIngredients();

                for (int i =0;i<ingredientArrayList.size();i++){
                    stringIngredientsList.add(ingredientArrayList.get(i).getIngredient());
                }

            }


            return stringIngredientsList;
        }

        @Override
        public void onCreate() {
            remoteViewIngredientsList=getIngredientsList();
        }

        @Override
        public void onDataSetChanged() {
            remoteViewIngredientsList=getIngredientsList();
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return remoteViewIngredientsList.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {

            RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.widget_list_item);

            views.setTextViewText(R.id.list_item_view,remoteViewIngredientsList.get(position));
            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }

}
