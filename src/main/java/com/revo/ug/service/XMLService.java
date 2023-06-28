package com.revo.ug.service;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.revo.ug.dto.response.InvoiceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.UUID;

@Service
@Slf4j
public class XMLService {

    public void saveToXMLFile(final InvoiceResponse response) {
        log.info("saveToXMLFile() - response = {}", response);
        try {
            final String uuid = UUID.randomUUID().toString();
            final XmlMapper xmlMapper = new XmlMapper();
            final String jarLocation = InvoiceService.class.getProtectionDomain().getCodeSource().getLocation()
                    .toURI().getPath().toString();
            xmlMapper.writeValue(new File(jarLocation+uuid+".xml"), response);
            final File file = new File(jarLocation+uuid+".xml");
            file.createNewFile();
        } catch (Exception exception){
            log.error("saveToXMLFile() - error", exception);
        }
    }
}
