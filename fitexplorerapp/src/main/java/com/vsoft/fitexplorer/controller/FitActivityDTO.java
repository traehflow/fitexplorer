package com.vsoft.fitexplorer.controller;

import com.vsoft.fitexplorer.jpl.entity.FitActivityType;
import com.vsoft.fitexplorer.jpl.entity.FitUnit;
import com.vsoft.fitexplorer.jpl.entity.UserData;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FitActivityDTO {
    int id;
    long startTime;
    String originalFile;
    List<FitUnitDTO> fitUnitList;
    FitActivityType fitActivityType;
    //================================
    String activityId;
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
