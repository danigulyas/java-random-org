package response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

/**
 * @author dani
 */
public interface Response<T> {
    int getBitsUsed();
    int getBitsLeft();
    int getRequestsLeft();
    int getAdvisoryDelay();
    Result<T> getRandom();
}
