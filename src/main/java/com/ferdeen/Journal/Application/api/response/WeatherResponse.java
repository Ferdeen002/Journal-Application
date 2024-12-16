package com.ferdeen.Journal.Application.api.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

//de serialise
@Data
public class WeatherResponse{

    private Current current;

    @Data
    public class Current{

        private int temperature;



        @JsonProperty("weather_descriptions")
        private List<String> weatherDescriptions;

        private int feelslike;

    }




}




