package bitcoin.service;

import bitcoin.model.Alert;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AlertService {

    private final Set<Alert> alerts = new HashSet<>();

    public Set<Alert> getAlerts() {
        return new HashSet<>(alerts);
    }

    public Alert addAlert(Alert alert) {
        alerts.remove(alert);
        alerts.add(alert);
        return alert;
    }

    public void deleteAlert(Alert alert) {
        alerts.remove(alert);
    }
}
