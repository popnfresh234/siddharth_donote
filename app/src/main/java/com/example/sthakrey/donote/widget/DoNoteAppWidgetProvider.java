package com.example.sthakrey.donote.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.sthakrey.donote.EditNoteActivity;
import com.example.sthakrey.donote.R;
import com.example.sthakrey.donote.widget.DoNoteRemoteViewsService;


/**
 * Created by siddharth.thakrey on 21-01-2017.
 */
public class DoNoteAppWidgetProvider extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        super.onUpdate(context, appWidgetManager, appWidgetIds);

        for(int i=0;i<appWidgetIds.length;i++) {
            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_donote_detail);
            rv.setRemoteAdapter(
                    R.id.detail_widget_list, new Intent(context, DoNoteRemoteViewsService.class));
            Intent templateIntent = new Intent(context, EditNoteActivity.class);
            PendingIntent pendingTemplateIntent = PendingIntent.getActivity(context,
                    0, templateIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            rv.setPendingIntentTemplate(R.id.detail_widget_list, pendingTemplateIntent);
            appWidgetManager.updateAppWidget(appWidgetIds[i], rv);

        }



    }

    @Override
    public void onReceive(Context context, Intent intent) {

        super.onReceive(context, intent);
        AppWidgetManager am = AppWidgetManager.getInstance(context);
            int[] appWidgetIds = am.getAppWidgetIds(new ComponentName(context, DoNoteAppWidgetProvider.class));
            am.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.detail_widget_list);

        }
}