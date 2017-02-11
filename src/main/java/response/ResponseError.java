package response;

import com.thetransactioncompany.jsonrpc2.JSONRPC2Error;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;
import lombok.Getter;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * @author dani
 */
public class ResponseError {
    @Getter public final String message;
    @Getter public final Integer code;
    @Getter public final Object data;
    @Getter public final Object requestId;

    public ResponseError(JSONRPC2Response response) {
        checkArgument(!response.indicatesSuccess(), "Result was successful, there's no error to parse.");

        this.requestId = response.getID();

        JSONRPC2Error error = response.getError();
        this.message = error.getMessage();
        this.code = error.getCode();
        this.data = error.getData();
    }
}
