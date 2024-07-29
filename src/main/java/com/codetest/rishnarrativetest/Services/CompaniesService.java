package com.codetest.rishnarrativetest.Services;


import com.codetest.rishnarrativetest.Model.Companies;
import com.codetest.rishnarrativetest.Model.Company;
import com.codetest.rishnarrativetest.Model.CompanyOfficer;
import com.codetest.rishnarrativetest.Model.CompanyOfficers;
import com.codetest.rishnarrativetest.Repositories.RiskNarrativeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
public class CompaniesService {

    private final String SEARCH_COMPANIES = "https://exercise.trunarrative.cloud/TruProxyAPI/rest/Companies/v1/Search?Query=";
    private final String SEARCH_COMPANIES_OFFICERS = "https://exercise.trunarrative.cloud/TruProxyAPI/rest/Companies/v1/Officers?CompanyNumber=";
    private final String API_KEY = System.getenv("x-api-key");

    private final RestTemplate restTemplate = new RestTemplate();

    private static final Logger log = LoggerFactory.getLogger(CompaniesService.class);

    @Autowired
    RiskNarrativeRepository riskNarrativeRepository;

    private HttpEntity<String> getHttpEntity(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("x-api-key",API_KEY);
        headers.add("User-Agent","PostmanRuntime/7.40.0");
        headers.add("Accept","*/*");
        headers.add("Accept-Encoding","gzip, deflate, br");
        headers.add("Connection","keep-alive");
        return new HttpEntity<>("body", headers);
    }

    public ResponseEntity<Companies> getCompanies(Optional<String> companyNumber,Optional<String> companyName,Optional<Boolean> activeCompany) {


        RestTemplate restTemplate = new RestTemplate();
        String search;
        boolean searchNumber = false;

        Optional<List<Company>> searchResults = Optional.empty();
        log.debug("Determining if company number or company name is used for the search");
        if (companyNumber.isPresent() && companyName.isPresent()) {
            log.debug("company number and company name is present, therefore company number is selected");
            search = companyNumber.get();
            searchResults = Optional.ofNullable(riskNarrativeRepository.findCompanyByNumber(search));
            searchNumber = true;
        } else {
            if (companyName.isPresent()) {
                log.debug("Company Name is only present and therefore Company Name is selected");
                search = companyName.get();
            } else {
                if (companyNumber.isPresent()) {
                    log.debug("Company Number is only present and therefore Company Number is selected");
                    search = companyNumber.get();
                    searchResults = Optional.ofNullable(riskNarrativeRepository.findCompanyByNumber(search));
                    searchNumber = true;

                } else {
                    search = "";
                }
            }

        }

            String url = SEARCH_COMPANIES + search;
            List<Company> filteredCompanies;

        log.error(url);
            Company foundCompany = new Company();
            Companies companies = new Companies();

            if (searchResults.orElse(new ArrayList<>()).isEmpty() || !searchNumber) {
                companies = restTemplate.exchange(url, HttpMethod.GET, getHttpEntity(), Companies.class, HttpStatus.OK).getBody();

                if(companies != null && activeCompany.orElse(false)){
                     filteredCompanies =  companies.getItems()  // or .orElse(Collections.emptyList())
                            .stream()
                            .filter(c -> c.getCompanyStatus().equals("active"))
                            .toList();
                     companies.getItems().addAll(filteredCompanies);
                     companies.setTotalResults(companies.getItems().size());
                }



                if (Optional.ofNullable(companies).isPresent()) {
                    if (!companies.getItems().isEmpty()) {
                        companies.getItems()
                                .forEach(c -> {
                            c.setOfficers(getCompanyOfficers(c.getCompanyNumber()));
                        });



                        riskNarrativeRepository.saveAll(companies.getItems());

                    }
                }
            } else {


                if(searchNumber) {
                    List<Company> foundCompanies;
                    companies = new Companies();
                    foundCompanies = riskNarrativeRepository.findCompanyByNumber(search);
                    companies.getItems().addAll(foundCompanies);
                    companies.setTotalResults(companies.getItems().size());
                }


            }
            return new ResponseEntity<>(companies, HttpStatus.OK);


    }

    public ArrayList<CompanyOfficer> getCompanyOfficers(String search) {
        RestTemplate restTemplate = new RestTemplate();

        log.error("Semding api url = " + SEARCH_COMPANIES + search);

        String url = SEARCH_COMPANIES_OFFICERS+search;

        log.error(url);

        CompanyOfficers companyOfficers = restTemplate.exchange(url, HttpMethod.GET, getHttpEntity(), CompanyOfficers.class, HttpStatus.OK).getBody();
        ArrayList<CompanyOfficer> officerList = companyOfficers != null ? companyOfficers.getItems() : null;
        ArrayList<CompanyOfficer>  filteredOfficers = new ArrayList<>();
        if(!(officerList == null)){

            filteredOfficers.addAll(officerList.stream().filter(e -> e.getResignedOn() == null).toList());
        }
        return filteredOfficers;
    }
}
