package com.example.webfluxdemo.utils;

import com.example.webfluxdemo.domain.ObjectMapperConfiguration;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.function.Supplier;
import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public interface JsonUtils {
    String FILE_EXCEPTION        = "File: {%s}, error: {%s}";
    String JSON_STRING_EXCEPTION = "json: {%s}, error: {%s}";
    String JSON_STREAM_EXCEPTION = "Stream: {%s}, error: {%s}";

    Supplier<ObjectMapper> OBJECT_MAPPER = () ->
        new ObjectMapperConfiguration(new ObjectMapper()).getObjectMapper();

    static JSONObject readJson(final File file) {
        try {
            return new JSONObject(new JSONTokener(FileUtils.readFileToString(file, StandardCharsets.UTF_8)));
        }
        catch (IOException | JSONException e) {
            throw new IllegalStateException(String.format(FILE_EXCEPTION, file, e));
        }
    }

    static <T> T getObjectFromJson(final File file, final Class<T> classType) {
        try {
            return OBJECT_MAPPER
                .get()
                .readValue(file, classType);
        }
        catch (IOException e) {
            throw new IllegalStateException(String.format(FILE_EXCEPTION, file, e));
        }
    }

    static <T> T getObjectFromJson(final String jsonString, final Class<T> classType) {
        try {
            return OBJECT_MAPPER
                .get()
                .readValue(jsonString, classType);
        }
        catch (IOException e) {
            throw new IllegalStateException(String.format(JSON_STRING_EXCEPTION, jsonString, e));
        }
    }

    static <T> T getObjectFromJson(final InputStream jsonStream, final Class<T> classType) {
        try {
            return OBJECT_MAPPER
                .get()
                .readValue(jsonStream, classType);
        }
        catch (IOException e) {
            throw new IllegalStateException(String.format(JSON_STREAM_EXCEPTION, jsonStream, e));
        }
    }

    static <T> List<T> getObjectListFromJson(final File file, final Class<T> type) {
        final ObjectMapper mapper = OBJECT_MAPPER.get();
        try {
            final JavaType valueType = mapper
                .getTypeFactory()
                .constructCollectionType(List.class, type);
            return mapper.readValue(file, valueType);
        }
        catch (IOException e) {
            throw new IllegalStateException(String.format(FILE_EXCEPTION, file, e));
        }
    }

    static <T> List<T> getObjectListFromJson(final String jsonString, final Class<T> type) {
        final ObjectMapper mapper = OBJECT_MAPPER.get();
        try {
            final JavaType valueType = mapper
                .getTypeFactory()
                .constructCollectionType(List.class, type);
            return mapper.readValue(jsonString, valueType);
        }
        catch (IOException e) {
            throw new IllegalStateException(String.format(JSON_STRING_EXCEPTION, jsonString, e));
        }
    }

    static <T> List<T> getObjectListFromJson(final InputStream inputStream, final Class<T> type) {
        final ObjectMapper mapper = OBJECT_MAPPER.get();
        try {
            final JavaType valueType = mapper
                .getTypeFactory()
                .constructCollectionType(List.class, type);
            return mapper.readValue(inputStream, valueType);
        }
        catch (IOException e) {
            throw new IllegalStateException(String.format(JSON_STREAM_EXCEPTION, inputStream, e));
        }
    }

    static String getJsonStringFromPojo(final Object jsonObject) {
        try {
            return OBJECT_MAPPER
                .get()
                .writeValueAsString(jsonObject);
        }
        catch (IOException e) {
            throw new IllegalStateException(String.format(JSON_STRING_EXCEPTION, jsonObject, e));
        }
    }

}
