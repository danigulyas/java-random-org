import com.fasterxml.jackson.core.type.TypeReference;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import request.RequestBuilder;
import request.RequestException;
import request.RequestHandler;
import response.Response;
import response.ResponseBaseTest;
import response.mapper.Result;

import java.net.MalformedURLException;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * @author dani
 */
public class RandomApiContextTestResponse extends ResponseBaseTest {

    @Test(expected = NullPointerException.class)
    public void throwsWhenInstantiatedWithNullApiKey() throws MalformedURLException {
        String key = null;
        new RandomApiContext(key);
    }

    @Test(expected = NullPointerException.class)
    public void throwsWhenInstnatiatedWithNullUrl() throws MalformedURLException {
        String url = null;
        new RandomApiContext("a", url);
    }

    @Test(expected = MalformedURLException.class)
    public void throwsWhenMalformedUrl() throws MalformedURLException {
        new RandomApiContext("a", "http;//thislooksweird.com");
    }

    @Test
    public void attachesApiKeyToParameters() throws MalformedURLException, RequestException {
        String apiKey = "mykey";
        ArgumentCaptor<RequestBuilder> captor = ArgumentCaptor.forClass(RequestBuilder.class);
        RequestHandler handler = getMockedHandlerReturningErrorResponse();
        RandomApiContext context = new RandomApiContext(apiKey, "http://google.com", handler);

        try {
            context.query(getValidBuilder(), new TypeReference<Result<Integer>>() {});
        } catch(NullPointerException e) {
            // it'll throw null pointer because of the mock, but we don't mind, we only inspect the args
        }

        verify(handler, times(1)).makeRequest(captor.capture());

        Map<String, Object> parameters = captor.getAllValues().get(0).build(0).getNamedParams();
        assertTrue(parameters.containsKey("apiKey"));
        assertEquals(parameters.get("apiKey"), apiKey);
    }

    protected RequestHandler getMockedHandlerReturningErrorResponse() throws RequestException {
        RequestHandler handler = mock(RequestHandler.class);

        when(handler.makeRequest(any(RequestBuilder.class))).thenReturn(null);

        return handler;
    }

    protected RequestBuilder getValidBuilder() {
        return RequestBuilder.of("one", 1).setMethodName("methodName");
    }
}
