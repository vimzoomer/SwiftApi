package com.example.swiftapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SwiftCodeRequest {

    @NotBlank(message = "Swift code is required")
    private String swiftCode;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "Bank name is required")
    private String bankName;

    @NotBlank(message = "Country ISO2 code is required")
    private String countryISO2;

    @NotBlank(message = "Country name is required")
    private String countryName;

    @NotNull(message = "isHeadquarter flag is required")
    private Boolean isHeadquarter;
}


