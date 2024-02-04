package com.vsoft.fitexplorer.service.impl;

import com.vsoft.fitexplorer.jpl.FitRepository;
import com.vsoft.fitexplorer.jpl.UserRepository;
import com.vsoft.fitexplorer.jpl.entity.FitActivity;
import com.vsoft.fitexplorer.jpl.entity.FitUnit;
import com.vsoft.fitexplorer.parsing.garmin.Coordinate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

@Service
public class ActivityService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    FitRepository fitRepository;

    @Autowired
    private FitFileParserService fitFileParserService;

    public void saveActivity(InputStream fitFileStream, String activityName, String activityId) throws FileNotFoundException {
        this.saveActivity(fitFileStream, this.userRepository, this.fitRepository, activityName, activityId);
    }

    public void saveActivity(InputStream fitFileStream, UserRepository userRepository, FitRepository fitRepository, String activityName, String activityId) throws FileNotFoundException {
        var fitFileData = fitFileParserService.parseFitFile(fitFileStream);
        List<Coordinate> track = fitFileData.getCoordinates();
        var user = userRepository.loadUser("wolfheart@mail.com");
        FitActivity fitActivity = new FitActivity();
        fitActivity.setUserData(user);
        fitActivity.setStartTime(0L);
        if(!track.isEmpty()) {
            fitActivity.setFitActivityType(fitFileParserService.convert(fitFileData.getSport()));
            fitActivity.setStartTime(track.get(0).getTimestamp().getTimestamp());
            fitActivity.setOriginalFile("/home/vladimir/activities/" + activityId);
            fitActivity.setActivityName(activityName);
            fitActivity.setActivityId(activityId);
            fitActivity.setStartTimeLocal(fitFileData.getStartTimeLocal());
            fitActivity.setDistance(fitFileData.getDistance());
            fitActivity.setDuration(fitFileData.getDuration());
            fitActivity.setElapsedDuration(fitFileData.getElapsedDuration());
            fitActivity.setMovingDuration(fitFileData.getMovingDuration());
            fitActivity.setElevationGain(fitFileData.getElevationGain());
            fitActivity.setElevationLoss(fitFileData.getElevationLoss());
            fitActivity.setAverageSpeed(fitFileData.getAverageSpeed());
            fitActivity.setMaxSpeed(fitFileData.getMaxSpeed());

            fitRepository.save(fitActivity);

            track.stream().forEach(x -> {
                FitUnit fitUnit = new FitUnit();
                fitUnit.setFitActivity(fitActivity);
                fitUnit.setTimestamp(x.getTimestamp().getTimestamp());
                fitUnit.setLatitude(x.getLatitude());
                fitUnit.setLongitude(x.getLongitude());
                fitUnit.setAltitude(x.getAltitude());
                fitUnit.setDistance(x.getDistance());
                fitUnit.setHeartRate(x.getHeartRate());

                fitUnit.setHeartRate(x.getHeartRate());
                fitRepository.save(fitUnit);
            });
        }
    }
}
