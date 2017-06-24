package com.example.sthakrey.donote;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sthakrey.donote.data.Notes;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.sthakrey.donote.SettingsFragment.colorList;
import static com.example.sthakrey.donote.SettingsFragment.labeltextView;
import static com.example.sthakrey.donote.SettingsFragment.selectedItem;

public class EditNoteActivity extends AppCompatActivity {

    EditText title;
    EditText description;
    public static String ACTION_DATA_UPDATE = "com.example.sthakrey.donote.widget.ACTION_DATA_UPDATE";
    FrameLayout note_header;
    private final static int REQUEST_IMAGE_CAPTURE = 1;
    private String notesKey;
    private String label ;
    private String functionality;
    ImageView myphoto = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        Toolbar toolbar = (Toolbar) findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        title = (EditText) findViewById(R.id.input_title);
        description = (EditText)findViewById(R.id.input_description);

        myphoto = (ImageView) findViewById(R.id.myphoto);


        notesKey = getIntent().getStringExtra("NotesKey");
        functionality = getIntent().getStringExtra("Functionality");
        note_header = (FrameLayout) findViewById(R.id.note_header);

        if(savedInstanceState!=null) {
            note_header.setBackgroundColor(savedInstanceState.getInt("Color"));
        }if(functionality.equals("edit")) {
            DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference("/user/notes/" + notesKey);
            ValueEventListener postListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // Get Post object and use the values to update the UI
                    System.out.print("sdsd");
                    Notes notes = dataSnapshot.getValue(Notes.class);
                    title.setText(notes.getTitle());
                    label = notes.getLabel();
                    description.setText(notes.getDescription());
                    int color = (int) Long.parseLong(notes.getColor(), 16);
                    int r = (color >> 16) & 0xFF;
                    int g = (color >> 8) & 0xFF;
                    int b = (color >> 0) & 0xFF;

                    note_header.setBackgroundColor(Color.rgb(r, g, b));
                    if (colorList[0].equals(notes.getColor()))
                        selectedItem = 1;
                    else


                    {
                        if (colorList[1].equals(notes.getColor()))
                            selectedItem = 2;
                        else {
                            if (colorList[2].equals(notes.getColor()))
                                selectedItem = 3;
                            else {
                                if (colorList[3].equals(notes.getColor()))
                                    selectedItem = 4;
                                else {
                                    if (colorList[4].equals(notes.getColor()))
                                        selectedItem = 5;
                                    else
                                        selectedItem = -1;

                                }
                                }
                        }
                    }// ...
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Getting Post failed, log a message
                    // ...
                }
            };
            firebaseDatabase.addValueEventListener(postListener);
        }
        FloatingActionButton fab= (FloatingActionButton) findViewById(R.id.fab_settings);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment x = getSupportFragmentManager().findFragmentByTag("tag");
                if(x==null) {

                    FragmentTransaction ftr;
                    Fragment fra=null;
                    ftr = getSupportFragmentManager().beginTransaction();

                    ftr.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_left, R.anim.exit_to_right);
                    fra = new SettingsFragment();
                    Bundle message = new Bundle();
                    message.putString("Type", "notes");
                    message.putString("Key", notesKey);
                    message.putString("Functionality", getIntent().getStringExtra("Functionality"));
                    fra.setArguments(message);
                    ftr.addToBackStack("lah");
                    ftr.replace(R.id.settings_container, fra, "tag");

                    ftr.commit();

                }
                else {
                getSupportFragmentManager().popBackStack();

                }

            }
        });

        fab = (FloatingActionButton)findViewById(R.id.fab_save);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference fdbref = firebaseDatabase.getReference("/user/notes");
                if(functionality.equals("add")) {
                    if(labeltextView==null) {
                        labeltextView = new TextView(getBaseContext());
                        labeltextView.setText("NoLabel");
                    }
                    String color ;
                    if(selectedItem == -1)
                        color ="ffffff";
                    else
                        color =colorList[selectedItem-1];
                    fdbref.push().setValue(new Notes(title.getText().toString(), description.getText().toString(),
                           labeltextView.getText().toString() ,color));
                }
                else
                {
                    if(functionality.equals("edit"))
                    {
                        String color ;
                        if(selectedItem!=-1)
                        color = colorList[selectedItem-1];
                        else
                        color = "ffffff";

                        Notes editNote = new Notes(title.getText().toString(), description.getText().toString(),
                                label,color);
                        fdbref.child(notesKey).setValue(editNote);

                    }
                }

                Intent myaction = new Intent();
                myaction.setAction(ACTION_DATA_UPDATE);
                sendBroadcast(myaction);
                finish();


            }
        });
    }



        @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE) {

            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            myphoto.setImageBitmap(imageBitmap);
            myphoto.setVisibility(View.VISIBLE);
    }
    }

    @Override
    protected void onPause() {
        super.onPause();
        onSaveInstanceState(new Bundle());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        int backColor = Color.TRANSPARENT;
        Drawable background = note_header.getBackground();
        if (background instanceof ColorDrawable)
            backColor = ((ColorDrawable) background).getColor();

        outState.putInt("Color", backColor);
        super.onSaveInstanceState(outState);
    }


}
