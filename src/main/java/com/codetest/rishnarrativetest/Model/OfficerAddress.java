package com.codetest.rishnarrativetest.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonPropertyOrder(value = {"premises", "locality","address_line_1","country","postal_code"})
public class OfficerAddress
{
    private String premises;

    @JsonProperty("postal_code")
    private String postalCode;

    private String country;

    private String locality;

    @JsonProperty("address_line_1")
    private String address;
}
