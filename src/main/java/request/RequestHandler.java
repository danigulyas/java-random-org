package request;

import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;
import com.thetransactioncompany.jsonrpc2.client.JSONRPC2Session;
import com.thetransactioncompany.jsonrpc2.client.JSONRPC2SessionException;
import lombok.RequiredArgsConstructor;

import java.net.URL;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Responsible for tracking the current requestID and for interacting with the JSONRPC2Session.
 * @author dani
 */
public class RequestHandler {
    private final JSONRPC2Session session;

    private Integer requestId = 0;

    public RequestHandler(URL url) {
        checkNotNull(url);
        this.session = new JSONRPC2Session(url);
    }

    public RequestHandler(JSONRPC2Session session) {
        checkNotNull(session);
        this.session = session;
    }

    public JSONRPC2Response makeRequest(RequestBuilder builder) throws RequestException {
        try {
            return session.send(builder.build(getNextRequestId()));
        } catch(JSONRPC2SessionException ex) {
            throw new RequestException("Error during the request / parsing the response, see cause.", ex);
        }
    }

    private synchronized Integer getNextRequestId() {
        if(requestId >= Integer.MAX_VALUE - 1) requestId = 0;
        return requestId++;
    }
}
