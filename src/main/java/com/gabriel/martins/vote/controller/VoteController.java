package com.gabriel.martins.vote.controller;

import com.gabriel.martins.vote.dto.VoteDto;
import com.gabriel.martins.vote.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vote")
public class VoteController {

    @Autowired
    private VoteService service;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody VoteDto vote) {
        service.create(vote);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
