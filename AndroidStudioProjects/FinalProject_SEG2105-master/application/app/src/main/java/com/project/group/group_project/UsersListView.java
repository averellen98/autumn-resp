package com.project.group.group_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import android.os.Bundle;


public class UsersListView extends AppCompatActivity {

    private UserDatabase userDatabase = UserDatabase.getInstance();

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);

        recyclerView = findViewById(R.id.usersRecyclerView);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new CustomAdapter();

        recyclerView.setAdapter(adapter);
    }

    public void returnTOAdminViewOnClick(View view) {

        Intent intent = new Intent(this, AdminView.class);

        startActivity(intent);
    }

    private class CustomAdapter extends RecyclerView.Adapter<UserViewHolder> {

        @Override
        public UserViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.text_row_item, viewGroup, false);

            return new UserViewHolder(v);
        }

        @Override
        public void onBindViewHolder(UserViewHolder viewHolder, final int position) {

            User user = userDatabase.getUsers().get(position);

            viewHolder.getTextView().setText(Util.buildUserView(user));
        }

        @Override
        public int getItemCount() {
            return userDatabase.getUsers().size();
        }
    }

    private static class UserViewHolder extends RecyclerView.ViewHolder {

        private final TextView textView;

        public UserViewHolder(View v) {
            super(v);

            textView = (TextView) v.findViewById(R.id.textView);
        }

        public TextView getTextView() {
            return textView;
        }
    }
}