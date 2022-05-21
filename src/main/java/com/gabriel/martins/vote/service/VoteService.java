package com.gabriel.martins.vote.service;

import com.gabriel.martins.vote.converter.VoteConverter;
import com.gabriel.martins.vote.dto.CpfReturnDto;
import com.gabriel.martins.vote.dto.VoteDto;
import com.gabriel.martins.vote.entity.AssemblyEntity;
import com.gabriel.martins.vote.entity.AssociateEntity;
import com.gabriel.martins.vote.entity.VoteEntity;
import com.gabriel.martins.vote.enums.AssemblyStatus;
import com.gabriel.martins.vote.enums.CpfStatus;
import com.gabriel.martins.vote.feign.ValidateCpf;
import com.gabriel.martins.vote.repository.AssemblyRepository;
import com.gabriel.martins.vote.repository.AssociateRepository;
import com.gabriel.martins.vote.repository.VoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
public class VoteService {

    private static final Logger LOG = LoggerFactory.getLogger(VoteService.class);

    @Autowired
    private VoteRepository repository;

    @Autowired
    private AssemblyRepository assemblyRepository;

    @Autowired
    private AssociateRepository associateRepository;

    @Autowired
    private VoteConverter converter;

    @Autowired
    private ValidateCpf validateCpf;

    @Async
    public void create(final VoteDto voteDto){
        try {
            Optional<AssociateEntity> associateOp = associateRepository.findById(Long.parseLong(voteDto.getAssociateID().toString()));

            if(associateOp.isPresent()){
                String cpfClean = associateOp.get().getCpf().replace(".","").replace("-","");

                ResponseEntity<CpfReturnDto> response = validateCpf.isValidCpf(cpfClean);

                if(Objects.nonNull(response) && Objects.nonNull(response.getBody()) && response.getBody().getStatus().equals(CpfStatus.ABLE_TO_VOTE)){
                    Optional<AssemblyEntity> assemblyOp = assemblyRepository.findById(voteDto.getAssemblyID());

                    if(assemblyOp.isPresent() && assemblyOp.get().getStatus().equals(AssemblyStatus.VOTING)){
                        final VoteEntity entity = converter.convertToEntity(voteDto);
                        entity.setCreatedDate(LocalDateTime.now());
                        entity.setAssembly(assemblyOp.get());
                        entity.setAssociate(associateOp.get());
                        entity.setVote(voteDto.getVoteType());

                        repository.save(entity);
                        LOG.info("Voto registrado.");
                    } else {
                        LOG.info("Código de pauta inválido ou votação já encerrada");
                    }
                } else {
                    LOG.info("Associado não habilitado para votar.");
                }
            } else {
                LOG.info("Código do associado inválido.");
            }
        } catch (DataIntegrityViolationException dive) {
            LOG.error("Voto já cadastrado para o associado {} na pauta {}", voteDto.getAssociateID(), voteDto.getAssemblyID());
        } catch (Exception e){
            LOG.error("Não foi possível cadastrar o voto. {}", e.getMessage());
        }
    }
}
