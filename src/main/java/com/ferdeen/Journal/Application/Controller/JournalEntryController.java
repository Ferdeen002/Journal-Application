package com.ferdeen.Journal.Application.Controller;

import com.ferdeen.Journal.Application.Entity.JournalEntry;
import com.ferdeen.Journal.Application.Entity.User;
import com.ferdeen.Journal.Application.Service.JournalEntryService;
import com.ferdeen.Journal.Application.Service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@RequestMapping("/journal")
@RestController
public class JournalEntryController {

    @Autowired
    JournalEntryService journalEntryService;

    @Autowired
    UserService userService;

    @GetMapping("/{userName}")
    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String userName){
       User user = userService.findbyUserName(userName);
        List<JournalEntry> all = user.getJournalEntries();
        if(all!=null && !all.isEmpty()) {
            return new  ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PostMapping("/{userName}")
    public ResponseEntity<JournalEntry>  createEntry(@RequestBody JournalEntry journalEntry,@PathVariable String userName){

        try {
            journalEntryService.saveEntry(journalEntry , userName);
            return new ResponseEntity<>(journalEntry,HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("id/{myid}")
    public ResponseEntity<JournalEntry> searchbyID(@PathVariable ObjectId myid){
        Optional<JournalEntry> journalEntry = journalEntryService.findbyId(myid);
        if(journalEntry.isPresent() ){
            return new ResponseEntity<>(journalEntry.get() , HttpStatus.OK);
        }
        return new ResponseEntity<>( HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("id/{userName}/{myid}")
    public ResponseEntity<JournalEntry> deleteId(@PathVariable ObjectId myid ,@PathVariable String userName ){
        journalEntryService.deletebyId(myid , userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("id/{userName}/{myid}")
    public ResponseEntity<JournalEntry> putID(@PathVariable String userName,
             @PathVariable ObjectId myid,
            @RequestBody JournalEntry newEntry) {
       JournalEntry old =  journalEntryService.findbyId(myid).orElse(null);
       if(old != null){
          old.setTitle(newEntry.getTitle() !=null && !newEntry.getTitle().equals("")? newEntry.getTitle() : old.getTitle());
          old.setContent(newEntry.getContent()!=null && !newEntry.getContent().equals("") ? newEntry.getContent() : old.getContent());
           journalEntryService.saveEntry(old);
           return new ResponseEntity<>(old , HttpStatus.OK);
       }
        return new ResponseEntity<>( HttpStatus.NOT_FOUND);
    }


}
