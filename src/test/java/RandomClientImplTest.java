import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Error;
import com.thetransactioncompany.jsonrpc2.client.JSONRPC2SessionException;
import lombok.Getter;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import response.Response;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.*;

/**
 * @author dani
 */
public class RandomClientImplTest {

    public final int PORT = 31337;
    @Rule
    public WireMockRule wireMockRule = new WireMockRule(PORT);
    public RandomClientImpl.Builder builder = null;
    public RandomClientImpl instance = null;

    @Before
    public void instantiate() throws MalformedURLException {
        if(builder == null)
            builder = new RandomClientImpl.Builder().url(String.format("http://localhost:%d/json-rpc/1/invoke", PORT));

        this.instance = builder.build();
    }

    @Test
    public void testGenerateResponsePosts() throws IOException, JSONRPC2Error, JSONRPC2SessionException {
        List<Integer> replyData = stubDummyResponse();
        Response<Integer> response = instance.generateIntegers(5, 0, 10);
        verify(postRequestedFor(urlEqualTo("/json-rpc/1/invoke")));
    }

    @Test
    public void testGenerateResponseReturnsCorrectly() throws IOException, JSONRPC2Error, JSONRPC2SessionException {
        List<Integer> replyData = stubDummyResponse();
        Response<Integer> response = instance.generateIntegers(5, 0, 10000);
        assertEquals(replyData, response.getRandom().getData());
    }

    @Test
    public void testSetApiKey() throws IOException, JSONRPC2Error, JSONRPC2SessionException {
        final String apiKey = "00000000-3133-7331-1337-000000000000";
        instance = builder.apiKey(apiKey).build();
        builder = null; //in order to be recreated next round

        stubDummyResponse();

        instance.generateIntegers(5, 0, 10000);

        verify(postRequestedFor(urlEqualTo("/json-rpc/1/invoke"))
                .withRequestBody(matching(".*\"apiKey\":\"" + apiKey + "\".*")));
    }

    public List<Integer> stubDummyResponse() {
        List<Integer> replyData = new ArrayList();
        for(int i = 0; i < 10; i++) replyData.add(i);

        stubFor(post(urlEqualTo("/json-rpc/1/invoke"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json; charset=utf-8")
                        .withBody(  "{" +
                                        "\"jsonrpc\":\"2.0\"," +
                                        "\"result\": {" +
                                            "\"random\": {" +
                                                "\"data\":" + replyData.toString() + "," +
                                                "\"completionTime\":\"2017-02-09 11:26:13Z\"" +
                                            "}," +
                                            "\"bitsUsed\":33," +
                                            "\"bitsLeft\":999934," +
                                            "\"requestsLeft\":199998," +
                                            "\"advisoryDelay\":210" +
                                        "}," +
                                        "\"id\":1" +
                                    "}")));

        return replyData;
    }
}
