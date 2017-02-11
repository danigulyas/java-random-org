package request;

import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
import org.junit.Test;
import org.junit.runner.Request;

import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

/**
 * @author dani
 */
public class RequestBuilderTest {

    @Test
    public void litralInstatiatorWorksCorrectly() {
        Map<String, Object> ofOne = RequestBuilder.of("a", 1).getMapBuilder().build();
        Map<String, Object> ofTwo = RequestBuilder.of("a", 1, "b", 2).getMapBuilder().build();
        Map<String, Object> ofThree = RequestBuilder.of("a", 1, "b", 2, "c", 3).getMapBuilder().build();
        Map<String, Object> ofFour = RequestBuilder.of("a", 1, "b", 2, "c", 3, "d", 4).getMapBuilder().build();
        Map<String, Object> ofFive = RequestBuilder.of("a", 1, "b", 2, "c", 3, "d", 4, "e", 5).getMapBuilder().build();

        assertEquals(ofOne.size(), 1);
        assertEquals(ofTwo.size(), 2);
        assertEquals(ofThree.size(), 3);
        assertEquals(ofFour.size(), 4);
        assertEquals(ofFive.size(), 5);

        assertEquals(ofFive.get("e"), 5);
        assertEquals(ofOne.get("a"), 1);
    }

    @Test(expected = IllegalStateException.class)
    public void throwsErrorWhenTryingToBuildWithoutMethodName() {
        RequestBuilder.of("a", 1).build(0);
    }

    @Test(expected = NullPointerException.class)
    public void throwsErrorWhenTryingToBuildWithoutRequestId() {
        Integer test = null;
        RequestBuilder.of("a", 1).setMethodName("method").build(test);
    }

    @Test
    public void assignsPropertiesCorrectly() {
        final Integer requestId = 11;
        final String methodName = "methodName";
        JSONRPC2Request request = RequestBuilder.of("one", 1).setMethodName(methodName).build(requestId);

        assertEquals(requestId, request.getID());
        assertEquals(methodName, request.getMethod());
        assertEquals(1, request.getNamedParams().size());
        assertTrue(request.getNamedParams().containsKey("one"));
        assertEquals(1, request.getNamedParams().get("one"));
    }
}
