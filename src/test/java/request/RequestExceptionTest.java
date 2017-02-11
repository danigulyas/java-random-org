package request;

import com.thetransactioncompany.jsonrpc2.client.JSONRPC2SessionException;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.mock;

/**
 * @author dani
 */
public class RequestExceptionTest {

    @Test
    public void instantiatedSuccessfully() {
        JSONRPC2SessionException sessionException = mock(JSONRPC2SessionException.class);
        RequestException requestException = new RequestException("Message", sessionException);

        assertEquals(requestException.getCause(), sessionException);
    }

    @Test(expected = NullPointerException.class)
    public void throwsWhenInstantiatedWithNullMessage() {
        JSONRPC2SessionException sessionException = mock(JSONRPC2SessionException.class);
        new RequestException(null, sessionException);
    }

    @Test(expected = NullPointerException.class)
    public void throwsWhenInstantiatedWithNullCause() {
        new RequestException("Message", null);
    }
}
