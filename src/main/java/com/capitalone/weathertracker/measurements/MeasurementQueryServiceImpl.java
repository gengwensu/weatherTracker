package com.capitalone.weathertracker.measurements;

import com.capitalone.weathertracker.abstractions.MeasurementQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Service class to query and return stats
 */
@Service
public class MeasurementQueryServiceImpl implements MeasurementQueryService {
    private HashMap<ZonedDateTime, Measurement> measurementHM;

    @Autowired
    public MeasurementQueryServiceImpl(HashMap<ZonedDateTime, Measurement> measurementHM){
        this.measurementHM = measurementHM;
    }

    /**
     * Queries data btw a date range and return a list of measurements
     * @param from Timestamp including
     * @param to Timestamp until. excluded
     * @return List<Measurement></Measurement>
     */
    @Override
    public List<Measurement> queryDateRange(ZonedDateTime from, ZonedDateTime to) {
        List<Measurement> mlist = new ArrayList<>();

        for (ZonedDateTime t : measurementHM.keySet()) { // looping through all entries
            if (t.isEqual(from) || (t.isAfter(from) && t.isBefore(to))) {// include from but exclude to
                mlist.add(measurementHM.get(t)); // add to the list
            }
        }

        return mlist;
    }
}

