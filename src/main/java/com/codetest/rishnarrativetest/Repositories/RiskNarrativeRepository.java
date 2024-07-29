package com.codetest.rishnarrativetest.Repositories;

import com.codetest.rishnarrativetest.Model.Company;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface RiskNarrativeRepository extends MongoRepository<Company, String > {

    @Query("{ 'companyNumber' : ?0 }")
    List<Company> findCompanyByNumber(String companyNumber);

    @Query("{ 'companyName' : { $regex: ?0, $options: 'i' } }")
    List<Company> findCompanyByName(String companyName);
}
