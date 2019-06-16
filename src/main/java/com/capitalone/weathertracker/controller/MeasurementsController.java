package com.capitalone.weathertracker.controller;

import com.capitalone.weathertracker.measurements.Measurement;
import com.capitalone.weathertracker.abstractions.MeasurementStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;


import javax.validation.Valid;

@RestController
@RequestMapping("/measurements")
public class MeasurementsController {
  private final MeasurementStore store;
  private final DateTimeFormatter dateTimeFormatter;
  Logger logger = LoggerFactory.getLogger(MeasurementsController.class);

  public MeasurementsController(MeasurementStore store, DateTimeFormatter dateTimeFormatter) {
    this.store = store;
    this.dateTimeFormatter = dateTimeFormatter;
  }

  // features/01-measurements/01-add-measurement.feature
  @PostMapping
  public ResponseEntity<?> createMeasurement(@Valid @RequestBody Measurement measurement) {
    logger.debug("POST payload : timestamp " + measurement.getTimestamp().toString());
    logger.debug(" metrics : " + measurement.getMetrics().toString());
    store.add(measurement);
    return ResponseEntity
      .created(URI.create("/measurements/" +  dateTimeFormatter.format(measurement.getTimestamp())))
      .build();
  }

  // features/01-measurements/02-get-measurement.feature
  @GetMapping("/{timestamp:.+}")
  public ResponseEntity<Measurement> getMeasurement(@PathVariable ZonedDateTime timestamp) {
    Measurement measurement = store.fetch(timestamp);

    if (measurement != null) {
      return ResponseEntity.ok(measurement);
    } else {
      return ResponseEntity.notFound().build();
    }
  }
}
