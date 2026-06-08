package com.Gautam.journalApp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServicetests {


    @Autowired
    private EmailService emailService;

    @Test
    void testSendMail(){
        emailService.sendEmail("202301214@dau.ac.in","Testing Java Send Email","Hello we are leaning email sending via spring boot");
    }
}
