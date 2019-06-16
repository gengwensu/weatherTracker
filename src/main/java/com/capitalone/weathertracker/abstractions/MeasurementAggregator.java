package com.capitalone.weathertracker.abstractions;

import java.util.List;

import com.capitalone.weathertracker.measurements.Measurement;
import com.capitalone.weathertracker.statistics.AggregateResult;
import com.capitalone.weathertracker.statistics.Statistics;

public interface MeasurementAggregator {
  List<AggregateResult> analyze(List<Measurement> measurements, List<String> metrics, List<Statistics> stats);
}
