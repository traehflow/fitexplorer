package com.vsoft.fitexplorer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.garmin.fit.*;
import com.vsoft.fitexplorer.jpl.FitRepository;
import com.vsoft.fitexplorer.jpl.UserRepository;
import com.vsoft.fitexplorer.jpl.entity.FitActivity;
import com.vsoft.fitexplorer.jpl.entity.FitActivityType;
import com.vsoft.fitexplorer.jpl.entity.FitUnit;
import com.vsoft.fitexplorer.parsing.garmin.Coordinate;
import com.vsoft.fitexplorer.parsing.garmin.FitFileData;
import com.vsoft.fitexplorer.service.impl.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.PropertySource;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@PropertySource("classpath:application.yml")
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class})
public class FitFileParser {

    @Autowired
    ActivityService activityService;

    @Autowired
    private static UserRepository userRepository;


    public static void main(String[] args) throws IOException {
//        Resource resource = applicationContext.getResource("classpath:/");
//        Resource resource = resourceLoader.getResource("classpath:/");

        ApplicationContext context = new AnnotationConfigApplicationContext(FitFileParser.class);

        // Retrieve the bean from the context
        UserRepository userRepository = context.getBean(UserRepository.class);
        FitRepository fitRepository = context.getBean(FitRepository.class);
        ActivityService activityService = context.getBean(ActivityService.class);
        ///home/vladimir/Downloads/13123731108_ACTIVITY.fit
        System.out.println(System.getProperty("user.dir"));
        if (args.length != 1) {
            System.out.println("Usage: java FitFileParserService <fit-file-path>");
            System.exit(1);
        }


        String jsonFilePath = "/home/vladimir/activities.json";

        iterateActivitiesJsonFile("/home/vladimir/activities.json", userRepository, fitRepository, activityService);
        iterateActivitiesJsonFile("/home/vladimir/activities2.json", userRepository, fitRepository, activityService);

        /*Files.walk(Path.of("/home/vladimir/activities/"), FileVisitOption.FOLLOW_LINKS)
                .filter(Files::isRegularFile)
                .filter(p -> p.getFileName().toString().endsWith(".fit"))
                .forEach(x -> saveActivity(x.toFile().getPath(), userRepository, fitRepository, "hui"));
        */
    }

    private static void iterateActivitiesJsonFile(String jsonFilePath, UserRepository userRepository, FitRepository fitRepository, ActivityService activityService) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(new java.io.File(jsonFilePath));

        Iterator<JsonNode> elements = rootNode.elements();
        while (elements.hasNext()) {
            JsonNode element = elements.next();
            String activityId = element.get("activityId").asText();
            String activityName = element.get("activityName").asText();

            // Do something with activityId and activityName
            System.out.println("Saving Activity ID: " + activityId + " Activity Name: " + activityName);

            activityService.saveActivity("/home/vladimir/activities/" + activityId + "_ACTIVITY.fit", userRepository, fitRepository, activityName);
        }
    }





}
