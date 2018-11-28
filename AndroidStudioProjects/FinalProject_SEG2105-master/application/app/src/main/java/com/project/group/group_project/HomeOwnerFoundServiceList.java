package com.project.group.group_project;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class HomeOwnerFoundServiceList extends Activity {

    private static final ServiceDatabase serviceDatabase = ServiceDatabase.getInstance();

    private List<Service> foundServices;

    private String userId;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_owner_found_service_list);

        Intent intent = getIntent();
        userId = intent.getStringExtra(Util.USER_ID);
        String searchName = intent.getStringExtra(Util.SEARCH_NAME);
        int searchStartHour = intent.getIntExtra(Util.SEARCH_START_HOUR, -1);
        int searchEndHour = intent.getIntExtra(Util.SEARCH_END_HOUR, -1);
        int searchRating = intent.getIntExtra(Util.SEARCH_RATING, -1);

        foundServices = serviceDatabase.queryDatabaseForHomeOwner(searchName, searchStartHour, searchEndHour, searchRating);

        recyclerView = findViewById(R.id.servicesRecyclerView);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new HomeOwnerFoundServiceList.CustomAdapter();
        adapter.notifyDataSetChanged();

        recyclerView.setAdapter(adapter);
    }

    private class CustomAdapter extends RecyclerView.Adapter<HomeOwnerFoundServiceHolder> {

        @Override
        public HomeOwnerFoundServiceHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.home_owner_service_row, viewGroup, false);

            return new HomeOwnerFoundServiceHolder(v);
        }

        @Override
        public void onBindViewHolder(HomeOwnerFoundServiceHolder viewHolder, final int position) {

            final Service service = foundServices.get(position);

            viewHolder.getTextView().setText(Util.buildServiceView(service));

            viewHolder.getBookButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bookOnClick(service);
                }
            });
        }

        @Override
        public int getItemCount() {
            return foundServices.size();
        }
    }

    public void returnToSearchServicesOnClick(View view) {

        Intent intent = new Intent(this, HomeOwnerSearchServices.class);
        intent.putExtra(Util.USER_ID, userId);

        startActivity(intent);
    }

    public void bookOnClick(Service service){

        Intent intent = new Intent(this, CreateBooking.class);
        intent.putExtra(Util.SERVICE_ID, service.getId());
        intent.putExtra(Util.USER_ID, userId);

        startActivity(intent);
    }

    private static class HomeOwnerFoundServiceHolder extends RecyclerView.ViewHolder {

        private final TextView textView;
        private Button bookButton;

        public HomeOwnerFoundServiceHolder(View v) {
            super(v);

            textView = v.findViewById(R.id.textView);
            bookButton = v.findViewById(R.id.bookButton);
        }

        public TextView getTextView() {
            return textView;
        }

        public Button getBookButton() {
            return bookButton;
        }
    }
}