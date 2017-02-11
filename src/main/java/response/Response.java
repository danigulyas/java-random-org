package response;

import lombok.Getter;
import response.mapper.Result;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Simple generalization for the response.
 * @author dani
 */
public class Response<T> {
    @Getter private final ResponseError error;
    @Getter private final T result;

    public <T extends ResponseError> Response(T error) {
        checkNotNull(error);
        this.result = null;
        this.error = error;
    }

    public Response(T result) {
        checkNotNull(result);
        this.result = result;
        this.error = null;
    }

    public Boolean isSuccessful() {
        return this.error == null;
    }
}
