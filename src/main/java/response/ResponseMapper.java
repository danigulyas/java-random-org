package response;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;
import response.mapper.Result;

/**
 * @author dani
 */
public class ResponseMapper {
    public final ObjectMapper mapper;

    public ResponseMapper() {
        this.mapper = new ObjectMapper().registerModule(new JodaModule());
    }

    public <T> Response<T> mapResponse(JSONRPC2Response response, TypeReference<Result<T>> type) {
        //TODO(dani): MIGHT BE FISHY, double check on it!
        if(isSuccessfulResponse(response)) {
            //TODO(dani): see if there's easier ways
            return new Response((Result<T>) mapper.convertValue(response.getResult(), new TypeReference<Result<T>>() {}));
        } else {
            return new Response(new ResponseError(response));
        }
    }

    public static Boolean isSuccessfulResponse(JSONRPC2Response response) {
        return response.indicatesSuccess();
    }
}
