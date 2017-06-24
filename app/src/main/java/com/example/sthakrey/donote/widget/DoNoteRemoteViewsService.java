package com.example.sthakrey.donote.widget;

import android.app.Notification;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.support.v4.content.CursorLoader;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

/**
 * Created by siddharth.thakrey on 21-01-2017.
 */
public class DoNoteRemoteViewsService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new DoNoteRemoteViewsFactory(getApplicationContext(), intent);
    }
}
