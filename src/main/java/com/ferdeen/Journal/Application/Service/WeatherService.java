package com.ferdeen.Journal.Application.Service;

import com.ferdeen.Journal.Application.api.response.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {
    private static final String apiKey ="249f57da4ad28d3eed4196d33e642a7c";

    private static final String Api = "http://api.weatherstack.com/current?access_key=API&query =CITY";

    @Autowired
    private RestTemplate restTemplate;

    public WeatherResponse getWeather(String city){
        String finalApi = Api.replace("CITY" , city).replace("API", apiKey);
         ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalApi, HttpMethod.GET, null, WeatherResponse.class);
         WeatherResponse body = response.getBody();
         return body;
    }

}
