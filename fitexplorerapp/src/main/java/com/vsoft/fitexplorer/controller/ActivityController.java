package com.vsoft.fitexplorer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vsoft.fitexplorer.Roles;
import com.vsoft.fitexplorer.dto.AuthDetails;
import com.vsoft.fitexplorer.jpl.FitRepository;
import com.vsoft.fitexplorer.jpl.entity.FitActivity;
import com.vsoft.fitexplorer.jpl.entity.FitUnit;
import com.vsoft.fitexplorer.parsing.garmin.FitFileData;
import com.vsoft.fitexplorer.service.impl.GarminActivity;
import com.vsoft.fitexplorer.service.impl.SyncService;
import io.jenetics.jpx.GPX;
import io.jenetics.jpx.Track;
import io.jenetics.jpx.TrackSegment;
import io.jenetics.jpx.WayPoint;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.temporal.TemporalField;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/activities/")
public class ActivityController {
    @Autowired
    private FitRepository fitRepository;

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
            GPX gpx = GPX.read(inputStream);

            StringBuilder result = new StringBuilder();

            FitActivity fitActivity = new FitActivity();
            fitActivity.setActivityId(Integer.valueOf(gpx.hashCode()).toString());
            var metadata = gpx.getMetadata();
            if(metadata.isPresent()) {
                fitActivity.setActivityName(metadata.get().getName().orElse(""));
                fitActivity.setStartTime(metadata.get().getTime().get().toInstant().toEpochMilli());
                fitActivity.setDescription(metadata.get().getDescription().orElse(""));
                fitActivity.setStartTimeLocal(Date.from(metadata.get().getTime().get().toInstant()));
            }
            List<FitUnit> list = new ArrayList<>();

            fitActivity.setFitUnitList(list);

            for (WayPoint wp : gpx.getWayPoints()) {
                result.append("Waypoint: ").append(wp).append("<br>\n");
            }
            for (Track track : gpx.getTracks()) {
                fitActivity.setActivityName(String.join(", ", fitActivity.getActivityName(), track.getName().orElse(null)));
                fitActivity.setDescription(String.join(", ", fitActivity.getDescription(), track.getDescription().orElse(null)));
            }
            fitRepository.save(fitActivity);

            for (Track track : gpx.getTracks()) {
                result.append("Track: ").append(track).append(", Track type: ").append(track.getType()).append("<br>\n");
                for (TrackSegment segment : track.getSegments()) {
                    for (WayPoint wp : segment.getPoints()) {
                        FitUnit f = new FitUnit();
                        f.setLatitude(wp.getLatitude().doubleValue());
                        f.setLongitude(wp.getLongitude().doubleValue());
                        f.setAltitude(wp.getElevation().get().floatValue());
                        f.setTimestamp(wp.getTime().get().toInstant().toEpochMilli());
                        f.setFitActivity(fitActivity);
                        list.add(f);
                        fitRepository.save(f);
                        result.append("Track Point: ").append(wp).append(", time:").append(wp.getTime().orElse(null)).append("<br>\n");
                    }
                }
            }
            fitActivity.setFitUnitList(list);


            return result.toString();

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
