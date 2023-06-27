package com.revo.ug.controller;

import com.revo.ug.dto.request.InvoiceRequest;
import com.revo.ug.dto.request.Sort;
import com.revo.ug.dto.response.InvoiceResponse;
import com.revo.ug.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    @GetMapping("/{name}/{sort}")
    public InvoiceResponse findByContainsName(@PathVariable final String name, @PathVariable final Sort sort) {
        log.info("findByContainsName() - name = {}", name);

        final InvoiceResponse response = invoiceService.findByContainsName(name, sort);

        log.info("findByContainsName() - response = {}", response);

        return response;
    }

    @GetMapping("/{start}/{end}/{sort}")
    public InvoiceResponse findPostingDateBetween(@PathVariable final String start, @PathVariable final String end, @PathVariable final Sort sort) {
        log.info("findPostingDateBetween() - start = {} - end = {}", start, end);

        final InvoiceResponse response = invoiceService.findPostingDateBetween(LocalDate.parse(start), LocalDate.parse(end), sort);

        log.info("findPostingDateBetween() - response = {}", response);

        return response;
    }

    @PostMapping
    public InvoiceResponse processInvoice(@RequestBody final InvoiceRequest request) {
        log.info("processInvoice() - request = {}", request);

        final InvoiceResponse response = invoiceService.processInvoice(request);

        log.info("processInvoice() - response = {}", response);

        return response;
    }

}
