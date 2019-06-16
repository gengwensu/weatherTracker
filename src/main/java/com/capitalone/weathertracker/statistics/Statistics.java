package com.capitalone.weathertracker.statistics;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Statistics {
  @JsonProperty("min") MIN,
  @JsonProperty("max") MAX,
  @JsonProperty("average") AVERAGE,
}
