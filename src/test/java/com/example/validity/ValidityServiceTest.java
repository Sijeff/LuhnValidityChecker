package com.example.validity;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

class ValidityServiceTest {

    @Test
    void testPersonalNumbers() throws IOException {
        ValidityService subject = new ValidityService();
        List<String> validPersonalNumbers = Files.lines(Path.of("src", "test", "resources", "valid-personal-numbers.txt")).collect(Collectors.toList());
        List<String> invalidPersonalNumbers = Files.lines(Path.of("src", "test", "resources", "invalid-personal-numbers.txt")).collect(Collectors.toList());
        List<String> validOrganizationNumbers = Files.lines(Path.of("src", "test", "resources", "valid-organization-numbers.txt")).collect(Collectors.toList());
        List<String> validCoordinationNumbers = Files.lines(Path.of("src", "test", "resources", "valid-co-ordination-numbers.txt")).collect(Collectors.toList());

        List<String> validNumbers = Stream.concat(validPersonalNumbers.stream(), validOrganizationNumbers.stream()).collect(Collectors.toList());
        validNumbers.addAll(validCoordinationNumbers);
        List<String> numbers = Stream.concat(validPersonalNumbers.stream(), invalidPersonalNumbers.stream()).collect(Collectors.toList());
        numbers.addAll(validOrganizationNumbers);
        numbers.addAll(validCoordinationNumbers);
        List<String> result = subject.validate(numbers);
        assertThat(result).containsExactlyElementsOf(validNumbers);
    }

}