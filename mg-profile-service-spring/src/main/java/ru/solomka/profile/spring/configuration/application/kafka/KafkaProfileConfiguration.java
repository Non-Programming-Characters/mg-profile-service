package ru.solomka.profile.spring.configuration.application.kafka;

import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import ru.solomka.profile.profile.KafkaProfileNotificationAdapter;
import ru.solomka.profile.profile.event.KafkaProfileEvent;

@Configuration
public class KafkaProfileConfiguration {

    @Bean
    KafkaTemplate<String, KafkaProfileEvent> kafkaProfileEventKafkaTemplate(@NonNull ProducerFactory<String, KafkaProfileEvent> profileEventProducerFactory) {
        return new KafkaTemplate<>(profileEventProducerFactory);
    }

    @Bean
    KafkaProfileNotificationAdapter kafkaProfileNotificationAdapter(@NonNull KafkaTemplate<String, KafkaProfileEvent> profileEventKafkaTemplate) {
        return new KafkaProfileNotificationAdapter(profileEventKafkaTemplate);
    }
}
