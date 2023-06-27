package com.revo.ug.dto.request;

import java.time.LocalDate;
import java.util.List;

public record InvoiceRequest(

        List<ComputerRequest> computers,
        LocalDate postingDate

) {
}
