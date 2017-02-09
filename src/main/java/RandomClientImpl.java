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

    public Response<Integer> generateIntegers(int n, int min, int max, Boolean replacement, Integer base) throws JSONRPC2SessionException, JSONRPC2Error, IOException {
        ImmutableMap.Builder<String, Object> builder = ImmutableMap.<String, Object>builder()
                .put("apiKey", this.apiKey)
                .put("n", n)
                .put("min", min)
                .put("max", max);

        if(replacement != null) builder.put("replacement", replacement);
        if(base != null) builder.put("base", base);

        JSONRPC2Request request = new JSONRPC2Request("generate", builder.build(), requestId);
        JSONRPC2Response response = this.session.send(request);

        if(!response.indicatesSuccess()) throw response.getError();

        String result = response.getResult().toString();
        return mapJsonToObject(result, new TypeReference<ResponseImpl<Integer>>() {});
    }

    private <T> T mapJsonToObject(String json, TypeReference<T> type) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JodaModule());
        return mapper.readValue(json, type);
    }
 }
