package io.github.quinnandrews.scheduler.modules.shifts.event;

import io.github.quinnandrews.scheduler.modules.shifts.core.ShiftEventService;
import io.github.quinnandrews.scheduler.modules.shifts.core.ShiftService;
import io.github.quinnandrews.scheduler.modules.shifts.core.domain.ShiftEvent;
import io.github.quinnandrews.scheduler.modules.shifts.event.translators.ShiftPublishedTranslator;
import io.github.quinnandrews.scheduler.modules.shifts.event.translators.ShiftRemovedTranslator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.concurrent.TimeUnit;

@Component
public class ShiftEventProducer {

    private static final Logger logger = LoggerFactory.getLogger(ShiftEventProducer.class);

    private final ApplicationEventPublisher eventPublisher;
    private final ShiftEventService shiftEventService;
    private final ShiftService shiftService;

    public ShiftEventProducer(final ApplicationEventPublisher eventPublisher,
                              final ShiftEventService shiftEventService,
                              final ShiftService shiftService) {
        this.eventPublisher = eventPublisher;
        this.shiftEventService = shiftEventService;
        this.shiftService = shiftService;
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handleEvent(final ShiftEvent event) {
        logger.info("handleEvent()");
        produceMessage(event);
    }

    @Scheduled(timeUnit = TimeUnit.SECONDS,
            initialDelay = 60,
            fixedDelay = 60)
    protected void pollEvents() {
        logger.info("pollEvents()");
        //shiftEventService.findEventsNotYetProduced().forEach(this::produceMessage)
    }

    private void produceMessage(final ShiftEvent event) {
        logger.info("produceMessage()");
        shiftEventService.messageAttempted(event);
        if (event.isPublication()) {
            producePublicationMessage(event);
        } else if (event.isRemoval()) {
            produceRemovalMessage(event);
        }
    }

    private void producePublicationMessage(final ShiftEvent event) {
        logger.info("producePublicationMessage()");
        final var snapshot = shiftService.getShiftSnapshotOrElseThrow(
                event.getShiftId(),
                event.getShiftVersion()
        );
        logger.info("{}", snapshot);
        final var shift = snapshot.getSnapshotEntity();
        logger.info("{}", shift);
        final var message = ShiftPublishedTranslator.messageOf(shift, event);
        logger.info("{}", message);
        eventPublisher.publishEvent(message);
        shiftEventService.messageProduced(event);
    }

    private void produceRemovalMessage(final ShiftEvent event) {
        logger.info("produceRemovalMessage()");
        final var message = ShiftRemovedTranslator.messageOf(event);
        logger.info("{}", message);
        eventPublisher.publishEvent(message);
        shiftEventService.messageProduced(event);
    }
}
