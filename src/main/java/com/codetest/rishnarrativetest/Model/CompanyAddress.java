package com.codetest.rishnarrativetest.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonPropertyOrder(value = {"locality", "postal_code","premises","address_line_1","country"})
public class CompanyAddress {

    private String locality;

    @JsonProperty("postal_code")
    private String postalCode;

    private String premises;

    @JsonProperty("address_line_1")
    private String addressLine;

    private String country;

}
