package com.example.sthakrey.donote;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sthakrey.donote.data.Notes;
import com.example.sthakrey.donote.data.Task;
import com.example.sthakrey.donote.data.Todo;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.example.sthakrey.donote.SettingsFragment.colorList;
import static com.example.sthakrey.donote.SettingsFragment.selectedItem;

public class EditTodoActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    FirebaseRecyclerAdapter<Task, TaskHolder> adapter =null;
    static int maxElement = 0;
    static int selectedItem = -1;
    FrameLayout todoHeader;
    String label ;
    TextView  title;
    String functionality;
    Map<String,Task> mymap ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_todo);

        mRecyclerView = (RecyclerView) findViewById(R.id.task_recylerview);
        mymap = new HashMap<String, Task>();
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        title = (TextView) findViewById(R.id.input_title);
//        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        todoHeader = (FrameLayout) findViewById(R.id.todo_header);
        final String todokey = getIntent().getStringExtra("TodoKey");
        functionality = getIntent().getStringExtra("Functionality");

        if(functionality.equals("edit")) {

            DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference("/user/todos/" + todokey);
            final ValueEventListener postListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // Get Post object and use the values to update the UI
                    System.out.print("sdsd");
                    Todo notes = dataSnapshot.getValue(Todo.class);

                    label = notes.getLabel();
                    title.setText(notes.getTitle());
                    int color = (int) Long.parseLong(notes.getColor(), 16);
                    int r = (color >> 16) & 0xFF;
                    int g = (color >> 8) & 0xFF;
                    int b = (color >> 0) & 0xFF;

                    todoHeader.setBackgroundColor(Color.rgb(r, g, b));
                    if (colorList[0].equals(notes.getColor()))
                        selectedItem = 1;
                    else {
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

            firebaseDatabase = FirebaseDatabase.getInstance().getReference("/user/tasks/" + todokey + "/");

            functionality = getIntent().getStringExtra("Functionality");

            adapter = new FirebaseRecyclerAdapter<Task, TaskHolder>(Task.class, R.layout.task_list_item, TaskHolder.class, firebaseDatabase) {


                @Override
                protected void populateViewHolder(final TaskHolder viewHolder, Task model, final int position) {
                    viewHolder.task.setText(model.getTask());

                    viewHolder.isChecked = model.getIsChecked();
                    if (viewHolder.isChecked)
                        viewHolder.checkbox.setChecked(true);

                    viewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            adapter.getRef(viewHolder.getAdapterPosition()).removeValue();

                        }


                    });
                    viewHolder.task.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                            Log.v("SD", "");
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                }


            };

            mRecyclerView.setAdapter(adapter);

        }

        FloatingActionButton fab= (FloatingActionButton) findViewById(R.id.fab_settings);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(findViewById(R.id.label_selected)==null) {

                    FragmentTransaction ftr;
                    Fragment fra=null;
                    ftr = getSupportFragmentManager().beginTransaction();

                    ftr.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_in_up, R.anim.slide_in_up, R.anim.slide_in_up);
                    fra = new SettingsFragment();
                    Bundle message = new Bundle();
                    message.putString("Type", "todos");
                    message.putString("Key", todokey);
                    fra.setArguments(message);
                    ftr.replace(R.id.settings_container, fra, "tag");
                    ftr.addToBackStack("nam");
                    ftr.commit();

                }
                else {
        getSupportFragmentManager().popBackStack("nam", FragmentManager.POP_BACK_STACK_INCLUSIVE);

                }

            }
        });

        ImageButton newTaskButton = (ImageButton)findViewById(R.id.add_newtask_button);
        newTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase fdb = FirebaseDatabase.getInstance();
                fdb.getReference("user/tasks/"+todokey).push().setValue(new Task("", false));


            }
        });

        FloatingActionButton fabSave = (FloatingActionButton)findViewById(R.id.fab_save);
        fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference fdbref = firebaseDatabase.getReference("/user/todos");
                if(functionality.equals("add")) {
                    fdbref.push().setValue(new Todo(title.getText().toString(),
                            label,colorList[selectedItem-1],mymap));
                    finish();
                }
                else
                {
                    if(functionality.equals("edit"))
                    {

                        Todo editTodo = new Todo(title.getText().toString(),
                                label,colorList[selectedItem-1], mymap);
                        fdbref.child(todokey).setValue(editTodo);
                        for(int i=0;i<adapter.getItemCount();i++)
                        {
                            DatabaseReference db = FirebaseDatabase.getInstance().getReference("/user/tasks/"+todokey+"/"+adapter.getRef(i).getKey());
                            TaskHolder t = (TaskHolder) mRecyclerView.findViewHolderForAdapterPosition(i);

                            db.child("task").setValue(t.task.getText().toString());
                            db.child("isChecked").setValue(t.checkbox.isChecked());
                        }
                        finish();

                    }
                }

            }



        });





    }

    public static class TaskHolder extends RecyclerView.ViewHolder {
        public EditText task;
        public CheckBox checkbox;
        public boolean isChecked ;
        public ImageButton deleteButton;


        public TaskHolder(View itemView) {

            super(itemView);
            checkbox = (CheckBox) itemView.findViewById(R.id.mycheckbox);
            task = (EditText) itemView.findViewById(R.id.task_text);

            deleteButton = (ImageButton) itemView.findViewById(R.id.delete_task_button);

        }

    }
}
