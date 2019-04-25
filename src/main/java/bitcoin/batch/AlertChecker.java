package bitcoin.batch;

import bitcoin.model.Alert;
import bitcoin.model.AlertState;
import bitcoin.model.BitcoinPrice;
import bitcoin.model.LastPrice;
import bitcoin.service.AlertService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Component
class AlertChecker {
    private static final Logger logger = LoggerFactory.getLogger(AlertChecker.class);

    private final AlertService alertService;
    private final BitcoinCurrentPriceProvider bitcoinCurrentPriceProvider;
    private final AlertSender alertSender;

    @Autowired
    AlertChecker(AlertService alertService, BitcoinCurrentPriceProvider bitcoinCurrentPriceProvider, AlertSender alertSender) {
        this.alertService = alertService;
        this.bitcoinCurrentPriceProvider = bitcoinCurrentPriceProvider;
        this.alertSender = alertSender;
    }

    @Scheduled(fixedRate = 10000)
    void checkAlerts() {
        updateAlertStates();
        sendRaisedAlerts();
    }

    private void sendRaisedAlerts() {
        Set<Alert> raisedAlerts = alertService.getAlerts().stream()
                .filter(alert -> alert.getState() == AlertState.RAISED)
                .collect(Collectors.toSet());

        if (!raisedAlerts.isEmpty()) {
            alertSender.sendAlerts(raisedAlerts);
        }
    }

    private void updateAlertStates() {
        alertService.getAlerts()
                .forEach(this::updateAlertState);
    }

    private void updateAlertState(Alert alert) {
        try {
            BitcoinPrice limit = alert.getLimit();
            LastPrice lastPrice1 = bitcoinCurrentPriceProvider.getLastPrice(limit.getCurrencyPair());
            BitcoinPrice lastPrice = lastPrice1.getBitcoinPrice();
            alert.setState(calculateState(limit, lastPrice));
            alert.setTimestamp(lastPrice1.getUpdateTime());
        } catch (IOException e) {
            alert.setState(AlertState.UNDEFINED);
            alert.setTimestamp(Instant.now());
            logger.warn("Cannot define state of alert: " + alert.getAlertName());
        }
    }

    private AlertState calculateState(BitcoinPrice limit, BitcoinPrice lastPrice) {
        return lastPrice.priceExceededLimit(limit) ? AlertState.RAISED : AlertState.NO_RAISED;
    }

}
