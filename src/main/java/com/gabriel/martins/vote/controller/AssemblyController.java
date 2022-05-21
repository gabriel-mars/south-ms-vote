package com.gabriel.martins.vote.controller;

import com.gabriel.martins.vote.dto.AssemblyDto;
import com.gabriel.martins.vote.service.AssemblyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/close/{id}")
    public ResponseEntity<String> closeAssembly(@PathVariable(value = "id") Long assemblyId) {
        service.closeAssemblyByID(assemblyId);
        return ResponseEntity.ok("Processo de fechamento da pauta iniciado.");
    }

    @PutMapping("/open/{id}")
    public ResponseEntity<String> openAssembly(@PathVariable(value = "id") Long assemblyId) {
        service.openAssembly(assemblyId);
        return ResponseEntity.ok("Processo de abertura da pauta iniciado.");
    }
}
