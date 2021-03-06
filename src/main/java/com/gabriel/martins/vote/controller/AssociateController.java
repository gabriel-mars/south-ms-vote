package com.gabriel.martins.vote.controller;

import com.gabriel.martins.vote.dto.AssociateDto;
import com.gabriel.martins.vote.service.AssociateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/associate")
public class AssociateController {

    @Autowired
    private AssociateService service;

    @PostMapping({"/v1", "/v2"})
    public ResponseEntity<Void> create(@RequestBody AssociateDto associate) {
        service.createAssociate(associate);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping({"/v1", "/v2"})
    public ResponseEntity<Page<AssociateDto>> getAssociates(@RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                              @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
                                              @RequestParam(name = "sortParameter", defaultValue = "id") String sortParameter,
                                              @RequestParam(name = "sortDirection", defaultValue = "ASC") String sortDirection){

        return ResponseEntity.ok(service.findAssociates(pageSize, pageNumber, sortParameter, sortDirection));
    }

    @PutMapping({"/v2/{id}"})
    public ResponseEntity<String> updateAssociate(@PathVariable(value = "id") Long associateId, @RequestBody AssociateDto associateDto) {
        service.updateAssociate(associateId, associateDto);
        return ResponseEntity.ok("Processo de atualização do associado iniciado.");
    }
}
