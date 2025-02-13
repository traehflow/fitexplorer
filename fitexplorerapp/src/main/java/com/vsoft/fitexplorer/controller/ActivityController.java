package com.vsoft.fitexplorer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vsoft.fitexplorer.UserProfile;
import com.vsoft.fitexplorer.dto.GarminAuthDetails;
import com.vsoft.fitexplorer.dto.MapMyRunAuthDetail;
import com.vsoft.fitexplorer.jpl.UserRepository;
import com.vsoft.fitexplorer.service.impl.ActivityService;
import com.vsoft.fitexplorer.service.impl.MapMyRunSyncService;
import com.vsoft.fitexplorer.service.impl.SyncService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;


@RestController
@RequestMapping("/activities/")
public class ActivityController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProfile userProfile;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private SyncService syncService;

    @Autowired
    private MapMyRunSyncService mapMyRunSyncService;

    //    @Secured(Roles.ROLE_PREFIX + Roles.TRAINEE)
    @GetMapping("/list")
    public List<FitActivityDTO> listActivitiesByUser() {
        return  activityService.listActivities(userProfile.getUserId());
    }

    @GetMapping("/laps")
    public List<PaceDetail> laps(int id) {
        return  activityService.getLaps(userProfile.getUserId(), id);
    }

  //  @Secured(Roles.ROLE_PREFIX + Roles.TRAINEE)
    @GetMapping("/val")
    @Transactional
    public FitActivityDTO showActivity(int id) {
        return activityService.retrieveActivity(id, userProfile.getUserId());
    }

    @GetMapping("/heatmap")
    public Map<Pair<Double, Double>, Long> heatmap() {
        return activityService.loadHeatMap(null);
    }

    @PostMapping("/sync")
    public ResponseEntity<String> sync(GarminAuthDetails garminAuthDetails) throws JsonProcessingException {
        syncTask(garminAuthDetails, userProfile);
        return ResponseEntity.accepted().body("Long task has started processing in the background.");
    }

    @PostMapping("/syncMapMyRun")
    public ResponseEntity<String> syncMapMyRun(MapMyRunAuthDetail mapMyRunAuthDetail) throws JsonProcessingException {
        syncTask(mapMyRunAuthDetail, userProfile);
        return ResponseEntity.accepted().body("Long task has started processing in the background.");
    }

    @PostMapping("uploadGpx")
    @Operation(summary = "Upload GPX file", description = "Uploads a GPX file and parses its content")
    public String uploadGpxFile(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return "File is empty";
        }

        try (InputStream inputStream = file.getInputStream()) {
            activityService.saveGpxFile(inputStream);
            return "done";
        } catch (IOException e) {
            e.printStackTrace();
            throw new HttpClientErrorException(BAD_REQUEST, "Error reading GPX file");
        }
    }

    @PostMapping("uploadTcx")
    @Operation(summary = "Upload TCX file", description = "Uploads a GPX file and parses its content")
    public ResponseEntity uploadTcxFile(@RequestParam("file2") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return new ResponseEntity<String>("File is empty", BAD_REQUEST);
        }
        try (InputStream inputStream = file.getInputStream()) {
            activityService.saveTcxFile(inputStream, "Uploaded activity");
            return new ResponseEntity(HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            throw new HttpClientErrorException(BAD_REQUEST, "Error reading GPX file");
        }
    }

    @Async("threadPoolTaskExecutor")
    public void syncTask(GarminAuthDetails garminAuthDetails, UserProfile userProfile) throws JsonProcessingException {
        syncService.sync(garminAuthDetails, userProfile.getUserId());
    }


    @Async("threadPoolMapMyRunTaskExecutor")
    public void syncTask(MapMyRunAuthDetail mapMyRunAuthDetail, UserProfile userProfile) throws JsonProcessingException {
        mapMyRunSyncService.sync(mapMyRunAuthDetail, userProfile.getUserId());
    }
}
