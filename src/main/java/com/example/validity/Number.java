package com.example.validity;

public class Number {

    private final String originalNumber;
    private final String formattedNumber;

    public Number(String originalNumber) {
        this.originalNumber = originalNumber;
        this.formattedNumber = originalNumber.replaceAll("[-+]", "");
    }

    public String getOriginalNumber() {
        return originalNumber;
    }

    public String getYear() {
        return formattedNumber.length() > 10 ? originalNumber.substring(0, 4) : originalNumber.substring(0, 2);
    }

    public String getMonth() {
        return formattedNumber.length() > 10 ? originalNumber.substring(4, 6) : originalNumber.substring(2, 4);
    }

    public String getDay() {
        return formattedNumber.length() > 10 ? originalNumber.substring(6, 8) : originalNumber.substring(4, 6);
    }

    public String getDateString() {
        return getYear() + getMonth() + getDay();
    }

    /**
     *
     * @return Original number with any dash or plus sign removed
     */
    public String getFormattedNumber() {
        return formattedNumber;
    }

    public boolean containsPlus() {
        return originalNumber.contains("+");
    }

    public boolean isOrganizationalNumber() {
        return Integer.parseInt(getMonth()) >= 20;
    }

    public boolean isCoordinationNumber() {
        return Integer.parseInt(getDay()) > 60;
    }

    /**
     *
     * @return Original number, with dash or plus sign removed, with year formatted as YY, and without the last digit
     */
    public String getNumberWithoutChecksum() {
        return formattedNumber.length() == 12 ? formattedNumber.substring(2, formattedNumber.length() - 1) : formattedNumber.substring(0, formattedNumber.length() - 1);
    }

    public Integer getCheckSum() {
        return Integer.valueOf(originalNumber.substring(originalNumber.length() - 1));
    }
}
