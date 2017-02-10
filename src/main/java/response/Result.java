package response;

import org.joda.time.DateTime;

import java.util.List;

/**
 * @author dani
 * @param <T> The type of variables in the result list ("result.random.data" in json).
 */
public interface Result<T> {
    List<T> getData();
    DateTime getCompletionTime();
}
