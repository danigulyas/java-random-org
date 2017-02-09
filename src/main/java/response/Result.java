package response;

import lombok.Getter;
import org.joda.time.DateTime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @author dani
 */
public interface Result<T> {
    List<T> getData();
    DateTime getCompletionTime();
}
