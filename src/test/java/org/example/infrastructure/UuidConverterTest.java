package org.example.infrastructure;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UuidConverterTest {
    private UuidConverter converter = new UuidConverter();

    @Test
    void testMarshal() throws Exception {
        final UUID value = UUID.fromString("17ef3c93-1607-4424-a403-95fd5f5587af");

        final String result = converter.marshal(value);

        assertEquals("17ef3c93-1607-4424-a403-95fd5f5587af", result);
    }

    @Test
    void testUnmarshal() throws Exception {
        final String value = "17ef3c93-1607-4424-a403-95fd5f5587af";

        final UUID result = converter.unmarshal(value);

        assertEquals(UUID.fromString("17ef3c93-1607-4424-a403-95fd5f5587af"), result);
    }
}