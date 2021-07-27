package com.example.validity;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class NumberTest {

    @Test
    void testNumberCreation() {
        Number coordinationNumber = new Number("190910799824");
        assertThat(coordinationNumber.getYear()).isEqualTo("1909");
        assertThat(coordinationNumber.getMonth()).isEqualTo("10");
        assertThat(coordinationNumber.getDay()).isEqualTo("79");
        assertThat(coordinationNumber.getDateString()).isEqualTo("19091079");
        assertThat(coordinationNumber.isCoordinationNumber()).isTrue();
        assertThat(coordinationNumber.isOrganizationalNumber()).isFalse();
        assertThat(coordinationNumber.containsPlus()).isFalse();
        assertThat(coordinationNumber.getCheckSum()).isEqualTo(4);

        Number organizationNumber = new Number("16556601-6399");
        assertThat(organizationNumber.getYear()).isEqualTo("1655");
        assertThat(organizationNumber.getMonth()).isEqualTo("66");
        assertThat(organizationNumber.getDay()).isEqualTo("01");
        assertThat(organizationNumber.getDateString()).isEqualTo("16556601");
        assertThat(organizationNumber.getFormattedNumber()).isEqualTo("165566016399");
        assertThat(organizationNumber.isCoordinationNumber()).isFalse();
        assertThat(organizationNumber.isOrganizationalNumber()).isTrue();
        assertThat(organizationNumber.containsPlus()).isFalse();
        assertThat(organizationNumber.getCheckSum()).isEqualTo(9);
    }

    @Test
    void testPersonalNumberCreation() {
        Number first = new Number("900118+9811");
        assertThat(first.getYear()).isEqualTo("90");
        assertThat(first.getMonth()).isEqualTo("01");
        assertThat(first.getDay()).isEqualTo("18");
        assertThat(first.getDateString()).isEqualTo("900118");
        assertThat(first.getFormattedNumber()).isEqualTo("9001189811");
        assertThat(first.isCoordinationNumber()).isFalse();
        assertThat(first.isOrganizationalNumber()).isFalse();
        assertThat(first.containsPlus()).isTrue();
        assertThat(first.getCheckSum()).isEqualTo(1);

        Number second = new Number("4607137454");
        assertThat(second.getYear()).isEqualTo("46");
        assertThat(second.getMonth()).isEqualTo("07");
        assertThat(second.getDay()).isEqualTo("13");
        assertThat(second.getDateString()).isEqualTo("460713");
        assertThat(second.getFormattedNumber()).isEqualTo("4607137454");
        assertThat(second.isCoordinationNumber()).isFalse();
        assertThat(second.isOrganizationalNumber()).isFalse();
        assertThat(second.containsPlus()).isFalse();
        assertThat(second.getCheckSum()).isEqualTo(4);
    }
}