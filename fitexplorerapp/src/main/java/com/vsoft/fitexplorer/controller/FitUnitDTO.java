package com.vsoft.fitexplorer.controller;

import com.garmin.fit.DateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FitUnitDTO {
    private Double latitude;
    private Double longitude;
    private Float altitude;
    private Short heartRate;
    private long timestamp;
    private Float distance;
    private Byte temperature;
}
