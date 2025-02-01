package com.vsoft.fitexplorer.controller;

import lombok.Data;

@Data
public class PaceDetail {
    int minutes;
    int seconds;
    float elevationGain;
    float elevationLoss;
    float averageHeartbeat;
    float maxHeartbeat;
    float minHeartbeat;
}
