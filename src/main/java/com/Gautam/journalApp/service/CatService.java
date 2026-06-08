package com.Gautam.journalApp.service;

import com.Gautam.journalApp.api.response.CatResponse;
import com.Gautam.journalApp.cache.AppCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CatService {

    @Value("${cat.api.key}")
    private String ApiKey;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppCache appCache;

    public CatResponse getCat()
    {
        String finalAPI = appCache.APP_CACHE.get("catApi").replace("<ApiKey>",ApiKey);

        ResponseEntity<CatResponse[]> response =  restTemplate.exchange(finalAPI,HttpMethod.GET,null, CatResponse[].class);

        CatResponse body = response.getBody()[0];

        return body;
    }

//    For post call

//    public CatResponse getCat()
//    {
//        String finalAPI = API.replace("API_KEY",apiKey);
//        String requestBody = "{\n" +
//                "    \"userName\":\"bharti\",\n" +
//                "    \"password\":\"bharti123\"\n" +
//                "}";
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.set("key","value");
//        HttpEntity<String> httpEntity = new HttpEntity<>(requestBody,httpHeaders);
//        ResponseEntity<CatResponse[]> response =  restTemplate.exchange(finalAPI,HttpMethod.POST,httpEntity, CatResponse[].class);
//
//        CatResponse body = response.getBody()[0];
//
//        return body;
//    }
}
