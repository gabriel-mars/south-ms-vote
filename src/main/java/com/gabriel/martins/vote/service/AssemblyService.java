package com.gabriel.martins.vote.service;

import com.gabriel.martins.vote.converter.AssemblyConverter;
import com.gabriel.martins.vote.dto.AssemblyDto;
import com.gabriel.martins.vote.entity.AssemblyEntity;
import com.gabriel.martins.vote.enums.AssemblyStatus;
import com.gabriel.martins.vote.repository.AssemblyRepositoty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class AssemblyService {

    private static final Logger LOG = LoggerFactory.getLogger(AssemblyService.class);

    @Autowired
    private AssemblyRepositoty repository;

    @Autowired
    private AssemblyConverter converter;

    public void create(AssemblyDto assemblyDto) {
        try {
            final AssemblyEntity entity = converter.convertToEntity(assemblyDto);
            entity.setCreatedDate(LocalDateTime.now());

            if(entity.getStartingDate().isBefore(LocalDate.now()) || entity.getStartingDate().isEqual(LocalDate.now())){
                entity.setAvailable(Boolean.TRUE);
                entity.setStatus(AssemblyStatus.VOTING);
            } else if(entity.getStartingDate().isAfter(LocalDate.now())){
                entity.setAvailable(Boolean.FALSE);
                entity.setStatus(AssemblyStatus.WAITING);
            }

            if (!entity.getEndingDate().isBefore(LocalDate.now())) {
                repository.save(entity);
                LOG.info("Pauta cadastrada.");
            } else {
                LOG.info("Pauta não cadastrada pois a data de fechamento é anterior a hoje.");
            }
        } catch (Exception e){
            LOG.error("Não foi possível cadastrar a pauta.");
        }
    }
}
