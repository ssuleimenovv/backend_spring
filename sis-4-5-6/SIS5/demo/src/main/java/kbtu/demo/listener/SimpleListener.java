package kbtu.demo.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class SimpleListener {
    @KafkaListener(topics = "baeldung", groupId = "foo")
    public void listen(String message) {
        System.out.println("📩 Received: " + message);
    }
}
// follows to baeldung
// sent message - automatically listen()
// outputs message in terminal
