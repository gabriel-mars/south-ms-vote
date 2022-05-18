package com.gabriel.martins.vote.controller;

import com.gabriel.martins.vote.dto.AssemblyDto;
import com.gabriel.martins.vote.dto.AssociateDto;
import com.gabriel.martins.vote.service.AssemblyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/assembly")
public class AssemblyController {

    @Autowired
    private AssemblyService service;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody AssemblyDto assembly) {
        service.create(assembly);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
