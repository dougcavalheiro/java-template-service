package com.viafoura.template.microservice.domain.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@JsonSerialize
public class StreamEvent {

    Map<String, Object> values;

    public void addCustomValue(String key, Object value) {
        this.values.put(key, value);
    }

    public void markAsVerified() {
        addCustomValue("verified", true);
    }

}
