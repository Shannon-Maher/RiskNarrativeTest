package com.codetest.rishnarrativetest.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@JsonPropertyOrder(value = {"name", "officer_role","appointed_on","address"})
public class CompanyOfficer {

    private String name;

    @JsonProperty("officer_role")
    private String officerRole;

    @JsonProperty("appointed_on")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate appointedOn;

    @JsonProperty("resigned_on")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate resignedOn;

   @JsonProperty("address")
    private OfficerAddress address;
}
