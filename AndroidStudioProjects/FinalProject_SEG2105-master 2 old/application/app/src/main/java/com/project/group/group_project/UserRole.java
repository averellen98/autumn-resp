package com.project.group.group_project;

import java.util.HashMap;
import java.util.Map;

public enum UserRole {
    ADMIN("Admin"),
    SERVICE_PROVIDER("Service Provider"),
    HOME_OWNER("Home Owner");

    private String name;
    private static final Map<String, UserRole> roleNameMap = new HashMap<String, UserRole>();

    static {
        for (UserRole userRole: UserRole.values()) {
            roleNameMap.put(userRole.getName(), userRole);
        }
    }

    UserRole(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static UserRole getRoleByName(String name) {
        return roleNameMap.get(name);
    }

    public static UserRole getRoleByNameForFirebase(String name) {

        if (name.equals("ADMIN")) {
            return UserRole.ADMIN;
        } else if (name.equals("HOME_OWNER")) {
            return UserRole.HOME_OWNER;
        } else if (name.equals("SERVICE_PROVIDER")) {
            return UserRole.SERVICE_PROVIDER;
        }

        return null;
    }
}
