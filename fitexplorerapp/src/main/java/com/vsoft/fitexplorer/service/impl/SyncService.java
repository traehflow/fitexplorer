package com.vsoft.fitexplorer.service.impl;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vsoft.fitexplorer.FitFileParser;
import com.vsoft.fitexplorer.dto.AuthDetails;
import com.vsoft.fitexplorer.jpl.FitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
public class SyncService {
    static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Autowired
    FitRepository fitRepository;

    @Autowired
    ActivityService activityService;

    @Async
    public CompletableFuture<Map<String, GarminActivity>> asSync(AuthDetails authDetails) throws JsonProcessingException {
        var result = sync(authDetails);
        return new AsyncResult<>(result).completable();
    }

    public Map<String, GarminActivity> sync(AuthDetails jwtToken) throws JsonProcessingException {
        List<GarminActivity> garminActivities = new ArrayList<GarminActivity>();
        int start = 0;
        int count = 1000;
        List<GarminActivity> result = new ArrayList<>();
        Map<String, GarminActivity> map = new HashMap<>();
        List<GarminActivity> current;
        do {
            current = extractActivities(jwtToken, count, start);
            map.putAll(current.stream().collect(Collectors.toMap(GarminActivity::getActivityId, x -> x, (x, y) -> x)));
            start += count;
        } while (current.size() == count);

        Set<String> persistedIds = fitRepository.listFitActivitiesIDs();
        persistedIds.stream().map(String::valueOf).collect(Collectors.toSet()).forEach(map::remove);

        map.values().stream().forEach(x -> {
            try {
                downloadActivity(jwtToken, x.getActivityId(), x.getActivityName());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        return map;
    }

    private List<GarminActivity> extractActivities(AuthDetails jwtToken, int count, int start) throws JsonProcessingException {
        String url = "https://connect.garmin.com/activitylist-service/activities/search/activities?limit=" + count + "&start=" + start + "&_=1706304901809";
        HttpHeaders headers = new HttpHeaders();

        headers.set("authority", "connect.garmin.com");
        headers.set("accept", "application/json, text/javascript, */*; q=0.01");
        headers.set("accept-language", "en-US,en;q=0.9,bg-BG;q=0.8,bg;q=0.7");
        headers.set("di-backend", "connectapi.garmin.com");
        headers.set("Authorization", "Bearer " + jwtToken.getJwtToken());
        headers.set("nk", "NT");
        headers.set("sec-ch-ua", "\"Not_A Brand\";v=\"8\", \"Chromium\";v=\"120\", \"Google Chrome\";v=\"120\"");
        headers.set("sec-ch-ua-mobile", "?0");
        headers.set("sec-ch-ua-platform", "\"Linux\"");
        headers.set("sec-fetch-dest", "empty");
        headers.set("sec-fetch-mode", "cors");
        headers.set("sec-fetch-site", "same-origin");
        headers.set("user-agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");
        headers.set("x-app-ver", "4.74.2.4");
        headers.set("x-lang", "en-US");
        headers.set("x-requested-with", "XMLHttpRequest");
        headers.set("referer", "https://connect.garmin.com/modern/activities'");
        headers.set("cookie", "JWT_FGP=" + jwtToken.getJwtFgt());
        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();


        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        String responseBody = responseEntity.getBody();
        int statusCode = responseEntity.getStatusCode().value();
        System.out.println("Response Code: " + statusCode);
        System.out.println("Response Body: " + responseBody);

        List<GarminActivity> activities = objectMapper.readValue(responseBody, new TypeReference<List<GarminActivity>>() {
        });
        return activities;
    }

    private String downloadActivity(AuthDetails jwtToken, String activityId, String activityName) throws IOException {
        String url = "https://connect.garmin.com/download-service/files/activity/" + activityId;
        HttpEntity<String> entity = getStringHttpEntity(jwtToken, activityId);

        RestTemplate restTemplate = new RestTemplate();


        ResourceHttpMessageConverter resourceHttpMessageConverter = new ResourceHttpMessageConverter();
        resourceHttpMessageConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.parseMediaType("application/x-zip-compressed")));

        restTemplate.getMessageConverters().add(resourceHttpMessageConverter);


        ResponseEntity<Resource> responseEntity = restTemplate.exchange(url, HttpMethod.GET,
                entity, Resource.class);
        ZipInputStream zis = new ZipInputStream(responseEntity.getBody().getInputStream());
        ZipEntry entry;
        String result = "";
        while ((entry = zis.getNextEntry()) != null) {
            activityService.saveActivity(zis, activityName, activityId);
            result += "entry: " + entry.getName() + ", " + entry.getSize();
        }
        return result;
    }

    private static HttpEntity<String> getStringHttpEntity(AuthDetails jwtToken, String activityId) {
        HttpHeaders headers = new HttpHeaders();

        headers.set("authority", "connect.garmin.com");
        headers.set("accept", "*/*");
        headers.set("Accept-Encoding", "gzip, deflate, br");
        headers.set("accept-language", "en-US,en;q=0.9,bg-BG;q=0.8,bg;q=0.7");
        headers.set("di-backend", "connectapi.garmin.com");
        headers.set("Authorization", "Bearer " + jwtToken.getJwtToken());
        headers.set("nk", "NT");
        headers.set("sec-ch-ua", "\"Not_A Brand\";v=\"8\", \"Chromium\";v=\"120\", \"Google Chrome\";v=\"120\"");
        headers.set("sec-ch-ua-mobile", "?0");
        headers.set("sec-ch-ua-platform", "\"Linux\"");
        headers.set("sec-fetch-dest", "empty");
        headers.set("sec-fetch-mode", "cors");
        headers.set("sec-fetch-site", "same-origin");
        headers.set("user-agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");
        headers.set("x-app-ver", "4.74.2.4");
        headers.set("x-lang", "en-US");
        headers.set("x-requested-with", "XMLHttpRequest");
        headers.set("referer", "https://connect.garmin.com/modern/activity/" + activityId);
        headers.set("cookie", "JWT_FGP=" + jwtToken.getJwtFgt());
        HttpEntity<String> entity = new HttpEntity<>(headers);
        return entity;
    }

    private static GarminActivity parseActivity(JsonParser parser) throws IOException {
        GarminActivity garminActivity = new GarminActivity();
        while (!JsonToken.END_OBJECT.equals(parser.nextToken())) {
            if (JsonToken.FIELD_NAME.equals(parser.currentToken())) {
                String fieldName = parser.getCurrentName();
                parser.nextToken();

                if ("activityId".equals(fieldName)) {
                    garminActivity.setActivityId(parser.getText());
                    System.out.println("Activity ID: " + parser.getLongValue());
                } else if ("activityName".equals(fieldName)) {
                    garminActivity.setActivityName(parser.getText());
                    System.out.println("Activity Name: " + parser.getText());
                }

            }
        }
        return garminActivity;
    }

}
