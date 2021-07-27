
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
    private static final Pattern CORRECT_CHARACTERS = Pattern.compile("^\\d{6,8}\\-?\\d{4}");//todo: bit ugly
    private static final Pattern CORRECT_CHARACTERS_WITH_PLUS = Pattern.compile("^\\d{6,8}\\+?\\d{4}");//todo: bit ugly

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
                .filter(this::validate)
                .collect(Collectors.toList());
    }

    private boolean validate(String number){
        try {
            if (!isOrganizationalNumber(number)) {
                validateDate(number);
            }
            String formattedNumber = formatNumber(number);
            validateCorrectLength(formattedNumber);
            validateChecksum(formattedNumber);
        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.INFO, String.format("%s invalid, cause: %s", number, e.getMessage()));
            return false;
        }
        return true;
    }

    private boolean isOrganizationalNumber(String number) {
        number = number.length() > 11 ? number.substring(2) : number;
        // By definition, the middle pair of digits in an organizational number must be at least 20
        return Integer.parseInt(number.substring(2, 4)) >= 20;
    }

    private void validateDate(String number) {
        try {
            if (number.length() >= 12) {
                LocalDate.parse(number.substring(0, 8), STANDARD_FORMATTER);
            } else if (number.contains("+")) {
                LocalDate.parse(number.substring(0, 6), MORE_THAN_HUNDRED_YEARS);
            } else {
                LocalDate.parse(number.substring(0, 6), STANDARD_FORMATTER);
            }
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Number does not begin with a valid date");
        }
    }

    private boolean validateCorrectCharacters(String number) throws IllegalArgumentException {
        if (CORRECT_CHARACTERS.matcher(number).matches() || CORRECT_CHARACTERS_WITH_PLUS.matcher(number).matches()) {
            return true;
        }
        LOGGER.log(Level.INFO, String.format("Number: %s contains incorrect characters. Number may only contain digits, one dash, or one plus sign", number));
        return false;
    }

    private void validateCorrectLength(String number) throws IllegalArgumentException {
        if (number.length() != 10) {
            throw new IllegalArgumentException("Number has incorrect length");
        }
    }

    private String formatNumber(String number) {
        if (number.contains("-")) {
            number = number.replace("-", "");
        } else if (number.contains("+")) {
            number = number.replace("+", "");
        }

        return number.length() == 12 ? number.substring(2) : number;
    }

    private void validateChecksum(String number) {
        int checkSum = Integer.parseInt(number.substring(9));
        number = number.substring(0, 9);
        // cld we do this as a stream?
        int sum = 0;
        for (int i = 0; i < number.length(); i++) {
            int addition = Integer.parseInt(number.substring(i, i + 1));
            if (i % 2 == 0) {
                addition = doubleValue(addition);
            }
            sum += addition;
        }

        if ((sum + checkSum) % 10 != 0) {
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
