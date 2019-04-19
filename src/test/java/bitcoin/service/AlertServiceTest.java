package bitcoin.service;

import bitcoin.model.Alert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class AlertServiceTest {

    private static final Alert FIRST_ALERT = Alert.anAlert("My  firstAlert", BigDecimal.ONE);
    private static final Alert SECOND_ALERT = Alert.anAlert("My second Alert", BigDecimal.TEN);
    private static final Alert THIRD_ALERT = Alert.anAlert("My third Alert", BigDecimal.valueOf(123L));
    private AlertService alertService = new AlertService();

    @Test
    void addOneAlert() {
        alertService.addAlert(FIRST_ALERT);

        Set<Alert> alerts = alertService.getAlerts();

        assertThat(alerts).containsOnly(FIRST_ALERT);
    }

    @Test
    void addThenRemoveOneAlert() {
        alertService.addAlert(FIRST_ALERT);
        alertService.deleteAlert(FIRST_ALERT);

        Set<Alert> alerts = alertService.getAlerts();

        assertThat(alerts).isEmpty();
    }

    @Test
    void addThreeAlerts() {
        addAlerts(FIRST_ALERT, SECOND_ALERT, THIRD_ALERT);

        Set<Alert> alerts = alertService.getAlerts();

        assertThat(alerts).containsExactlyInAnyOrder(FIRST_ALERT, SECOND_ALERT, THIRD_ALERT);
    }

    @Test
    void addThreeAlertsRemoveTwo() {
        addAlerts(FIRST_ALERT, SECOND_ALERT, THIRD_ALERT);
        deleteAlerts(FIRST_ALERT, SECOND_ALERT);

        Set<Alert> alerts = alertService.getAlerts();

        assertThat(alerts).containsOnly(THIRD_ALERT);
    }

    @Test
    void filterAlertsAboveLimit(){
        addAlerts(FIRST_ALERT, SECOND_ALERT, THIRD_ALERT);

        Set<Alert> alertsAboveLimit = alertService.alertsAboveLimit(SECOND_ALERT.getPriceLimit());

        assertThat(alertsAboveLimit).containsOnly(THIRD_ALERT);
    }

    private void addAlerts(Alert... alerts) {
        for (Alert alert : alerts) {
            alertService.addAlert(alert);
        }
    }

    private void deleteAlerts(Alert... alerts) {
        for (Alert alert : alerts) {
            alertService.deleteAlert(alert);
        }
    }

}