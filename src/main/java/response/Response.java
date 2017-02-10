package response;

/**
 * @author dani
 * @param <T> The type of variables in "result.random.data".
 */
public interface Response<T> {
    int getBitsUsed();
    int getBitsLeft();
    int getRequestsLeft();
    int getAdvisoryDelay();
    Result<T> getRandom();
}
