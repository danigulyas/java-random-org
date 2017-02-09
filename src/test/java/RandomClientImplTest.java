import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Error;
import com.thetransactioncompany.jsonrpc2.client.JSONRPC2SessionException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import response.Response;

import java.io.IOException;
import java.net.MalformedURLException;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

/**
 * @author dani
 */
public class RandomClientImplTest {

    public final int PORT = 31337;
    @Rule public WireMockRule wireMockRule = new WireMockRule(PORT);
    public RandomClientImpl.Builder builder = null;
    public RandomClientImpl instance = null;

    @Before
    public void instantiate() throws MalformedURLException {
        if(builder == null)
            builder = new RandomClientImpl.Builder().url(String.format("http://localhost:%d/json-rpc/1/invoke", PORT));

        this.instance = builder.build();
    }

    @Test
    public void testGenerateIntegers() throws InterruptedException, IOException, JSONRPC2Error, JSONRPC2SessionException {
        stubFor(post(urlEqualTo("/json-rpc/1/invoke"))
                .willReturn(aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json; charset=utf-8")
                    .withBody("{\"jsonrpc\":\"2.0\",\"result\":{\"random\":{\"data\":[3,8,4,5,6,2,2,5,9,7],\"completionTime\":\"2017-02-09 11:26:13Z\"},\"bitsUsed\":33,\"bitsLeft\":999934,\"requestsLeft\":199998,\"advisoryDelay\":210},\"id\":1}")));

        Response<Integer> response = instance.generateIntegers(5, 0, 10, true, 10);

        verify(postRequestedFor(urlEqualTo("/json-rpc/1/invoke")));
    }
}
