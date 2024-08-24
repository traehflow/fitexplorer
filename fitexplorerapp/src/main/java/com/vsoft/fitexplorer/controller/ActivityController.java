package com.vsoft.fitexplorer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vsoft.fitexplorer.dto.AuthDetails;
import com.vsoft.fitexplorer.jpl.FitRepository;
import com.vsoft.fitexplorer.jpl.UserRepository;
import com.vsoft.fitexplorer.jpl.entity.FitActivity;
import com.vsoft.fitexplorer.jpl.entity.FitUnit;
import com.vsoft.fitexplorer.service.impl.ActivityService;
import com.vsoft.fitexplorer.service.impl.SyncService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/activities/")
public class ActivityController {
    @Autowired
    private FitRepository fitRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ActivityService activityService;

    @Autowired
    private SyncService syncService;

//    @Secured(Roles.ROLE_PREFIX + Roles.TRAINEE)
    @GetMapping("/list")
    public List<FitActivityDTO> listActivities() {
        return  convertA(fitRepository.listFitActivities());
    }

    private List<FitActivityDTO> convertA(List<FitActivity> fitActivities) {
        return  fitActivities.stream().map(x -> new FitActivityDTO(x.getId(), x.getStartTime(), x.getOriginalFile(), List.of(), x.getFitActivityType(), x.getActivityId(), x.getActivityName(), x.getDescription(), x.getStartTimeLocal(), x.getDistance(), x.getDuration(), x.getElapsedDuration(), x.getMovingDuration(), x.getElevationGain(), x.getElevationLoss(), x.getAverageSpeed(), x.getMaxSpeed()))
                .collect(Collectors.toList());
    }

    private FitActivityDTO convertA(FitActivity x) {
        return new FitActivityDTO(x.getId(), x.getStartTime(), x.getOriginalFile(),  convertAA(fitRepository.listFitActivityUnits(x)), x.getFitActivityType(), x.getActivityId(), x.getActivityName(), x.getDescription(), x.getStartTimeLocal(), x.getDistance(), x.getDuration(), x.getElapsedDuration(), x.getMovingDuration(), x.getElevationGain(), x.getElevationLoss(), x.getAverageSpeed(), x.getMaxSpeed());
    }
//631065600
    private List<FitUnitDTO> convertAA(List<FitUnit> fitUnitList) {
        return fitUnitList.stream().map(x -> new FitUnitDTO(x.getLatitude(), x.getLongitude(), x.getAltitude(), x.getHeartRate(), x.getTimestamp() + 631065600, x.getDistance(), x.getTemperature())).collect(Collectors.toList());
    }

  //  @Secured(Roles.ROLE_PREFIX + Roles.TRAINEE)
    @GetMapping("/val")
    @Transactional
    public FitActivityDTO showActivity( int id) {
        return convertA(fitRepository.listFitActivity(id));
    }

    @GetMapping("/heatmap")
    public Map<Pair<Double, Double>, Long> heatmap() {
        return fitRepository.loadHeatMap(null);
    }

    @PostMapping("/sync")
    public ResponseEntity<String> sync(AuthDetails jwtToken) throws JsonProcessingException {
        syncTask(jwtToken);
        return ResponseEntity.accepted().body("Long task has started processing in the background.");
    }

    @PostMapping("upload")
    @Operation(summary = "Upload GPX file", description = "Uploads a GPX file and parses its content")
    public String uploadGpxFile(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return "File is empty";
        }

        try (InputStream inputStream = file.getInputStream()) {
            return activityService.saveGpxFile(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            return "Error reading GPX file";
        }
    }

    @Async("threadPoolTaskExecutor")
    public void syncTask(AuthDetails jwtToken) throws JsonProcessingException {
        syncService.sync(jwtToken);
    }
}
