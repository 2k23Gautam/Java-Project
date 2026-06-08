package com.Gautam.journalApp.service;

import com.Gautam.journalApp.entity.JournalEntry;
import com.Gautam.journalApp.entity.User;
import com.Gautam.journalApp.repository.JournalEntryRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private UserService userService;



    @Transactional
    public void saveJournalEntry(JournalEntry journalEntry,String userName)
    {
        try{
            User user = userService.findByUserName(userName);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved =  journalEntryRepository.save(journalEntry);
            List<JournalEntry> journalEntries = user.getJournalEntries();
            journalEntries.add(saved);
    //        user.setUserName(null); //if we add  this line then below line will not execute so it will cause inconsistency because jounalEntry is saved inside journal matrix but will not shown in user's List because user is not saved because of some issue
            userService.saveUser(user);
        }catch (Exception e)
        {

            throw new RuntimeException("An error occurred while saving the entry.",e);
        }
    }
    public void saveJournalEntry(JournalEntry journalEntry)
    {

        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAllEntries(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findJournalEntryById(ObjectId Id){
        return journalEntryRepository.findById(Id);
    }

    @Transactional
    public boolean deleteEntryById(ObjectId Id,String userName)
    {
        try{

            User user = userService.findByUserName(userName);
            List<JournalEntry> usersJournalentires = user.getJournalEntries();
            boolean b= usersJournalentires.removeIf(x -> x.getId().equals(Id));

            if(b)
            {
                userService.saveUser(user);
                journalEntryRepository.deleteById(Id);
            }
            return b;
        }catch(Exception e)
        {
            log.error("error",e);
            throw new RuntimeException("An error occurred while deleting the entry,", e);
        }
    }

    public List<JournalEntry> findByUserName(String userName)
    {
        return userService.findByUserName(userName).getJournalEntries();
    }


}
