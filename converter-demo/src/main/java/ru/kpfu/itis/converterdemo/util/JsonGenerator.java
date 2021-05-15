package ru.kpfu.itis.converterdemo.util;

import com.google.gson.Gson;
import ru.kpfu.itis.converterdemo.util.DataCreator;

import java.io.FileWriter;
import java.io.IOException;


public class JsonGenerator {
    public static void main(String[] args) throws IOException {
        FileWriter writer = new FileWriter("pdf.json");
        DataCreator dataCreator = new DataCreator();
        writer.write(new Gson().toJson(dataCreator.generateData()));
    }
}
