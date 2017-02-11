package response;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;
import response.mapper.Result;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author dani
 */
public class ResponseMapper {
    public final ObjectMapper mapper;

    public ResponseMapper() {
        this.mapper = new ObjectMapper().registerModule(new JodaModule());
    }

    public <T> Response<T> mapResponse(JSONRPC2Response response, TypeReference type) {
        checkNotNull(response);
        checkNotNull(type);

        if(isSuccessfulResponse(response)) {
            return new Response((T) mapper.convertValue(response.getResult(), type));
        } else {
            return new Response(new ResponseError(response));
        }
    }

    public static Boolean isSuccessfulResponse(JSONRPC2Response response) {
        return response.indicatesSuccess();
    }
}
