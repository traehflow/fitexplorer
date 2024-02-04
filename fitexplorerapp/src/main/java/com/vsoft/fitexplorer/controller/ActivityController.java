package com.vsoft.fitexplorer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vsoft.fitexplorer.Roles;
import com.vsoft.fitexplorer.dto.AuthDetails;
import com.vsoft.fitexplorer.jpl.FitRepository;
import com.vsoft.fitexplorer.jpl.entity.FitActivity;
import com.vsoft.fitexplorer.jpl.entity.FitUnit;
import com.vsoft.fitexplorer.service.impl.GarminActivity;
import com.vsoft.fitexplorer.service.impl.SyncService;
import io.swagger.annotations.ApiParam;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
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

    @PostMapping("/sync")
    public Map<String, GarminActivity> sync(AuthDetails jwtToken) throws JsonProcessingException {
        return syncService.sync(jwtToken);

        /*
        String url = "https://connect.garmin.com/activitylist-service/activities/search/activities?limit=1000&start=20&_=1706304901809";

        HttpHeaders headers = new HttpHeaders();
        headers.set("authority", "connect.garmin.com");
        headers.set("accept", "application/json, text/javascript, **; q=0.01");
        headers.set("accept-language", "en-US,en;q=0.9,bg-BG;q=0.8,bg;q=0.7");
        headers.set("authorization", "Bearer " + jwtToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        String responseBody = responseEntity.getBody();
        int statusCode = responseEntity.getStatusCodeValue();
        System.out.println("Response Code: " + statusCode);
        System.out.println("Response Body: " + responseBody);
        */
    }
}
