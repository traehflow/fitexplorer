package com.vsoft.fitexplorer.service.impl;

import com.garmin.fit.DateTime;
import com.vsoft.fitexplorer.jpl.FitRepository;
import com.vsoft.fitexplorer.jpl.UserRepository;
import com.vsoft.fitexplorer.jpl.entity.FitActivity;
import com.vsoft.fitexplorer.jpl.entity.FitUnit;
import com.vsoft.fitexplorer.parsing.garmin.Coordinate;
import com.vsoft.fitexplorer.parsing.garmin.FitFileData;
import io.jenetics.jpx.GPX;
import io.jenetics.jpx.Track;
import io.jenetics.jpx.TrackSegment;
import io.jenetics.jpx.WayPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ActivityService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FitRepository fitRepository;

    @Autowired
    private FitFileParserService fitFileParserService;


    public void saveActivity(InputStream fitFileStream, String activityName, String activityId) throws FileNotFoundException {
        var fitFileData = fitFileParserService.parseFitFile(fitFileStream);
        saveActivity(fitFileData, activityName, activityId);
    }

    public void saveActivity(FitFileData fitFileData, String activityName, String activityId) throws FileNotFoundException {
        List<Coordinate> track = fitFileData.getCoordinates();
        var user = userRepository.loadUser("wolfheart@mail.com");
        FitActivity fitActivity = new FitActivity();
        fitActivity.setUserData(user);
        fitActivity.setStartTime(0L);

        fitActivity.setFitActivityType(fitFileParserService.convert(fitFileData.getSport()));

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
        fitActivity.setStartTime(fitFileData.getStartTimeLocal() == null
                ? null : fitFileData.getStartTimeLocal().getTime());

        fitRepository.save(fitActivity);

        // Keep in mind that some activities does not have coordinates.
        if (!track.isEmpty()) {
            track.stream().forEach(x -> {
                FitUnit fitUnit = new FitUnit();
                fitUnit.setFitActivity(fitActivity);
                // I don't think that there are activity tracking without timestamps, but let it be safe.
                if (x.getTimestamp() != null) {
                    fitUnit.setTimestamp(x.getTimestamp().getTimestamp());
                }
                // Some activities may not have latitude and longitude, for example indoor swimming
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

    public String saveGpxFile(InputStream inputStream) throws IOException {
        GPX gpx = GPX.read(inputStream);
        StringBuilder result = new StringBuilder();
        FitFileData fitFileData = new FitFileData();
        fitFileData.setActivityId((long) gpx.hashCode());
        var metadata = gpx.getMetadata();
        if(metadata.isPresent()) {
            fitFileData.setActivityName(metadata.get().getName().orElse(""));
            fitFileData.setDescription(metadata.get().getDescription().orElse(""));
            fitFileData.setStartTimeLocal(Date.from(metadata.get().getTime().get().toInstant()));
        }
        List<Coordinate> list = new ArrayList<>();

        fitFileData.setCoordinates(list);

        for (WayPoint wp : gpx.getWayPoints()) {
            result.append("Waypoint: ").append(wp).append("<br>\n");
        }
        for (Track track : gpx.getTracks()) {
            fitFileData.setActivityName(String.join(", ", fitFileData.getActivityName(), track.getName().orElse(null)));
            fitFileData.setDescription(String.join(", ", fitFileData.getDescription(), track.getDescription().orElse(null)));
        }

        for (Track track : gpx.getTracks()) {
            result.append("Track: ").append(track).append(", Track type: ").append(track.getType()).append("<br>\n");
            for (TrackSegment segment : track.getSegments()) {
                for (WayPoint wp : segment.getPoints()) {
                    Coordinate f = new Coordinate();

                    f.setLatitude(wp.getLatitude().doubleValue());
                    f.setLongitude(wp.getLongitude().doubleValue());
                    f.setAltitude(wp.getElevation().get().floatValue());
                    f.setTimestamp(new DateTime(wp.getTime().get().toInstant().toEpochMilli()));

                    list.add(f);

                    result.append("Track Point: ").append(wp).append(", time:").append(wp.getTime().orElse(null)).append("<br>\n");
                }
            }
        }
        fitFileData.setCoordinates(list);
        saveActivity(fitFileData, fitFileData.getActivityName(), String.valueOf(fitFileData.getActivityId()));
        return result.toString();
    }
}
