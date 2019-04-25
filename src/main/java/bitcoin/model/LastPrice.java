package bitcoin.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;

@Data(staticConstructor = "lastPrice")
public class LastPrice {
    private final BitcoinPrice bitcoinPrice;
    private final Instant updateTime;

    public static LastPrice lastPrice(BigDecimal limit, String currencyPair) {
        return lastPrice(BitcoinPrice.anBitcoinPrice(limit, currencyPair), Instant.now());
    }
}
