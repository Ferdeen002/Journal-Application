package com.ferdeen.Journal.Application.Service;

import com.ferdeen.Journal.Application.Cache.AppCache;
import com.ferdeen.Journal.Application.api.response.WeatherResponse;
import com.ferdeen.Journal.Application.constants.Placeholders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

//import java.lang.foreign.PaddingLayout;

@Service
public class WeatherService {


    @Value("${weather.api.key}")
    private   String apiKey ;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    AppCache appCache;

    public WeatherResponse getWeather(String city){
        String finalApi = appCache.appCache.get(AppCache.keys.WEATHER_API.toString()).replace(Placeholders.CITY, city).replace(Placeholders.API_KEY, apiKey);
         ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalApi, HttpMethod.GET, null, WeatherResponse.class);
         WeatherResponse body = response.getBody();
         return body;
    }

}
