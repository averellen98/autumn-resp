package com.project.group.group_project;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RatingDatabase {

    private static final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static final DatabaseReference databaseRatings = database.getReference("ratings");

    private static final RatingDatabase instance = new RatingDatabase();

    private static List<Rating> ratingList = new ArrayList<>();

    static {

        databaseRatings.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ratingList.clear();

                for (DataSnapshot ds: dataSnapshot.getChildren()) {

                    String id = ds.child("id").getValue(String.class);
                    String serviceId = ds.child("serviceId").getValue(String.class);
                    String comment = ds.child("comment").getValue(String.class);
                    int rate = ds.child("rate").getValue(Integer.class);

                    Rating rating = new Rating(id, serviceId, comment, rate);

                    ratingList.add(rating);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private RatingDatabase() {}

    public static RatingDatabase getInstance() {return instance;}

    public Rating addRating(String serviceId, String comment, int rate) {

        String id = databaseRatings.push().getKey();

        Rating rating = new Rating(id, serviceId, comment, rate);

        databaseRatings.child(id).setValue(rating);

        return rating;
    }

    public List<Rating> getRatingByService(String serviceId) {

        List<Rating> ratings = new ArrayList<>();

        for (Rating rating: ratingList) {

            if (rating.getServiceId().equals(serviceId)) {
                ratings.add(rating);
            }
        }

        return ratings;
    }

    public Rating getRatingById(String id) {

        for (Rating rating: ratingList) {

            if (rating.getId().equals(id)) {
                return rating;
            }
        }

        return null;
    }
}