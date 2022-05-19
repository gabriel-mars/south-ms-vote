package com.gabriel.martins.vote.producer;

import com.gabriel.martins.vote.configuration.KafkaTopicConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
public class KafkaProducer {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaProducer.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaAssemblyTemplate;

    public void sendToTopic(final String dto) {
        final ListenableFuture<SendResult<String, String>> future =
                kafkaAssemblyTemplate.send(KafkaTopicConfiguration.ASSEMBLY_RESULT_TOPIC, dto);

        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onSuccess(final SendResult<String, String> result) {
                LOG.info("Pauta enviada para o tópico");
            }

            @Override
            public void onFailure(final Throwable ex) {
                LOG.error("Erro ao enviar a pauta para o tópico. {}", ex.getMessage());
            }
        });
    }
}
