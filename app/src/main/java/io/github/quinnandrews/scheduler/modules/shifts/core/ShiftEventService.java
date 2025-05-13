package io.github.quinnandrews.scheduler.modules.shifts.core;

import io.github.quinnandrews.scheduler.modules.shifts.core.domain.ShiftEvent;
import io.github.quinnandrews.scheduler.modules.shifts.core.domain.repository.ShiftEventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Objects;

@Service
public class ShiftEventService {

    private static final Logger logger = LoggerFactory.getLogger(ShiftEventService.class);

    private final ShiftEventRepository shiftEventRepository;

    public ShiftEventService(final ShiftEventRepository shiftEventRepository) {
        this.shiftEventRepository = shiftEventRepository;
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handleEvent(final ShiftEvent event) {
        logger.info("handleEvent()");
        requireNonNull(event);
        shiftEventRepository.save(event);
    }

    public void messageAttempted(final ShiftEvent event) {
        logger.info("messageAttempted()");
        requireNonNull(event);
        shiftEventRepository.save(event.messageAttempted());
    }

    public void messageProduced(final ShiftEvent event) {
        logger.info("messageProduced()");
        requireNonNull(event);
        shiftEventRepository.save(event.messageProduced());
    }

    private static void requireNonNull(final ShiftEvent event) {
        Objects.requireNonNull(event, "Argument 'event' must not be null.");
    }
}
