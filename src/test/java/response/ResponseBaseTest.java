package response;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Error;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;
import response.ResponseError;
import response.mapper.Result;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author dani
 */
public class ResponseBaseTest {
    public final Integer REQUEST_ID = 12;
    public final String MESSAGE = "The API key you specified does not exist";
    public final Integer CODE = 400;
    public final List<Integer> DATA = ImmutableList.of(1, 2, 3, 4);

    /**
     * Mimicking a response of a generateIntegers call, after a successful response is parse by JSONRPC2.
     * - https://api.random.org/json-rpc/1/basic
     */
    public final Map<String, Object> EXAMPLE_RANDOM = ImmutableMap
            .of("completionTime", "2011-10-10 13:19:12Z", "data", DATA);

    public final Map<String, Object> EXAMPLE_PARSED_RESULT = ImmutableMap
            .of("bitsUsed", 16, "bitsLeft", 199984, "requestsLeft", 9999, "advisoryDelay", 0, "random", EXAMPLE_RANDOM);


    protected JSONRPC2Error getRpcErrorMock(String message, Integer code, Object data) {
        JSONRPC2Error error = mock(JSONRPC2Error.class);

        when(error.getMessage()).thenReturn(message);
        when(error.getCode()).thenReturn(code);
        when(error.getData()).thenReturn(data);

        return error;
    }

    protected JSONRPC2Error getRpcErrorMock() {
        return getRpcErrorMock(MESSAGE, CODE, DATA);
    }

    protected JSONRPC2Response getRpcResponseMock(JSONRPC2Error error, Object requestId) {
        JSONRPC2Response response = mock(JSONRPC2Response.class);

        when(response.indicatesSuccess()).thenReturn(false);
        when(response.getError()).thenReturn(error);
        when(response.getID()).thenReturn(requestId);

        return response;
    }

    protected JSONRPC2Response getRpcResponseMock(Object result, Object requestId) {
        JSONRPC2Response response = mock(JSONRPC2Response.class);

        when(response.indicatesSuccess()).thenReturn(true);
        when(response.getResult()).thenReturn(result);
        when(response.getID()).thenReturn(requestId);

        return response;
    }

    protected JSONRPC2Response getRpcResponseMockWithError() {
        return getRpcResponseMock(getRpcErrorMock(), REQUEST_ID);
    }

    protected JSONRPC2Response getRpcResponseMockWithoutError() {
        return getRpcResponseMock(DATA, REQUEST_ID);
    }

    protected ResponseError getResponseErrorMock() {
        return mock(ResponseError.class);
    }

    protected Result getResultMock() {
        return mock(Result.class);
    }
}
