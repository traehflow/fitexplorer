package com.vsoft.fitexplorer.parsing.garmin;

import com.garmin.fit.*;
import com.vsoft.fitexplorer.jpl.entity.FitActivityType;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FitFileParserService {
    public FitFileData parseFitFile(String fitFilePath) {
        List<Coordinate> track = new ArrayList<>();
        FitFileData fitFileData = new FitFileData();
        final Sport[] sport = {Sport.RUNNING};

        try {
            FileInputStream fileInputStream = new FileInputStream(fitFilePath);

            Decode decode = new Decode();
            MesgBroadcaster mesgBroadcaster = new MesgBroadcaster(decode);
            mesgBroadcaster.addListener(new SessionMesgListener() {
                @Override
                public void onMesg(SessionMesg mesg) {
                    fitFileData.setSport(mesg.getSport());
                    fitFileData.setStartTimeLocal(mesg.getStartTime().getDate());
                    fitFileData.setDistance(doubleOrNull(mesg.getTotalDistance()));

                    fitFileData.setDuration(doubleOrNull(mesg.getTotalElapsedTime()));
                    fitFileData.setElapsedDuration(doubleOrNull(mesg.getTotalElapsedTime()));
                    fitFileData.setMovingDuration(doubleOrNull(mesg.getTotalMovingTime()));

                    fitFileData.setElevationGain(doubleOrNull(mesg.getTotalAscent()));
                    fitFileData.setElevationLoss(doubleOrNull(mesg.getTotalDescent()));

                    fitFileData.setAverageSpeed(doubleOrNull(mesg.getAvgSpeed()));

                    fitFileData.setAverageSpeed(doubleOrNull(mesg.getAvgSpeed()));
                    fitFileData.setMaxSpeed(doubleOrNull(mesg.getMaxSpeed()));

                }
            });

            mesgBroadcaster.addListener(new ActivityMesgListener() {
                @Override
                public void onMesg(ActivityMesg mesg) {
                    // If the activity name is stored in ActivityMesg
                    //fitFileData.setActivityName(mesg.getEvent().name());
                }
            });

            mesgBroadcaster.addListener(new MesgListener() {
                @Override
                public void onMesg(Mesg mesg) {
                    if (mesg.getNum() == MesgNum.FILE_ID) {
                        FileIdMesg fileIdMesg = new FileIdMesg(mesg);
                        // You can fetch other header information here if needed
                    }
                    if(mesg.getNum() == MesgNum.ACTIVITY) {
                        fitFileData.setActivityName(mesg.getName());
                    }
                }
            });

            mesgBroadcaster.addListener((RecordMesgListener) mesg -> {
                Coordinate coordinate = extractCoordinates(mesg);
                if (coordinate != null) {
                    track.add(coordinate);
                }
            });



            decode.read(fileInputStream, mesgBroadcaster, mesgBroadcaster);


        } catch (FitRuntimeException | IOException e) {
            e.printStackTrace();
        }

        fitFileData.setCoordinates(track);
        return fitFileData;
    }

    public Coordinate extractCoordinates(RecordMesg mesg) {
        if (mesg.getPositionLat() != null && mesg.getPositionLong() != null) {
            Double latitude = mesg.getPositionLat() == null? null : mesg.getPositionLat()  * (180.0 / Math.pow(2, 31));
            Double longitude = mesg.getPositionLong() == null? null : mesg.getPositionLong() * (180.0 / Math.pow(2, 31));
            Short heartRate = mesg.getHeartRate();
            DateTime timestamp = mesg.getTimestamp();
            Float altitude = mesg.getEnhancedAltitude();
            Float distance = mesg.getDistance();

            Byte temperature = mesg.getTemperature();


            //System.out.println("(" + latitude + ", " + longitude + ": " + altitude + "m asl, " + heartRate + " time: " + timestamp);


            return new Coordinate(null, latitude, longitude, altitude, heartRate, timestamp, distance, temperature);
        }
        return null;
    }

    public FitActivityType convert(Sport activityType) {
        if(activityType == null) {
            return null;
        }
        return switch (activityType) {
            case GENERIC, CROSS_COUNTRY_SKIING, ALPINE_SKIING, SNOWBOARDING, MOUNTAINEERING -> FitActivityType.OTHER;
            case ROWING -> FitActivityType.ROWING;
            case RUNNING -> FitActivityType.RUNNING;
            case CYCLING -> FitActivityType.CYCLING;
            case WALKING -> FitActivityType.WALKING;
            case TRANSITION, FITNESS_EQUIPMENT, INVALID, ALL -> FitActivityType.OTHER;
            case SWIMMING -> FitActivityType.SWIMMING;
            case BASKETBALL, SOCCER, TENNIS, AMERICAN_FOOTBALL, TRAINING -> FitActivityType.OTHER;
            case HIKING -> FitActivityType.HIKING;
            case MULTISPORT -> null;
            case PADDLING -> null;
            case FLYING -> null;
            case E_BIKING -> FitActivityType.E_BIKING;
            case MOTORCYCLING -> null;
            case BOATING -> null;
            case DRIVING -> null;
            case GOLF -> null;
            case HANG_GLIDING -> null;
            case HORSEBACK_RIDING -> null;
            case HUNTING -> null;
            case FISHING -> null;
            case INLINE_SKATING -> null;
            case ROCK_CLIMBING -> null;
            case SAILING -> null;
            case ICE_SKATING -> null;
            case SKY_DIVING -> null;
            case SNOWSHOEING -> null;
            case SNOWMOBILING -> null;
            case STAND_UP_PADDLEBOARDING -> null;
            case SURFING -> null;
            case WAKEBOARDING -> null;
            case WATER_SKIING -> null;
            case KAYAKING -> FitActivityType.KAYAKING;
            case RAFTING -> null;
            case WINDSURFING -> null;
            case KITESURFING -> null;
            case TACTICAL -> null;
            case JUMPMASTER -> null;
            case BOXING -> null;
            case FLOOR_CLIMBING -> null;
            case DIVING -> null;
            case HIIT -> null;
            case RACKET -> null;
            case WHEELCHAIR_PUSH_WALK -> null;
            case WHEELCHAIR_PUSH_RUN -> null;
            case MEDITATION -> null;
            case WATER_TUBING -> null;
            case WAKESURFING -> null;
        };
    }


    private static Double doubleOrNull(Float value) {
        return  value == null? null : Double.valueOf(value);
    }

    private static Double doubleOrNull(Integer value) {
        return  value == null? null : Double.valueOf(value);
    }

}
