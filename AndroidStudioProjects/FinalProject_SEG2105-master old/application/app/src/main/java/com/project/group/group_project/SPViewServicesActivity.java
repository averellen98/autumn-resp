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

public class SPViewServicesActivity extends Activity {

    private static final ServiceDatabase serviceDatabase = ServiceDatabase.getInstance();
    public static final String SERVICE_PROVIDER_ID = "service_provider_id";
    private static final UserDatabase userDatabase = UserDatabase.getInstance();

    private String serviceProviderId;
    private List<Service> services = serviceDatabase.getServices();
    private List<Service> theseServices;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spview_services);

        Intent intent = getIntent();
        serviceProviderId = intent.getStringExtra(SERVICE_PROVIDER_ID);
        theseServices  = serviceDatabase.getServiceForProvider(serviceProviderId);

        recyclerView = findViewById(R.id.servicesRecyclerView);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new SPViewServicesActivity.CustomAdapter();
        adapter.notifyDataSetChanged();

        recyclerView.setAdapter(adapter);
    }

    private String buildServiceView(int index) {

        StringBuilder sb = new StringBuilder();

        String n = "\r\n";

        Service service = serviceDatabase.getServices().get(index);

        String rate = "$" + (service.getRatePerHour() / 100);

        sb.append("Name: " + service.getName() + n);
        sb.append("Description: " + service.getDescription() + n);
        sb.append("Rate per hour: " + rate + n);

        return sb.toString();
    }

    private class CustomAdapter extends RecyclerView.Adapter<SPViewServiceViewHolder> {

        @Override
        public SPViewServiceViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.sp_service_delete_row, viewGroup, false);

            return new SPViewServiceViewHolder(v);
        }

        @Override
        public void onBindViewHolder(SPViewServiceViewHolder viewHolder, final int position) {

            final Service service = serviceDatabase.getServices().get(position);

            if (!theseServices.contains(service)){
                viewHolder.getTextView().setText(buildServiceView(position));
            }

            viewHolder.getDeleteButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    serviceDatabase.deleteServiceFromProvider(serviceProviderId,service.getId());
                    Intent intent = new Intent(getApplicationContext(), SPViewServicesActivity.class);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return serviceDatabase.getServices().size();
        }
    }

    private static class SPViewServiceViewHolder extends RecyclerView.ViewHolder {

        private final TextView textView;
        private Button deleteButton;

        public SPViewServiceViewHolder(View v) {
            super(v);

            textView = (TextView) v.findViewById(R.id.textView);
            deleteButton = v.findViewById(R.id.addOrDeleteButton);
        }

        public TextView getTextView() {
            return textView;
        }

        public Button getDeleteButton() {
            return deleteButton;
        }
    }

    public void onClickLogOut(View view) {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
