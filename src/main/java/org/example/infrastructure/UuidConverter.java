package org.example.infrastructure;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.util.UUID;

public class UuidConverter extends XmlAdapter<String, UUID> {

    @Override
    public UUID unmarshal(String value) throws Exception {
        return UUID.fromString(value);
    }

    @Override
    public String marshal(UUID value) throws Exception {
        return value.toString();
    }

}
