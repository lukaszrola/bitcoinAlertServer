package bitcoin.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Objects;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Alert {
    private static final BigDecimal EMPTY_LIMIT = BigDecimal.ZERO;

    @EqualsAndHashCode.Include
    private final String alertName;
    private final BigDecimal priceLimit;

    public static Alert anAlert(String alertName, BigDecimal limit){
        return new Alert(alertName,limit);
    }

    public static Alert anTemplateAlert(String alertName){
        return new Alert(alertName, EMPTY_LIMIT);
    }

}
