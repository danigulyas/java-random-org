package response.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

/**
 * @author dani
 */
public class Result<T> {
    @Getter @JsonProperty private int bitsUsed;
    @Getter @JsonProperty private int bitsLeft;
    @Getter @JsonProperty private int requestsLeft;
    @Getter @JsonProperty private int advisoryDelay;

    @Getter @JsonProperty private Random<T> random;
}
