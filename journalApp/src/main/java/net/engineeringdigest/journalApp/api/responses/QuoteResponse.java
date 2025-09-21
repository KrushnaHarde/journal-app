package net.engineeringdigest.journalApp.api.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true) // in case API sends extra fields
@Getter
@Setter
public class QuoteResponse {
    @JsonProperty("quote")
    private String quote;
    private String author;
    private String category;
}