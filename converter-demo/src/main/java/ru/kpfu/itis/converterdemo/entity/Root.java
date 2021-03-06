package ru.kpfu.itis.converterdemo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.validation.Valid;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@XmlRootElement(name = "root")
@XmlAccessorType(XmlAccessType.FIELD)
public class Root implements Serializable {

    @XmlElementWrapper(name = "pdfs")
    @Valid
    @XmlElement(name = "pdf")
    private List<Pdf> pdfs;
}
