package org.example.reader.xml.service;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.example.infrastructure.reader.AddressReader;
import org.example.infrastructure.reader.xml.model.XmlAddress;
import org.example.infrastructure.reader.xml.model.XmlAddresses;
import org.example.infrastructure.reader.xml.model.XmlHierarchies;
import org.example.infrastructure.reader.xml.model.XmlHierarchy;
import org.example.infrastructure.reader.xml.service.XmlAddressReader;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.StringReader;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class XmlXmlAddressReaderTest {

    private final AddressReader addressReader = new XmlAddressReader();
    private final String testResourcesPath = "src/test/resources/";

    @Test
    void testReadAddressFromString() throws JAXBException {
        final StringReader stringReader = new StringReader(
                "<OBJECT ID=\"1784296\" OBJECTID=\"1447806\" OBJECTGUID=\"19e872bf-8e45-46cf-88e8-afbc34d0cc3b\" " +
                        "CHANGEID=\"3984973\" NAME=\"Южная\" TYPENAME=\"ул\" LEVEL=\"8\" OPERTYPEID=\"1\" PREVID=\"0\" " +
                        "NEXTID=\"0\" UPDATEDATE=\"2017-09-07\" STARTDATE=\"1900-01-01\" ENDDATE=\"2079-06-06\" " +
                        "ISACTUAL=\"1\" ISACTIVE=\"1\" />"
        );
        final Unmarshaller unmarshaller = JAXBContext
                .newInstance(XmlAddress.class)
                .createUnmarshaller();
        final XmlAddress xmlAddress = (XmlAddress) unmarshaller.unmarshal(stringReader);

        assertAll(
                () -> assertNotNull(xmlAddress),
                () -> assertEquals(1784296, xmlAddress.getId()),
                () -> assertEquals(1447806, xmlAddress.getObjectId()),
                () -> assertEquals(UUID.fromString("19e872bf-8e45-46cf-88e8-afbc34d0cc3b"), xmlAddress.getObjectGuid()),
                () -> assertEquals(3984973, xmlAddress.getChangeId()),
                () -> assertEquals("Южная", xmlAddress.getName()),
                () -> assertEquals("ул", xmlAddress.getTypename()),
                () -> assertEquals(8, xmlAddress.getLevel()),
                () -> assertEquals(1, xmlAddress.getOpenTypeId()),
                () -> assertEquals(0, xmlAddress.getPrevId()),
                () -> assertEquals(0, xmlAddress.getNextId()),
                () -> assertEquals(LocalDate.parse("2017-09-07"), xmlAddress.getUpdateDate()),
                () -> assertEquals(LocalDate.parse("1900-01-01"), xmlAddress.getStartDate()),
                () -> assertEquals(LocalDate.parse("2079-06-06"), xmlAddress.getEndDate()),
                () -> assertTrue(xmlAddress.isActual()),
                () -> assertTrue(xmlAddress.isActive())
        );
    }

    @Test
    void testReadAddresses() throws JAXBException {
        final Unmarshaller unmarshaller = JAXBContext
                .newInstance(XmlAddresses.class)
                .createUnmarshaller();
        final File file = new File(testResourcesPath + "AS_ADDR_OBJ.XML");
        final XmlAddresses xmlAddresses = (XmlAddresses) unmarshaller.unmarshal(file);
        final XmlAddress xmlAddress = xmlAddresses.getObjects().get(0);

        assertAll(
                () -> assertEquals(1745891, xmlAddress.getId()),
                () -> assertEquals(1418203, xmlAddress.getObjectId()),
                () -> assertEquals(UUID.fromString("17ef3c93-1607-4424-a403-95fd5f5587af"), xmlAddress.getObjectGuid()),
                () -> assertEquals(3872344, xmlAddress.getChangeId()),
                () -> assertEquals("Хатанзейского", xmlAddress.getName()),
                () -> assertEquals("ул", xmlAddress.getTypename()),
                () -> assertEquals(8, xmlAddress.getLevel()),
                () -> assertEquals(1, xmlAddress.getOpenTypeId()),
                () -> assertEquals(0, xmlAddress.getPrevId()),
                () -> assertEquals(0, xmlAddress.getNextId()),
                () -> assertEquals(LocalDate.parse("2018-07-16"), xmlAddress.getUpdateDate()),
                () -> assertEquals(LocalDate.parse("1900-01-01"), xmlAddress.getStartDate()),
                () -> assertEquals(LocalDate.parse("2079-06-06"), xmlAddress.getEndDate()),
                () -> assertTrue(xmlAddress.isActual()),
                () -> assertTrue(xmlAddress.isActive())
        );
    }

    @Test
    void testReadHierarchies() throws JAXBException {
        final Unmarshaller unmarshaller = JAXBContext
                .newInstance(XmlHierarchies.class)
                .createUnmarshaller();
        final File file = new File(testResourcesPath + "AS_ADM_HIERARCHY.XML");
        final XmlHierarchies xmlHierarchies = (XmlHierarchies) unmarshaller.unmarshal(file);
        final XmlHierarchy xmlHierarchy = xmlHierarchies.getItems().get(0);

        assertAll(
                () -> assertEquals(81993226, xmlHierarchy.getId()),
                () -> assertEquals(1447184, xmlHierarchy.getObjectId()),
                () -> assertEquals(0, xmlHierarchy.getParentObjId()),
                () -> assertEquals(3982063, xmlHierarchy.getChangeId()),
                () -> assertEquals(0, xmlHierarchy.getPrevId()),
                () -> assertEquals(0, xmlHierarchy.getNextId()),
                () -> assertEquals(LocalDate.parse("1900-01-01"), xmlHierarchy.getUpdateDate()),
                () -> assertEquals(LocalDate.parse("1900-01-01"), xmlHierarchy.getStartDate()),
                () -> assertEquals(LocalDate.parse("2079-06-06"), xmlHierarchy.getEndDate()),
                () -> assertTrue(xmlHierarchy.isActive())
        );
    }

    @Test
    void readAddresses_shouldReadAddressObjectsFromFile() {
        final XmlAddresses xmlAddresses = addressReader
                .readAddresses(
                        XmlAddresses.class,
                        testResourcesPath + "AS_ADDR_OBJ.XML"
                );

        assertNotNull(xmlAddresses);
        assertFalse(xmlAddresses.getObjects().isEmpty());
        assertNotNull(xmlAddresses.getObjects().get(0));

        final XmlAddress firstXmlAddress = xmlAddresses.getObjects().get(0);

        assertEquals(1745891, firstXmlAddress.getObjectId());
        assertEquals("Хатанзейского", firstXmlAddress.getName());
        assertEquals("ул", firstXmlAddress.getTypename());
    }

    @Test
    void readAddresses_shouldReadHierarchyObjectsFromFile() {
        final XmlHierarchies xmlHierarchies = addressReader
                .readAddresses(
                        XmlHierarchies.class,
                        testResourcesPath + "AS_ADM_HIERARCHY.XML"
                );

        assertNotNull(xmlHierarchies);
        assertFalse(xmlHierarchies.getItems().isEmpty());
        assertNotNull(xmlHierarchies.getItems().get(0));

        final XmlHierarchy firstXmlHierarchy = xmlHierarchies.getItems().get(0);

        assertEquals(1, firstXmlHierarchy.getObjectId());
        assertEquals(0, firstXmlHierarchy.getParentObjId());
        assertEquals("1900-01-01", firstXmlHierarchy.getStartDate().toString());
        assertEquals("2079-06-06", firstXmlHierarchy.getEndDate().toString());
    }

}