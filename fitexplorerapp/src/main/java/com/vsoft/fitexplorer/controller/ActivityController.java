package com.vsoft.fitexplorer.controller;

import com.vsoft.fitexplorer.Roles;
import com.vsoft.fitexplorer.jpl.FitRepository;
import com.vsoft.fitexplorer.jpl.entity.FitActivity;
import com.vsoft.fitexplorer.jpl.entity.FitUnit;
import io.swagger.annotations.ApiParam;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/activities/")
public class ActivityController {
    @Autowired
    private FitRepository fitRepository;

//    @Secured(Roles.ROLE_PREFIX + Roles.MERCHANT)
    @GetMapping("/list")
    public List<FitActivityDTO> listActivities() {
        return  convertA(fitRepository.listFitActivities());
    }

    private List<FitActivityDTO> convertA(List<FitActivity> fitActivities) {
        return  fitActivities.stream().map(x -> new FitActivityDTO(x.getId(), x.getStartTime() + 631065600, x.getOriginalFile(), List.of(), x.getFitActivityType(), x.getActivityId(), x.getActivityName(), x.getDescription(), x.getStartTimeLocal(), x.getDistance(), x.getDuration(), x.getElapsedDuration(), x.getMovingDuration(), x.getElevationGain(), x.getElevationLoss(), x.getAverageSpeed(), x.getMaxSpeed()))
                .collect(Collectors.toList());
    }

    private FitActivityDTO convertA(FitActivity x) {
        return new FitActivityDTO(x.getId(), x.getStartTime() + 631065600, x.getOriginalFile(),  convertAA(fitRepository.listFitActivityUnits(x)), x.getFitActivityType(), x.getActivityId(), x.getActivityName(), x.getDescription(), x.getStartTimeLocal(), x.getDistance(), x.getDuration(), x.getElapsedDuration(), x.getMovingDuration(), x.getElevationGain(), x.getElevationLoss(), x.getAverageSpeed(), x.getMaxSpeed());
    }

    private List<FitUnitDTO> convertAA(List<FitUnit> fitUnitList) {
        return fitUnitList.stream().map(x -> new FitUnitDTO(x.getLatitude(), x.getLongitude(), x.getAltitude(), x.getHeartRate(), x.getTimestamp() + 631065600, x.getDistance(), x.getTemperature())).collect(Collectors.toList());
    }

  //  @Secured(Roles.ROLE_PREFIX + Roles.MERCHANT)
    @GetMapping("/val")
    @Transactional
    public FitActivityDTO showActivity( int id) {
        return convertA(fitRepository.listFitActivity(id));
    }

    @GetMapping("/heatmap")
    public Map<Pair<Double, Double>, Long> heatmap() {
        return fitRepository.loadHeatMap(null);
    }
}
