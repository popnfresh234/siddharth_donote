package com.example.sthakrey.donote;


import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContentResolverCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sthakrey.donote.data.Notes;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;


public class NotesFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    FirebaseRecyclerAdapter<Notes, NotesViewHolder> adapter =null;

    public NotesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View view = inflater.inflate(R.layout.notes_fragment, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getContext());

        Log.e("BOO", "note fragment");

        Query firebaseDatabase;
        if(getArguments()==null)
        firebaseDatabase = FirebaseDatabase.getInstance().getReference("/user/notes");
        else {
            String label = getArguments().getString("label");
            firebaseDatabase = FirebaseDatabase.getInstance().getReference("/user/notes").orderByChild("label").equalTo(label);
        }

        adapter = new FirebaseRecyclerAdapter<Notes, NotesViewHolder>(Notes.class, R.layout.notes_list_item, NotesViewHolder.class, firebaseDatabase) {


            @Override
            protected void populateViewHolder(final NotesViewHolder viewHolder, Notes model, final int position) {

                String key = adapter.getRef(position).getKey();
                DatabaseReference db = FirebaseDatabase.getInstance().getReference("/user/notes/"+key);
                ValueEventListener ve = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Notes n = dataSnapshot.getValue(Notes.class);
                        int color = (int)Long.parseLong(n.getColor(), 16);
                        int r = (color >> 16) & 0xFF;
                        int g = (color >> 8) & 0xFF;
                        int b = (color >> 0) & 0xFF;

                        viewHolder.mView.setBackgroundColor(Color.rgb(r,g,b));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                };
                db.addValueEventListener(ve);


                viewHolder.title.setText(model.getTitle());

                viewHolder.description.setText(model.getDescription());
                viewHolder.mView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String key = adapter.getRef(position).getKey();
                        Intent edit = new Intent(getActivity(), EditNoteActivity.class);
                        edit.putExtra("Functionality", "edit");
                        edit.putExtra("NotesKey", key);
                        Log.e("key is ", key);
                        //startActivity(edit);
                        startActivity(edit,
                                ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
                    }
                });
                viewHolder.label = model.getLabel();
                viewHolder.color = model.getColor();
                viewHolder.labelView.setText(model.getLabel());
            }


        };

        mRecyclerView.setAdapter(adapter);

        FloatingActionButton addnote_fab = (FloatingActionButton) view.findViewById(R.id.fab_addnote);
        addnote_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editIntent = new Intent(getActivity(), EditNoteActivity.class);
                editIntent.putExtra("Functionality", "add");

                startActivity(editIntent);
            }
        });
        return view;
    }


    public static class NotesViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView description;
        public String label, color;
        public View mView;
        public TextView labelView;


        public NotesViewHolder(View itemView) {

            super(itemView);
            mView = itemView;
            labelView = (TextView) itemView.findViewById(R.id.note_label);
            title = (TextView) itemView.findViewById(R.id.note_title);
            description = (TextView) itemView.findViewById(R.id.note_description);
        }

    }
}
