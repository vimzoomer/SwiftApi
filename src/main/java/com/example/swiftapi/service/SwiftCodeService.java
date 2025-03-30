package com.example.swiftapi.service;

import com.example.swiftapi.dto.SwiftCodeRequest;
import com.example.swiftapi.model.SwiftCode;
import com.example.swiftapi.repository.SwiftCodeRepository;
import org.springframework.stereotype.Service;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.io.Reader;


import java.util.ArrayList;
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

    public void addSwiftCodesFromCSV(MultipartFile file) {
        try {
            Reader reader = new InputStreamReader(file.getInputStream());
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader("swiftCode", "address", "bankName", "countryISO2", "countryName", "isHeadquarter"));
            List<SwiftCode> swiftCodes = new ArrayList<>();

            for (CSVRecord record : csvParser) {
                String swiftCode = record.get("swiftCode");
                String address = record.get("address");
                String bankName = record.get("bankName");
                String countryISO2 = record.get("countryISO2");
                String countryName = record.get("countryName");
                boolean isHeadquarter = Boolean.parseBoolean(record.get("isHeadquarter"));

                SwiftCode code = SwiftCode.builder()
                        .swiftCode(swiftCode)
                        .address(address)
                        .bankName(bankName)
                        .countryISO2(countryISO2)
                        .countryName(countryName)
                        .isHeadquarter(isHeadquarter)
                        .build();

                swiftCodes.add(code);
            }

            repository.saveAll(swiftCodes);
        } catch (Exception e) {
            throw new RuntimeException("Failed to process CSV file", e);
        }
    }
}
