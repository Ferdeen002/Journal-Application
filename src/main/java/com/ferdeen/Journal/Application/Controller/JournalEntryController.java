package com.ferdeen.Journal.Application.Controller;

import com.ferdeen.Journal.Application.Entity.JournalEntry;
import com.ferdeen.Journal.Application.JournalEntryService.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;

@RequestMapping("/journal")
@RestController
public class JournalEntryController {

    @Autowired
    JournalEntryService journalEntryService;

    @GetMapping("/getAll")
    public List<JournalEntry> getAll(){
        return null;
    }
    @PostMapping("/createJournal")
    public JournalEntry createEntry(@RequestBody JournalEntry journalEntry){
        journalEntry.setDate(LocalTime.now());
        journalEntryService.saveJournalEntry(journalEntry);
        return journalEntry;
    }

    @GetMapping("id/{myid}")
    public JournalEntry searchbyID(@PathVariable ObjectId myid){
       return journalEntryService.findbyId(myid).orElse(null);
    }

    @DeleteMapping("id/{myid}")
    public boolean deleteId(@PathVariable ObjectId myid){
        journalEntryService.deletebyId(myid);
        return true;
    }

    @PutMapping("id/{myid}")
    public JournalEntry putID(@PathVariable ObjectId myid,@RequestBody JournalEntry newEntry){
       JournalEntry old =  journalEntryService.findbyId(myid).orElse(null);
       if(old != null){
          old.setTitle(newEntry.getTitle() !=null && !newEntry.getTitle().equals("")? newEntry.getTitle() : old.getTitle());
          old.setContent(newEntry.getContent()!=null && !newEntry.getContent().equals("") ? newEntry.getContent() : old.getContent());
       }
        journalEntryService.saveJournalEntry(old);
        return old;
    }

}
