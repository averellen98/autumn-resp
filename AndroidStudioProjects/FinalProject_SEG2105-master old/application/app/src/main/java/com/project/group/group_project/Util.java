package com.project.group.group_project;

import android.content.Intent;

public class Util {

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

        try {
            Integer.parseInt(hour);
        } catch (Exception e) {
            return false;
        }

        int h = Integer.parseInt(hour);

        if (h < 0 || h > 23) {
            return false;
        }

        return true;
    }

    public static boolean validateMinute(String minute) {

        try {
            Integer.parseInt(minute);
        } catch (Exception e) {
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
        }
        return true;
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
