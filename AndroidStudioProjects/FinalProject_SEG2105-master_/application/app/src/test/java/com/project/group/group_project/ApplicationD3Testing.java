package com.project.group.group_project;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;

public class ApplicationD3Testing {

    @Test
    public void verify_SPNotNull(){
        String id = "sp";
        String username = "sp";
        String password = "sp";
        UserRole role = UserRole.getRoleByName("SERVICE_PROVIDER");
        ServiceProvider serviceProvider = new ServiceProvider(id, username, password, role);

        assertNotNull(serviceProvider);
    }

    @Test
    public void verify_availabilityNotNull(){
        String id = "sp";
        String username = "sp";
        String password = "sp";
        UserRole role = UserRole.getRoleByName("SERVICE_PROVIDER");
        ServiceProvider serviceProvider = new ServiceProvider(id, username, password, role);

        Availability monday = new Availability("monday", serviceProvider.getId(), Util.WeekDay.MONDAY, 2, 0, 7, 0);
        Availability wednesday = new Availability("wednesday", serviceProvider.getId(), Util.WeekDay.WEDNESDAY, 9, 0, 18, 0);
        Availability friday = new Availability("friday", serviceProvider.getId(), Util.WeekDay.FRIDAY, 11, 0, 2, 0);

        List<Availability> availabilities = new ArrayList<Availability>();
        availabilities.add(monday);
        availabilities.add(wednesday);
        availabilities.add(friday);
        serviceProvider.setAvailabilities(availabilities);

        assertNotNull(availabilities);
    }

}
