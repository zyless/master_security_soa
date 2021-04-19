package com.fh.ssoa.covid.plattform.controller;

import com.fh.ssoa.covid.plattform.aop.TrackExecutionTime;
import com.fh.ssoa.covid.plattform.common.CommonConstant;
import com.fh.ssoa.covid.plattform.entity.Countries;
import com.github.opendevl.JFlat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping("/covid")
public class Controller {
    // Autowiring the default cacheManager
    @Autowired
    CacheManager cacheManager;
    //Autowiring the Countries which the injected properties
    @Autowired
    Countries countries;
    //Autowiring the restTemplate defined in the "Application" Class
    @Autowired
    RestTemplate restTemplate;

    // REST API for getting the anaylized data
    // PathVariable location can be eu or sa for selecting the regional plot
    @CrossOrigin(origins = "*")
    @GetMapping(path = "/kmeans/{location}")
    @TrackExecutionTime
    @Cacheable("plot")
    public @ResponseBody String getKmeans(@PathVariable String location) throws IOException {
        // The png plot gets transformed into a base64 for string for the resttransmititon
        File f=new File("./"+ location +"/" + location + ".png");
        BufferedImage o=ImageIO.read(f);
        ByteArrayOutputStream b=new ByteArrayOutputStream();
        ImageIO.write(o, "png", b);
        byte[] img=b.toByteArray();
        String base64 = Base64.getEncoder().encodeToString(img);
        return base64;
    }
    // Scheduled method for querying the API
    @TrackExecutionTime
    @Scheduled(cron = "0 39 9 * * ?", zone = "Europe/Vienna")
    public void queryCovidApi() throws InterruptedException, IOException {
        // Http Entity for the request get created
        HttpEntity entity = createHttpEntity();
        // for loop to query the api to get the european stats and transforming them into a csv
        // 5 second delay because the freemium version of the api doesnt allow to fast calling
        for (String country : countries.getEurope()) {
            ResponseEntity<String> response = restTemplate.exchange(CommonConstant.https + CommonConstant.queryString + country, HttpMethod.GET,
                    entity, String.class);
            // Wrapper method for printing the JSON response into a csv-file
            JsonToCsv(country, response, "eu");
            TimeUnit.SECONDS.sleep(5);
        }
        // for loop to query the api to get the european stats and transforming them into a csv
        // 5 second delay because the freemium version of the api doesnt allow to fast calling
        for (String country : countries.getSouth_america()) {
            ResponseEntity<String> response = restTemplate.exchange(CommonConstant.https + CommonConstant.queryString + country, HttpMethod.GET,
                    entity, String.class);
            // Wrapper method for printing the JSON response into a csv-file
            JsonToCsv(country, response, "sa");
            TimeUnit.SECONDS.sleep(5);
        }
        // executing the python scripts for analysing the european and the south american countries
        Runtime.getRuntime().exec("python3 eu/analysis.py");
        Runtime.getRuntime().exec("python3 sa/analysis.py");
        //resetting the cache
        cacheManager.getCache("plot").clear();
    }
    // Methods for transforming the requested data into a csv
    // In order to do so the package JFLAT was used from https://github.com/opendevl/Json2Flat
    public void JsonToCsv(String country, ResponseEntity<String> response, String continent) {
        try {
            JFlat flatMe = new JFlat(response.getBody());
            flatMe
                    .json2Sheet()
                    .headerSeparator("/")
                    .write2csv(continent +"/" + country + ".csv");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(country);
    }
    //Method for generating the HTTP-Headers needed for rapid-api
    public HttpEntity createHttpEntity(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("x-rapidapi-host","certzuii");
        headers.add("X-RapidAPI-Key","vbgtnhtb");
        HttpEntity entity = new HttpEntity(headers);
        return entity;
    }


}


