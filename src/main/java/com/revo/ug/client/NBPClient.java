package com.revo.ug.client;

import com.revo.ug.dto.client.ExchangeRate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class NBPClient {

    private static final String BASE_URL = "http://api.nbp.pl/api/exchangerates/rates/a/";

    private final WebClient client;

    public ExchangeRate getMidCurrency(final String code, final LocalDate date){

        final String URL = BASE_URL+code+"/"+date.toString();

        log.info("getMidCurrency() - URL = {}", URL);

        final ExchangeRate response = client.get()
                .uri(URL)
                .retrieve()
                .bodyToMono(ExchangeRate.class)
                .block();

        log.info("getMidCurrency() - response = {}", response);

        return response;
    }
}
