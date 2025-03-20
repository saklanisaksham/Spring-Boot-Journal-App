package net.eg.journalApp.service;


import net.eg.journalApp.api.response.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class WeatherService {

    @Value("${weather.api.key}")
   private String apiKey ;
   private static final String API = "http://api.weatherapi.com/v1/current.json?key=API_KEY&q=CITY&aqi=no";

   @Autowired
   private RestTemplate restTemplate;

   public WeatherResponse getWeather(String city){
       String finalAPI = API.replace("CITY", city).replace("API_KEY", apiKey);
       ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.GET,null, WeatherResponse.class);
       WeatherResponse body = response.getBody();
       return body;
   }
}