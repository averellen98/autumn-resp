package com.project.group.group_project;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SPAvailableServiceActivity extends Activity {

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
        setContentView(R.layout.activity_spavailable_service);

        Intent intent = getIntent();
        serviceProviderId = intent.getStringExtra(SERVICE_PROVIDER_ID);
        theseServices  = serviceDatabase.getServiceForProvider(serviceProviderId);

        /**for (int i = 0; i < theseServices.size(); i++){
            Service service = theseServices.get(i);
            if (services.contains(service)){
                services.remove(service);
            }
        }*/

        recyclerView = findViewById(R.id.servicesRecyclerView);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new SPAvailableServiceActivity.CustomAdapter();
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

    private class CustomAdapter extends RecyclerView.Adapter<SPServiceViewHolder> {

        @Override
        public SPServiceViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.sp_service_row, viewGroup, false);

            return new SPServiceViewHolder(v);
        }

        @Override
        public void onBindViewHolder(SPServiceViewHolder viewHolder, final int position) {

            final Service service = serviceDatabase.getServices().get(position);

            if (services.contains(service)){
                viewHolder.getTextView().setText(buildServiceView(position));
            }

            viewHolder.getAddButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //currentUser.addServicesSP(service);
                    serviceDatabase.addServiceToServiceProvider(serviceProviderId, service.getId());
                    services.remove(service);
                    Intent intent = new Intent(getApplicationContext(), SPAvailableServiceActivity.class);
                    startActivity(intent);

                }
            });
        }

        @Override
        public int getItemCount() {
            return serviceDatabase.getServices().size();
        }
    }

    private static class SPServiceViewHolder extends RecyclerView.ViewHolder {

        private final TextView textView;
        private Button addButton;

        public SPServiceViewHolder(View v) {
            super(v);

            textView = (TextView) v.findViewById(R.id.textView);
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

        Intent intent = new Intent(this, SPViewServicesActivity.class);
        intent.putExtra(ServiceProviderView.SERVICE_PROVIDER_ID, serviceProviderId);
        startActivity(intent);
    }

}
