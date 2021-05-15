package ru.kpfu.itis.converterdemo.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan({"ru.kpfu.itis.converterdemo"})
@PropertySource("classpath:application.properties")
public class AppConfig {

}
