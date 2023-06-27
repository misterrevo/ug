package com.revo.ug.dto.request;

import com.sun.istack.NotNull;

public record ComputerRequest(

        @NotNull
        String name,
        double costUSD

) {
}
