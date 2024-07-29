package com.codetest.rishnarrativetest.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDate;
import java.util.ArrayList;

@Getter
@Setter
@JsonPropertyOrder(value = {"company_number", "company_type","title","company_status","date_of_creation","address","officers"})
public class Company {

    @JsonProperty("company_number")
    @Indexed(unique = true)
    private String companyNumber;

    @JsonProperty("company_type")
    private String companyType;

    @JsonProperty("title")
    private String companyName;

    @JsonProperty("company_status")
    private String companyStatus;

    @JsonProperty("date_of_creation")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate dateOfCreation;

    private CompanyAddress address;

    @Setter
    private ArrayList<CompanyOfficer> officers;


}
