package org.example.exception;

import jakarta.xml.bind.JAXBException;

public class FileReadException extends RuntimeException {

    public FileReadException(JAXBException e) {
    }

}
