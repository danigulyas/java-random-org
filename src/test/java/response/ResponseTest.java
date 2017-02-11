package response;

import org.junit.Test;
import response.mapper.Result;

import static junit.framework.TestCase.*;

/**
 * @author dani
 */
public class ResponseTest extends ResponseBaseTest {

    @Test(expected = RuntimeException.class)
    public void throwsInvalidArgumentErrorWhenInstantiatedWithNullError() {
        ResponseError response = null;
        new Response(response);
    }

    @Test(expected = RuntimeException.class)
    public void throwsInvalidARgumentErrorWhenInstantiatedWithNullResult() {
        Result<Integer> result = null;
        new Response(result);
    }

    @Test
    public void initiatedSuccessfullyFromErrorResponse() {
        ResponseError responseError = getResponseErrorMock();
        Response<Integer> response = new Response(responseError);

        assertFalse(response.isSuccessful());
        assertEquals(response.getError(), responseError);
    }

    @Test
    public void initiatedSuccessfullyFromResult() {
        Result<Integer> result = getResultMock();
        Response<Integer> response = new Response(result);

        assertTrue(response.isSuccessful());
        assertEquals(response.getResult(), result);
    }
}
