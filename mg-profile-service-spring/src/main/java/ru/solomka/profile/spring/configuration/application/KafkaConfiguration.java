package ru.solomka.profile.spring.configuration.application;

import lombok.NonNull;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import ru.solomka.profile.kafka.KafkaNotificationReceiverAdapter;
import ru.solomka.profile.profile.ProfileService;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfiguration {

    @Bean
    KafkaNotificationReceiverAdapter kafkaEntityNotificationReceiverAdapter(@NonNull ProfileService profileService) {
        return new KafkaNotificationReceiverAdapter(profileService);
    }

    @Bean
    public ConsumerFactory<String, Object> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:29092");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

        props.put(JsonDeserializer.TYPE_MAPPINGS,
                "ru.solomka.identity.kafka.event.KafkaUserCreatedEvent:ru.solomka.profile.kafka.event.KafkaUserCreatedEvent," +
                        "ru.solomka.identity.kafka.event.KafkaUserUpdatedEvent:ru.solomka.profile.kafka.event.KafkaUserUpdatedEvent," +
                        "ru.solomka.identity.kafka.event.KafkaUserDeletedEvent:ru.solomka.profile.kafka.event.KafkaUserDeletedEvent");

        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
