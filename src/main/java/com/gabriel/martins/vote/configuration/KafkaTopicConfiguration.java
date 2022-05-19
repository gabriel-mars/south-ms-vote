package com.gabriel.martins.vote.configuration;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.converter.RecordMessageConverter;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfiguration {

    private static int DEFAULT_PARTITION_NUMBER = 1;
    private static int DEFAULT_REPLICATION_FACTOR_NUMBER = 1;
    public static final String ASSEMBLY_RESULT_TOPIC = "assembly_result_topic";

    @Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        final Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic topicAssemblyResult() {
        return new NewTopic(ASSEMBLY_RESULT_TOPIC,
                DEFAULT_PARTITION_NUMBER,
                (short) DEFAULT_REPLICATION_FACTOR_NUMBER);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactoryConfigurer kafkaListenerContainerFactoryConfigurer(
            final KafkaProperties kafkaProperties,
            final ObjectProvider<RecordMessageConverter> messageConverterObjectProvider,
            final ObjectProvider<KafkaTemplate<Object, Object>> kafkaTemplateObjectProvider) {

        final RecordMessageConverter messageConverter = messageConverterObjectProvider.getIfUnique();
        final KafkaTemplate<Object, Object> kafkaTemplate = kafkaTemplateObjectProvider.getIfUnique();

        return new ConcurrentKafkaListenerContainerFactoryConfigurer() {

            @Override
            public void configure(final ConcurrentKafkaListenerContainerFactory<Object, Object> listenerFactory,
                                  final ConsumerFactory<Object, Object> consumerFactory) {

                listenerFactory.setConsumerFactory(consumerFactory);
                configureListenerFactory(listenerFactory);
                configureContainer(listenerFactory.getContainerProperties());
            }

            private void
            configureListenerFactory(final ConcurrentKafkaListenerContainerFactory<Object, Object> factory) {
                final PropertyMapper map = PropertyMapper.get();
                final KafkaProperties.Listener properties = kafkaProperties.getListener();
                map.from(properties::getConcurrency).whenNonNull().to(factory::setConcurrency);
                map.from(() -> messageConverter).whenNonNull().to(factory::setMessageConverter);
                map.from(() -> kafkaTemplate).whenNonNull().to(factory::setReplyTemplate);
                map.from(properties::getType)
                        .whenEqualTo(KafkaProperties.Listener.Type.BATCH)
                        .toCall(() -> factory.setBatchListener(true));
            }

            private void configureContainer(final ContainerProperties container) {
                final PropertyMapper map = PropertyMapper.get();
                final KafkaProperties.Listener properties = kafkaProperties.getListener();
                map.from(properties::getAckMode).whenNonNull().to(container::setAckMode);
                map.from(properties::getAckCount).whenNonNull().to(container::setAckCount);
                map.from(properties::getAckTime).whenNonNull().as(Duration::toMillis).to(container::setAckTime);
                map.from(properties::getPollTimeout).whenNonNull().as(Duration::toMillis).to(container::setPollTimeout);
                map.from(properties::getNoPollThreshold).whenNonNull().to(container::setNoPollThreshold);
                map.from(properties::getIdleEventInterval)
                        .whenNonNull()
                        .as(Duration::toMillis)
                        .to(container::setIdleEventInterval);
                map.from(properties::getMonitorInterval)
                        .whenNonNull()
                        .as(Duration::getSeconds)
                        .as(Number::intValue)
                        .to(container::setMonitorInterval);
            }

        };
    }
}
