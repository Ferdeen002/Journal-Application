package com.ferdeen.Journal.Application.Scheduler;

import com.ferdeen.Journal.Application.Cache.AppCache;
import com.ferdeen.Journal.Application.Entity.JournalEntry;
import com.ferdeen.Journal.Application.Entity.User;
import com.ferdeen.Journal.Application.Repo.UserRepositoryImpl;
import com.ferdeen.Journal.Application.Service.EmailService;
import com.ferdeen.Journal.Application.Service.SentimentAnalysis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class UserScheduler {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositoryImpl userRepositoryImpl;

    @Autowired
    SentimentAnalysis sentimentAnalysis;

    @Autowired
    AppCache appCache;

    @Scheduled(cron = "0 0 9 * * SUN")
    public void fetchUsersAndSendEmail(){
        List<User> users = userRepositoryImpl.getUserSA();
        for(User user : users){
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<String> filteredjournalentries = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalTime.now().minus(7, ChronoUnit.DAYS))).map(x-> x.getContent()).collect(Collectors.toList());
            String entry = String.join(" " , filteredjournalentries);
            String sentiment = sentimentAnalysis.getSentiment(entry);
            emailService.sendMail(user.getEmail(), "Sentiment Analysis Report" , sentiment);
        }
    }



    @Scheduled(cron = "0 0/10 * ? * *")
    public void clearAppCache(){
        appCache.init();
    }


}
