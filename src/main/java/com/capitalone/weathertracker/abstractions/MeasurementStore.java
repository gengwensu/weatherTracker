package com.capitalone.weathertracker.abstractions;

import com.capitalone.weathertracker.measurements.Measurement;

import java.time.ZonedDateTime;

public interface MeasurementStore {
  void add(Measurement measurement);

  Measurement fetch(ZonedDateTime timestamp);
}
