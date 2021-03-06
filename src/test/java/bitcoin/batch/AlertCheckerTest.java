package bitcoin.batch;

import bitcoin.model.Alert;
import bitcoin.model.AlertState;
import bitcoin.model.BitcoinPrice;
import bitcoin.model.LastPrice;
import bitcoin.service.AlertService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@RunWith(MockitoJUnitRunner.class)
class AlertCheckerTest {

    private static final BigDecimal SOME_HIGHER_VALUE = BigDecimal.valueOf(11);
    private static final BigDecimal SOME_LOWER_VALUE = BigDecimal.TEN;
    private static final String SOME_CURRENCY = "BTC/USD";
    private static final String SOME_ALERT_NAME = "SOME_ALERT";

    @Mock
    private BitcoinCurrentPriceProvider bitcoinCurrentPriceProvider;
    @Mock
    private AlertSender alertSender;

    private AlertService alertService;
    private AlertChecker alertChecker;

    @BeforeEach
    void setUp() {
        alertService = new AlertService();
        alertChecker = new AlertChecker(alertService, bitcoinCurrentPriceProvider, alertSender);
    }

    @Test
    void priceExceedLimit() throws IOException {
        alertService.addAlert(Alert.anAlert(SOME_ALERT_NAME, SOME_LOWER_VALUE, SOME_CURRENCY));
        Mockito.when(bitcoinCurrentPriceProvider.getLastPrice(SOME_CURRENCY)).thenReturn(LastPrice.lastPrice(SOME_HIGHER_VALUE, SOME_CURRENCY));

        alertChecker.checkAlerts();
        Optional<AlertState> alert = alertService.getAlerts().stream().findAny().map(Alert::getState);

        verify(alertSender).sendAlerts(any());
        assertThat(alert).hasValue(AlertState.RAISED);
    }

    @Test
    void priceNotExceedLimit() throws IOException {
        alertService.addAlert(Alert.anAlert(SOME_ALERT_NAME, SOME_HIGHER_VALUE, SOME_CURRENCY));
        Mockito.when(bitcoinCurrentPriceProvider.getLastPrice(SOME_CURRENCY)).thenReturn(LastPrice.lastPrice(SOME_LOWER_VALUE, SOME_CURRENCY));

        alertChecker.checkAlerts();
        Optional<AlertState> alert = alertService.getAlerts().stream().findAny().map(Alert::getState);

        verify(alertSender, never()).sendAlerts(any());
        assertThat(alert).hasValue(AlertState.NO_RAISED);
    }

    @Test
    void priceNotDefined() throws IOException {
        alertService.addAlert(Alert.anAlert(SOME_ALERT_NAME, SOME_HIGHER_VALUE, SOME_CURRENCY));
        Mockito.when(bitcoinCurrentPriceProvider.getLastPrice(SOME_CURRENCY)).thenThrow(new IOException());

        alertChecker.checkAlerts();
        Optional<AlertState> alert = alertService.getAlerts().stream().findAny().map(Alert::getState);

        verify(alertSender, never()).sendAlerts(any());
        assertThat(alert).hasValue(AlertState.UNDEFINED);
    }


}