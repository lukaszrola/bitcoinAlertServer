package bitcoin.batch;

import bitcoin.model.Alert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
class AlertSender {
    private static final String NEW_SECTION_SEPARATOR = "\n****************************************************\n";
    private static final String TITLE = "**************Currently raised alerts***************\n";
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final String raisedAlertsTopic;

    @Autowired
    AlertSender(SimpMessagingTemplate simpMessagingTemplate, @Value("${raised.alerts.topic}") String raisedAlertsTopic) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.raisedAlertsTopic = raisedAlertsTopic;
        simpMessagingTemplate.setMessageConverter(new StringMessageConverter());
    }

    void sendAlerts(Set<Alert> alerts) {
        String raisedAlerts = prepareRaisedAlertsMessage(alerts);
        simpMessagingTemplate.convertAndSend(raisedAlertsTopic, raisedAlerts);
    }

    private String prepareRaisedAlertsMessage(Set<Alert> alerts) {
        String raisedAlerts = alerts.stream()
                .map(Alert::toString)
                .collect(Collectors.joining("\n"));

        return buildMessage(raisedAlerts);
    }

    private String buildMessage(String raisedAlerts) {
        return NEW_SECTION_SEPARATOR
                .concat(TITLE)
                .concat(raisedAlerts)
                .concat(NEW_SECTION_SEPARATOR);
    }
}
