package com.capitalone.weathertracker.measurements;

import com.capitalone.weathertracker.abstractions.MeasurementStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.HashMap;

/**
 * Implementation of MeasurementStore interface with a HashMap
 */
@Service
public class MeasurementStoreImpl implements MeasurementStore {
    private HashMap<ZonedDateTime, Measurement> measurementHM;

    @Autowired
    public MeasurementStoreImpl(HashMap<ZonedDateTime, Measurement> measurementHM){
        this.measurementHM = measurementHM;
    }

    /**
     * Method to add a measurement
     * @param measurement
     */
    public void add(Measurement measurement) {
        measurementHM.put(measurement.getTimestamp(), measurement);
    }

    /**
     * Method to fetch a measurement
     * @param timestamp
     * @return measurement
     */
    public Measurement fetch(ZonedDateTime timestamp){
        for (ZonedDateTime t : measurementHM.keySet()){
            if (timestamp.isEqual(measurementHM.get(t).getTimestamp())){
                return measurementHM.get(t);
            }
        }
        return null;
    }
}
