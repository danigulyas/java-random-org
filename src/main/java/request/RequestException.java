package request;

import com.thetransactioncompany.jsonrpc2.client.JSONRPC2SessionException;

/**
 * @author dani
 */
public class RequestException extends Exception {
    public RequestException(String message, JSONRPC2SessionException cause) {
        super(message, cause);
    }
}
