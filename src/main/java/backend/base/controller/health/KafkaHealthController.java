package backend.base.controller.health;

import backend.base.data.api.ApiResponse;
import backend.base.enums.KafkaTopic;
import backend.base.service.KafkaService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Lazy
@AllArgsConstructor
@RestController
@RequestMapping(value = {"/health"})
public class KafkaHealthController {

    private KafkaService kafkaService;

    @GetMapping(value = {"/v1/kafka"})
    public ApiResponse healthCheckKafka() {
        kafkaService.send(KafkaTopic.HEALTH_CHECK, "");
        return ApiResponse.success();
    }

}
