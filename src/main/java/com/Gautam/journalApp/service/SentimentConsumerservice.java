package com.Gautam.journalApp.service;

import com.Gautam.journalApp.model.SentimentData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class SentimentConsumerservice {

    @Autowired
    private EmailService emailService;

    //continuously finds that data has come or not if come then consume
    @KafkaListener(topics = "weekly_sentiments",groupId = "weekly_sentiment_group")
    public void consume(SentimentData sentimentData){
        sendEmail(sentimentData);
    }

    private void sendEmail(SentimentData sentimentData){
        emailService.sendEmail(sentimentData.getEmail(),"Sentiment for previous week. ",sentimentData.getSentiment());
    }
}
