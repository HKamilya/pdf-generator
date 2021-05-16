package ru.kpfu.itis.converterdemo.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@XmlAccessorType(XmlAccessType.FIELD)
public class Entity {
    @PastOrPresent(message = "wrong date")
    @NotNull(message = "it's not a date")
    private Date formed;
    @PastOrPresent(message = "wrong date")
    @NotNull(message = "it's not a date")
    private Date formalized;
    @PastOrPresent(message = "wrong date")
    @NotNull(message = "it's not a date")
    private Date credited;
    @Valid
    private Comment comment;
    @Valid
    private Employee employee;
    @NotEmpty
    @Pattern(regexp = "^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)(\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)){3}$", message = "it's not ip-address")
    private String ipAddress;

}
