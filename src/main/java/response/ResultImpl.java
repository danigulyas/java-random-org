package response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.joda.deser.DateTimeDeserializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.joda.time.DateTime;

import java.util.Date;
import java.util.List;

/**
 * @author dani
 */
@AllArgsConstructor
public class ResultImpl<T> implements Result<T> {
    @Getter @JsonProperty private List<T> data;
    @Getter @JsonProperty @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ssZZ") private DateTime completionTime;
}