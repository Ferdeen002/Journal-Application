package com.ferdeen.Journal.Application.JournalEntryService;

import com.ferdeen.Journal.Application.Entity.JournalEntry;
import com.ferdeen.Journal.Application.JournalEntryRepo.JournalEntryRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepo journalEntryRepo;


    public void saveJournalEntry(JournalEntry journalEntry){
        journalEntryRepo.save(journalEntry);
    }
    @GetMapping("getAllJournals")
    public List<JournalEntry> getall(){
       return journalEntryRepo.findAll();
    }

    public Optional<JournalEntry> findbyId(ObjectId myid) {
        return journalEntryRepo.findById(myid);
    }

    public void deletebyId(ObjectId myid) {
        journalEntryRepo.deleteById(myid);
    }

}
