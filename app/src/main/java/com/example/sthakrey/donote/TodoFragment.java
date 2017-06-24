package com.example.sthakrey.donote;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.sthakrey.donote.data.Task;
import com.example.sthakrey.donote.data.Todo;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class TodoFragment extends Fragment {


    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    static FirebaseRecyclerAdapter<Todo, TodoViewHolder> adapter = null;
    private static Context s;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_todo, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewtodo);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);


        DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference("/user/todos");

        s = getContext();
        adapter = new FirebaseRecyclerAdapter<Todo, TodoViewHolder>(Todo.class, R.layout.todos_list_item, TodoViewHolder.class, firebaseDatabase) {


            @Override
            protected void populateViewHolder(final TodoViewHolder viewHolder, Todo model, final int position) {
                viewHolder.title.setText(model.getTitle());
                int color = (int) Long.parseLong(model.getColor(), 16);
                int r = (color >> 16) & 0xFF;
                int g = (color >> 8) & 0xFF;
                int b = (color >> 0) & 0xFF;

                viewHolder.mView.setBackgroundColor(Color.rgb(r, g, b));

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent editTodo = new Intent(getActivity(), EditTodoActivity.class);
                        String key = adapter.getRef(position).getKey();
                        editTodo.putExtra("TodoKey", key);
                        editTodo.putExtra("Label", "NoLabel");
                        editTodo.putExtra("Functionality", "edit");
                        startActivity(editTodo);
                    }
                });


                DatabaseReference nesteddbr = FirebaseDatabase.getInstance().getReference("/user/tasks/" + adapter.getRef(position).getKey());
                FirebaseRecyclerAdapter<Task, TaskHolder> nestedadp = new FirebaseRecyclerAdapter<Task, TaskHolder>(Task.class, R.layout.task_list_main_item, TaskHolder.class, nesteddbr) {


                    @Override
                    protected void populateViewHolder(TaskHolder viewHol, Task mod, int pos) {
                        viewHol.task.setText(mod.getTask());

                        viewHol.isChecked = mod.getIsChecked();
                        if(viewHol.isChecked)
                        viewHol.checkbox.setChecked(true);


                    }


                };

                LinearLayoutManager lm = new LinearLayoutManager(getContext());
                lm.setOrientation(LinearLayoutManager.VERTICAL);
                viewHolder.recyclerView.setLayoutManager(lm);
                viewHolder.recyclerView.setAdapter(nestedadp);
                viewHolder.label = model.getLabel();
                viewHolder.color = model.getColor();

            }


        };

        mRecyclerView.setAdapter(adapter);

        FloatingActionButton addnote_fab = (FloatingActionButton) view.findViewById(R.id.fab_addtodo);
        addnote_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editIntent = new Intent(getActivity(), EditTodoActivity.class);
                editIntent.putExtra("Functionality", "add");
                startActivity(editIntent);

            }
        });
        return view;
    }


    public static class TodoViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public String label, color;
        public View mView;
        public RecyclerView recyclerView;


        public TodoViewHolder(View itemView) {

            super(itemView);
            mView = itemView;
            title = (TextView) itemView.findViewById(R.id.todo_title);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.tasklist);


        }

    }

    public static class TaskHolder extends RecyclerView.ViewHolder {
        public TextView task;
        public CheckBox checkbox;
        public boolean isChecked;



        public TaskHolder(View itemView) {

            super(itemView);
            checkbox = (CheckBox) itemView.findViewById(R.id.mycheckbox);
            task = (TextView) itemView.findViewById(R.id.task_text);


        }


    }
}
