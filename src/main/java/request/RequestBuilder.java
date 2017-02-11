package request;

import com.google.common.collect.ImmutableMap;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
import lombok.Getter;

import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * Class to create JSONRPC2 Requests.
 * @author dani
 */
public class RequestBuilder {
    @Getter
    private ImmutableMap.Builder<String, Object> mapBuilder;
    private String methodName;

    public RequestBuilder() {
        mapBuilder = new ImmutableMap.Builder();
    }

    public RequestBuilder put(String key, Object value) {
        mapBuilder.put(key, value);
        return this;
    }

    public RequestBuilder setMethodName(String name) {
        this.methodName = name;
        return this;
    }

    public JSONRPC2Request build(Integer requestId) {
        checkNotNull(requestId);
        checkState(this.methodName !=  null,
                "Please set method name before building with .setMethodName(String).");

        return new JSONRPC2Request(methodName, mapBuilder.build(), requestId);
    }


    /**
     * Example: JSONRPC2Request =
     *  request.RequestBuilder.of("apiKey", apikey, "n", n).setMethodName("methodName")build(requestId);
     * This is needed in order to have some literal-ish construction,
     * but yet still remain able to use the builder of the ImmutableMap.
     * TODO(dani): This looks ugly, do something about it.
     */
    public static RequestBuilder of(String k1, Object v1) {
        return new RequestBuilder().put(k1, v1);
    }

    public static RequestBuilder of(String k1, Object v1, String k2, Object v2) {
        return new RequestBuilder().put(k1, v1).put(k2, v2);
    }

    public static RequestBuilder of(
            String k1, Object v1, String k2, Object v2, String k3, Object v3) {
        return new RequestBuilder().put(k1, v1).put(k2, v2).put(k3, v3);
    }

    public static RequestBuilder of(
            String k1, Object v1, String k2, Object v2, String k3, Object v3, String k4, Object v4) {
        return new RequestBuilder().put(k1, v1).put(k2, v2).put(k3, v3).put(k4, v4);
    }

    public static RequestBuilder of(
            String k1, Object v1, String k2, Object v2, String k3, Object v3, String k4, Object v4, String k5, Object v5) {
        return new RequestBuilder()
                .put(k1, v1).put(k2, v2).put(k3, v3).put(k4, v4).put(k5, v5);
    }

    //If need more than 5 params, use .put(). ^

}
