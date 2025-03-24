package backend.base.service;

import backend.base.enums.KafkaTopic;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Lazy
@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaService {

    private final ObjectMapper objectMapper;
    private KafkaTemplate<String, String> kafkaTemplate;

    @SneakyThrows
    public void send(KafkaTopic topic, Object object) {

        String message = objectMapper.writeValueAsString(object);

        kafkaTemplate.send(topic.name(), message)
                .thenAccept(result ->
                        log.info("Sent message=[{}] with offset=[{}]", message, result.getRecordMetadata().offset()))
                .exceptionally(exception -> {
                    log.error("Unable to send message=[{}] due to : {}", message, exception.getMessage());
                    return null;
                })
                ;
    }

}
