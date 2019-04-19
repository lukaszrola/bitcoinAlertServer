package bitcoin.controller;

import bitcoin.model.Alert;
import bitcoin.service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@RestController
public class AlertConrtoller {

    private final AlertService alertService;

    @Autowired
    public AlertConrtoller(AlertService alertService) {
        this.alertService = alertService;
    }

    @GetMapping("/alerts")
    public Set<Alert> getAlerts() {
        return alertService.getAlerts();
    }

    @PutMapping("/alert")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public Alert putAlert(@RequestParam String name, @RequestParam BigDecimal limit, @RequestParam(name = "pair") String currencyPair) {
        Alert alert = Alert.anAlert(name, limit, currencyPair);
        alertService.addAlert(alert);
        return alert;
    }

    @DeleteMapping("/alert")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public String deleteAlert(@RequestParam String name) {
        Alert alert = Alert.anTemplateAlert(name);
        alertService.deleteAlert(alert);
        return name;
    }
}
