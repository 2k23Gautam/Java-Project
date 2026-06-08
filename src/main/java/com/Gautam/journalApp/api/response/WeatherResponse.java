package com.Gautam.journalApp.api.response;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WeatherResponse{
    public Main main;

    @Getter
    @Setter
    public class Main{
        public int temp;
        public double feels_like;
    }

}

