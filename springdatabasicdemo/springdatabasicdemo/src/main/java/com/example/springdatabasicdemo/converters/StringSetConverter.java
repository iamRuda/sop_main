package com.example.springdatabasicdemo.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Converter
public class StringSetConverter implements AttributeConverter<Set<String>, String> {

    @Override
    public String convertToDatabaseColumn(Set<String> set) {
        return set != null ? String.join(",", set) : "";
    }

    @Override
    public Set<String> convertToEntityAttribute(String joined) {
        return joined != null && !joined.isEmpty() ?
                Arrays.stream(joined.split(","))
                        .collect(Collectors.toSet()) :
                Set.of();
    }
}
