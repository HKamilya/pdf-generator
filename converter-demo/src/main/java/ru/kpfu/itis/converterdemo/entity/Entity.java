package ru.kpfu.itis.converterdemo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.PastOrPresent;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@XmlAccessorType(XmlAccessType.FIELD)
public class Entity {
    @PastOrPresent(message = "not correct date")
    private Date formed;
    @PastOrPresent(message = "not correct date")
    private Date formalized;
    @PastOrPresent(message = "not correct date")
    private Date credited;
    @Valid
    private Comment comment;
    private Employee employee;
    private String ipAddress;

}
