package com.revo.ug.service;

import com.revo.ug.dto.request.ComputerRequest;
import com.revo.ug.dto.request.InvoiceRequest;
import com.revo.ug.dto.request.Sort;
import com.revo.ug.dto.response.InvoiceResponse;
import com.revo.ug.entity.Computer;
import com.revo.ug.repository.ComputerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class InvoiceServiceTest {

    private static final String NAME_1 = "komputer 1";
    private static final String NAME_2 = "komputer 2";
    private static final String NAME_3 = "komputer 3";
    private static final String DATE_1 = "2023-01-03";
    private static final String DATE_2 = "2023-01-10";
    @Autowired
    private InvoiceService underTest;

    @Autowired
    private ComputerRepository computerRepository;

    @Test
    void findByContainsName() {
        //given
        computerRepository.save(Computer.builder()
                        .name(NAME_1)
                        .costUSD(100.00)
                        .costPLN(100.00)
                        .postingDate(LocalDate.parse(DATE_1))
                .build());
        //when
        final InvoiceResponse response = underTest.findByContainsName(NAME_1, Sort.NAME);
        //then
        assertThat(response.computers().get(0).name()).isEqualTo(NAME_1);
        assertThat(response.computers().get(0).costPLN()).isEqualTo(100.00);
        assertThat(response.computers().get(0).costUSD()).isEqualTo(100.00);
        assertThat(response.computers().get(0).postingDate()).isEqualTo(DATE_1);
    }

    @Test
    void findPostingDateBetween() {
        //given
        computerRepository.save(Computer.builder()
                .name(NAME_1)
                .costUSD(100.00)
                .costPLN(100.00)
                .postingDate(LocalDate.parse(DATE_1))
                .build());
        //when
        final InvoiceResponse response = underTest.findPostingDateBetween(LocalDate.parse(DATE_1), LocalDate.parse(DATE_1), Sort.NAME);
        //then
        assertThat(response.computers().get(0).name()).isEqualTo(NAME_1);
        assertThat(response.computers().get(0).costPLN()).isEqualTo(100.00);
        assertThat(response.computers().get(0).costUSD()).isEqualTo(100.00);
        assertThat(response.computers().get(0).postingDate()).isEqualTo(DATE_1);
    }

    @Test
    void processInvoice() {
        //given
        final InvoiceRequest invoiceRequest_1 = new InvoiceRequest(
                List.of(
                        new ComputerRequest(
                                NAME_1,
                                345.00
                        ),
                        new ComputerRequest(
                                NAME_2,
                                543.00
                        ),
                        new ComputerRequest(
                                NAME_3,
                                346.00
                        )
                ),
                LocalDate.parse(DATE_1)
        );
        final InvoiceRequest invoiceRequest_2 = new InvoiceRequest(
                List.of(
                        new ComputerRequest(
                                NAME_1,
                                345.00
                        ),
                        new ComputerRequest(
                                NAME_2,
                                543.00
                        ),
                        new ComputerRequest(
                                NAME_3,
                                346.00
                        )
                ),
                LocalDate.parse(DATE_2)
        );
        //when
        final InvoiceResponse response_1 = underTest.processInvoice(invoiceRequest_1);
        final InvoiceResponse response_2 = underTest.processInvoice(invoiceRequest_2);
        //then
        assertThat(response_1.computers().get(0).name()).isEqualTo(NAME_1);
        assertThat(response_1.computers().get(0).costPLN()).isEqualTo(1530.8684999999998);
        assertThat(response_1.computers().get(0).costUSD()).isEqualTo(345.00);
        assertThat(response_1.computers().get(0).postingDate()).isEqualTo(DATE_1);
        assertThat(response_1.computers().get(1).name()).isEqualTo(NAME_2);
        assertThat(response_1.computers().get(1).costPLN()).isEqualTo(2409.4539);
        assertThat(response_1.computers().get(1).costUSD()).isEqualTo(543.00);
        assertThat(response_1.computers().get(1).postingDate()).isEqualTo(DATE_1);
        assertThat(response_1.computers().get(2).name()).isEqualTo(NAME_3);
        assertThat(response_1.computers().get(2).costPLN()).isEqualTo(1535.3057999999999);
        assertThat(response_1.computers().get(2).costUSD()).isEqualTo(346.00);
        assertThat(response_1.computers().get(2).postingDate()).isEqualTo(DATE_1);

        assertThat(response_2.computers().get(0).name()).isEqualTo(NAME_1);
        assertThat(response_2.computers().get(0).costPLN()).isEqualTo(1508.34);
        assertThat(response_2.computers().get(0).costUSD()).isEqualTo(345.00);
        assertThat(response_2.computers().get(0).postingDate()).isEqualTo(DATE_2);
        assertThat(response_2.computers().get(1).name()).isEqualTo(NAME_2);
        assertThat(response_2.computers().get(1).costPLN()).isEqualTo(2373.996);
        assertThat(response_2.computers().get(1).costUSD()).isEqualTo(543.00);
        assertThat(response_2.computers().get(1).postingDate()).isEqualTo(DATE_2);
        assertThat(response_2.computers().get(2).name()).isEqualTo(NAME_3);
        assertThat(response_2.computers().get(2).costPLN()).isEqualTo(1512.712);
        assertThat(response_2.computers().get(2).costUSD()).isEqualTo(346.00);
        assertThat(response_2.computers().get(2).postingDate()).isEqualTo(DATE_2);
    }
}