package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.api.responses.QuoteResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
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

    @Autowired
    private RedisService redisService;


    public String getQuote(){

        QuoteResponse cachedQoteResponse = redisService.get("quote_for_" + LocalDate.now(), QuoteResponse.class);
        if(cachedQoteResponse != null){
            return cachedQoteResponse.getQuote() + " ... by " + cachedQoteResponse.getAuthor();
        }

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

        redisService.set("quote_for_" + LocalDate.now(), quoteResponse[0], 24);
        String quote = quoteResponse[0].getQuote();
        String author = quoteResponse[0].getAuthor();

        return  quote + "\" by " + author;
    }
}
