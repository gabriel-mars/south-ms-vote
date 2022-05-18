package com.gabriel.martins.vote.service;

import com.gabriel.martins.vote.converter.AssociateConverter;
import com.gabriel.martins.vote.dto.AssociateDto;
import com.gabriel.martins.vote.entity.AssociateEntity;
import com.gabriel.martins.vote.repository.AssociateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AssociateService {

    private static final Logger LOG = LoggerFactory.getLogger(AssociateService.class);

    @Autowired
    private AssociateRepository repository;

    @Autowired
    private AssociateConverter converter;

    public void createAssociate(final AssociateDto associateDto){
        try {
            final AssociateEntity entity = converter.convertToEntity(associateDto);
            entity.setCreatedDate(LocalDateTime.now());

            repository.save(entity);
            LOG.info("Associado cadastrado.");
        } catch (Exception e){
            LOG.error("Não foi possível cadastrar o associado.");
        }
    }

    public Page<AssociateDto> findAssociates(Integer pageSize, Integer pageNumber, String sortParameter, String sortDirection){
        try{
            LOG.info("Buscando os associados cadastrados.");
            PageRequest pageRequest = PageRequest.of(pageNumber, pageSize,
                    Sort.Direction.valueOf(sortDirection), sortParameter);

            Page<AssociateDto> associates = repository.findAll(pageRequest).map(a -> converter.convertToDto(a));
            return associates;
        } catch (Exception e){
            LOG.error("Não foi possível buscar os associados.");
            return null;
        }
    }
}
