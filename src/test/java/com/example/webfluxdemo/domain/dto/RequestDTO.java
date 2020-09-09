package com.example.webfluxdemo.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RequestDTO {
    @JsonProperty("text")
    private String text;
}
