package com.vsoft.fitexplorer.parsing.garmin;

import com.garmin.fit.DateTime;
import com.vsoft.fitexplorer.jpl.entity.FitActivityType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class Coordinate {
    private final FitActivityType fitActivityType;
    private final Double latitude;
    private final Double longitude;
    private final Float altitude;
    private final Short heartRate;
    private final DateTime timestamp;
    private final Float distance;
    private final Byte temperature;

}
