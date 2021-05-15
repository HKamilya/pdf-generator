package ru.kpfu.itis.converterdemo.util;

import ru.kpfu.itis.converterdemo.entity.*;

import java.util.ArrayList;
import java.util.List;

public class DataCreator {
    public Root generateData() {
        Header header = Header.builder()
                .countOfStudents("65")
                .institute("ИТИС (ИНН: 5038093740)")
                .login("Login")
                .number("32223")
                .type("TT")
                .build();
        Employee employee = Employee.builder()
                .fullname("Иванов Иван Иванович")
                .position("boss")
                .build();


        Employee employee1 = Employee.builder()
                .fullname("Пётр Петрович")
                .position("Студент второго курса ИТИС")
                .build();
        Entity entity;
        Created created = Created.builder()
                .date("25-09-2020")
                .time("13:46:26")
                .build();
        Comment comment = Comment.builder()
                .date("25-09-2020")
                .comment("Какой-нибудь длинный, а может " +
                        "быть не очень длинный текст, " +
                        "главное чтобы верстка не полетела")
                .version("5.15.0/3.3.26")
                .build();
        entity = Entity.builder()
                .comment(comment)
                .credited(created)
                .formalized(created)
                .formed(created)
                .ipAddress("192.168.0.15")
                .employee(employee).build();

        List<Entity> entities = new ArrayList<>();
        entities.add(entity);
        entities.add(entity);
        entities.add(entity);
        entities.add(entity);
        entities.add(entity);
        entities.add(entity);
        entities.add(entity);
        entities.add(entity);
        comment = Comment.builder()
                .date("25-09-2020")
                .comment("Не очень длинный текст")
                .version("5.15.0/3.3.26")
                .build();
        entity = Entity.builder()
                .comment(comment)
                .credited(created)
                .formalized(created)
                .formed(created)
                .ipAddress("192.168.0.15")
                .employee(employee1).build();
        entities.add(entity);
        entities.add(entity);
        entities.add(entity);

        Pdf pdf = Pdf.builder()
                .date("15:02:49 12.04.2021")
                .number("10323010/250920/0007140")
                .entities(entities)
                .header(header)
                .build();

        List<Pdf> pdfs = new ArrayList<>();
        pdfs.add(pdf);
        pdfs.add(pdf);

        return Root.builder().pdfs(pdfs).build();
    }
}