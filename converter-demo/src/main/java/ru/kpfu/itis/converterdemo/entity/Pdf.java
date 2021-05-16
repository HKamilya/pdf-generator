package ru.kpfu.itis.converterdemo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@XmlAccessorType(XmlAccessType.FIELD)
public class Pdf {
    @NotEmpty
    private String number;
    @Valid
    private Header header;
    @PastOrPresent(message = "wrong date")
    @NotNull(message = "it's not a date")
    private Date date;
    @XmlElementWrapper(name = "entities")
    @XmlElement(name = "entity")
    @Valid
    private List<Entity> entities;
}
