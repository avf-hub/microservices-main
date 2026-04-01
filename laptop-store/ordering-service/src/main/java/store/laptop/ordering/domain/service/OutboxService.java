package store.laptop.ordering.domain.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.laptop.ordering.domain.model.OutboxMessage;
import store.laptop.ordering.domain.model.OutboxMessageRepository;
import store.laptop.ordering.shared.model.web.dto.OrderData;

import java.util.List;

@Service
public class OutboxService {
    private final OutboxMessageRepository outboxMessageRepository;
    private final StreamBridge streamBridge;
    private final ObjectMapper mapper;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public OutboxService(
            OutboxMessageRepository outboxMessageRepository,
            StreamBridge streamBridge,
            ObjectMapper mapper
    ) {
        this.outboxMessageRepository = outboxMessageRepository;
        this.streamBridge = streamBridge;
        this.mapper = mapper;
    }

    @Transactional
    @Scheduled(fixedRate = 3000)  // Запуск каждые 3 секунды
    public void processOutboxMessages() throws JsonProcessingException {
        List<OutboxMessage> outboxMessages =
                outboxMessageRepository.findAllBySentIsFalseOrderByDate();

        // Обрабатываем сообщения
        for (OutboxMessage outboxMessage : outboxMessages) {
            OrderData orderData = mapper.readValue(
                    outboxMessage.getMessage(), OrderData.class);
            boolean sent = streamBridge.send("orders", orderData);

            if (sent) {
                logger.info("Событие о заказе отправлено в Kafka: {}", orderData);
                outboxMessage.setSent(true);
                outboxMessageRepository.save(outboxMessage);
            } else {
                throw new RuntimeException("Ошибка при отправке сообщения в Kafka");
            }
        }
    }
}
