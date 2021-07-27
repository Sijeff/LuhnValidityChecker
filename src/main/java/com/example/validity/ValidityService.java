package com.example.validity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ValidityService {

    private static final Logger LOGGER = Logger.getLogger(ValidityService.class.getName());
    private static final Pattern CORRECT_CHARACTERS = Pattern.compile("^\\d{6,8}\\-?\\d{4}|^\\d{6,8}\\+?\\d{4}");

    private static final DateTimeFormatter STANDARD_FORMATTER = new DateTimeFormatterBuilder()
            .appendValueReduced(ChronoField.YEAR, 2, 4, LocalDate.now().minusYears(99))
            .appendPattern("MMdd")
            .toFormatter().withResolverStyle(ResolverStyle.STRICT);

    private static final DateTimeFormatter MORE_THAN_HUNDRED_YEARS = new DateTimeFormatterBuilder()
            .appendValueReduced(ChronoField.YEAR_OF_ERA, 2, 2, LocalDate.now().minusYears(199))
            .appendPattern("MMdd")
            .toFormatter();

    public List<String> validate(List<String> numbers) {
        return numbers.stream()
                .filter(this::validateCorrectCharacters)
                .map(Number::new)
                .filter(this::validate)
                .map(Number::getOriginalNumber)
                .collect(Collectors.toList());
    }

    private boolean validate(Number number){
        try {
            if (!number.isOrganizationalNumber()) {
                validateDate(number);
            }

            validateChecksum(number);
        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.INFO, String.format("%s invalid, cause: %s", number.getOriginalNumber(), e.getMessage()));
            return false;
        }
        return true;
    }

    private void validateDate(Number number) {
        String dateString = number.getDateString();
        if (number.isCoordinationNumber()) {
            dateString = number.getYear() + number.getMonth() + String.format("%02d", Integer.parseInt(number.getDay()) - 60);
        }

        try {
            if (number.containsPlus()) {
                LocalDate.parse(dateString, MORE_THAN_HUNDRED_YEARS);
            } else {
                LocalDate.parse(dateString, STANDARD_FORMATTER);
            }
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Number does not begin with a valid date");
        }
    }

    private boolean validateCorrectCharacters(String number) throws IllegalArgumentException {
        if (CORRECT_CHARACTERS.matcher(number).matches()) {
            return true;
        }
        LOGGER.log(Level.INFO, String.format("Number: %s contains incorrect characters. Number may only contain digits, one dash, or one plus sign", number));
        return false;
    }

    private void validateChecksum(Number number) {
        int lengthOfNumber = number.getFormattedNumber().length();
        String numberWithoutChecksum = lengthOfNumber == 12 ? number.getFormattedNumber().substring(2, lengthOfNumber - 1): number.getFormattedNumber().substring(0, lengthOfNumber - 1);
        int sum = 0;

        for (int i = 0; i < numberWithoutChecksum.length(); i++) {
            int addition = Integer.parseInt(numberWithoutChecksum.substring(i, i + 1));
            if (i % 2 == 0) {
                addition = doubleValue(addition);
            }
            sum += addition;
        }

        if ((sum + number.getCheckSum()) % 10 != 0) {
           throw new IllegalArgumentException("Number has incorrect check sum, does not pass Luhn validation");
        }
    }

    private static int doubleValue(int value) {
        value *= 2;
        if (value > 9) {
            return 1 + value % 10;
        }
        return value;
    }
}
