package request;

import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
import com.thetransactioncompany.jsonrpc2.client.JSONRPC2Session;
import com.thetransactioncompany.jsonrpc2.client.JSONRPC2SessionException;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.net.URL;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.*;

/**
 * @author dani
 */
public class RequestHandlerTest {

    @Test(expected = NullPointerException.class)
    public void throwsWhenInstantiatedWithNullURL() {
        URL url = null;
        new RequestHandler(url);
    }

    @Test(expected = NullPointerException.class)
    public void throwsWhenInstantiatedWithNullSession() {
        JSONRPC2Session session = null;
        new RequestHandler(session);
    }

    @Test
    public void increasesRequestIdOnEachCall() throws RequestException, JSONRPC2SessionException {
        ArgumentCaptor<JSONRPC2Request> requestCaptor = ArgumentCaptor.forClass(JSONRPC2Request.class);
        JSONRPC2Session session = getMockSession();
        RequestHandler handler = new RequestHandler(session);

        handler.makeRequest(getValidBuilder());
        handler.makeRequest(getValidBuilder());

        verify(session, times(2)).send(requestCaptor.capture());

        assertEquals(requestCaptor.getAllValues().get(0).getID(), 0);
        assertEquals(requestCaptor.getAllValues().get(1).getID(), 1);
    }

    @Test
    public void throwsRequestExceptionOnly() throws JSONRPC2SessionException {
        JSONRPC2Session session = getMockSession();
        RequestHandler handler = new RequestHandler(session);
        JSONRPC2SessionException sessionException =
                new JSONRPC2SessionException("Doh!", JSONRPC2SessionException.NETWORK_EXCEPTION);

        when(session.send(any(JSONRPC2Request.class))).thenThrow(sessionException);

        try {
            handler.makeRequest(getValidBuilder());
        } catch(RequestException e) {
            assertEquals(sessionException, e.getCause());
        }
    }

    protected JSONRPC2Session getMockSession() {
        return mock(JSONRPC2Session.class);
    }

    protected RequestBuilder getValidBuilder() {
        return RequestBuilder.of("one", 1).setMethodName("methodName");
    }
}
