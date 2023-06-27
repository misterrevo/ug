package com.revo.ug.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import java.util.List;

@JsonRootName("faktura")
public record InvoiceResponse (

        @JsonProperty("komputer")
        @JacksonXmlElementWrapper(useWrapping = false)
        List<ComputerResponse> computers

) { }
