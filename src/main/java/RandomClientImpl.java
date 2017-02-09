import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.google.common.collect.ImmutableMap;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Error;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;
import com.thetransactioncompany.jsonrpc2.client.JSONRPC2Session;
import com.thetransactioncompany.jsonrpc2.client.JSONRPC2SessionException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import response.Response;
import response.ResponseImpl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dani
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class RandomClientImpl {
    private final String url;
    private final String apiKey;
    private final JSONRPC2Session session;
    private int requestId = 0;

    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JodaModule());

    public static class Builder {
        private String url = "https://api.random.org/json-rpc/1/invoke";
        private String apiKey = "00000000-0000-0000-0000-000000000000";
        private JSONRPC2Session session = null;

        public Builder() {}
        public Builder url(String url) { this.url = url; return this; }
        public Builder apiKey(String apiKey) { this.apiKey = apiKey; return this; }
        public Builder session(JSONRPC2Session session) { this.session = session; return this; }

        public RandomClientImpl build() throws MalformedURLException {
            if(this.session == null) this.session = new JSONRPC2Session(new URL(this.url));
            return new RandomClientImpl(url, apiKey, session);
        }
    }

    public Response<Integer> generateIntegers(int n, int min, int max)
            throws JSONRPC2SessionException, JSONRPC2Error, IOException { return generateIntegers(n, min, max, true, 10); }

    public Response<Integer> generateIntegers(int n, int min, int max, Boolean replacement, Integer base)
            throws JSONRPC2SessionException, JSONRPC2Error, IOException {

        JSONRPC2RequestBuilder requestBuilder =
                JSONRPC2RequestBuilder.of("n", n, "min", min, "max", max, "replacement", replacement, "base", base);

        return makeRequestAndConvert(requestBuilder, "generateIntegers", new TypeReference<ResponseImpl<Integer>>() {});
    }

    public Response<String> generateIntegersWithBaseHigherThanTen(int n, int min, int max, Boolean replacement, Integer base)
            throws JSONRPC2SessionException, JSONRPC2Error, IOException {

        JSONRPC2RequestBuilder requestBuilder =
                JSONRPC2RequestBuilder.of("n", n, "min", min, "max", max, "replacement", replacement, "base", base);

        return makeRequestAndConvert(requestBuilder, "generateIntegers", new TypeReference<ResponseImpl<String>>() {});
    }

    public <T> T makeRequestAndConvert(JSONRPC2RequestBuilder requestBuilder, String methodName, TypeReference<T> type)
            throws JSONRPC2SessionException, JSONRPC2Error, IOException {

        requestId++;

        //extend with apiKey for auth
        requestBuilder.put("apiKey", apiKey);

        JSONRPC2Response response = this.session.send(requestBuilder.build(methodName, requestId));

        if(!response.indicatesSuccess()) throw response.getError();

        return mapJsonToObject(response.getResult().toString(), type);
    }

    private <T> T mapJsonToObject(String json, TypeReference<T> type) throws IOException {
        return mapper.readValue(json, type);
    }
 }
