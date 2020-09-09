package com.example.webfluxdemo.domain;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.UnaryOperator;
import lombok.NoArgsConstructor;
import org.springframework.boot.jackson.JsonComponent;

@JsonComponent
@NoArgsConstructor
public class ZonedDateTimeSerializer extends JsonSerializer<ZonedDateTime> {
    private static final String                ZULU_CHAR             = "Z";
    private static final String                ZULU_CHAR_REPLACEMENT = "+00";
    private static final UnaryOperator<String> HANDLE_ZULU_TIME      = (input) ->
        input.contains(ZULU_CHAR) ? input.replace(ZULU_CHAR, ZULU_CHAR_REPLACEMENT) : input;

    @Override public void serialize(ZonedDateTime zonedDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
        throws IOException {
        final String stringZonedDateTime = zonedDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssX"));
        jsonGenerator.writeString(HANDLE_ZULU_TIME.apply(stringZonedDateTime));
    }
}
