package com.example.bakingapp.view;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.bakingapp.R;
import com.example.bakingapp.model.Recipe;

/**
 * Implementation of App Widget functionality.
 */
public class BakingAppWidget extends AppWidgetProvider {

    static Recipe selectedRecipe;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {


        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget);

        Intent intent = new Intent(context,BakingWidgetService.class);
        views.setRemoteAdapter(R.id.list_view,intent);
        // Instruct the widget manager to update the widget
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId,R.id.list_view);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static void updateWidgets (Context context,AppWidgetManager appWidgetManager,int[]appWidgetIds){
        for(int appWidgetId:appWidgetIds){
            updateAppWidget(context,appWidgetManager,appWidgetId);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[]appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context,BakingAppWidget.class));

        final String action = intent.getAction();

        if(action.equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)){
            selectedRecipe = intent.getParcelableExtra("selected_recipe");
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds,R.id.list_view);
            //refresh all widgets
            BakingAppWidget.updateWidgets(context,appWidgetManager,appWidgetIds);

            super.onReceive(context, intent);
        }



    }
}

