package com.codetest.rishnarrativetest.PayLoad;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchRequest {
    private String companyName;
    private String companyNumber;
    private Boolean activeCompany;
}
