package com.capitalone.weathertracker.statistics;

import com.capitalone.weathertracker.abstractions.MeasurementAggregator;
import com.capitalone.weathertracker.measurements.Measurement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service to perform measurement aggregations
 */
@Service
public class MeasurementAggregatorImpl implements MeasurementAggregator {
    Logger logger = LoggerFactory.getLogger(MeasurementAggregatorImpl.class);
    /**
     * Method to aggregate
     */
    @Override
    public List<AggregateResult> analyze(List<Measurement> measurements, List<String> metrics, List<Statistics> stats) {
        List<AggregateResult> alist = new ArrayList<>();
        HashMap<String, Double> totalMap = new HashMap<>(); // <metric,total>
        HashMap<String, Integer> countsMap = new HashMap<>(); // <metric,counts>
        HashMap<String, Double> minMap = new HashMap<>(); // <metric,min>
        HashMap<String, Double> maxMap = new HashMap<>(); // <metric,max>
        Set<String> metricsSet = new HashSet<>(metrics); // de-dupe metrics
        Set<Statistics> statsSet = new HashSet<>(stats); // de-dup stats
        logger.debug("metricsSet: " + metricsSet.toString());
        logger.debug("statsSet: " + statsSet.toString());

        Set<String> mset = new HashSet<>(); // set of metrics in measurements

        measurements.forEach(m-> mset.addAll(m.getMetrics().keySet())); // collect all metrics in measurements
        // initialize 4 statsMaps
        for (String s : mset) {
            totalMap.put(s, 0.0);
            countsMap.put(s, 0);
            minMap.put(s, Double.MAX_VALUE);
            maxMap.put(s, Double.MIN_VALUE);
        }
        logger.debug("mset: " + mset.toString());
        for (Measurement m : measurements) {
            for(Map.Entry<String, Double> entry : m.getMetrics().entrySet()) {
                String key = entry.getKey();
                Double value = entry.getValue();

                totalMap.put(key, totalMap.get(key)+value);
                countsMap.put(key, countsMap.get(key) + 1);
                minMap.put(key, Math.min(minMap.get(key), value));
                maxMap.put(key, Math.max(maxMap.get(key), value));
            }
        }
        logger.debug("totalMap: " + totalMap);
        logger.debug("countsMap: " + countsMap);
        logger.debug("minMap: " + minMap);
        logger.debug("maxMap: " + maxMap);
        for (String metric : metricsSet) { // loop through metrics list
            logger.debug("metric: " + metric);
            if(mset.contains(metric)) { //
                int count = countsMap.get(metric).intValue();
                logger.debug("count: " + count);
                if (count > 0) {
                    for (Statistics stat : statsSet) { // loop through stats list
                        logger.debug("stat: " + stat.toString());
                        AggregateResult oneAR = null;
                        switch (stat) {
                            case MAX:
                                oneAR = new AggregateResult(metric, Statistics.MAX, maxMap.get(metric).doubleValue());
                                alist.add(oneAR);
                                logger.debug("MAX, added: " + oneAR.getMetric() + " | " + oneAR.getStatistics().toString() + " | " + oneAR.getValue());
                                break;

                            case MIN:
                                oneAR = new AggregateResult(metric, Statistics.MIN, minMap.get(metric).doubleValue());
                                alist.add(oneAR);
                                logger.debug("MIN, added: " + oneAR.getMetric() + " | " + oneAR.getStatistics().toString() + " | " + oneAR.getValue());
                                break;

                            case AVERAGE:
                                oneAR = new AggregateResult(metric, Statistics.AVERAGE, totalMap.get(metric).doubleValue() / count);
                                alist.add(oneAR);
                                logger.debug("AVERAGE added: " + oneAR.getMetric() + " | " + oneAR.getStatistics().toString() + " | " + oneAR.getValue());
                                break;

                            default:
                                logger.error("This can not happen!");
                        }
                    }
                }
            }
        }

        return alist;
    }
}