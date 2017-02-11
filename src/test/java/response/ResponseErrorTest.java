package response;

import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * @author dani
 */
public class ResponseErrorTest extends ResponseBaseTest {

    @Test
    public void parsesErrorCorrectly() {
        JSONRPC2Response rpcResponseWithError = getRpcResponseMock(getRpcErrorMock(MESSAGE, CODE, DATA), REQUEST_ID);

        ResponseError response = new ResponseError(rpcResponseWithError);

        assertEquals(response.getRequestId(), REQUEST_ID);
        assertEquals(response.getMessage(), MESSAGE);
        assertEquals(response.getCode(), CODE);
        assertEquals(response.getData(), DATA);
    }

    @Test(expected = RuntimeException.class)
    public void throwsInvalidArgumentExceptionWhenCalledWithSuccessfulResponse() {
        JSONRPC2Response successfulResponse = getRpcResponseMock(DATA, REQUEST_ID);

        new ResponseError(successfulResponse);
    }
}
