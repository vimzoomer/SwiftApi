package com.example.swiftapi.service;

import com.example.swiftapi.dto.SwiftCodeRequest;
import com.example.swiftapi.model.SwiftCode;
import com.example.swiftapi.repository.SwiftCodeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SwiftCodeService {
    private final SwiftCodeRepository repository;

    public SwiftCodeService(SwiftCodeRepository repository) {
        this.repository = repository;
    }

    public SwiftCode getSwiftCode(String swiftCode) {
        return repository.findById(swiftCode).orElse(null);
    }

    public List<SwiftCode> getSwiftCodesByCountry(String countryISO2) {
        return repository.findByCountryISO2(countryISO2.toUpperCase());
    }

    public void addSwiftCode(SwiftCodeRequest request) {
        SwiftCode swiftCode = SwiftCode.builder()
                .swiftCode(request.getSwiftCode())
                .address(request.getAddress())
                .bankName(request.getBankName())
                .countryISO2(request.getCountryISO2().toUpperCase())
                .countryName(request.getCountryName().toUpperCase())
                .isHeadquarter(request.getIsHeadquarter())
                .build();
        repository.save(swiftCode);
    }

    public void deleteSwiftCode(String swiftCode) {
        repository.deleteById(swiftCode);
    }
}
