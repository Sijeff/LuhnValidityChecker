import java.util.Arrays;

public class Application {

    public static void main(String[] args) {
        ValidityService validityService = new ValidityService();
        validityService.validate(Arrays.asList(args));
    }
}
