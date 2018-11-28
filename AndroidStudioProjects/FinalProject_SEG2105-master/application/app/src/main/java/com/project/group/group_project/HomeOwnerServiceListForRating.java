package com.project.group.group_project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class HomeOwnerServiceListForRating extends Activity{

    private static final ServiceDatabase serviceDatabase = ServiceDatabase.getInstance();

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.h_o_view_services);
        Toast.makeText(this, "size: " + serviceDatabase.getAllServices().size(), Toast.LENGTH_SHORT).show();

        recyclerView = findViewById(R.id.servicesRecyclerView);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new HomeOwnerServiceListForRating.CustomAdapter();
        adapter.notifyDataSetChanged();

        recyclerView.setAdapter(adapter);
    }

    private class CustomAdapter extends RecyclerView.Adapter<HomeOwnerRateServiceViewHolder> {

        @Override
        public HomeOwnerRateServiceViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.home_owner_rate_service_row, viewGroup, false);

            return new HomeOwnerRateServiceViewHolder(v);
        }

        @Override
        public void onBindViewHolder(HomeOwnerRateServiceViewHolder viewHolder, final int position) {

            final Service service = serviceDatabase.getAllServices().get(position);

            viewHolder.getTextView().setText(Util.buildServiceView(service));

            viewHolder.getRateButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    doRate(service);
                }
            });
        }

        @Override
        public int getItemCount() {
            return serviceDatabase.getAllServices().size();
        }
    }

    private void doRate(Service service) {

        Intent intent = new Intent(this, CreateServiceRating.class);
        intent.putExtra(Util.SERVICE_ID, service.getId());

        startActivity(intent);
    }

    private static class HomeOwnerRateServiceViewHolder extends RecyclerView.ViewHolder {

        private final TextView textView;
        private Button rateButton;

        public HomeOwnerRateServiceViewHolder(View v) {
            super(v);

            textView = (TextView) v.findViewById(R.id.textView);
            rateButton = v.findViewById(R.id.rateButton);
        }

        public TextView getTextView() {
            return textView;
        }

        public Button getRateButton() {
            return rateButton;
        }

    }

}
