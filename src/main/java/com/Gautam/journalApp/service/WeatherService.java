package com.Gautam.journalApp.service;

import com.Gautam.journalApp.api.response.CatResponse;
import com.Gautam.journalApp.api.response.WeatherResponse;
import com.Gautam.journalApp.cache.AppCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {
    @Value("${weather.api.key}")
    private String ApiKey;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppCache appCache;

    @Autowired
    private RedisService redisService;
    public WeatherResponse getWeather(String city)
    {

        WeatherResponse weatherRespons = redisService.get("weather_of_" + city,WeatherResponse.class);

        if(weatherRespons != null)
            return weatherRespons;
        else
        {
            String finalAPI = appCache.APP_CACHE.get("weatherApi").replace("<ApiKey>",ApiKey).replace("<City>",city);

            ResponseEntity<WeatherResponse> response =  restTemplate.exchange(finalAPI, HttpMethod.GET,null, WeatherResponse.class);

            WeatherResponse body = response.getBody();

            if(body != null)
                redisService.set("weather_of_" + city,body,300l);
            return body;
        }


    }

}
