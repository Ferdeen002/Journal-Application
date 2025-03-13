package com.ferdeen.Journal.Application.Scheduler;

import com.ferdeen.Journal.Application.Cache.AppCache;
import com.ferdeen.Journal.Application.ENUM.Sentiment;
import com.ferdeen.Journal.Application.Entity.JournalEntry;
import com.ferdeen.Journal.Application.Entity.User;
import com.ferdeen.Journal.Application.Repo.UserRepositoryImpl;
import com.ferdeen.Journal.Application.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Component
public class UserScheduler {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositoryImpl userRepositoryImpl;


    @Autowired
    AppCache appCache;

    @Scheduled(cron = "0 0 9 * * SUN")
    public void fetchUsersAndSendEmail(){
        List<User> users = userRepositoryImpl.getUserSA();
        for(User user : users){
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<Sentiment> sentiments = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalTime.now().minus(7, ChronoUnit.DAYS))).map(x-> x.getSentiment()).collect(Collectors.toList());
            Map<Sentiment , Integer> sentimentcounts = new HashMap<>();
            for(Sentiment sentiment : sentiments){
                sentimentcounts.put(sentiment , sentimentcounts.getOrDefault(sentiment , 0) +1);
            }
            Sentiment mostFrequentSentiment  = null;
            int maxCount = 0;
            for( Map.Entry<Sentiment,Integer> entry : sentimentcounts.entrySet()){
                if(entry.getValue() > maxCount){
                    maxCount = entry.getValue();
                }
            }
            if(mostFrequentSentiment != null){
                emailService.sendMail(user.getEmail(), "Sentiment Analysis Report" , mostFrequentSentiment.toString());

            }
        }
    }



    @Scheduled(cron = "0 0/10 * ? * *")
    public void clearAppCache(){
        appCache.init();
    }


}
