package com.vsoft.fitexplorer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.garmin.fit.*;
import com.vsoft.fitexplorer.jpl.FitRepository;
import com.vsoft.fitexplorer.jpl.UserRepository;
import com.vsoft.fitexplorer.jpl.entity.FitActivity;
import com.vsoft.fitexplorer.jpl.entity.FitActivityType;
import com.vsoft.fitexplorer.jpl.entity.FitUnit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.PropertySource;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@PropertySource("classpath:application.yml")
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class})
public class FitFileParser {

    @Autowired
    private static UserRepository userRepository;


    public static void main(String[] args) throws IOException {
//        Resource resource = applicationContext.getResource("classpath:/");
//        Resource resource = resourceLoader.getResource("classpath:/");

        ApplicationContext context = new AnnotationConfigApplicationContext(FitFileParser.class);

        // Retrieve the bean from the context
        UserRepository userRepository = context.getBean(UserRepository.class);
        FitRepository fitRepository = context.getBean(FitRepository.class);
        ///home/vladimir/Downloads/13123731108_ACTIVITY.fit
        System.out.println(System.getProperty("user.dir"));
        if (args.length != 1) {
            System.out.println("Usage: java FitFileParser <fit-file-path>");
            System.exit(1);
        }


        String jsonFilePath = "/home/vladimir/activities.json";

        iterateActivitiesJsonFile("/home/vladimir/activities.json", userRepository, fitRepository);
        iterateActivitiesJsonFile("/home/vladimir/activities2.json", userRepository, fitRepository);

        /*Files.walk(Path.of("/home/vladimir/activities/"), FileVisitOption.FOLLOW_LINKS)
                .filter(Files::isRegularFile)
                .filter(p -> p.getFileName().toString().endsWith(".fit"))
                .forEach(x -> saveActivity(x.toFile().getPath(), userRepository, fitRepository, "hui"));
        */
    }

    private static void iterateActivitiesJsonFile(String jsonFilePath, UserRepository userRepository, FitRepository fitRepository) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(new java.io.File(jsonFilePath));

        Iterator<JsonNode> elements = rootNode.elements();
        while (elements.hasNext()) {
            JsonNode element = elements.next();
            String activityId = element.get("activityId").asText();
            String activityName = element.get("activityName").asText();

            // Do something with activityId and activityName
            System.out.println("Saving Activity ID: " + activityId + " Activity Name: " + activityName);

            saveActivity("/home/vladimir/activities/" + activityId + "_ACTIVITY.fit", userRepository, fitRepository, activityName);
        }
    }

    private static void saveActivity(String fitFilePath, UserRepository userRepository, FitRepository fitRepository, String activityName) {
        var fitFileData = parseFitFile(fitFilePath);
        List<Coordinate> track = fitFileData.getCoordinates();
        var user = userRepository.loadUser("wolfheart@mail.com");
        FitActivity fitActivity = new FitActivity();
        fitActivity.setUserData(user);
        fitActivity.setStartTime(0L);
        if(!track.isEmpty()) {
            fitActivity.setFitActivityType(convert(fitFileData.getSport()));
            fitActivity.setStartTime(track.get(0).getTimestamp().getTimestamp());
            fitActivity.setOriginalFile(fitFilePath);
            fitActivity.setActivityName(activityName);

            //String description;
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

    private static FitFileData parseFitFile(String fitFilePath) {
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
            //MesgBroadcaster mesgBroadcaster2 = new MesgBroadcaster(decode);
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

    private static Coordinate extractCoordinates(RecordMesg mesg) {
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

    private static FitActivityType convert(Sport activityType) {
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
            case E_BIKING -> null;
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
            case KAYAKING -> null;
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

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class FitFileData {
        private List<Coordinate> coordinates;
        private Sport sport;
        //==================
        Long activityId;
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

    @Data
    @ToString
    @AllArgsConstructor
    private static class Coordinate {
        private final FitActivityType fitActivityType;
        private final Double latitude;
        private final Double longitude;
        private final Float altitude;
        private final Short heartRate;
        private final DateTime timestamp;
        private final Float distance;
        private final Byte temperature;

    }

    private static Double doubleOrNull(Float value) {
        return  value == null? null : Double.valueOf(value);
    }

    private static Double doubleOrNull(Integer value) {
        return  value == null? null : Double.valueOf(value);
    }

}
