package com.gabriel.martins.vote.service;

import com.gabriel.martins.vote.converter.AssemblyConverter;
import com.gabriel.martins.vote.dto.AssemblyDto;
import com.gabriel.martins.vote.entity.AssemblyEntity;
import com.gabriel.martins.vote.enums.AssemblyStatus;
import com.gabriel.martins.vote.enums.VoteType;
import com.gabriel.martins.vote.repository.AssemblyRepositoty;
import com.gabriel.martins.vote.repository.VoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AssemblyService {

    private static final Logger LOG = LoggerFactory.getLogger(AssemblyService.class);

    @Autowired
    private AssemblyRepositoty repository;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private AssemblyConverter converter;

    public void create(AssemblyDto assemblyDto) {
        try {
            AssemblyEntity entity = converter.convertToEntity(assemblyDto);
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
            LOG.error("Não foi possível cadastrar a pauta. {}", e.getMessage());
        }
    }

    public void closeAssembly(Long assemblyId){
        try {
            Optional<AssemblyEntity> assemblyOp = repository.findById(assemblyId);
            if(assemblyOp.isPresent() && assemblyOp.get().getStatus().equals(AssemblyStatus.VOTING)){
                Long countSim = voteRepository.countVotesByVoteTypeAndAssemblyId(VoteType.SIM, assemblyId);
                Long countNao = voteRepository.countVotesByVoteTypeAndAssemblyId(VoteType.NAO, assemblyId);

                AssemblyEntity entity =  assemblyOp.get();
                entity.setAvailable(Boolean.FALSE);
                entity.setEndingDate(LocalDate.now());

                if(countSim > countNao){
                    entity.setStatus(AssemblyStatus.APPROVED);
                } else if(countSim < countNao) {
                    entity.setStatus(AssemblyStatus.REPROVED);
                } else if(countSim == countNao) {
                    entity.setStatus(AssemblyStatus.TIED);
                } else {
                    entity.setStatus(AssemblyStatus.CLOSED);
                }

                repository.save(entity);
                LOG.info("Pauta {} fechada", assemblyId);
                //enviar para o tópico aqui
            } else {
                LOG.info("Pauta {} não encontrada ou já fechada para votação.", assemblyId);
            }
        } catch (Exception e) {
            LOG.error("Não foi possível fechar a pauta {}. {}", assemblyId, e.getMessage());
        }
    }

    public void openAssembly(Long assemblyId){
        try {
            Optional<AssemblyEntity> assemblyOp = repository.findById(assemblyId);
            if(assemblyOp.isPresent()){
                if(!assemblyOp.get().getAvailable() && assemblyOp.get().getStartingDate().isAfter(LocalDate.now())){
                    AssemblyEntity entity =  assemblyOp.get();
                    entity.setAvailable(Boolean.TRUE);
                    entity.setStatus(AssemblyStatus.VOTING);
                    entity.setStartingDate(LocalDate.now());

                    repository.save(entity);
                    LOG.info("Pauta {} aberta para votação!", assemblyId);
                } else if (assemblyOp.get().getAvailable() || assemblyOp.get().getStatus().equals(AssemblyStatus.VOTING)){
                    LOG.info("Pauta {} já está aberta para votação!", assemblyId);
                } else {
                    LOG.info("Pauta {} já está para votação!", assemblyId);
                }
            } else {
                LOG.info("Pauta {} não registrada!", assemblyId);
            }
        } catch (Exception e) {
            LOG.error("Não foi possível abrir a pauta {}. {}", assemblyId, e.getMessage());
        }
    }
}
