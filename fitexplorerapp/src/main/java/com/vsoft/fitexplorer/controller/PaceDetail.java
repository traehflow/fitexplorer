package com.vsoft.fitexplorer.controller;

import lombok.Data;

@Data
public class PaceDetail {
    int minutes;
    int seconds;
    float elevationGain;
    float elevationLoss;
    Short averageHeartbeat;
    Short maxHeartbeat;
    Short minHeartbeat;
}
