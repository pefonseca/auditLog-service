package auditservice.domain.producer;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuditLogErrorProducer {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void generatedResponse(String message) {
        amqpTemplate.convertAndSend(
                "audit-log-response-error-exchange",
                "audit-log-response-error-rout-key",
                message
        );
    }
}
