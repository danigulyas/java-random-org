/**
 * Class to translate exceptions to the abstraction of the API.
 * @author dani
 */
public class RequestException extends Exception {
    public RequestException(Exception cause) {
        super(cause);
    }

    public RequestException(String message, Exception cause) {
        super(message, cause);
    }
}
