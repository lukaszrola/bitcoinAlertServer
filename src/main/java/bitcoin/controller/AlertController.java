package bitcoin.controller;

import bitcoin.model.Alert;
import bitcoin.service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Set;

@RestController
class AlertController {

    private final AlertService alertService;

    @Autowired
    AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    @SuppressWarnings("unused")
    @GetMapping("/alerts")
    Set<Alert> getAlerts() {
        return alertService.getAlerts();
    }

    @SuppressWarnings("unused")
    @PutMapping("/alert")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    Alert putAlert(@RequestParam String name, @RequestParam BigDecimal limit, @RequestParam(name = "pair") String currencyPair) {
        Alert alert = Alert.anAlert(name, limit, currencyPair);
        return alertService.addAlert(alert);
    }

    @SuppressWarnings("unused")
    @DeleteMapping("/alert")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    String deleteAlert(@RequestParam String name) {
        Alert alert = Alert.anTemplateAlert(name);
        alertService.deleteAlert(alert);
        return name;
    }
}
