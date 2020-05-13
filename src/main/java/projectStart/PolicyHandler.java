package projectStart;

import projectStart.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{
    
    @Autowired
    PaymentRepository paymentRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverReserved_Payment(@Payload Reserved reserved){

        if(reserved.isMe()){
            Payment p = new Payment();
            p.setCustomerId(reserved.getCustomerId());
            paymentRepository.save(p);
            System.out.println("##### listener Payment : " + reserved.toJson());
        }
    }

}
