package bitcoin.batch;

import bitcoin.model.Alert;
import bitcoin.model.AlertState;
import bitcoin.model.BitcoinPrice;
import bitcoin.service.AlertService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Set;

@Component
class AlertChecker {
    private static final Logger logger = LoggerFactory.getLogger(AlertChecker.class);

    private final AlertService alertService;
    private final BitcoinCurrentPriceProvider bitcoinCurrentPriceProvider;

    @Autowired
    AlertChecker(AlertService alertService, BitcoinCurrentPriceProvider bitcoinCurrentPriceProvider) {
        this.alertService = alertService;
        this.bitcoinCurrentPriceProvider = bitcoinCurrentPriceProvider;
    }

    @Scheduled(fixedRate = 10000)
    void checkAlerts() {

        alertService.getAlerts()
                .forEach(this::updateAlertState);
        alertService.getAlerts()
                .forEach(alert -> logger.warn(alert.toString()));
    }

    private void updateAlertState(Alert alert) {
        try {
            BitcoinPrice limit = alert.getLimit();
            BitcoinPrice lastPrice = bitcoinCurrentPriceProvider.getLastPrice(limit.getCurrencyPair());
            alert.setState(calculateState(limit, lastPrice));
        } catch (IOException e) {
            alert.setState(AlertState.UNDEFINED);
            logger.warn("Cannot define state of alert: " + alert.getAlertName());
        }
    }

    private AlertState calculateState(BitcoinPrice limit, BitcoinPrice lastPrice) {
        return lastPrice.priceExceededLimit(limit) ? AlertState.RAISED : AlertState.NO_RAISED;
    }

}
