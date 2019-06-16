package com.capitalone.weathertracker.statistics;

import com.fasterxml.jackson.annotation.JsonGetter;

public class AggregateResult {
  private String metric;
  private Statistics statistics;
  private double value;

  public AggregateResult(String metric, Statistics statistics, double value) {
    this.metric = metric;
    this.statistics = statistics;
    this.value = value;
  }

  @JsonGetter("metric")
  public String getMetric() {
    return this.metric;
  }

  @JsonGetter("stat")
  public Statistics getStatistics() {
    return this.statistics;
  }

  @JsonGetter("value")
  public double getValue() {
    return this.value;
  }
}
