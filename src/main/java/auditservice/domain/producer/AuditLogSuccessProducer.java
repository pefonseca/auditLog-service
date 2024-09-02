package auditservice.domain.producer;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuditLogSuccessProducer {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void generatedResponse(String message) {
        amqpTemplate.convertAndSend(
                "audit-log-response-success-exchange",
                "audit-log-response-success-rout-key",
                message
        );
    }

}
