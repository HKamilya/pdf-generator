package ru.kpfu.itis.converterdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.converterdemo.entity.Root;
import ru.kpfu.itis.converterdemo.service.ConvertingService;

import java.io.*;


@RestController
public class MainController {

    private final ConvertingService convertingService;

    public MainController(ConvertingService convertingService) {
        this.convertingService = convertingService;
    }


    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> post(@RequestBody Root root) {

        System.out.println(root);
        String name = convertingService.createPdf(root.getPdfs());
        String FILE = "data/";
        File file = new File(FILE + name);
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-disposition", "inline;filename=" + name);

        InputStreamResource resource = null;
        try {
            resource = new InputStreamResource(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/pdf"))
                .body(resource);

    }
}