import com.google.common.collect.ImmutableMap;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;

import java.util.Map;

import static com.google.common.base.Preconditions.checkState;

/**
 * Class to create JSONRPC2 Requests.
 * @author dani
 * @param <K> Type of the Key in the map of parameters.
 * @param <V> Type of the Value in the map of parameters.
 */
public class JSONRPC2RequestBuilder<K, V> {
    private ImmutableMap.Builder<K, V> mapBuilder;
    private String methodName;

    public JSONRPC2RequestBuilder() {
        mapBuilder = new ImmutableMap.Builder();
    }

    public JSONRPC2RequestBuilder put(K key, V value) {
        mapBuilder.put(key, value);
        return this;
    }

    public JSONRPC2RequestBuilder setMethodName(String name) {
        this.methodName = name;
        return this;
    }

    public JSONRPC2Request build(Integer requestId) {
        checkState(this.methodName !=  null,
                "Please set method name before building with .setMethodName(String).");

        return new JSONRPC2Request(methodName, (Map<String, Object>) mapBuilder.build(), requestId);
    }


    /**
     * Example: JSONRPC2Request =
     *  JSONRPC2RequestBuilder.of("apiKey", apikey, "n", n).setMethodName("methodName")build(requestId);
     * This is needed in order to have some literal-ish construction,
     * but yet still remain able to use the builder of the ImmutableMap.
     */
    public static <K, V> JSONRPC2RequestBuilder<K, V> of(K k1, V v1) {
        return new JSONRPC2RequestBuilder().put(k1, v1);
    }

    public static <K, V> JSONRPC2RequestBuilder<K, V> of(K k1, V v1, K k2, V v2) {
        return new JSONRPC2RequestBuilder().put(k1, v1).put(k2, v2);
    }

    public static <K, V> JSONRPC2RequestBuilder<K, V> of(
            K k1, V v1, K k2, V v2, K k3, V v3) {
        return new JSONRPC2RequestBuilder().put(k1, v1).put(k2, v2).put(k3, v3);
    }

    public static <K, V> JSONRPC2RequestBuilder<K, V> of(
            K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
        return new JSONRPC2RequestBuilder().put(k1, v1).put(k2, v2).put(k3, v3).put(k4, v4);
    }

    public static <K, V> JSONRPC2RequestBuilder<K, V> of(
            K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
        return new JSONRPC2RequestBuilder()
                .put(k1, v1).put(k2, v2).put(k3, v3).put(k4, v4).put(k5, v5);
    }

    //If need more than 6 params, use .put(). ^

}
