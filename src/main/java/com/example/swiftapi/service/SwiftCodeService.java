package com.example.swiftapi.service;

import com.example.swiftapi.dto.SwiftCodeRequest;
import com.example.swiftapi.model.SwiftCode;
import com.example.swiftapi.repository.SwiftCodeRepository;
import org.springframework.stereotype.Service;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                .countryISO2(request.getCountryISO2() != null ? request.getCountryISO2().toUpperCase() : null)
                .townName(request.getTownName() != null ? request.getTownName().toUpperCase() : null)
                .countryName(request.getCountryName() != null ? request.getCountryName().toUpperCase() : null)
                .isHeadquarter(request.getSwiftCode() != null && request.getSwiftCode().endsWith("XXX"))
                .build();
        repository.save(swiftCode);
    }

    public void deleteSwiftCode(String swiftCode) {
        repository.deleteById(swiftCode);
    }

    public void addSwiftCodesFromCSV(MultipartFile file) throws IOException {
        List<SwiftCode> swiftCodes = new ArrayList<>();
        Map<String, String> headquarters = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withHeader("COUNTRY ISO2 CODE", "SWIFT CODE", "CODE TYPE", "NAME", "ADDRESS", "TOWN NAME", "COUNTRY NAME", "TIME ZONE")
                    .withSkipHeaderRecord()
                    .parse(reader);

            for (CSVRecord record : records) {
                String swiftCodeStr = record.get("SWIFT CODE");
                String bankName = record.get("NAME");
                String address = record.get("ADDRESS");
                String countryISO2 = record.get("COUNTRY ISO2 CODE");
                String countryName = record.get("COUNTRY NAME");
                String townName = record.get("TOWN NAME");
                String timeZone = record.get("TIME ZONE");

                boolean isHeadquarter = swiftCodeStr.endsWith("XXX");

                if (!isHeadquarter) {
                    String headquarterSwiftCode = headquarters.get(swiftCodeStr.substring(0, 8));
                    if (headquarterSwiftCode != null) {
                        isHeadquarter = true;
                    }
                } else {
                    headquarters.put(swiftCodeStr.substring(0, 8), swiftCodeStr);
                }

                SwiftCode swiftCode = SwiftCode.builder()
                        .swiftCode(swiftCodeStr)
                        .address(address)
                        .bankName(bankName)
                        .countryISO2(countryISO2)
                        .countryName(countryName)
                        .townName(townName)
                        .timeZone(timeZone)
                        .isHeadquarter(isHeadquarter)
                        .build();

                swiftCodes.add(swiftCode);
            }

            repository.saveAll(swiftCodes);
        }
    }
}
