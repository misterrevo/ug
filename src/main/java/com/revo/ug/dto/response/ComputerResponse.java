package com.revo.ug.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;


public record ComputerResponse (

        @JsonProperty("nazwa")
        String name,
        @JsonProperty("data_ksiegowania")
        String postingDate,
        @JsonProperty("koszt_USD")
        double costUSD,
        @JsonProperty("koszt_PLN")
        double costPLN
        
){ }
