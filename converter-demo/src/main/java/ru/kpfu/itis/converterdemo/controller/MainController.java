package ru.kpfu.itis.converterdemo.controller;

import com.itextpdf.text.DocumentException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.converterdemo.entity.Root;
import ru.kpfu.itis.converterdemo.service.ConvertingService;

import javax.validation.Valid;
import java.io.*;


@RestController
@RequestMapping("/pdf")
public class MainController {

    private final ConvertingService convertingService;

    public MainController(ConvertingService convertingService) {
        this.convertingService = convertingService;
    }


    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity post(@Valid @RequestBody Root root) throws IOException {
        boolean valid = convertingService.checkValidity(root);
        if (valid) {
            String name = null;
            File file = null;
            InputStreamResource resource = null;
            try {
                name = convertingService.createPdf(root.getPdfs());

                String FILE = "data/";
                file = new File(FILE + name);

                try {

                    resource = new InputStreamResource(new FileInputStream(file));
                    HttpHeaders headers = new HttpHeaders();
                    headers.add("content-disposition", "inline;filename=" + name);

                    return ResponseEntity.ok()
                            .headers(headers)
                            .contentLength(file.length())
                            .contentType(MediaType.APPLICATION_PDF)
                            .body(resource);
                } catch (FileNotFoundException e) {
                    throw new FileNotFoundException();
                }
            } catch (IOException | DocumentException e) {
                throw new IOException(e);
            }
        } else {
            throw new NullPointerException();
        }
    }
}