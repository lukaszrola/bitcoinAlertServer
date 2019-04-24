package bitcoin.service;

import bitcoin.model.Alert;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AlertService {

    private final Set<Alert> alerts = new HashSet<>();

    public Set<Alert> getAlerts() {
        return new HashSet<>(alerts);
    }

    public void addAlert(Alert alert) {
        alerts.remove(alert);
        alerts.add(alert);
    }

    public void deleteAlert(Alert alert) {
        alerts.remove(alert);
    }
}
