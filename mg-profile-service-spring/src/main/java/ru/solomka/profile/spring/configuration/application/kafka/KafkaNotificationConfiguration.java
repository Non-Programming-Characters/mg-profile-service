package ru.solomka.profile.spring.configuration.application.kafka;

import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.solomka.profile.common.JsonNodeConvertor;
import ru.solomka.profile.user.KafkaNotificationReceiverAdapter;
import ru.solomka.profile.profile.ProfileService;
import ru.solomka.profile.user.KafkaUserJsonNodeConvertorAdapter;
import ru.solomka.profile.user.entity.KafkaUserEntity;

@Configuration
public class KafkaNotificationConfiguration {

    @Bean
    KafkaNotificationReceiverAdapter kafkaEntityNotificationReceiverAdapter(@NonNull ProfileService profileService,
                                                                            @NonNull JsonNodeConvertor<KafkaUserEntity> jsonNodeConvertor) {
        return new KafkaNotificationReceiverAdapter(profileService, jsonNodeConvertor);
    }

    @Bean
    KafkaUserJsonNodeConvertorAdapter kafkaUserJsonNodeConvertorAdapter() {
        return new KafkaUserJsonNodeConvertorAdapter();
    }
}