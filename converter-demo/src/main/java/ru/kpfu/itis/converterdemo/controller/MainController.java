package ru.kpfu.itis.converterdemo.controller;

import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    public ResponseEntity post(@RequestBody Root root) {
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
                    return ResponseEntity
                            .status(HttpStatus.BAD_REQUEST)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body("data isn't correct, please check your xml/json for mistakes");

                }
            } catch (IOException | DocumentException e) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("data isn't correct, please check your xml/json for mistakes");

            }
        } else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("data isn't correct, please check your xml/json for mistakes in types");
        }
    }
}