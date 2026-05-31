package com.Gautam.journalApp.controller;

import com.Gautam.journalApp.entry.JournalEntry;
import com.Gautam.journalApp.entry.User;
import com.Gautam.journalApp.service.JournalEntryService;
import com.Gautam.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllJournalEntriesOfUser(){
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        String username =  authentication.getName();
        User user = userService.findByUserName(username);

        List<JournalEntry> all= user.getJournalEntries();
        if(!all.isEmpty() && all != null)
        {
            return new ResponseEntity<>(all,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            journalEntryService.saveJournalEntry(myEntry,username);
            return new ResponseEntity<>(myEntry,HttpStatus.CREATED);
        }catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myId){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        List<JournalEntry> listOfJournalEntriesOfUser =  journalEntryService.findByUserName(userName);
        List<JournalEntry> journalEntryWithMyid =  listOfJournalEntriesOfUser.stream().filter(x->x.getId().equals(myId)).collect(Collectors.toList());

        if(!journalEntryWithMyid.isEmpty())
        {
            Optional<JournalEntry> journalEntry =  journalEntryService.findJournalEntryById(myId);
           return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }
       else
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{myId}")
    public ResponseEntity<?> deleteJounalEntryById(@PathVariable ObjectId myId)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        boolean removed = journalEntryService.deleteEntryById(myId,userName);
        if(removed)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
    @PutMapping("/id/{myId}")
    public ResponseEntity<?> updateJournalEntryById(@PathVariable ObjectId myId,@RequestBody JournalEntry newEntry)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        List<JournalEntry> usersJournalEntryWithMyid = userService.findByUserName(username).getJournalEntries().stream().filter(x->x.getId().equals(myId)).collect(Collectors.toList());

        if(usersJournalEntryWithMyid.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        JournalEntry oldEntry = journalEntryService.findJournalEntryById(myId).orElse(null);

            oldEntry.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : oldEntry.getContent());
            oldEntry.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : oldEntry.getTitle());
            journalEntryService.saveJournalEntry(oldEntry);
            return new ResponseEntity<>(oldEntry,HttpStatus.OK);

    }

}
