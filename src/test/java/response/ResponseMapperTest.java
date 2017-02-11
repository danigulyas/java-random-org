package response;

import com.fasterxml.jackson.core.type.TypeReference;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Error;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Before;
import org.junit.Test;
import response.mapper.Random;
import response.mapper.Result;

import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.*;
import static org.assertj.core.api.Assertions.assertThat;
import static response.ResponseMapper.isSuccessfulResponse;

/**
 * @author dani
 */
public class ResponseMapperTest extends ResponseBaseTest {
    private ResponseMapper mapper;

    @Before
    public void setUp() {
        mapper = new ResponseMapper();
    }

    @Test
    public void isSuccessfulResponseWorksCorrectly() {
        JSONRPC2Response responseWithError = getRpcResponseMockWithError();
        JSONRPC2Response responseWithoutError = getRpcResponseMockWithoutError();

        assertFalse(isSuccessfulResponse(responseWithError));
        assertTrue(isSuccessfulResponse(responseWithoutError));
    }

    @Test
    public void mapResponseWorksCorrectlyWithErrorResponse() {
        JSONRPC2Error rpcError = getRpcErrorMock(MESSAGE, CODE, DATA);
        JSONRPC2Response rpcResponseWithError = getRpcResponseMock(rpcError, REQUEST_ID);

        Response<Integer> response = mapper.mapResponse(rpcResponseWithError, new TypeReference<Result<Integer>>() {});
        ResponseError error = response.getError();

        assertFalse(response.isSuccessful());
        assertEquals(response.getResult(), null);
        assertEquals(error.getMessage(), MESSAGE);
        assertEquals(error.getCode(), CODE);
        assertEquals(error.getData(), DATA);
    }

    @Test
    public void mapResponseWorksCorrectlyWithSuccessfulResponse() {
        JSONRPC2Response rpcResponse = getRpcResponseMock(EXAMPLE_PARSED_RESULT, REQUEST_ID);

        Response<Result<Integer>> response = mapper.mapResponse(rpcResponse, new TypeReference<Result<Integer>>() {});
        Result<Integer> result = response.getResult();

        assertTrue(response.isSuccessful());
    }

    @Test
    public void mapResponseMapsMetadataCorrectly() {
        JSONRPC2Response rpcResponse = getRpcResponseMock(EXAMPLE_PARSED_RESULT, REQUEST_ID);

        Response<Result<Integer>> response = mapper.mapResponse(rpcResponse, new TypeReference<Result<Integer>>() {});
        Result<Integer> result = response.getResult();

        assertTrue(response.isSuccessful());
        assertEquals(result.getBitsUsed(), EXAMPLE_PARSED_RESULT.get("bitsUsed"));
        assertEquals(result.getBitsLeft(), EXAMPLE_PARSED_RESULT.get("bitsLeft"));
        assertEquals(result.getRequestsLeft(), EXAMPLE_PARSED_RESULT.get("requestsLeft"));
        assertEquals(result.getAdvisoryDelay(), EXAMPLE_PARSED_RESULT.get("advisoryDelay"));
    }

    @Test
    public void mapResponseMapsRandomResultCorrectly() {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ssZZ");
        JSONRPC2Response rpcResponse = getRpcResponseMock(EXAMPLE_PARSED_RESULT, REQUEST_ID);

        Response<Result<Integer>> response = mapper.mapResponse(rpcResponse, new TypeReference<Result<Integer>>() {});
        Random<Integer> random = response.getResult().getRandom();

        Map<String, Object> randomInExampleResult = (Map<String, Object>) EXAMPLE_PARSED_RESULT.get("random");
        List<Integer> dataInRandomExample = (List<Integer>) ((Map<String, Object>) EXAMPLE_PARSED_RESULT.get("random")).get("data");

        assertEquals(dataInRandomExample.size(), random.getData().size());
        assertEquals(dataInRandomExample.get(0), random.getData().get(0));

        /**
         * To compare the completionTime we need a little trickery, Jackson + JodaTime converts each DateTime to UTC,
         * resulting to weird errors where the assert fails because one time carries a timezone and is
         * x hours behind, whereas the other (parsed by Jackson) doesn't but x hours later, where x is the time
         * difference of the timezone from local time.
         */
        DateTime parsedByImplementation = random.getCompletionTime();
        DateTime parsedHere = LocalDateTime.parse((String) randomInExampleResult.get("completionTime"), formatter)
                .toDateTime(DateTimeZone.UTC);

        assertThat(parsedHere.toDateTime(DateTimeZone.UTC)).isEqualTo(parsedByImplementation);
    }
}
