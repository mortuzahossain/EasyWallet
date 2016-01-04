package com.appracks.easy_wallet.dateOperation;

/**
 * Created by HABIB on 12/9/2015.
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class DateOperation {

    GregorianCalendar gc;

    public static String getNextDate(String cur){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(cur));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, 1);
        return sdf.format(c.getTime());
    }
    public String getDateFromRaw(int year,int month,int day){
        String d;
        String m;
        if (day < 10) {
            d = "0" + String.valueOf(day);
        } else {
            d = String.valueOf(day);
        }
        if (month < 10) {
            m = "0" + String.valueOf(month);
        } else {
            m = String.valueOf(month);
        }
        return d+"-"+m+"-"+String.valueOf(year);
    }
    public String getCurrentYear() {
        gc = new GregorianCalendar();
        int year = gc.get(Calendar.YEAR);
        return String.valueOf(year);
    }

    public String getCurrentMonth() {
        gc = new GregorianCalendar();
        int month = gc.get(Calendar.MONTH) + 1;
        String m;
        if (month < 10) {
            m = "0" + String.valueOf(month);
        } else {
            m = String.valueOf(month);
        }
        return m;
    }

    public String getCurrentDay() {
        gc = new GregorianCalendar();
        int day = gc.get(Calendar.DAY_OF_MONTH);
        String d;
        if (day < 10) {
            d = "0" + String.valueOf(day);
        } else {
            d = String.valueOf(day);
        }
        return d;
    }
    public String getDay(String date) {
        int day;
        day = Integer.valueOf(date.substring(0,2));
        String d;
        if (day < 10) {
            d = "0" + String.valueOf(day);
        } else {
            d = String.valueOf(day);
        }

        return d;
    }
    public String getMonth(String date) {
        int month;
        month = Integer.valueOf(date.substring(3,5));
        String m;
        if (month < 10) {
            m = "0" + String.valueOf(month);
        } else {
            m = String.valueOf(month);
        }

        return m;
    }
    public String getYear(String date) {
        return date.substring(6);
    }
    public String getDateOrder(String date) {
        int day, month;
        day = Integer.valueOf(date.substring(0,2));
        month = Integer.valueOf(date.substring(3,5));
        String d;
        String m;
        if (day < 10) {
            d = "0" + String.valueOf(day);
        } else {
            d = String.valueOf(day);
        }
        if (month < 10) {
            m = "0" + String.valueOf(month);
        } else {
            m = String.valueOf(month);
        }

        return date.substring(6) + m + d;
    }
    public String getCurrentDateOrder() {
        gc = new GregorianCalendar();
        int day, month, year;
        day = gc.get(Calendar.DAY_OF_MONTH);
        month = gc.get(Calendar.MONTH) + 1;
        year = gc.get(Calendar.YEAR);
        String d;
        String m;
        if (day < 10) {
            d = "0" + String.valueOf(day);
        } else {
            d = String.valueOf(day);
        }
        if (month < 10) {
            m = "0" + String.valueOf(month);
        } else {
            m = String.valueOf(month);
        }

        return String.valueOf(year) + m + d;
    }
    public String getCurrentDate() {
        gc = new GregorianCalendar();
        int day, month, year;
        day = gc.get(Calendar.DAY_OF_MONTH);
        month = gc.get(Calendar.MONTH) + 1;
        year = gc.get(Calendar.YEAR);
        String d;
        String m;
        if (day < 10) {
            d = "0" + String.valueOf(day);
        } else {
            d = String.valueOf(day);
        }
        if (month < 10) {
            m = "0" + String.valueOf(month);
        } else {
            m = String.valueOf(month);
        }

        return d+"-"+m+"-"+String.valueOf(year);
    }

    public String getCurrentDateN7() {
        gc = new GregorianCalendar();
        int day, month, year;
        day = gc.get(Calendar.DAY_OF_MONTH);
        month = gc.get(Calendar.MONTH) + 1;
        year = gc.get(Calendar.YEAR);

        if (day <= 7) {
            switch (month) {
                case 1:
                    switch (day) {
                        case 1:
                            day = 26;
                            month = 12;
                            year = year - 1;
                            break;
                        case 2:
                            day = 27;
                            month = 12;
                            year = year - 1;
                            break;
                        case 3:
                            day = 28;
                            month = 12;
                            year = year - 1;
                            break;
                        case 4:
                            day = 29;
                            month = 12;
                            year = year - 1;
                            break;
                        case 5:
                            day = 30;
                            month = month - 1;
                            year = year - 1;
                            break;
                        case 6:
                            day = 31;
                            month = 12;
                            year = year - 1;
                            break;
                        case 7:
                            day = 1;
                            month = 1;
                            year = year;
                            break;

                    }
                    break;
                case 2:
                    if (year % 4 == 0 || year % 100 == 0) {
                        switch (day) {
                            case 1:
                                day = 24;
                                month = month - 1;
                                break;
                            case 2:
                                day = 25;
                                month = month - 1;
                                break;
                            case 3:
                                day = 26;
                                month = month - 1;
                                break;
                            case 4:
                                day = 27;
                                month = month - 1;
                                break;
                            case 5:
                                day = 28;
                                month = month - 1;
                                break;
                            case 6:
                                day = 29;
                                month = month - 1;
                                break;
                            case 7:
                                day = 1;
                                month = month;
                                break;

                        }
                    } else {
                        switch (day) {
                            case 1:
                                day = 23;
                                month = month - 1;
                                break;
                            case 2:
                                day = 24;
                                month = month - 1;
                                break;
                            case 3:
                                day = 25;
                                month = month - 1;
                                break;
                            case 4:
                                day = 26;
                                month = month - 1;
                                break;
                            case 5:
                                day = 27;
                                month = month - 1;
                                break;
                            case 6:
                                day = 28;
                                month = month - 1;
                                break;
                            case 7:
                                day = 1;
                                month = month;
                                break;

                        }
                    }
                    break;
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    switch (day) {
                        case 1:
                            day = 26;
                            month = month - 1;
                            break;
                        case 2:
                            day = 27;
                            month = month - 1;
                            break;
                        case 3:
                            day = 28;
                            month = month - 1;
                            break;
                        case 4:
                            day = 29;
                            month = month - 1;
                            break;
                        case 5:
                            day = 30;
                            month = month - 1;
                            break;
                        case 6:
                            day = 31;
                            month = month - 1;
                            break;
                        case 7:
                            day = 1;
                            month = month;
                            break;

                    }
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    switch (day) {
                        case 1:
                            day = 25;
                            month = month - 1;
                            break;
                        case 2:
                            day = 26;
                            month = month - 1;
                            break;
                        case 3:
                            day = 27;
                            month = month - 1;
                            break;
                        case 4:
                            day = 28;
                            month = month - 1;
                            break;
                        case 5:
                            day = 29;
                            month = month - 1;
                            break;
                        case 6:
                            day = 30;
                            month = month - 1;
                            break;
                        case 7:
                            day = 1;
                            month = month;
                            break;

                    }
                    break;
            }
        }else{
            day=day-6;
        }

        String d;
        String m;
        if (day < 10) {
            d = "0" + String.valueOf(day);
        } else {
            d = String.valueOf(day);
        }
        if (month < 10) {
            m = "0" + String.valueOf(month);
        } else {
            m = String.valueOf(month);
        }
        return d+"-"+m+"-"+String.valueOf(year);
    }
}

