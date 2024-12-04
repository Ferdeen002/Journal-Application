package com.ferdeen.Journal.Application.Service;

import com.ferdeen.Journal.Application.Entity.JournalEntry;
import com.ferdeen.Journal.Application.Entity.User;
import com.ferdeen.Journal.Application.Repo.JournalEntryRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepo journalEntryRepo;

    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName){
        try{
            User user = userService.findbyUserName(userName);
            journalEntry.setDate(LocalTime.now());
            JournalEntry saved = journalEntryRepo.save(journalEntry);
            user.getJournalEntries().add(saved);
            user.setUserName(null);
            userService.save(user);
        }catch (Exception e){
            System.out.println(e);
            throw  new RuntimeException("An error has occured while saving the entry" , e);
        }


    }
    public void saveEntry(JournalEntry journalEntry){
        journalEntryRepo.save(journalEntry);
    }
    @GetMapping("getAllJournals")
    public List<JournalEntry> getall(){
       return journalEntryRepo.findAll();
    }

    public Optional<JournalEntry> findbyId(ObjectId myid) {
        return journalEntryRepo.findById(myid);
    }

    public void deletebyId(ObjectId myid, String userName) {
     User user =   userService.findbyUserName(userName);
     user.getJournalEntries().removeIf(x -> x.getId().equals(myid));
     userService.save(user);
        journalEntryRepo.deleteById(myid);
    }

}
