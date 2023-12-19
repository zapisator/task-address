package org.example.domen.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.apache.commons.lang3.tuple.Pair;
import org.example.domen.formatter.Formatter;
import org.example.domen.model.Address;
import org.example.domen.model.Hierarchy;
import org.example.infrastructure.reader.AddressReader;
import org.example.infrastructure.reader.xml.model.XmlAddress;
import org.example.infrastructure.reader.xml.model.XmlAddresses;
import org.example.infrastructure.reader.xml.model.XmlHierarchies;
import org.example.infrastructure.reader.xml.model.XmlHierarchy;
import org.example.infrastructure.reader.xml.service.XmlAddressReader;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@AllArgsConstructor
@Builder
public class AddressService {

    @Builder.Default
    private final String path = "src/test/resources/";
    @Builder.Default
    private final String addressFile = "AS_ADDR_OBJ.XML";
    @Builder.Default
    private final String hierarchyFile = "AS_ADM_HIERARCHY.XML";
    @Builder.Default
    private final AddressReader addressReader = new XmlAddressReader();

    public void findByThenPrint(LocalDate date, Set<Integer> objectIds) {
        printLines(findBy(date, objectIds));
    }

    public void findByParentIdAndIsActualAndThenPrint(String typename) {
        printLines(findByParentIdAndIsActualAnd(typename));
    }

    private void printLines(List<String> lines) {
        System.out.println(
                lines.stream()
                        .collect(Collectors.joining(System.lineSeparator()))
        );
    }

    public List<String> findBy(LocalDate date, Set<Integer> objectIds) {
        checkParameters(date, objectIds);

        return addressReader
                .readAddresses(XmlAddresses.class, path + addressFile)
                .getObjects()
                .stream()
                .filter(xmlAddress -> objectIds.contains(xmlAddress.getObjectId())
                        && (xmlAddress.getStartDate().isBefore(date) || xmlAddress.getStartDate().isEqual(date))
                        && (xmlAddress.getEndDate().isAfter(date) || xmlAddress.getEndDate().isEqual(date)))
                .map(XmlAddress::convertXmlToModelAddress)
                .map(Formatter::objectIdAndTypeName)
                .toList();
    }

    public List<String> findByParentIdAndIsActualAnd(String typename) {
        final Map<Integer, Address> addresses = addressReader
                .readAddresses(XmlAddresses.class, path + addressFile)
                .getObjects()
                .stream()
                .filter(XmlAddress::isActual)
                .map(XmlAddress::convertXmlToModelAddress)
                .collect(toMap(Address::objectId, identity(), (a1, a2) -> a1));
        final Map<Integer, Hierarchy> hierarchies = addressReader
                .readAddresses(XmlHierarchies.class, path + hierarchyFile)
                .getItems()
                .stream()
                .filter(XmlHierarchy::isActive)
                .map(XmlHierarchy::convertXmlToModelHierarchy)
                .collect(toMap(Hierarchy::objectId, identity(), (h1, h2) -> h1));
        return addresses
                .values()
                .stream()
                .filter(address -> address.typename().equals(typename))
                .map(Address::objectId)
                .map(integer -> Pair.of(
                                integer,
                                hierarchyLine(hierarchies, integer)
                                        .stream()
                                        .map(hierarchy -> addresses.get(hierarchy.objectId()))
                                        .map(Formatter::typeNameName)
                                        .toList()
                        )
                )
                .map(integerListPair -> String.join(", ", integerListPair.getRight()))
                .toList();
    }

    private List<Hierarchy> hierarchyLine(Map<Integer, Hierarchy> hierarchies, int objectId) {
        final LinkedList<Hierarchy> line = new LinkedList<>();
        int currentObjectId = objectId;
        Hierarchy currentHierarchy = hierarchies.get(currentObjectId);
        while (nonNull(currentHierarchy)) {
            line.addFirst(currentHierarchy);
            currentObjectId = currentHierarchy.parentObjId();
            currentHierarchy = hierarchies.get(currentObjectId);
        }
        return line;
    }

    private void checkParameters(LocalDate date, Set<Integer> objectIds) {
        if (date == null || objectIds == null || objectIds.isEmpty()) {
            throw new IllegalArgumentException("Date and object ids must not be null or empty");
        }
    }
}
