package com.example.validity;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Application {

    private final static Logger LOGGER = Logger.getLogger(Application.class.getName());

    public static void main(String[] args) {
        ValidityService validityService = new ValidityService();
        List<String> validNumbers = validityService.validate(Arrays.asList(args));
        validNumbers.forEach(s -> LOGGER.log(Level.INFO, String.format("Number %s is valid", s)));
    }
}
