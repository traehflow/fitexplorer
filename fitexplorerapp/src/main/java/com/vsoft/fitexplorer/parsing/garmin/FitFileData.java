package com.vsoft.fitexplorer.parsing.garmin;

import com.garmin.fit.Sport;
import com.vsoft.fitexplorer.FitFileParser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FitFileData {
    private List<Coordinate> coordinates;
    private Sport sport;
    //==================
    Long activityId;
    String activityName;
    String description;
    Date startTimeLocal;
    Double distance;
    Double duration;
    Double elapsedDuration;
    Double movingDuration;
    Double elevationGain;
    Double elevationLoss;
    Double averageSpeed;
    Double maxSpeed;

}
