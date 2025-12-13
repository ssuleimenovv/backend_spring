package kbtu.demo.api;

import kbtu.demo.service.MessageProducer;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class EventController {

    private final MessageProducer producer;

    public EventController(MessageProducer producer) {
        this.producer = producer;
    }

    @GetMapping("/publish")
    public String send(@RequestParam String message) {
        producer.sendMessage("demo_topic", message);
        return "✅ Sent message to Kafka: " + message;
    }

    @GetMapping("/ping")
    public String ping() {
        return "✅ Server is running!";
    }
}
