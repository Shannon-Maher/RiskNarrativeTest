package com.codetest.rishnarrativetest;

import com.codetest.rishnarrativetest.Model.CompanyOfficer;
import com.codetest.rishnarrativetest.Model.CompanyOfficers;
import com.codetest.rishnarrativetest.Model.OfficerAddress;
import com.codetest.rishnarrativetest.Services.CompaniesService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class CompaniesServicesTest {

    @Mock
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;
    private ObjectMapper mapper = new ObjectMapper();

    @InjectMocks
    CompaniesService companiesService;


    private final String SEARCH_COMPANIES = "https://exercise.trunarrative.cloud/TruProxyAPI/rest/Companies/v1/Search?Query=";
    private final String SEARCH_COMPANIES_OFFICERS = "https://exercise.trunarrative.cloud/TruProxyAPI/rest/Companies/v1/Officers?CompanyNumber=";
    private final String API_KEY = System.getenv("x-api-key");


    @BeforeEach
    public void init() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

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

    @Test
    public void testOfficersApi() {

        String url = SEARCH_COMPANIES_OFFICERS+"06500244";

        CompanyOfficers companyOfficers = new CompanyOfficers();
        CompanyOfficer companyOfficer = new CompanyOfficer();

        companyOfficer.setName("Fred Bloggs");
        companyOfficer.setOfficerRole("Software Engineer");
        companyOfficer.setAppointedOn(LocalDate.now());

        OfficerAddress officerAddress = new OfficerAddress();
        officerAddress.setAddress("123 Bloggs Street");
        officerAddress.setPostalCode("M1 11A");
        officerAddress.setPremises("something");
        officerAddress.setCountry("UK");

        companyOfficer.setAddress(officerAddress);
        companyOfficers.setItems(new ArrayList<>());
        companyOfficers.getItems().add(companyOfficer);

           Mockito
                   .when(restTemplate.exchange
                        (Mockito.eq(url), Mockito.eq(HttpMethod.GET), Mockito.eq(getHttpEntity()),
                                Mockito.eq(CompanyOfficers.class)).getBody())
                           .thenReturn(companyOfficers);


        assertTrue(Optional.ofNullable(companyOfficers).isPresent());

        assertThat(companyOfficers.getItems()).isNotEmpty();
    }


}
