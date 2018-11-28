package com.project.group.group_project;

import java.util.Calendar;
import java.util.List;

public class Util {

    private Util() {}

    // Constants
    public static final String USER_ID = "com.project.group.util_user_id";
    public static final String SERVICE_NAME = "com.project.group.util_service_name";
    public static final String SERVICE_DESCRIPTION = "com.project.group.util_service_Description";
    public static final String SERVICE_RATE = "com.project.group.util_service_rate";
    public static final String SERVICE_ID = "com.project.group.util_service_id";
    public static final String USERNAME = "com.project.group.util_user_name";
    public static final String PASSWORD = "com.project.group.util_password";
    public static final String ROLE = "com.project.group.util_role";
    public static final String FIRST_NAME = "com.project.group.util_first_name";
    public static final String LAST_NAME = "com.project.group.util_last_name";
    public static final String SEARCH_NAME = "com.project.group.util_search_name";
    public static final String SEARCH_START_HOUR = "com.project.group.util_search_start_hour";
    public static final String SEARCH_END_HOUR = "com.project.group.util_search_end_hour";
    public static final String SEARCH_RATING = "com.project.group.util_search_rating";

    private static String n = "\r\n";

    public static String buildServiceView(Service service) {

        StringBuilder sb = new StringBuilder();

        String rate = "$" + (service.getRatePerHour() / 100);

        sb.append("Name: " + service.getName() + n);
        sb.append("Description: " + service.getDescription() + n);
        sb.append("Rate per hour: " + rate + n);
        sb.append("Rating: " + service.getRating());

        return sb.toString();
    }

    public static String buildUserView(User user) {

        StringBuilder sb = new StringBuilder();

        sb.append("Username: " + user.getUsername() + n);
        sb.append("Password: " + user.getPassword() + n);
        sb.append("Role: " + user.getRole().getName() + n);
        sb.append("First name: " + user.getFirstName() + n);
        sb.append("Last name: " + user.getLastName());

        return sb.toString();
    }

    public static String buildAvailabilityString(List<Availability> availabilityList) {

        StringBuilder sb = new StringBuilder();

        int counter = 0;

        for (Availability availability: availabilityList) {

            switch (availability.getWeekDay()) {
                case MONDAY:
                    sb.append("MON: " + availability.getStartHour() + ":" + availability.getStartMinute() + "-");
                    sb.append(availability.getEndHour() + ":" + availability.getEndMinute() + ", ");
                    break;
                case TUESDAY:
                    sb.append("TUE: " + availability.getStartHour() + ":" + availability.getStartMinute() + "-");
                    sb.append(availability.getEndHour() + ":" + availability.getEndMinute() + ", ");
                    break;
                case WEDNESDAY:
                    sb.append("WED: " + availability.getStartHour() + ":" + availability.getStartMinute() + "-");
                    sb.append(availability.getEndHour() + ":" + availability.getEndMinute() + ", ");
                    break;
                case THURSDAY:
                    sb.append("THU: " + availability.getStartHour() + ":" + availability.getStartMinute() + "-");
                    sb.append(availability.getEndHour() + ":" + availability.getEndMinute() + ", ");
                    break;
                case FRIDAY:
                    sb.append("FRI: " + availability.getStartHour() + ":" + availability.getStartMinute() + "-");
                    sb.append(availability.getEndHour() + ":" + availability.getEndMinute() + ", ");
                    break;
                case SATURDAY:
                    sb.append("SAT: " + availability.getStartHour() + ":" + availability.getStartMinute() + "-");
                    sb.append(availability.getEndHour() + ":" + availability.getEndMinute() + ". ");
                    break;
                case SUNDAY:
                    sb.append("SUN: " + availability.getStartHour() + ":" + availability.getStartMinute() + "-");
                    sb.append(availability.getEndHour() + ":" + availability.getEndMinute() + ", ");
                    break;
            }

            counter ++;

            if (counter == 7) {
                sb.append(n);
                counter = 0;
            }
        }

        return sb.toString();
    }

    public static boolean validateRating(int rating) {

        return rating > 0 && rating <= 5;
    }

