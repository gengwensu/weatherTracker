package com.capitalone.weathertracker.controller;

import java.time.ZonedDateTime;
import java.util.List;

import com.capitalone.weathertracker.measurements.Measurement;
import com.capitalone.weathertracker.abstractions.MeasurementQueryService;

import com.capitalone.weathertracker.statistics.AggregateResult;
import com.capitalone.weathertracker.abstractions.MeasurementAggregator;
import com.capitalone.weathertracker.statistics.Statistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stats")
public class StatsController {
  private final MeasurementQueryService queryService;
  private final MeasurementAggregator aggregator;
  Logger logger = LoggerFactory.getLogger(StatsController.class);

  public StatsController(MeasurementQueryService queryService, MeasurementAggregator aggregator) {
    this.queryService = queryService;
    this.aggregator = aggregator;
  }

  @GetMapping
  public List<AggregateResult> getStats(
    @RequestParam("metric") List<String> metrics,
    @RequestParam("stat") List<Statistics> stats,
    @RequestParam("fromDateTime") ZonedDateTime from,
    @RequestParam("toDateTime") ZonedDateTime to
    ) {
      logger.debug("metric: " + metrics.toString());
      logger.debug("stat: " + stats.toString());
      logger.debug("to: " + to.toString() + " from: " + from.toString());
      List<Measurement> measurements = queryService.queryDateRange(from, to);
      return aggregator.analyze(measurements, metrics, stats);
  }
}
