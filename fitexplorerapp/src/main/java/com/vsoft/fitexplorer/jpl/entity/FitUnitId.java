package com.vsoft.fitexplorer.jpl.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FitUnitId implements Serializable {
    private FitActivity fitActivity;
    long timestamp;

}
