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

import java.util.List;

public class HomeOwnerBookings extends Activity {
    private static final BookingDatabase bookingDatabase = BookingDatabase.getInstance();

    private String userId;

    private List<Booking> homeOwnerBookings;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_owner_bookings);

        Intent intent = getIntent();
        userId = intent.getStringExtra(Util.USER_ID);
        homeOwnerBookings = bookingDatabase.getBookingByHomeOwner(userId);

        recyclerView = findViewById(R.id.bookingsRecyclerView);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new HomeOwnerBookings.CustomAdapter();
        adapter.notifyDataSetChanged();

        recyclerView.setAdapter(adapter);
    }

    private String buildServiceView(int index) {

        StringBuilder sb = new StringBuilder();

        String n = "\r\n";

        Booking booking = homeOwnerBookings.get(index);

        sb.append("Start: " + booking.getStartHour() + ":" + booking.getStartMinute() + n);
        sb.append("End: " + booking.getEndHour() + ":" + booking.getEndMinute() + n);
        sb.append("Date: " + booking.getMonth() + ". " + booking.getDay() + ", " + booking.getYear() + n);

        return sb.toString();
    }

    private class CustomAdapter extends RecyclerView.Adapter<HomeOwnerBookingViewHolder> {

        @Override
        public HomeOwnerBookingViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.text_row_item, viewGroup, false);

            return new HomeOwnerBookingViewHolder(v);
        }

        @Override
        public void onBindViewHolder(HomeOwnerBookingViewHolder viewHolder, final int position) {

            final Booking booking = homeOwnerBookings.get(position);

            viewHolder.getTextView().setText(buildServiceView(position));

        }

        @Override
        public int getItemCount() {
            return homeOwnerBookings.size();
        }
    }

    private static class HomeOwnerBookingViewHolder extends RecyclerView.ViewHolder {

        private final TextView textView;

        public HomeOwnerBookingViewHolder(View v) {
            super(v);

            textView = (TextView) v.findViewById(R.id.textView);
        }

        public TextView getTextView() {
            return textView;
        }
    }

    public void onClickDone(View view) {

        Intent intent = new Intent(this, HomeOwnerView.class);
        intent.putExtra(Util.USER_ID, userId);
        startActivity(intent);
    }

}
