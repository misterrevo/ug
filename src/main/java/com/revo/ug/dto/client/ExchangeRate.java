package com.revo.ug.dto.client;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public record ExchangeRate(

        @NotBlank
        String table,
        @NotBlank
        String currency,
        @NotBlank
        String code,
        @NotNull
        List<Rate> rates

) {
}
