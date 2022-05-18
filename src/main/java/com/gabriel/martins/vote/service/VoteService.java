package com.gabriel.martins.vote.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabriel.martins.vote.converter.VoteConverter;
import com.gabriel.martins.vote.dto.CpfReturnDto;
import com.gabriel.martins.vote.dto.VoteDto;
import com.gabriel.martins.vote.entity.AssemblyEntity;
import com.gabriel.martins.vote.entity.AssociateEntity;
import com.gabriel.martins.vote.entity.VoteEntity;
import com.gabriel.martins.vote.enums.AssemblyStatus;
import com.gabriel.martins.vote.enums.CpfStatus;
import com.gabriel.martins.vote.repository.AssemblyRepositoty;
import com.gabriel.martins.vote.repository.AssociateRepository;
import com.gabriel.martins.vote.repository.VoteRepository;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
public class VoteService {

    private static final Logger LOG = LoggerFactory.getLogger(VoteService.class);

    @Autowired
    private VoteRepository repository;

    @Autowired
    private AssemblyRepositoty assemblyRepository;

    @Autowired
    private AssociateRepository associateRepository;

    @Autowired
    private VoteConverter converter;

    public void create(final VoteDto voteDto){
        try {
            Optional<AssociateEntity> associateOp = associateRepository.findById(Long.parseLong(voteDto.getAssociateID().toString()));

            if(associateOp.isPresent()){
                String cpfClean = associateOp.get().getCpf().replace(".","").replace("-","");

                CpfReturnDto cpfValidated = validateCpf(cpfClean);

                if(Objects.nonNull(cpfValidated) && cpfValidated.getStatus().equals(CpfStatus.ABLE_TO_VOTE)){
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
        } catch (Exception e){
            LOG.error("Não foi possível cadastrar o voto. {}", e.getMessage());
        }
    }

    private CpfReturnDto validateCpf(String cpfClean){
        try{
            LOG.info("Consultando CPF.");

            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet("https://user-info.herokuapp.com/users/".concat(cpfClean));
            HttpResponse response = client.execute(request);

            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line = rd.readLine();
            LOG.info("Return: {}", line);

            if (Objects.nonNull(line) && !line.isEmpty()){
                ObjectMapper mapper = new ObjectMapper();
                CpfReturnDto cpfReturn = mapper.readValue(line, CpfReturnDto.class);
                return cpfReturn;
            } else {
                LOG.info("CPF inválido: {}", cpfClean);
                return null;
            }
        } catch (Exception e) {
            LOG.error("Não foi possível validar o CPF. {}", e.getMessage());
            return null;
        }
    }
}
