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
import java.util.List;

public class HomeOwnerFoundServiceList extends Activity {
    //TODO this will display the services found in HomeOwnerSearchServices

    private static final ServiceDatabase serviceDatabase = ServiceDatabase.getInstance();
    private static final BookingDatabase bookingDatabase = BookingDatabase.getInstance();
    private static final RatingDatabase ratingDatabase = RatingDatabase.getInstance();

    private List<Service> allServices = serviceDatabase.getAllServices();
    private List<Service> searchedServices;

    private String userId;
    private String searchType;
    private String serviceName;
    private String serviceId;
    private String serviceStartHour;
    private String serviceEndHour;
    private String serviceRating;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_owner_found_service_list);

        Intent intent = getIntent();
        userId = intent.getStringExtra(Util.USER_ID);
        searchType = intent.getStringExtra("searchType");

        if (searchType.equalsIgnoreCase("allServices")){
            searchedServices = allServices;
        }

        if (searchType.equalsIgnoreCase("serviceNameAndTime")){
            serviceName = intent.getStringExtra("serviceName");
            serviceStartHour = intent.getStringExtra("serviceStart");
            serviceEndHour = intent.getStringExtra("serviceEnd");
            //TODO unsure how to find services in a certain time range
            //will have to look at all the Availabilities in the database, and once and
            //service is booked, the availability must not include the booked time
        }

        if (searchType.equalsIgnoreCase("serviceNameAndRating")){
            serviceName = intent.getStringExtra("serviceName");
            serviceRating = intent.getStringExtra("serviceRate");
            searchedServices = getServicesByRating(serviceName, serviceRating);
        }

        recyclerView = findViewById(R.id.servicesRecyclerView);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new HomeOwnerFoundServiceList.CustomAdapter();
        adapter.notifyDataSetChanged();

        recyclerView.setAdapter(adapter);
    }

    private String buildServiceView(int index) {

        StringBuilder sb = new StringBuilder();

        String n = "\r\n";

        Service service = searchedServices.get(index);

        String rate = "$" + (service.getRatePerHour() / 100);

        sb.append("Name: " + service.getName() + n);
        sb.append("Description: " + service.getDescription() + n);
        sb.append("Rate per hour: " + rate + n);

        return sb.toString();
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

            final Service service = searchedServices.get(position);

            viewHolder.getTextView().setText(buildServiceView(position));

            viewHolder.getBookButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bookOnClick(service);
                }
            });
        }

        @Override
        public int getItemCount() {
            return searchedServices.size();
        }
    }

    public void bookOnClick(Service service){
        Intent intent = new Intent(this, CreateBooking.class);
        intent.putExtra(Util.SERVICE_NAME, serviceName);
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

    private List<Service> getServicesByNames(String serviceName) {
        serviceName = serviceName.toLowerCase();

        List<Service> serviceListToReturn = new ArrayList<>();

        List<Service> allServices = serviceDatabase.getAllServices();

        if (allServices != null && !allServices.isEmpty()) {

            for (Service service: allServices) {
                if (service.getName() == serviceName || service.getName().equalsIgnoreCase(serviceName) || service.getName().contains(serviceName)){
                    serviceListToReturn.add(service);
                }
            }
        } else {
            serviceListToReturn = null;
        }

        return serviceListToReturn;
    }

    private List<Service> getServicesByRating(String serviceName, String serviceRating){
        List<Service> services = getServicesByNames(serviceName);
        List<Service> searchedServices = new ArrayList<>();

        for (Service service: services){
            List<Rating> ratings = ratingDatabase.getRatingByService(service.getId());
            int totalRate = 0;
            double avgRate = 0.0;
            for (Rating rating: ratings){
                totalRate = totalRate + rating.getRate();
            }
            avgRate = totalRate / ratings.size();
            if (avgRate >= Integer.parseInt(serviceRating)){
                searchedServices.add(service);
            }
        }

        return  searchedServices;
    }

}