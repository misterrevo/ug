package com.revo.ug.dto.client;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

public record Rate(

        @NotBlank
        String no,
        @NotNull
        Date effectiveDate,
        double mid

) {
}
