package com.example.webfluxdemo.domain;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.NoArgsConstructor;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.core.codec.DecodingException;
import reactor.core.Exceptions;

//import com.sun.xml.internal.org.jvnet.mimepull.DecodingException;

@JsonComponent
@NoArgsConstructor
public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    @Override public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
        throws IOException {
        try {
            return LocalDateTime.parse(jsonParser.getValueAsString(), DateTimeFormatter.ISO_DATE);
        }
        catch (JsonMappingException | DateTimeException | DecodingException var) {
            throw Exceptions.propagate(new IllegalStateException("local date time format error, must match yyyy-MM-dd"));
        }
    }
}
