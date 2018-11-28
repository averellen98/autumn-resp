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

public class SPAvailableServiceActivity extends Activity {

    private static final ServiceDatabase serviceDatabase = ServiceDatabase.getInstance();

    private String serviceProviderId;
    private List<Service> allServices = serviceDatabase.getAllServices();
    private List<Service> serviceProviderAvailableServices;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spavailable_service);

        Intent intent = getIntent();
        serviceProviderId = intent.getStringExtra(Util.USER_ID);
        serviceProviderAvailableServices = getServicesServiceProviderDoesNotHave();

        recyclerView = findViewById(R.id.servicesRecyclerView);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new SPAvailableServiceActivity.CustomAdapter();
        adapter.notifyDataSetChanged();

        recyclerView.setAdapter(adapter);
    }

    private List<Service> getServicesServiceProviderDoesNotHave() {

        List<Service> serviceListToReturn = new ArrayList<>();

        List<Service> takenServices = serviceDatabase.getServiceForProvider(serviceProviderId);

        if (takenServices != null && !takenServices.isEmpty()) {

            for (Service service: allServices) {

                if (!takenServices.contains(service)) {
                    serviceListToReturn.add(service);
                }
            }
        } else {
            serviceListToReturn = allServices;
        }

        return serviceListToReturn;
    }

    private String buildServiceView(Service service) {

        StringBuilder sb = new StringBuilder();

        String n = "\r\n";

        String rate = "$" + (service.getRatePerHour() / 100);

        sb.append("Name: " + service.getName() + n);
        sb.append("Description: " + service.getDescription() + n);
        sb.append("Rate per hour: " + rate + n);

        return sb.toString();
    }

    private class CustomAdapter extends RecyclerView.Adapter<SPServiceViewHolder> {

        @Override
        public SPServiceViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.sp_service_add_row, viewGroup, false);

            return new SPServiceViewHolder(v);
        }

        @Override
        public void onBindViewHolder(SPServiceViewHolder viewHolder, final int position) {

            final Service service = serviceProviderAvailableServices.get(position);

            viewHolder.getTextView().setText(buildServiceView(service));

            viewHolder.getAddButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    serviceDatabase.addServiceToServiceProvider(serviceProviderId, service.getId());
                    serviceProviderAvailableServices.remove(service);

                    notifyDataSetChanged();
                }
            });
        }

        @Override
        public int getItemCount() {
            return serviceProviderAvailableServices.size();
        }
    }

    private static class SPServiceViewHolder extends RecyclerView.ViewHolder {

        private final TextView textView;
        private Button addButton;

        public SPServiceViewHolder(View v) {
            super(v);

            textView = v.findViewById(R.id.textView);
            addButton = v.findViewById(R.id.addOrDeleteButton);
        }

        public TextView getTextView() {
            return textView;
        }

        public Button getAddButton() {
            return addButton;
        }
    }

    public void onClickDone(View view) {

        Intent intent = new Intent(this, ServiceProviderView.class);
        intent.putExtra(Util.USER_ID, serviceProviderId);

        startActivity(intent);
    }
}