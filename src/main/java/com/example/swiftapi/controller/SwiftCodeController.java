package com.example.swiftapi.controller;

import com.example.swiftapi.dto.SwiftCodeRequest;
import com.example.swiftapi.model.SwiftCode;
import com.example.swiftapi.service.SwiftCodeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/v1/swift-codes")
public class SwiftCodeController {
    private final SwiftCodeService service;

    public SwiftCodeController(SwiftCodeService service) {
        this.service = service;
    }

    @GetMapping("/{swiftCode}")
    public ResponseEntity<SwiftCode> getSwiftCode(@PathVariable String swiftCode) {
        SwiftCode swift = service.getSwiftCode(swiftCode);
        return (swift != null) ? ResponseEntity.ok(swift) : ResponseEntity.notFound().build();
    }

    @GetMapping("/country/{countryISO2}")
    public ResponseEntity<List<SwiftCode>> getSwiftCodesByCountry(@PathVariable String countryISO2) {
        return ResponseEntity.ok(service.getSwiftCodesByCountry(countryISO2));
    }

    @PostMapping
    public ResponseEntity<String> addSwiftCode(@Valid @RequestBody SwiftCodeRequest request) {
        service.addSwiftCode(request);
        return ResponseEntity.ok("Swift code added successfully.");
    }

    @DeleteMapping("/{swiftCode}")
    public ResponseEntity<String> deleteSwiftCode(@PathVariable String swiftCode) {
        service.deleteSwiftCode(swiftCode);
        return ResponseEntity.ok("Swift code deleted successfully.");
    }

    @PostMapping("/upload-csv")
    public ResponseEntity<String> uploadSwiftCodesFromCSV(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("No file uploaded.");
        }

        try {
            service.addSwiftCodesFromCSV(file);
            return ResponseEntity.ok("Swift codes successfully added from CSV file.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to process CSV file: " + e.getMessage());
        }
    }
}
