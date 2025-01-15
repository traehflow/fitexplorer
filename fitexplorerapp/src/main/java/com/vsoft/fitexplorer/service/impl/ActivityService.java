package com.vsoft.fitexplorer.service.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.garmin.fit.DateTime;
import com.vsoft.fitexplorer.importTypes.tcx.Lap;
import com.vsoft.fitexplorer.importTypes.tcx.TrainingCenterDatabase;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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


    private static final Logger logger = LoggerFactory.getLogger(ActivityService.class.getCanonicalName());

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
            List<FitUnit> list = new ArrayList<>();
            long lastTimestamp = 0;

            for(int i = 0; i < track.size(); ++i) {
            //track.forEach(x -> {
                Coordinate x = track.get(i);
                FitUnit fitUnit = new FitUnit();
                fitUnit.setFitActivity(fitActivity);
                // I don't think that there are activity tracking without timestamps, but let it be safe.
                if (x.getTimestamp() != null && x.getTimestamp().getTimestamp() != null
                   && x.getTimestamp().getTimestamp() != lastTimestamp) {

                    fitUnit.setTimestamp(x.getTimestamp().getTimestamp());
                    lastTimestamp = x.getTimestamp().getTimestamp();
                } else {
                    continue;
                }
                // Some activities may not have latitude and longitude, for example indoor swimming
                fitUnit.setLatitude(x.getLatitude());
                fitUnit.setLongitude(x.getLongitude());
                fitUnit.setAltitude(x.getAltitude());
                fitUnit.setDistance(x.getDistance());
                fitUnit.setHeartRate(x.getHeartRate());

                fitUnit.setHeartRate(x.getHeartRate());
                list.add(fitUnit);
                if(list.size() == 500) {
                    fitRepository.save(list);
                    list.clear();

                }
            }
            if(!list.isEmpty()) {
                fitRepository.save(list);
            }
        }
    }

    public void saveGpxFile(InputStream inputStream) throws IOException {
        GPX gpx = GPX.read(inputStream);
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

        if(logger.isTraceEnabled()) {
            for (WayPoint wp : gpx.getWayPoints()) {
                logger.trace("Waypoint: " + wp);
            }
        }
        for (Track track : gpx.getTracks()) {
            fitFileData.setActivityName(String.join(", ", fitFileData.getActivityName(), track.getName().orElse(null)));
            fitFileData.setDescription(String.join(", ", fitFileData.getDescription(), track.getDescription().orElse(null)));
        }
        double currentDistance = 0.0;
        double oldLatitude = 0;
        double oldLongitude = 0;
        boolean first = true;

        for (Track track : gpx.getTracks()) {
            if(logger.isTraceEnabled()) {
                logger.trace("Track: " + track + ", Track type: " + track.getType());
            }
            for (TrackSegment segment : track.getSegments()) {
                for (WayPoint wp : segment.getPoints()) {


                    Coordinate f = new Coordinate();

                    f.setLatitude(wp.getLatitude().doubleValue());
                    f.setLongitude(wp.getLongitude().doubleValue());
                    f.setAltitude(wp.getElevation().get().floatValue());
                    f.setTimestamp(new DateTime(wp.getTime().get().toInstant().toEpochMilli()));

                    if (!first) {
                        currentDistance = currentDistance +
                                haversine(
                                        oldLatitude,
                                        oldLongitude,
                                        wp.getLatitude().doubleValue(),
                                        wp.getLongitude().doubleValue()
                                );

                    } else {
                        first = false;
                    }


                    f.setDistance((float)currentDistance);

                    list.add(f);

                    oldLongitude = f.getLongitude();
                    oldLatitude = f.getLatitude();
                    if (logger.isTraceEnabled()) {
                        logger.trace("Track Point: " + wp + ", time:" + wp.getTime().orElse(null));
                    }
                }
            }
        }
        fitFileData.setCoordinates(list);
        saveActivity(fitFileData, fitFileData.getActivityName(), String.valueOf(fitFileData.getActivityId()));
    }

    public void saveTcxFile(InputStream inputStream, String name) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        TrainingCenterDatabase db = xmlMapper.readValue(inputStream, TrainingCenterDatabase.class);

        FitFileData fitFileData = new FitFileData();
        if (!db.getActivities().isEmpty()) {
            var metadata = db.getActivities().get(0);
            logger.info("Extracting " + name + " from: " + metadata.getId());
            fitFileData.setActivityName(name);
            fitFileData.setDescription(metadata.getNotes());
            List<Coordinate> list = new ArrayList<>();
            fitFileData.setStartTimeLocal(Date.from(metadata.getLap().get(0).getStartTime().toGregorianCalendar().toInstant()));
            for (Lap l : metadata.getLap()) {
                if (l.getTrack() != null && !l.getTrack().isEmpty()) {
                    for (var wp : l.getTrack()) {
                        Coordinate coordinate = new Coordinate();

                        if (wp.getPosition() != null) {
                            coordinate.setLatitude(wp.getPosition().getLatitudeDegrees());
                            coordinate.setLongitude(wp.getPosition().getLongitudeDegrees());
                        } else {
                                // Some activities may contain points without coordinates \
                                // (gps is not turned on while doing the activity)
                                //System.out.println(wp);
                        }
                        if (wp.getAltitudeMeters() != null) {
                            coordinate.setAltitude(wp.getAltitudeMeters().floatValue());
                        }
                        if (wp.getDistanceMeters() != null) {
                            coordinate.setDistance(wp.getDistanceMeters().floatValue());
                        }
                        if (wp.getHeartRateBpm() != null) {
                            coordinate.setHeartRate(wp.getHeartRateBpm().getValue());
                        }
                        coordinate.setTimestamp(new DateTime(wp.getTime().toGregorianCalendar().getTime()));
                        list.add(coordinate);
                    }
                } else {
                    logger.error("Missing track info for: " + name);
                }
            }

            fitFileData.setCoordinates(list);
            saveActivity(fitFileData, fitFileData.getActivityName(), String.valueOf(fitFileData.getActivityId()));
        } else {
            logger.error("Empty activity: " + name);
        }
    }

    public static double haversine(double lat1, double lon1, double lat2, double lon2) {
        final double R = 6371; // Earth's radius in km

        // Convert degrees to radians
        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);

        // Differences in coordinates
        double dlat = lat2 - lat1;
        double dlon = lon2 - lon1;

        // Haversine formula
        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // Distance
        return R * c;
    }
}
