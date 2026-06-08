package com.Gautam.journalApp.cache;

import com.Gautam.journalApp.entity.ConfigAppJournalAppEntity;
import com.Gautam.journalApp.repository.ConfigJournalAppRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {

    @Autowired
    private ConfigJournalAppRepository configJournalAppRepository;

    public Map<String,String> APP_CACHE;

    @PostConstruct
    public void init(){
        APP_CACHE = new HashMap<>();
        List<ConfigAppJournalAppEntity> all = configJournalAppRepository.findAll();
        for(ConfigAppJournalAppEntity configAppJournalAppEntity : all){
            APP_CACHE.put(configAppJournalAppEntity.getKey(),configAppJournalAppEntity.getValue());
        }
    }
}
