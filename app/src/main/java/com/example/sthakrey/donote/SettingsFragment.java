package com.example.sthakrey.donote;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sthakrey.donote.data.Notes;
import com.example.sthakrey.donote.data.Todo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class SettingsFragment extends Fragment {


    public static String[] colorList = new String[] {"80deea", "a5d6a7", "fff59d", "ef9a9a", "ce93d8"};
    public static TextView labeltextView ;
    private final static int REQUEST_IMAGE_CAPTURE = 1;
    static int selectedItem = -1;
    FrameLayout noteHeader;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_settings, container, false);
         labeltextView = (TextView) view.findViewById(R.id.label_selected);



        if(getArguments().getString("Type").equals("notes"))
        noteHeader = (FrameLayout) getActivity().findViewById(R.id.note_header);
        else
            noteHeader = (FrameLayout) getActivity().findViewById(R.id.todo_header);

        int backColor = Color.TRANSPARENT;
        Drawable background = noteHeader.getBackground();
        if (background instanceof ColorDrawable)
            backColor = ((ColorDrawable) background).getColor();


        ImageButton ib1 = (ImageButton) view.findViewById(R.id.imageButton_1);
        int color = (int)Long.parseLong(colorList[0], 16);
        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = (color >> 0) & 0xFF;
        ib1.getBackground().setColorFilter(Color.rgb(r, g, b), PorterDuff.Mode.SRC_ATOP);
        if(backColor == Color.rgb(r,g,b))
            ib1.setImageResource(R.drawable.ic_action_accept);

        ImageButton ib2 = (ImageButton) view.findViewById(R.id.imageButton_2);

        color = (int)Long.parseLong(colorList[1], 16);
        r = (color >> 16) & 0xFF;
        g = (color >> 8) & 0xFF;
        b = (color >> 0) & 0xFF;
        ib2.getBackground().setColorFilter(Color.rgb(r, g, b), PorterDuff.Mode.SRC_ATOP);
        if(backColor == Color.rgb(r,g,b))
            ib2.setImageResource(R.drawable.ic_action_accept);


        ImageButton ib3 = (ImageButton) view.findViewById(R.id.imageButton_3);
        color = (int)Long.parseLong(colorList[2], 16);
        r = (color >> 16) & 0xFF;
        g = (color >> 8) & 0xFF;
        b = (color >> 0) & 0xFF;
        ib3.getBackground().setColorFilter(Color.rgb(r, g, b), PorterDuff.Mode.SRC_ATOP);
        if(backColor == Color.rgb(r,g,b))
            ib3.setImageResource(R.drawable.ic_action_accept);

        ImageButton ib4 = (ImageButton) view.findViewById(R.id.imageButton_4);
        color = (int)Long.parseLong(colorList[3], 16);
        r = (color >> 16) & 0xFF;
        g = (color >> 8) & 0xFF;
        b = (color >> 0) & 0xFF;
        ib4.getBackground().setColorFilter(Color.rgb(r, g, b), PorterDuff.Mode.SRC_ATOP);
        if(backColor == Color.rgb(r,g,b))
            ib4.setImageResource(R.drawable.ic_action_accept);

        ImageButton ib5 = (ImageButton) view.findViewById(R.id.imageButton_5);
        color = (int)Long.parseLong(colorList[4], 16);
        r = (color >> 16) & 0xFF;
        g = (color >> 8) & 0xFF;
        b = (color >> 0) & 0xFF;
        ib5.getBackground().setColorFilter(Color.rgb(r, g, b), PorterDuff.Mode.SRC_ATOP);
        if(backColor == Color.rgb(r,g,b))
            ib5.setImageResource(R.drawable.ic_action_accept);

     final ImageButton[] buttonarray = new ImageButton[]{null, ib1, ib2, ib3, ib4, ib5};


        ib1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedItem!=-1) {
                    if(selectedItem==1)
                    {
                        int color = (int)Long.parseLong("ffffff", 16);
                        int r = (color >> 16) & 0xFF;
                        int g = (color >> 8) & 0xFF;
                        int b = (color >> 0) & 0xFF;
                        noteHeader.setBackgroundColor(Color.rgb(r,g,b));
                        ((ImageButton)v).setImageResource(android.R.color.transparent);
                        selectedItem = -1;

                        return ;
                    }

                    buttonarray[selectedItem].setImageResource(android.R.color.transparent);
                }int color = (int)Long.parseLong(colorList[0], 16);
                int r = (color >> 16) & 0xFF;
                int g = (color >> 8) & 0xFF;
                int b = (color >> 0) & 0xFF;
                noteHeader.setBackgroundColor(Color.rgb(r,g,b));
                ((ImageButton)v).setImageResource(R.drawable.ic_action_accept);

                if(getArguments().getString("Type").equals("notes") && getArguments().getString("Functionality").equals("edit"))
                {
                    DatabaseReference dbr = FirebaseDatabase.getInstance().getReference("/user/notes/"+getArguments().getString("Key")+"/color");
                    dbr.setValue(colorList[0]);
                }
            selectedItem =1 ;


            }
        });
        ib2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedItem!=-1) {
                    if (selectedItem == 2) {
                        int color = (int) Long.parseLong("ffffff", 16);
                        int r = (color >> 16) & 0xFF;
                        int g = (color >> 8) & 0xFF;
                        int b = (color >> 0) & 0xFF;
                        noteHeader.setBackgroundColor(Color.rgb(r, g, b));
                        ((ImageButton) v).setImageResource(android.R.color.transparent);
                        selectedItem = -1;
                        return;
                    }


                    buttonarray[selectedItem].setImageResource(android.R.color.transparent);
                }int color = (int)Long.parseLong(colorList[1], 16);
                int r = (color >> 16) & 0xFF;
                int g = (color >> 8) & 0xFF;
                int b = (color >> 0) & 0xFF;
                noteHeader.setBackgroundColor(Color.rgb(r,g,b));
                ((ImageButton)v).setImageResource(R.drawable.ic_action_accept);
                if(getArguments().getString("Type").equals("notes") && getArguments().getString("Functionality").equals("edit"))
                {
                    DatabaseReference dbr = FirebaseDatabase.getInstance().getReference("/user/notes/"+getArguments().getString("Key")+"/color");
                    dbr.setValue(colorList[1]);
                }
        selectedItem = 2;

            }
        });
        ib3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedItem!=-1) {
                    if(selectedItem==3)
                    {
                        int color = (int)Long.parseLong("ffffff", 16);
                        int r = (color >> 16) & 0xFF;
                        int g = (color >> 8) & 0xFF;
                        int b = (color >> 0) & 0xFF;
                        noteHeader.setBackgroundColor(Color.rgb(r,g,b));
                        ((ImageButton)v).setImageResource(android.R.color.transparent);
                        selectedItem = -1;
                        return ;
                    }

                    buttonarray[selectedItem].setImageResource(android.R.color.transparent);
                }int color = (int)Long.parseLong(colorList[2], 16);
                int r = (color >> 16) & 0xFF;
                int g = (color >> 8) & 0xFF;
                int b = (color >> 0) & 0xFF;
                noteHeader.setBackgroundColor(Color.rgb(r,g,b));
                ((ImageButton)v).setImageResource(R.drawable.ic_action_accept);
                if(getArguments().getString("Type").equals("notes") && getArguments().getString("Functionality").equals("edit"))
                {
                    DatabaseReference dbr = FirebaseDatabase.getInstance().getReference("/user/notes/"+getArguments().getString("Key")+"/color");
                    dbr.setValue(colorList[2]);
                }

                selectedItem = 3;

            }
        });
        ib4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedItem!=-1) {
                    if(selectedItem==4)
                    {
                        int color = (int)Long.parseLong("ffffff", 16);
                        int r = (color >> 16) & 0xFF;
                        int g = (color >> 8) & 0xFF;
                        int b = (color >> 0) & 0xFF;
                        noteHeader.setBackgroundColor(Color.rgb(r,g,b));
                        ((ImageButton)v).setImageResource(android.R.color.transparent);
                        selectedItem = -1;
                        return ;
                    }

                    buttonarray[selectedItem].setImageResource(android.R.color.transparent);
                }int color = (int)Long.parseLong(colorList[3], 16);
                int r = (color >> 16) & 0xFF;
                int g = (color >> 8) & 0xFF;
                int b = (color >> 0) & 0xFF;
                noteHeader.setBackgroundColor(Color.rgb(r,g,b));
                ((ImageButton)v).setImageResource(R.drawable.ic_action_accept);
                if(getArguments().getString("Type").equals("notes") && getArguments().getString("Functionality").equals("edit"))
                {
                    DatabaseReference dbr = FirebaseDatabase.getInstance().getReference("/user/notes/"+getArguments().getString("Key")+"/color");
                    dbr.setValue(colorList[3]);
                }
        selectedItem =4 ;

            }
        });
        ib5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedItem!=-1) {
                    if(selectedItem==5)
                    {
                        int color = (int)Long.parseLong("ffffff", 16);
                        int r = (color >> 16) & 0xFF;
                        int g = (color >> 8) & 0xFF;
                        int b = (color >> 0) & 0xFF;
                        noteHeader.setBackgroundColor(Color.rgb(r,g,b));
                        ((ImageButton)v).setImageResource(android.R.color.transparent);
                        selectedItem = -1;
                        return ;
                    }

                    buttonarray[selectedItem].setImageResource(android.R.color.transparent);
                }int color = (int)Long.parseLong(colorList[4], 16);
                int r = (color >> 16) & 0xFF;
                int g = (color >> 8) & 0xFF;
                int b = (color >> 0) & 0xFF;
                noteHeader.setBackgroundColor(Color.rgb(r,g,b));
                ((ImageButton)v).setImageResource(R.drawable.ic_action_accept);
                if(getArguments().getString("Type").equals("notes") && getArguments().getString("Functionality").equals("edit"))
                {
                    DatabaseReference dbr = FirebaseDatabase.getInstance().getReference("/user/notes/"+getArguments().getString("Key")+"/color");
                    dbr.setValue(colorList[4]);
                }
            selectedItem = 5;

            }
        });



        final TextView labelSelected = (TextView)view.findViewById(R.id.label_selected);

        final ImageButton editLabel = (ImageButton) view.findViewById(R.id.edit_label_button);
        editLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editLabelMessage = new Intent(getActivity(), EditLabelActivity.class);
                editLabelMessage.putExtra("Label", labelSelected.getText());
                editLabelMessage.putExtra("Type", getArguments().getString("Type"));
                editLabelMessage.putExtra("Key", getArguments().getString("Key"));
                editLabelMessage.putExtra("Functionality", getArguments().getString("Functionality"));
                startActivity(editLabelMessage);
            }
        });

        DatabaseReference fdb ;
        if(getArguments().getString("Type").equals("notes")) {

            fdb = FirebaseDatabase.getInstance().getReference("/user/notes/" + getArguments().getString("Key"));
            ValueEventListener ve = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Notes note = dataSnapshot.getValue(Notes.class);
                    if(note!=null)
                    labeltextView.setText(note.getLabel());
                    else
                    labeltextView.setText("NoLabel");
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }

            };
            fdb.addValueEventListener(ve);
        }else {
            fdb = FirebaseDatabase.getInstance().getReference("/user/todos/" + getArguments().getString("Key"));
            ValueEventListener ve = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Todo note = dataSnapshot.getValue(Todo.class);
                    labeltextView.setText(note.getLabel());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }

            };
            fdb.addValueEventListener(ve);
        }


    return view;
    }


    @Override
    public void onPause() {


        super.onPause();
    }
}
