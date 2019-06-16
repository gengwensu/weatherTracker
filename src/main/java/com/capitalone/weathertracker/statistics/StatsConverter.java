package com.capitalone.weathertracker.statistics;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
class StatsConverter implements Converter<String, Statistics> {
  private final ObjectMapper mapper;

  public StatsConverter(ObjectMapper mapper) {
    this.mapper = mapper;
  }

  @Override
  public Statistics convert(String source) {
    return mapper.convertValue(source, Statistics.class);
  }
}
