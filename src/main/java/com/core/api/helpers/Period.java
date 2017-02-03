package com.core.api.helpers;

import lombok.Data;

import static com.core.api.helpers.Constants.CURRENT_YEAR;

@Data
public class Period {
    private Date startDate;
    private Date endDate;

    private Period(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static Period of(Date start, Date end) {
        return new Period(start, end);
    }

    public static class Date {
        public String date;

        public Date(String date) {
            this.date = date;
        }

        @Override
        public String toString() {
            return date;
        }

        public static Date january(int date) {
            return build(CURRENT_YEAR, 1, date);
        }

        public static Date january(int year, int date) {
            return build(year, 1, date);
        }

        public static Date february(int year, int date) {
            return build(year, 2, date);
        }

        public static Date february(int date) {
            return build(CURRENT_YEAR, 2, date);
        }

        public static Date march(int year, int date) {
            return build(year, 3, date);
        }

        public static Date march(int date) {
            return build(CURRENT_YEAR, 3, date);
        }

        public static Date april(int year, int date) {
            return build(year, 4, date);
        }

        public static Date april(int date) {
            return build(CURRENT_YEAR, 4, date);
        }

        public static Date may(int year, int date) {
            return build(year, 5, date);
        }

        public static Date may(int date) {
            return build(CURRENT_YEAR, 5, date);
        }

        public static Date june(int year, int date) {
            return build(year, 6, date);
        }

        public static Date june(int date) {
            return build(CURRENT_YEAR, 6, date);
        }

        public static Date july(int year, int date) {
            return build(year, 7, date);
        }

        public static Date july(int date) {
            return build(CURRENT_YEAR, 7, date);
        }

        public static Date august(int year, int date) {
            return build(year, 8, date);
        }

        public static Date august(int date) {
            return build(CURRENT_YEAR, 8, date);
        }

        public static Date september(int year, int date) {
            return build(year, 9, date);
        }

        public static Date september(int date) {
            return build(CURRENT_YEAR, 9, date);
        }

        public static Date october(int year, int date) {
            return build(year, 10, date);
        }

        public static Date october(int date) {
            return build(CURRENT_YEAR, 10, date);
        }

        public static Date november(int year, int date) {
            return build(year, 11, date);
        }

        public static Date november(int date) {
            return build(CURRENT_YEAR, 11, date);
        }

        public static Date december(int year, int date) {
            return build(year, 12, date);
        }

        public static Date december(int date) {
            return build(CURRENT_YEAR, 12, date);
        }

        private static Date build(int year, int month, int day) {
            String formattedMonth = (month + "").length() == 1 ? "0" + month : month + "";
            String formattedDay = (day + "").length() == 1 ? "0" + day : day + "";

            return new Date(year + "-" + formattedMonth + "-" + formattedDay);
        }
    }
}
