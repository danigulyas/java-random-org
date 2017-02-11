import com.fasterxml.jackson.core.type.TypeReference;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;
import request.RequestBuilder;
import request.RequestException;
import request.RequestHandler;
import response.Response;
import response.ResponseMapper;
import response.mapper.Result;

import java.net.MalformedURLException;
import java.net.URL;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Class to provide contextual information for the static client calls.
 * @author dani
 */
public class RandomApiContext {
    private final String apiKey;
    private final URL apiUrl;
    private final RequestHandler requestHandler;
    private final ResponseMapper responseMapper;

    public RandomApiContext(String apiKey, String apiUrl, RequestHandler requestHandler, ResponseMapper responseMapper)
            throws MalformedURLException {
        checkNotNull(apiKey);
        checkNotNull(apiUrl);

        this.apiKey = apiKey;
        this.apiUrl = new URL(apiUrl);

        if(requestHandler != null) {
            this.requestHandler = requestHandler;
        } else {
            this.requestHandler = new RequestHandler(this.apiUrl);
        }

        if(responseMapper != null) {
            this.responseMapper = responseMapper;
        } else {
            this.responseMapper = new ResponseMapper();
        }
    }
    public RandomApiContext(String apiKey, String apiUrl, RequestHandler requestHandler)
            throws MalformedURLException {
        this(apiKey, apiUrl, requestHandler, null);
    }
    public RandomApiContext(String apiKey, String apiUrl) throws MalformedURLException {
        this(apiKey, apiUrl, null);
    }
    public RandomApiContext(String apiKey) throws MalformedURLException {
        this(apiKey, "https://api.random.org/json-rpc/1/invoke");
    }

    //Methods
    public <T> Response<T> query(RequestBuilder builder, TypeReference<T> type) throws RequestException {
        builder.put("apiKey", apiKey);
        JSONRPC2Response response = this.requestHandler.makeRequest(builder);

        return responseMapper.mapResponse(response, type);
    }
}
