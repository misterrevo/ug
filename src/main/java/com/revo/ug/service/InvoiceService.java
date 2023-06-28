package com.revo.ug.service;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.revo.ug.client.NBPClient;
import com.revo.ug.dto.client.ExchangeRate;
import com.revo.ug.dto.request.ComputerRequest;
import com.revo.ug.dto.request.InvoiceRequest;
import com.revo.ug.dto.request.Sort;
import com.revo.ug.dto.response.ComputerResponse;
import com.revo.ug.dto.response.InvoiceResponse;
import com.revo.ug.entity.Computer;
import com.revo.ug.mapper.ComputerMapper;
import com.revo.ug.repository.ComputerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class InvoiceService {

    private static final String CODE = "USD";

    private final NBPClient nbpClient;
    private final ComputerRepository computerRepository;
    private final ComputerMapper computerMapper;
    private final XMLService xmlService;

    public InvoiceResponse findByContainsName(final String name, final Sort sort) {
        log.info("findByContainsName() - name = {}", name);

        final List<Computer> computers = computerRepository.findByNameContains(name);

        final List<ComputerResponse> computerResponses = computers.stream()
                .map(computerMapper::toResponse)
                .collect(Collectors.toList());

        sortByDetails(sort, computerResponses);

        return new InvoiceResponse(computerResponses);
    }

    private void sortByDetails(final Sort sort, final List<ComputerResponse> responses) {
        Collections.sort(responses, new Comparator<ComputerResponse>() {
            @Override
            public int compare(final ComputerResponse o1, final ComputerResponse o2) {
                if (sort.equals(Sort.NAME)) {
                    return o1.name().compareTo(o2.name());
                } else {
                    return o1.postingDate().compareTo(o2.postingDate());
                }
            }
        });
    }

    public InvoiceResponse findPostingDateBetween(final LocalDate start, final LocalDate end, final Sort sort) {
        log.info("findBetweenPostingDate() - start = {} - end = {}", start, end);

        final List<Computer> computers = computerRepository.findByPostingDateBetween(start, end);

        final List<ComputerResponse> computerResponses = computers.stream()
                .map(computerMapper::toResponse)
                .collect(Collectors.toList());

        sortByDetails(sort, computerResponses);

        return new InvoiceResponse(computerResponses);
    }

    public InvoiceResponse processInvoice(final InvoiceRequest request) {
        log.info("processInvoice() - request = {}", request);

        final ExchangeRate exchangeRate = nbpClient.getMidCurrency(CODE, request.postingDate());

        final List<ComputerResponse> computers = request.computers()
                .stream()
                .map(target -> saveAndReturnComputer(target, exchangeRate, request))
                .collect(Collectors.toList());

        final InvoiceResponse response = new InvoiceResponse(computers);

        xmlService.saveToXMLFile(response);

        return response;
    }



    private ComputerResponse saveAndReturnComputer(final ComputerRequest target, final ExchangeRate exchangeRate, final InvoiceRequest request) {
        final Computer computer = buildComputer(target, exchangeRate, request);
        final Computer savedComputer = computerRepository.save(computer);
        return computerMapper.toResponse(savedComputer);
    }

    private Computer buildComputer(ComputerRequest target, ExchangeRate exchangeRate, InvoiceRequest request) {
        return Computer.builder()
                .costPLN(exchangeRate.rates().get(0).mid() * target.costUSD())
                .costUSD(target.costUSD())
                .postingDate(request.postingDate())
                .name(target.name())
                .build();
    }


}
