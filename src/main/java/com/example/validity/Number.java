package com.example.validity;

public class Number {

    private final String originalNumber;
    private final String year;
    private final String month;
    private final String day;
    private final String formattedNumber;
    private final boolean containsPlus;
    private final boolean isOrganizationalNumber;
    private final boolean isCoordinationNumber;
    private final Integer checkSum;

    public Number(String originalNumber) {
        this.originalNumber = originalNumber;
        this.formattedNumber = originalNumber.replaceAll("[-+]", "");
        this.year = formattedNumber.length() > 10 ? originalNumber.substring(0, 4) : originalNumber.substring(0, 2);
        this.month = formattedNumber.length() > 10 ? originalNumber.substring(4, 6) : originalNumber.substring(2, 4);
        this.day = formattedNumber.length() > 10 ? originalNumber.substring(6, 8) : originalNumber.substring(4, 6);
        this.containsPlus = originalNumber.contains("+");
        this.isOrganizationalNumber = Integer.parseInt(month) >= 20;
        this.isCoordinationNumber = Integer.parseInt(day) > 60;
        this.checkSum = Integer.valueOf(originalNumber.substring(originalNumber.length() - 1));
    }

    public String getOriginalNumber() {
        return originalNumber;
    }

    public String getYear() {
        return year;
    }

    public String getMonth() {
        return month;
    }

    public String getDay() {
        return day;
    }

    public String getDateString() {
        return year + month + day;
    }

    public String getFormattedNumber() {
        return formattedNumber;
    }

    public boolean containsPlus() {
        return containsPlus;
    }

    public boolean isOrganizationalNumber() {
        return isOrganizationalNumber;
    }

    public boolean isCoordinationNumber() {
        return isCoordinationNumber;
    }

    public Integer getCheckSum() {
        return checkSum;
    }
}
