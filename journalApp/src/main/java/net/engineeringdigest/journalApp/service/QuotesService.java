package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.api.responses.QuoteResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Objects;

@Component
public class QuotesService {

    @Value("${quotes.api.key}")
    private String apikey;

    @Value("${quotes.api.url}")
    private String url;

    @Autowired
    private RestTemplate restTemplate;

    public String getQuote(){
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Api-Key", apikey);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<QuoteResponse[]> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                QuoteResponse[].class
        );

        QuoteResponse[] quoteResponse = response.getBody();
        if (quoteResponse == null) {
            return "";
        }

        String quote = quoteResponse[0].getQuote();
        String author = quoteResponse[0].getAuthor();

        return  quote + "\" by " + author;
    }
}
