package response.mapper;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.joda.time.DateTime;

import java.util.List;

/**
 * @author dani
 */
@AllArgsConstructor
public class Random<T> {
    @Getter
    @JsonProperty
    private List<T> data;

    @Getter
    @JsonProperty
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ssZZ")
    private DateTime completionTime;
}
