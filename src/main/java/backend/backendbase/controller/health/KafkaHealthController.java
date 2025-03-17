package backend.backendbase.controller.health;

import backend.backendbase.data.api.ApiResponse;
import backend.backendbase.enums.KafkaTopic;
import backend.backendbase.service.KafkaService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping(value = {"/health"})
public class KafkaHealthController {

    private KafkaService kafkaService;

    @Hidden
    @GetMapping(value = {"/v1/kafka"})
    public ApiResponse healthCheckKafka() {
        kafkaService.send(KafkaTopic.HEALTH_CHECK, "");
        return ApiResponse.success();
    }

}
