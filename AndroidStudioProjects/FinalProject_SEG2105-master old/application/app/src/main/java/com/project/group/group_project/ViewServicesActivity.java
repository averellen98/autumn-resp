package com.project.group.group_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class ViewServicesActivity extends AppCompatActivity {

    private static final ServiceDatabase serviceDatabase = ServiceDatabase.getInstance();

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_services);

        recyclerView = findViewById(R.id.servicesRecyclerView);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new ViewServicesActivity.CustomAdapter();
        adapter.notifyDataSetChanged();

        recyclerView.setAdapter(adapter);
    }

    private String buildServiceView(int index) {

        StringBuilder sb = new StringBuilder();

        String n = "\r\n";

        Service service = serviceDatabase.getServices().get(index);

        String rate = "$" + (service.getRatePerHour() / 100);

        sb.append("Id: " + service.getId() + n);
        sb.append("Name: " + service.getName() + n);
        sb.append("Description: " + service.getDescription() + n);
        sb.append("Rate per hour: " + rate + n);

        return sb.toString();
    }

    private class CustomAdapter extends RecyclerView.Adapter<ServiceViewHolder> {

        @Override
        public ServiceViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.admin_service_row, viewGroup, false);

            return new ServiceViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ServiceViewHolder viewHolder, final int position) {

            final Service service = serviceDatabase.getServices().get(position);

            viewHolder.getTextView().setText(buildServiceView(position));

            viewHolder.getEditButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    doEdit(service);
                }
            });

            viewHolder.getDeleteButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    serviceDatabase.deleteService(service);
                    Intent intent = new Intent(getApplicationContext(), ViewServicesActivity.class);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return serviceDatabase.getServices().size();
        }
    }

    private void doEdit(Service service) {

        Intent intent = new Intent(this, CreateOrEditServiceActivity.class);
        intent.putExtra(CreateOrEditServiceActivity.SERVICE_NAME, service.getName());
        intent.putExtra(CreateOrEditServiceActivity.SERVICE_DESCRIPTION, service.getDescription());
        intent.putExtra(CreateOrEditServiceActivity.SERVICE_RATE, service.getRatePerHour());

        startActivity(intent);
    }

    private static class ServiceViewHolder extends RecyclerView.ViewHolder {

        private final TextView textView;
        private Button editButton;
        private Button deleteButton;

        public ServiceViewHolder(View v) {
            super(v);

            textView = (TextView) v.findViewById(R.id.textView);
            editButton = v.findViewById(R.id.editButton);
            deleteButton = v.findViewById(R.id.deleteButton);
        }

        public TextView getTextView() {
            return textView;
        }

        public Button getEditButton() {
            return editButton;
        }

        public Button getDeleteButton() {
            return deleteButton;
        }
    }

    public void onClickCreateService(View view) {

        Intent intent = new Intent(this, CreateOrEditServiceActivity.class);
        startActivity(intent);
    }
}
