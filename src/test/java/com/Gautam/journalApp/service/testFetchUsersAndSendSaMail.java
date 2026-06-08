package com.Gautam.journalApp.service;

import com.Gautam.journalApp.scheduler.UserScheduler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class testFetchUsersAndSendSaMail {
    @Autowired
    private UserScheduler userScheduler;

    @Test
    public void testfetchUserAndSendSaMail(){

        userScheduler.fetchUserAndSendSaMail();
    }
}
