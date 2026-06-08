package com.Gautam.journalApp.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CatResponse{
    private List<Breed> breeds;
    private String id;
    private String url;
    private int width;
    private int height;

    @Getter
    @Setter
    public static class Breed{
        private String id;
        private String name;
    }


}




