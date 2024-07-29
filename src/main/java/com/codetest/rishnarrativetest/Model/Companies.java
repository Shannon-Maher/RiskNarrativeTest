package com.codetest.rishnarrativetest.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.GeneratedValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Getter
@Setter
@Document(collection = "Companies")
@JsonPropertyOrder(value = {"total_results", "items"})
public class Companies {


    @Id
    @Field("_id")
    @JsonIgnore
    private String id;

    @JsonProperty("total_results")
    private  int totalResults = 0;

    private ArrayList<Company> items = new ArrayList<>();

}
