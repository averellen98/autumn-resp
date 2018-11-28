package com.project.group.group_project;

import java.util.ArrayList;
import java.util.List;

public class RatingTestDatabase {

    private static List<Rating> ratingList = new ArrayList<>();

    public RatingTestDatabase() {
        addRating("serviceTestID", "This is a test rating", 1);
        addRating("serviceTestID", "This is a test rating 2", 2);
        addRating("serviceTestID", "This is a test rating 3", 3);
    }

    public Rating addRating(String serviceId, String comment, int rate) {

        String id = "ratingTestID";

        Rating rating = new Rating(id, serviceId, comment, rate);

        ratingList.add(rating);

        return rating;
    }

    public List<Rating> getRatingsByService(String serviceId) {

        List<Rating> ratings = new ArrayList<>();

        for (Rating rating: ratingList) {

            if (rating.getServiceId().equals(serviceId)) {
                ratings.add(rating);
            }
        }

        return ratings;
    }

    public int getRatingForService(String serviceId) {

        List<Rating> ratings = getRatingsByService(serviceId);

        if (ratings.isEmpty()) {
            return 0;
        }

        int ratingInt = 0;

        for (Rating rating: ratings) {
            ratingInt += rating.getRate();
        }

        ratingInt = (Integer) (ratingInt / ratings.size());

        return ratingInt;
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
