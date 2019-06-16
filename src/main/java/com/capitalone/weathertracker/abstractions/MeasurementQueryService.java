package com.capitalone.weathertracker.abstractions;

import com.capitalone.weathertracker.measurements.Measurement;

import java.util.List;
import java.time.ZonedDateTime;

public interface MeasurementQueryService {
  List<Measurement> queryDateRange(ZonedDateTime from, ZonedDateTime to);
}
