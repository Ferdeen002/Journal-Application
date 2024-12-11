package com.ferdeen.Journal.Application.Controller;

import com.ferdeen.Journal.Application.Entity.JournalEntry;
import com.ferdeen.Journal.Application.Entity.User;
import com.ferdeen.Journal.Application.Service.JournalEntryService;
import com.ferdeen.Journal.Application.Service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestMapping("/journal")
@RestController
public class JournalEntryController {

    @Autowired
    JournalEntryService journalEntryService;

    @Autowired
    UserService userService;

    @GetMapping()
    public ResponseEntity<?> getAllJournalEntriesOfUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
       User user = userService.findbyUserName(userName);
        List<JournalEntry> all = user.getJournalEntries();
        if(all!=null && !all.isEmpty()) {
            return new  ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PostMapping()
    public ResponseEntity<JournalEntry>  createEntry(@RequestBody JournalEntry journalEntry){

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            journalEntryService.saveEntry(journalEntry , userName);
            return new ResponseEntity<>(journalEntry,HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("id/{myid}")
    public ResponseEntity<?> searchbyID(@PathVariable ObjectId myid){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findbyUserName(userName);
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x->x.getId().equals(myid)).collect(Collectors.toList());
        if(!collect.isEmpty()){
            Optional<JournalEntry> journalEntry = journalEntryService.findbyId(myid);
            if(journalEntry.isPresent() ){
                return new ResponseEntity<>(journalEntry.get() , HttpStatus.OK);
            }
        } // error to be fixed.
        return new ResponseEntity<>( HttpStatus.NOT_FOUND);

    }


    @DeleteMapping("id/{myid}")
    public ResponseEntity<JournalEntry> deleteId(@PathVariable ObjectId myid){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        boolean removed =journalEntryService.deletebyId(myid , userName);
        if(removed)
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("id/{myid}")
    public ResponseEntity<JournalEntry> putID(@PathVariable ObjectId myid, @RequestBody JournalEntry newEntry) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findbyUserName(userName);
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x->x.getId().equals(myid)).collect(Collectors.toList());
        if(!collect.isEmpty()){
            Optional<JournalEntry> journalEntry = journalEntryService.findbyId(myid);
            if(journalEntry.isPresent()){
                JournalEntry old = journalEntry.get();
                old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : old.getTitle() );
                old.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : old.getContent() );
                journalEntryService.saveEntry(old);
                return new ResponseEntity<>(old,HttpStatus.OK);
            }
        }
        return new ResponseEntity<>( HttpStatus.NOT_FOUND);
    }


}
