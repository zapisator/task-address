package org.example.domen.formatter;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.domen.model.Address;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Formatter {

    public static String objectIdAndTypeName(Address address) {
        return "%d: %s %s"
                .formatted(
                        address.objectId(),
                        address.typename(),
                        address.name()
                );
    }

    public static String typeNameName(Address address) {
        return "%s %s"
                .formatted(
                        address.typename(),
                        address.name()
                );
    }
}
