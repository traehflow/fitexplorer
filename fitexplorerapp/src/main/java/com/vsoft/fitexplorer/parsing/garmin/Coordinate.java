package com.vsoft.fitexplorer.parsing.garmin;

import com.garmin.fit.DateTime;
import com.vsoft.fitexplorer.jpl.entity.FitActivityType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Coordinate {
    private FitActivityType fitActivityType;
    private Double latitude;
    private Double longitude;
    private Float altitude;
    private Short heartRate;
    private DateTime timestamp;
    private Float distance;
    private Byte temperature;

}
