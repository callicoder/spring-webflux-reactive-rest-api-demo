package domain;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.core.codec.DecodingException;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import lombok.NoArgsConstructor;
import org.springframework.boot.jackson.JsonComponent;
import reactor.core.Exceptions;

@JsonComponent
@NoArgsConstructor
public class ZonedDateTimeDeserializer extends JsonDeserializer<ZonedDateTime> {

    @Override public ZonedDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
        throws IOException {
        try {
            return ZonedDateTime.parse(jsonParser.getValueAsString(), DateTimeFormatter.ISO_DATE);
        }
        catch (JsonMappingException | DateTimeException | DecodingException var) {
            throw Exceptions.propagate(new IllegalStateException("local date time format error, must match yyyy-MM-dd"));
        }
    }
}
