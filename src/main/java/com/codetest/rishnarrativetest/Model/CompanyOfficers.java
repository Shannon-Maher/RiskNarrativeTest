package com.codetest.rishnarrativetest.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class CompanyOfficers
{
    @JsonProperty("items")
    ArrayList<CompanyOfficer> items;
}
