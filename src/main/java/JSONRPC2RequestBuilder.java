import com.google.common.collect.ImmutableMap;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;

import java.util.Map;

/**
 * Class to create JSONRPC2 Requests.
 * @author dani
 */
public class JSONRPC2RequestBuilder<K,V> {
    private ImmutableMap.Builder<K, V> mapBuilder;

    public JSONRPC2RequestBuilder() {
        mapBuilder = new ImmutableMap.Builder();
    }

    public JSONRPC2RequestBuilder put(K key, V value) { mapBuilder.put(key, value); return this; }
    public JSONRPC2Request build(String methodName, Integer requestId) {
        return new JSONRPC2Request(methodName, (Map<String, Object>) mapBuilder.build(), requestId);
    }


    /**
     * Example: JSONRPC2Request = JSONRPC2RequestBuilder.of("apiKey", apikey, "n", n).build(methodName, requestId);
     * This is needed in order to have some literal-ish construction, but yet still remain able to use the builder of
     * the ImmutableMap.
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
        return new JSONRPC2RequestBuilder().put(k1, v1).put(k2, v2).put(k3, v3).put(k4, v4).put(k5, v5);
    }

    public static <K, V> JSONRPC2RequestBuilder<K, V> of(
            K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6) {
        return new JSONRPC2RequestBuilder().put(k1, v1).put(k2, v2).put(k3, v3).put(k4, v4).put(k5, v5).put(k6, v6);
    }

    //If need more than 6 params, use .put(). ^

}
