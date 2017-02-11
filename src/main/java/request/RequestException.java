package request;

import com.thetransactioncompany.jsonrpc2.client.JSONRPC2SessionException;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author dani
 */
public class RequestException extends Exception {
    public RequestException(String message, JSONRPC2SessionException cause) {
        super(message, cause);

        checkNotNull(message);
        checkNotNull(cause);
    }
}
