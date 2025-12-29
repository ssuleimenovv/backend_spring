package kbtu.demo.listener;

import kbtu.demo.model.Farewell;
import kbtu.demo.model.Greeting;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(id = "multiGroup", topics = "multitype")
public class MultiTypeKafkaListener {

    @KafkaHandler
    public void handleGreeting(Greeting greeting) {
        System.out.println("👋 Greeting received: " + greeting);
    }

    @KafkaHandler
    public void handleFarewell(Farewell farewell) {
        System.out.println("👋 Farewell received: " + farewell);
    }

    @KafkaHandler(isDefault = true)
    public void unknown(Object object) {
        System.out.println("❓ Unknown type: " + object);
    }
}
// follows to MultiType
// segmentation of type
// one listener - many types of data
