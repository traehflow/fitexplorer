package com.vsoft.fitexplorer.service.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vsoft.fitexplorer.dto.GarminAuthDetails;
import com.vsoft.fitexplorer.jpl.FitRepository;
import org.apache.commons.collections4.SetUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
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
    public CompletableFuture<Map<String, GarminActivity>> asSync(GarminAuthDetails garminAuthDetails, int userId) throws JsonProcessingException {
        var result = sync(garminAuthDetails, userId);
        return new AsyncResult<>(result).completable();
    }

    public Map<String, GarminActivity> sync(GarminAuthDetails garminAuthDetails, int userId) throws JsonProcessingException {
        List<GarminActivity> garminActivities = new ArrayList<GarminActivity>();
        int start = 0;
        int count = 1000;
        List<GarminActivity> result = new ArrayList<>();
        Map<String, GarminActivity> map = new HashMap<>();
        List<GarminActivity> current;
        do {
            current = extractGarminActivities(garminAuthDetails, count, start);
            map.putAll(current.stream().collect(Collectors.toMap(GarminActivity::getActivityId, x -> x, (x, y) -> x)));
            start += count;
        } while (current.size() == count);

        Set<String> persistedIds = fitRepository.listFitActivitiesIDs(userId);
        SetUtils.emptyIfNull(persistedIds).stream().map(String::valueOf).collect(Collectors.toSet()).forEach(map::remove);

        map.values().stream().forEach(x -> {
            try {
                downloadActivity(garminAuthDetails, x.getActivityId(), x.getActivityName());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        return map;
    }

    private List<GarminActivity> extractGarminActivities(GarminAuthDetails jwtToken, int count, int start) throws JsonProcessingException {
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
        headers.set("cookie", "_ga_1K2WNZ9N3T=GS1.1.1705145373.2.1.1705145375.0.0.0; utag_main_v_id_0188629cdbfb0067999f4b43b67005065001b05d0093c=undefined; utag_main__sn_187=undefined; utag_main_v_id=0193c5aba5c600ae0368497b79d805065001d05d0093c; GarminUserPrefs=en-US; SERVERID=cv1xpa-matomo01; CONSENTMGR=c1:1%7Cc2:1%7Cc3:1%7Cc4:1%7Cc5:1%7Cc6:1%7Cc7:1%7Cc8:1%7Cc9:1%7Cc10:1%7Cc11:1%7Cc12:1%7Cc13:1%7Cc14:1%7Cc15:1%7Cts:1739391083749%7Cconsent:true; utag_main__sn=209; _gid=GA1.2.138886237.1739391088; _ga=GA1.1.1945401928.1685281673; GARMIN-SSO=1; GARMIN-SSO-CUST-GUID=d99271f7-70a3-4862-bc8e-e4e48450d2ff;JWT_FGP=" + jwtToken.getJwtFgt());
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

    private String downloadActivity(GarminAuthDetails garminAuthDetails, String activityId, String activityName) throws IOException {
        String url = "https://connect.garmin.com/download-service/files/activity/" + activityId;
        HttpEntity<String> entity = getStringHttpEntity(garminAuthDetails, activityId);

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

    private static HttpEntity<String> getStringHttpEntity(GarminAuthDetails jwtToken, String activityId) {
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
