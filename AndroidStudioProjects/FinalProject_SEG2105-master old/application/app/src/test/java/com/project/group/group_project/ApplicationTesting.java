package com.project.group.group_project;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ApplicationTesting {

    @Test
    public void verify_AdminNotNull(){
        String id = "admin";
        String username = "admin";
        String password = "admin";
        UserRole role = UserRole.getRoleByName("ADMIN");
        Admin ad = new Admin(id, username, password, role);

        assertNotNull(ad);
    }

    @Test
    public void verify_ServiceIsNotNull(){
        String name = "Get Cleaning";
        String description = "Clean your whole house for a small price.";
        int ratePerHour = 2;
        String id = "1K2NS390820102";
        Service service = new Service(id,name,description,ratePerHour);

        assertNotNull(service);
    }

    @Test
    public void verify_ServiceIsValid(){
        String name = "Get Cleaning";
        String description = "Clean your whole house for a small price.";
        int ratePerHour = 2;
        String id = "1K2NS390820102";
        Service service = new Service(id,name,description,ratePerHour);

        boolean result = service.isValidService(service);

        assertTrue(result);
    }

    @Test
    public void verify_ServiceIsNotValid(){
        String name = null;
        String description = "Clean your whole house for a small price.";
        int ratePerHour = 2;
        String id = "1K2NS390820102";
        Service service = new Service(id,name,description,ratePerHour);

        boolean result = service.isValidService(service);

        assertNotEquals(false, result);
    }

    @Test
    public void verify_OnlyOneAdmin(){
        String id = "seg2105";
        String username = "admin";
        String password = "admin";
        UserRole role = UserRole.getRoleByName("ADMIN");
        Admin admin = new Admin(id, username, password, role);
        boolean result = admin.canBeAdmin(username, password, role);

        assertEquals(false, result);
    }

}
