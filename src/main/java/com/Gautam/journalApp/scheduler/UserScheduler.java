package com.Gautam.journalApp.scheduler;

import com.Gautam.journalApp.cache.AppCache;
import com.Gautam.journalApp.entity.JournalEntry;
import com.Gautam.journalApp.entity.User;
import com.Gautam.journalApp.enums.Sentiment;
import com.Gautam.journalApp.model.SentimentData;
import com.Gautam.journalApp.repository.UserRepositoryImpl;
import com.Gautam.journalApp.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
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
    private UserRepositoryImpl userRepository;

    @Autowired
    private AppCache appCache;

    @Autowired
    private KafkaTemplate<String, SentimentData> kafkaTemplate;

    @Scheduled(cron = "0 0 9 * * SUN")
//    @Scheduled(cron = "0 * * * * *")
    public void fetchUserAndSendSaMail(){
           List<User> users =  userRepository.getUserforSA();

           for(User user : users) {
               List<JournalEntry> journalEntries = user.getJournalEntries();
               List<Sentiment> sentiments = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x -> x.getSentiment()).collect(Collectors.toList());
               Map<Sentiment,Integer> sentimentCount = new HashMap<>();

               for(Sentiment sentiment : sentiments){
                   if(sentiment != null) {
                       sentimentCount.put(sentiment, sentimentCount.getOrDefault(sentiment, 0) + 1);
                   }
               }
               Sentiment mostFrequentSentiment = null;
               int maxCount = 0;

               for(Map.Entry<Sentiment,Integer> entry : sentimentCount.entrySet())
               {
                   if(entry.getValue() > maxCount){
                       maxCount = entry.getValue();
                       mostFrequentSentiment = entry.getKey();
                   }
               }

               if(mostFrequentSentiment != null)
               {
                   //if kafka fails then we will do normal api calling
                   SentimentData sentimentData = SentimentData.builder().email(user.getEmail()).sentiment("Sentiment For last 7 days "+ mostFrequentSentiment.toString()).build();
                   try{
                        kafkaTemplate.send("weekly_sentiments",sentimentData.getEmail(),sentimentData);
                   }catch(Exception e){
                       emailService.sendEmail(user.getEmail(),"Sentiment for previous week. ","Sentiment For last 7 days "+ mostFrequentSentiment.toString());
                   }
               }

           }
    }

    @Scheduled(cron = "0 */10 * * * *")
    public void clearAppCache(){
        appCache.init();
    }
}
