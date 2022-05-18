package com.gabriel.martins.vote.service;

import com.gabriel.martins.vote.converter.VoteConverter;
import com.gabriel.martins.vote.dto.VoteDto;
import com.gabriel.martins.vote.entity.VoteEntity;
import com.gabriel.martins.vote.repository.VoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class VoteService {

    private static final Logger LOG = LoggerFactory.getLogger(VoteService.class);

    @Autowired
    private VoteRepository repository;

    @Autowired
    private VoteConverter converter;

    public void create(final VoteDto voteDto){
        try {
            final VoteEntity entity = converter.convertToEntity(voteDto);
            entity.setCreatedDate(LocalDateTime.now());

            repository.save(entity);
            LOG.info("Voto registrado.");
        } catch (Exception e){
            LOG.error("Não foi possível cadastrar o voto.");
        }
    }
}
