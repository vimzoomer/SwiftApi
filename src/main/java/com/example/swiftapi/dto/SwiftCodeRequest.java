package com.example.swiftapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SwiftCodeRequest {

    private String swiftCode;

    private String address;

    private String bankName;

    private String townName;

    private String timeZone;

    private String countryISO2;

    private String countryName;

    private Boolean isHeadquarter;
}


