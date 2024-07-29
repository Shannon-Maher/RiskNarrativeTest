package com.codetest.rishnarrativetest.Controllers;

import com.codetest.rishnarrativetest.Model.Companies;
import com.codetest.rishnarrativetest.Model.Company;
import com.codetest.rishnarrativetest.Model.CompanyOfficer;
import com.codetest.rishnarrativetest.Model.CompanyOfficers;
import com.codetest.rishnarrativetest.PayLoad.SearchRequest;
import com.codetest.rishnarrativetest.Services.CompaniesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
public class CompanyController {

    @Autowired
    CompaniesService companiesService;

    @GetMapping("/company")
    public ResponseEntity<Companies> getCompanyByNumber(@RequestBody SearchRequest searchRequest){

        Optional<String> companyName = Optional.ofNullable(searchRequest.getCompanyName());
        Optional<String> companyNumber = Optional.ofNullable(searchRequest.getCompanyNumber());
        Optional<Boolean> activeCompany = Optional.ofNullable(searchRequest.getActiveCompany());

        if(companyNumber.isPresent() || companyName.isPresent()){
            return companiesService.getCompanies(companyNumber,companyName,activeCompany);
        }
        else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid search request");
        }

    }



}