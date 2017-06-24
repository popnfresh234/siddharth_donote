package com.example.sthakrey.donote.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.sthakrey.donote.R;
import com.example.sthakrey.donote.data.Notes;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sthakrey on 6/20/2017.
 */

public class DoNoteRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private List<Notes> notesList=null;
    private List<String> keyList = null;
    private Context context =null;

    @Override
    public void onCreate() {

    }


    DoNoteRemoteViewsFactory(Context c , Intent intent)
    {

        notesList = new ArrayList<Notes>();
        keyList = new ArrayList<String>();
        this.context = c;
    }

    @Override
    public int getCount() {
        return notesList.size();
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public void onDestroy() {
        notesList.clear();
        keyList.clear();

    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews view = new RemoteViews(context.getPackageName(),R.layout.widget_donote_list_item);
        view.setTextViewText(R.id.notes_title, notesList.get(position).getTitle());
        view.setTextViewText(R.id.notes_description, notesList.get(position).getDescription());
        view.setTextViewText(R.id.notes_label, notesList.get(position).getLabel());
        String colorstr = notesList.get(position).getColor();
        int color = (int)Long.parseLong(colorstr, 16);
        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = (color >> 0) & 0xFF;
        view.setInt(R.id.item_container, "setBackgroundColor", Color.rgb(r,g,b));

        final Intent intent = new Intent();
        intent.putExtra("Functionality", "edit");
        intent.putExtra("NotesKey", keyList.get(position));

        view.setOnClickFillInIntent(R.id.item_container, intent);
        return view;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public List<Notes> getNotesList() {
        return notesList;
    }





    @Override
    public void onDataSetChanged() {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("/user/notes");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot choreSnapshot : dataSnapshot.getChildren()) {
                    notesList.add(choreSnapshot.getValue(Notes.class));
                    keyList.add(choreSnapshot.getKey());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        databaseReference.addValueEventListener(valueEventListener);

        //databaseReference.removeEventListener(valueEventListener);

    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
