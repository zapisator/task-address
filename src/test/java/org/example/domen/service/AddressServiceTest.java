package org.example.domen.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AddressServiceTest {

    private AddressService addressService;

    @BeforeEach
    public void setUp() {
        addressService = AddressService
                .builder()
                .build();
    }

    @Test
    void testFindByThenPrint() {
        final LocalDate date = LocalDate.parse("2010-01-01");
        final Set<Integer> objectIds = Set.of(1422396, 1450759, 1449192, 1451562);

        assertDoesNotThrow(() -> addressService.findByThenPrint(date, objectIds));
    }

    @Test
    void testFindByParentIdAndIsActualAndThenPrint() {
        final String typename = "проезд";

        assertDoesNotThrow(() -> addressService.findByParentIdAndIsActualAndThenPrint(typename));
    }

    @Test
    void testFindByParentIdAndIsActualAnd() {
        final String typename = "проезд";
        final List<String> expected = List.of(
                "АО Ненецкий, г Нарьян-Мар, проезд Карьерный",
                "АО Ненецкий, г Нарьян-Мар, проезд Производственный",
                "АО Ненецкий, г Нарьян-Мар, проезд Ветеринарный",
                "АО Ненецкий, г Нарьян-Мар, проезд Им капитана Матросова",
                "АО Ненецкий, р-н Заполярный, рп Искателей, проезд Лая-Вожский",
                "АО Ненецкий, р-н Заполярный, рп Искателей, проезд Вертолетный",
                "АО Ненецкий, г Нарьян-Мар, проезд Лесопильщиков",
                "АО Ненецкий, г Нарьян-Мар, проезд Качгортский",
                "АО Ненецкий, г Нарьян-Мар, проезд Ноябрьский",
                "АО Ненецкий, г Нарьян-Мар, проезд Торговый",
                "АО Ненецкий, г Нарьян-Мар, проезд Брусничный",
                "АО Ненецкий, р-н Заполярный, рп Искателей, проезд Песчаный",
                "АО Ненецкий, г Нарьян-Мар, проезд Стивидоров"
        );
        final List<String> actual = addressService.findByParentIdAndIsActualAnd(typename);

        assertEquals(expected, actual);
    }

    @Test
    void testGetAddressDescriptionsWithValidDateAndIds() {
        final LocalDate date = LocalDate.of(2010, 1, 1);
        final Set<Integer> objectIds = new HashSet<>(Arrays.asList(1422396, 1450759, 1449192, 1451562));

        final List<String> expected = Arrays.asList(
                "1422396: ул Северная",
                "1450759: р-н Заполярный",
                "1449192: п Нельмин Нос",
                "1451562: п Екуша"
        );

        final List<String> actual = addressService
                .findBy(date, objectIds)
                .stream()
                .toList();

        assertEquals(expected, actual);
    }

    @Test
    void testGetAddressDescriptionsWithInvalidDateAndIds() {
        final LocalDate date = LocalDate.of(1, 1, 1);
        final Set<Integer> objectIds = new HashSet<>(Arrays.asList(-1, -2, -3, -4));

        final List<String> expected = Collections.emptyList();

        final List<String> actual = addressService
                .findBy(date, objectIds)
                .stream()
                .toList();

        assertEquals(expected, actual);
    }

    @Test
    void testGetAddressDescriptionsWithNullDate() {
        final Set<Integer> objectIds = new HashSet<>(Arrays.asList(1422396, 1450759, 1449192, 1451562));

        assertThrows(
                IllegalArgumentException.class,
                () -> addressService.findBy(null, objectIds)
        );
    }

    @Test
    void testGetAddressDescriptionsWithNullIds() {
        final LocalDate date = LocalDate.of(2010, 1, 1);

        assertThrows(
                IllegalArgumentException.class,
                () -> addressService.findBy(date, null)
        );
    }

    @Test
    void testGetAddressDescriptionsWithEmptyIds() {
        final LocalDate date = LocalDate.of(2010, 1, 1);
        final Set<Integer> objectIds = new HashSet<>();

        assertThrows(
                IllegalArgumentException.class,
                () -> addressService.findBy(date, objectIds)
        );
    }
}
