package com.gabriel.martins.vote.schedule;

import com.gabriel.martins.vote.service.AssemblyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AssemblySchedule {

    private static final Logger LOG = LoggerFactory.getLogger(AssemblySchedule.class);

    @Autowired
    private AssemblyService service;

    @Scheduled(fixedDelayString = "${schedule.open.time.interval.in.miliseconds}")
    public void processOpenAssemblies(){
        try{
            LOG.info("Inicindo processamento de abertura de pautas. {}", LocalDateTime.now());
            service.processOpenAssemblies();
        } catch (Exception e) {
            LOG.error("Falha ao executar a processamento de abertura de pautas. {}", e.getMessage());
        }
    }

    @Scheduled(fixedDelayString = "${schedule.close.time.interval.in.miliseconds}")
    public void processCloseAssemblies(){
        try{
            LOG.info("Inicindo processamento de fechamento de pautas. {}", LocalDateTime.now());
            service.processCloseAssemblies();
        } catch (Exception e) {
            LOG.error("Falha ao executar a processamento de fechamento de pautas. {}", e.getMessage());
        }
    }
}
