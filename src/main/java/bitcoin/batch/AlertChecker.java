package bitcoin.batch;

import bitcoin.model.Alert;
import bitcoin.service.AlertService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Set;

@Component
public class AlertChecker {
    private static final Logger logger = LoggerFactory.getLogger(AlertChecker.class);

    private final AlertService alertService;

    @Autowired
    public AlertChecker(AlertService alertService) {
        this.alertService = alertService;
    }

    @Scheduled(fixedRate = 10000)
    public void checkAlerts() {
        Set<Alert> alerts = alertService.alertsAboveLimit(BigDecimal.valueOf(500));
        logger.info("exceeded " + alerts.size() + " alerts");
    }
}
