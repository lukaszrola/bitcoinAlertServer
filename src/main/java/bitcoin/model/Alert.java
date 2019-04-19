package bitcoin.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Alert {
    private static final BigDecimal EMPTY_LIMIT = BigDecimal.ZERO;
    private final String alertName;
    private final BigDecimal priceLimit;

    private Alert(String alertName, BigDecimal limit) {
        this.alertName = alertName;
        this.priceLimit = limit;
    }

    public static Alert anAlert(String alertName, BigDecimal limit){
        return new Alert(alertName,limit);
    }

    public static Alert anTemplateAlert(String alertName){
        return new Alert(alertName, EMPTY_LIMIT);
    }

    public String getAlertName() {
        return alertName;
    }

    public BigDecimal getPriceLimit() {
        return priceLimit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Alert)) return false;
        Alert alert = (Alert) o;
        return Objects.equals(alertName, alert.alertName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(alertName);
    }
}
