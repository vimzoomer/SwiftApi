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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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
				.swiftCode("ABC123")
				.address("123 Bank St")
				.bankName("Bank Corp")
				.countryISO2("US")
				.countryName("United States")
				.isHeadquarter(true)
				.build();
	}

	@Test
	void getSwiftCode_shouldReturnSwiftCode_whenFound() {
		when(repository.findById("ABC123")).thenReturn(Optional.of(swiftCode));

		SwiftCode result = service.getSwiftCode("ABC123");

		assertNotNull(result);
		assertEquals("ABC123", result.getSwiftCode());
		verify(repository, times(1)).findById("ABC123");
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
	void deleteSwiftCode_shouldCallDeleteById() {
		service.deleteSwiftCode("ABC123");

		verify(repository, times(1)).deleteById("ABC123");
	}
}

