package com.example.swiftapi;

import com.example.swiftapi.dto.SwiftCodeRequest;
import com.example.swiftapi.model.SwiftCode;
import com.example.swiftapi.repository.SwiftCodeRepository;
import com.example.swiftapi.service.SwiftCodeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SwiftCodeServiceTest {

    @Mock
    private SwiftCodeRepository repository;

    @InjectMocks
    private SwiftCodeService service;

    private SwiftCode swiftCode;

    @BeforeEach
    void setUp() {
        swiftCode = SwiftCode.builder()
                .swiftCode("12345678XXX")
                .address("123 Bank St")
                .bankName("Bank Corp")
                .countryISO2("US")
                .countryName("United States")
                .isHeadquarter(true)
                .build();
    }

    @Test
    void getSwiftCode_existingCode_returnsSwiftCode() {
        when(repository.findById("12345678XXX")).thenReturn(Optional.of(swiftCode));

        SwiftCode result = service.getSwiftCode("12345678XXX");

        assertNotNull(result);
        assertEquals("12345678XXX", result.getSwiftCode());
        verify(repository, times(1)).findById("12345678XXX");
    }

    @Test
    void getSwiftCode_nonExistingCode_returnsNull() {
        when(repository.findById("12345678XXX")).thenReturn(Optional.empty());

        SwiftCode result = service.getSwiftCode("12345678XXX");

        assertNull(result);
        verify(repository, times(1)).findById("12345678XXX");
    }

    @Test
    void getSwiftCode_shouldReturnNull_whenNotFound() {
        when(repository.findById("XYZ999")).thenReturn(Optional.empty());

        SwiftCode result = service.getSwiftCode("XYZ999");

        assertNull(result);
        verify(repository, times(1)).findById("XYZ999");
    }

    @Test
    void addSwiftCode_shouldSaveSwiftCode() {
        SwiftCodeRequest request = new SwiftCodeRequest();
        request.setSwiftCode("XYZ999");
        request.setAddress("789 Street");
        request.setBankName("Test Bank");
        request.setCountryISO2("FR");
        request.setCountryName("France");
        request.setIsHeadquarter(false);

        service.addSwiftCode(request);

        verify(repository, times(1)).save(any(SwiftCode.class));
    }

    @Test
    void deleteSwiftCode_existingCode_deletesSwiftCode() {
        doNothing().when(repository).deleteById("12345678XXX");

        service.deleteSwiftCode("12345678XXX");

        verify(repository, times(1)).deleteById("12345678XXX");
    }

    @Test
    void addSwiftCodesFromCSV_validFile_savesSwiftCodes() throws IOException {
        String csvContent = "COUNTRY ISO2 CODE,SWIFT CODE,CODE TYPE,NAME,ADDRESS,TOWN NAME,COUNTRY NAME,TIME ZONE\n" +
                "US,12345678XXX,Type A,Bank A,Address A,New York,USA,EST\n";
        MultipartFile file = mock(MultipartFile.class);
        when(file.getInputStream()).thenReturn(new ByteArrayInputStream(csvContent.getBytes()));

        service.addSwiftCodesFromCSV(file);

        verify(repository, times(1)).saveAll(anyList());
    }

    @Test
    void deleteSwiftCode_shouldCallDeleteById() {
        service.deleteSwiftCode("12345678XXX");

        verify(repository, times(1)).deleteById("12345678XXX");
    }
}

