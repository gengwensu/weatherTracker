package com.capitalone.weathertracker.config;

import com.capitalone.weathertracker.measurements.Measurement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.ZonedDateTime;
import java.util.HashMap;

@Configuration
public class Config {

    @Bean
    public HashMap<ZonedDateTime, Measurement> createMeasurementHM() { // solving controller initialization problems
        return new HashMap<>();
    }
}
