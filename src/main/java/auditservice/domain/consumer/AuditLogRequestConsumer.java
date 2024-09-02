package auditservice.domain.consumer;

import auditservice.domain.entity.AuditLog;
import auditservice.domain.model.AuditMessageDTO;
import auditservice.domain.producer.AuditLogErrorProducer;
import auditservice.domain.producer.AuditLogSuccessProducer;
import auditservice.infra.repository.AuditLogRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class AuditLogRequestConsumer {

    @Autowired private AuditLogRepository auditLogRepository;
    @Autowired private AuditLogErrorProducer errorProducer;
    @Autowired private AuditLogSuccessProducer successProducer;

    @RabbitListener(queues = {"audit-log-request-queue"})
    public void logAudit(@Payload Message message) {
        var messageRequest = message.getPayload();

        if (messageRequest instanceof String) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();

                AuditMessageDTO auditMessageDTO = objectMapper.readValue((String) messageRequest, AuditMessageDTO.class);

                AuditLog log = new AuditLog(
                        null,
                        auditMessageDTO.getEntityName(),
                        auditMessageDTO.getEntityId(),
                        auditMessageDTO.getAction(),
                        LocalDateTime.now(),
                        auditMessageDTO.getDetails()
                );

                auditLogRepository.save(log);
                successProducer.generatedResponse("Mensagem de sucesso para salvar auditoria -> " + auditMessageDTO);
            } catch (JsonProcessingException e) {
                log.error("[AuditLogServiceImpl] -> (logAudit): Erro ao desserializar a mensagem: " + e.getMessage());
                errorProducer.generatedResponse("Mensagem de erro para salvar auditoria -> " + messageRequest);
            }
        } else {
            log.error("[AuditLogServiceImpl] -> (logAudit): Tipo de payload inesperado: " + messageRequest.getClass().getName());
            errorProducer.generatedResponse("Mensagem de erro para salvar auditoria -> " + messageRequest);
        }
    }

}
