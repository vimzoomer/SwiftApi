package com.example.swiftapi.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "swift_codes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SwiftCode {
    @Id
    private String swiftCode;

    private String address;
    private String bankName;
    private String countryISO2;
    private String countryName;
    private boolean isHeadquarter;
}
