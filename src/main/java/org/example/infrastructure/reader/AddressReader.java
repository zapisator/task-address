package org.example.infrastructure.reader;

public interface AddressReader {
   <T> T readAddresses(Class<T> type, String fileName);
}