    public static boolean validatePhoneNumber(String phoneNumber) {

        //checking the length of phoneNumber, should be 10

        if (phoneNumber.length() != 10) {
            return false;
        }

        try {
            Integer.parseInt(phoneNumber);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public static boolean validatePostalCode(String postalCode) {

        if (postalCode.length() != 6) {
            return false;
        }

        return true;
    }

    public static boolean validateProvince(String province) {

        if (province.equalsIgnoreCase("ontario")) {
            return true;
        } else if (province.equalsIgnoreCase("quebec")) {
            return true;
        } else if (province.equalsIgnoreCase("british columbia")) {
            return true;
        } else if (province.equalsIgnoreCase("alberta")) {
            return true;
        } else if (province.equalsIgnoreCase("nova scotia")) {
            return true;
        } else if (province.equalsIgnoreCase("saskatchewan")) {
            return true;
        } else if (province.equalsIgnoreCase("manitoba")) {
            return true;
        } else if (province.equalsIgnoreCase("new brunswick")) {
            return true;
        } else if (province.equalsIgnoreCase("newfoundland and labrador")) {
            return true;
        } else if (province.equalsIgnoreCase("prince edward island")) {
            return true;
        } else if (province.equalsIgnoreCase("yukon")) {
            return true;
        } else if (province.equalsIgnoreCase("nunavut")) {
            return true;
        } else if (province.equalsIgnoreCase("northwest territories")) {
            return true;
        }

        return false;
    }

    public static boolean validateHour(String hour) {

        if (!validateInteger(hour)) {
            return false;
        }

        int h = Integer.parseInt(hour);

        if (h < 0 || h > 23) {
            return false;
        }

        return true;
    }

    public static boolean validateInteger(String integer) {

        try {
            Integer.parseInt(integer);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public static boolean validateMinute(String minute) {

        if (!validateInteger(minute)) {
            return false;
        }

        int m = Integer.parseInt(minute);

        if (m < 0 || m > 59) {
            return false;
        }

        return true;
    }

    public static boolean startIsBeforeEnd(int startHour, int startMinute, int endHour, int endMinute) {

        if (startHour > endHour) {
            return false;
        } else if (startHour == endHour && startMinute < endMinute) {
            return true;
        } else if (startHour == endHour && startMinute > endMinute) {
            return false;
        } else if (endHour > startHour){
            return true;
        } else {
            return true;
        }
    }

    public static boolean validateMonth(int month) {

        return month >= 1 && month <= 12;
    }

    public static boolean validateDay(int day, int month, int year) {

        if (year % 4 == 0) {

            if (month == 2) {

                return day >= 1 && day <= 29;
            }
        } else {

            if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {

                return day >= 1 && day <= 31;

            } else if (month == 2) {

                return day >= 1 && day <= 28;

            } else if (month == 4 || month == 6 || month == 9 || month == 11) {

                return day >= 1 && day <= 30;
            }
        }

        return false;
    }

    public static WeekDay getWeekDayForDate(int day, int month, int year) {

        Calendar date = Calendar.getInstance();
        date.set(year, month - 1, day);

        int dayOfWeek = date.get(Calendar.DAY_OF_WEEK);

        if (dayOfWeek == 1) {
            return WeekDay.SUNDAY;
        } else if (dayOfWeek == 2) {
            return WeekDay.MONDAY;
        } else if (dayOfWeek == 3) {
            return WeekDay.TUESDAY;
        } else if (dayOfWeek == 4) {
            return WeekDay.WEDNESDAY;
        } else if (dayOfWeek == 5) {
            return WeekDay.THURSDAY;
        } else if (dayOfWeek == 6) {
            return WeekDay.FRIDAY;
        } else {
            return WeekDay.SATURDAY;
        }
    }

    public static boolean validateDateHasNotPassed(int day, int month, int year) {

        Calendar currentDate = Calendar.getInstance();

        if (currentDate.get(Calendar.YEAR) < year) {

            return true;

        } else if (currentDate.get(Calendar.YEAR) == year) {

            if (currentDate.get(Calendar.MONTH) < month) {

                return true;

            } else if (currentDate.get(Calendar.MONTH) == month) {

                if (currentDate.get(Calendar.DAY_OF_MONTH) < day) {
                    return true;
                }
            }
        }

        return false;
    }

    public enum WeekDay {
        SUNDAY {
            @Override
            public String toString() {
                return "Sunday";
            }
        }, MONDAY {
            @Override
            public String toString() {
                return "Monday";
            }
        }, TUESDAY {
            @Override
            public String toString() {
                return "Tuesday";
            }
        }, WEDNESDAY {
            @Override
            public String toString() {
                return "Wednesday";
            }
        }, THURSDAY {
            @Override
            public String toString() {
                return "Thursday";
            }
        }, FRIDAY {
            @Override
            public String toString() {
                return "Friday";
            }
        }, SATURDAY {
            @Override
            public String toString() {
                return "Saturday";
            }
        };

        public static WeekDay getWeekDayByName(String weekDay) {

            if (weekDay.equals("SUNDAY")) {
                return WeekDay.SUNDAY;
            } else if (weekDay.equals("MONDAY")) {
                return WeekDay.MONDAY;
            } else if (weekDay.equals("TUESDAY")) {
                return WeekDay.TUESDAY;
            } else if (weekDay.equals("WEDNESDAY")) {
                return WeekDay.WEDNESDAY;
            } else if (weekDay.equals("THURSDAY")) {
                return WeekDay.THURSDAY;
            } else if (weekDay.equals("FRIDAY")) {
                return WeekDay.FRIDAY;
            } else if (weekDay.equals("SATURDAY")) {
                return WeekDay.SATURDAY;
            }

            return null;
        }
    }
}