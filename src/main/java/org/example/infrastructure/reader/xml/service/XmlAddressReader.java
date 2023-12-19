package org.example.infrastructure.reader.xml.service;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import lombok.extern.slf4j.Slf4j;
import org.example.exception.FileReadException;
import org.example.infrastructure.reader.AddressReader;

import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
public class XmlAddressReader implements AddressReader {
    @Override
    public <T> T readAddresses(Class<T> type, String fileName) {
        try {
            return JAXBContext
                    .newInstance(type)
                    .createUnmarshaller()
                    .unmarshal(getSource(fileName), type)
                    .getValue();
        } catch (JAXBException e) {
            log.debug("Message: {}\n{}",
                    e.getMessage(),
                    Arrays.stream(e.getStackTrace())
                            .map(String::valueOf)
                            .collect(Collectors.joining("\n"))
            );
            throw new FileReadException(e);
        }
    }

    private StreamSource getSource(String fileName) {
        final File file = new File(fileName);
        return new StreamSource(file);
    }
}
