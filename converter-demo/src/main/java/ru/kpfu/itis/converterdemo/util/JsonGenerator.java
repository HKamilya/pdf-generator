package ru.kpfu.itis.converterdemo.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileWriter;
import java.io.IOException;


public class JsonGenerator {
    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        FileWriter writer = new FileWriter("examples/pdf.json");
        DataCreator dataCreator = new DataCreator();
        writer.write(objectMapper.writeValueAsString(dataCreator.generateData()));
    }
}
