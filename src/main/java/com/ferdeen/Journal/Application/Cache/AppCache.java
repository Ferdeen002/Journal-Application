package com.ferdeen.Journal.Application.Cache;


import com.ferdeen.Journal.Application.Entity.ConfigJournal;
import com.ferdeen.Journal.Application.Repo.ConfigJournalRepo;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {

    public enum keys{
        WEATHER_API;
    }
    @Autowired
    private ConfigJournalRepo configJournalRepo;

    public Map<String ,String>  appCache;

    @PostConstruct
    public void init(){
        appCache = new HashMap<>();
        List<ConfigJournal> all = configJournalRepo.findAll();
        for(ConfigJournal configJournal : all){
            appCache.put(configJournal.getKey() , configJournal.getKey());

        }
    }


}
