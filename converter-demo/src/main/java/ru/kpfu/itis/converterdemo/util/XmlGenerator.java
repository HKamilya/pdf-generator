package ru.kpfu.itis.converterdemo.util;

import ru.kpfu.itis.converterdemo.entity.*;
import ru.kpfu.itis.converterdemo.util.DataCreator;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.FileOutputStream;


public class XmlGenerator {
    public static void main(String[] args) throws Exception {
        JAXBContext contextObj = JAXBContext.newInstance(Root.class);

        Marshaller marshallerObj = contextObj.createMarshaller();
        marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        DataCreator dataCreator = new DataCreator();

        marshallerObj.marshal(dataCreator.generateData(), new FileOutputStream("pdf.xml"));

    }
}
