package com.Justice.Justice.Controllers;

import com.Justice.Justice.Models.Tribunal;
import com.Justice.Justice.Repositories.TribunalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tribunaux")
public class TribunalController {

    @Autowired
    private TribunalRepository tribunalRepository;

    @PostMapping
    public Tribunal createTribunal(@RequestBody Tribunal tribunal) {
        return tribunalRepository.save(tribunal);
    }

    @GetMapping
    public List<Tribunal> getAllTribunaux() {
        return tribunalRepository.findAll();
    }
}