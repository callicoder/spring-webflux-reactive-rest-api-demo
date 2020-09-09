package com.example.webfluxdemo.domain;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import lombok.NoArgsConstructor;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.core.codec.DecodingException;
import reactor.core.Exceptions;

@JsonComponent
@NoArgsConstructor
public class LocalDateDeserializer extends JsonDeserializer<LocalDate> {

    @Override public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
        throws IOException {
        try {
            return LocalDate.parse(jsonParser.getValueAsString(), DateTimeFormatter.ISO_DATE);
        }
        catch (JsonMappingException | DateTimeException | DecodingException var) {
            throw Exceptions.propagate(new IllegalStateException("local date format error, must match yyyy-MM-dd"));
        }
    }
}
