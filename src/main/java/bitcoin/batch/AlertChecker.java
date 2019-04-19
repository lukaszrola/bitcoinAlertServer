package bitcoin.batch;

import bitcoin.model.Alert;
import bitcoin.service.AlertService;
import org.knowm.xchange.currency.CurrencyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Set;

@Component
public class AlertChecker {
    private static final Logger logger = LoggerFactory.getLogger(AlertChecker.class);

    private final AlertService alertService;
    private final BitcoinCurrentPriceProvider bitcoinCurrentPriceProvider;

    @Autowired
    public AlertChecker(AlertService alertService, BitcoinCurrentPriceProvider bitcoinCurrentPriceProvider) {
        this.alertService = alertService;
        this.bitcoinCurrentPriceProvider = bitcoinCurrentPriceProvider;
    }

    @Scheduled(fixedRate = 10000)
    public void checkAlerts() {

        try {
            BigDecimal limit = bitcoinCurrentPriceProvider.getLastPrice("BTC/USD").getPrice();
            logger.info("Current price: " + limit);
            Set<Alert> alerts = alertService.alertsAboveLimit(limit);
            logger.info("exceeded " + alerts.size() + " alerts");
        } catch (IOException e) {
            logger.warn("Update failed");
        }


    }
}
