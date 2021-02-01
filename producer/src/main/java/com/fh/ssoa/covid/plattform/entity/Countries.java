package com.fh.ssoa.covid.plattform.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
@Data
@PropertySource("classpath:application.properties")
@EnableConfigurationProperties
@ConfigurationProperties("countries")
public class Countries {
    // class for injecting the countries from the application.properties files into the Arrays
    // @Data generates Getters, Setters, Constructors and ToString method

    List<String> europe = new ArrayList<String>();
    List<String> south_america = new ArrayList<String>();

}
