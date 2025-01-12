package com.vsoft.fitexplorer.service.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.vsoft.fitexplorer.dto.MapMyRunAuthDetail;
import com.vsoft.fitexplorer.jpl.FitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class MapMyRunSyncService {
    static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        objectMapper.findAndRegisterModules();


    }

    @Autowired
    FitRepository fitRepository;

    @Autowired
    ActivityService activityService;

    @Async
    public CompletableFuture<Map<String, MapMyRunActivity>> asSync(MapMyRunAuthDetail garminAuthDetails) throws JsonProcessingException {
        var result = sync(garminAuthDetails);
        return new AsyncResult<>(result).completable();
    }

    public Map<String, MapMyRunActivity> sync(MapMyRunAuthDetail mapMyRunAuthDetail) throws JsonProcessingException {
        List<MapMyRunActivity> garminActivities = new ArrayList<>();
        int start = 0;
        int count = 1000;
        List<MapMyRunActivity> result = new ArrayList<>();
        Map<String, MapMyRunActivity> map = new HashMap<>();
        List<MapMyRunActivity> current;
       // do {
            current = extractActivities(mapMyRunAuthDetail);
            map.putAll(current.stream().collect(Collectors.toMap(a -> a.getLinks().getSelf().get(0).getId(), x -> x, (x, y) -> x)));
            start += count;
        //} while (current.size() == count);

        Set<String> persistedIds = fitRepository.listFitActivitiesIDs();
        persistedIds.stream().map(String::valueOf).collect(Collectors.toSet()).forEach(map::remove);

        map.values().stream().forEach(x -> {
            try {
                downloadActivity(mapMyRunAuthDetail, x.getLinks().getSelf().get(0).getId(), x.getName());
            } catch (Exception e) {
                System.out.println("Cannot extract activity " + x.getName() + ", reason: " + e.getMessage());throw new RuntimeException(e);
            }
        });

        return map;
    }

    private List<MapMyRunActivity> extractActivities(MapMyRunAuthDetail authDetails) throws JsonProcessingException {
        String url = "https://www.mapmyrun.com/internal/allWorkouts/?user=" + authDetails.getUserId();
        HttpHeaders headers = new HttpHeaders();

        headers.set("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7");
        headers.set("accept-language", "en-US,en;q=0.9,bg-BG;q=0.8,bg;q=0.7");
        headers.set("cookie", "notice_preferences=2:; TAconsentID=43ce7b43-dacf-4f90-9d51-994cd91784a1; notice_gdpr_prefs=0,1,2:; cmapi_gtm_bl=; cmapi_cookie_privacy=permit 1,2,3; __privaci_cookie_consent_uuid=7a00aeab-5cad-4f73-8a52-baec60b5a0d5:2; __privaci_cookie_consent_generated=7a00aeab-5cad-4f73-8a52-baec60b5a0d5:2; " 
                + "__privaci_cookie_consents={\"consents\":{\"2269\":1,\"2270\":1,\"2271\":1,\"2272\":1},\"location\":\"22#BG\",\"lang\":\"en\",\"gpcInBrowserOnConsent\":false,\"gpcStatusInPortalOnConsent\":false,\"status\":\"record-consent-success\",\"implicit_consent\":false}; __privaci_latest_published_version=4; access-token=US.5857z0cv-IOTOrQszaUkPFgQem5u4IOVsME7tWj7IkcizP6JihNt9ZpPTGqq4VZ9IM0Ks8pEcXYks2opVjR0OWc9KHwqPAkEqFpeapVfxogvhG5txKVjDJEGZAZpiCDWdrQw; device_id=982c7657-6deb-4265-bd74-f144906e6b60R; api-key=f04cf680-9224-4de5-8ae8-5883c4730737;"
                +" auth-token=BG.58570_ZwjgQVamY88XoEmOkrYbRx69jNzVbGWHVk4PLzQOcs29EOfckSH2qy2BvLZSI9gdvkXDNLeiomWNJigJQDUqNNFKsEbC1PD_1h0QRw7oyEVBi1PADCgn_EZsXicl5Z;"
                + "runwebsessionid=undefined; "
                +" auth-token-expiry=2025-02-13T10%3A08%3A32%2B00%3A00; amplitude_id_57604f432418057dd065323ce1d21c42mapmyrun.com=eyJkZXZpY2VJZCI6Ijk4MmM3NjU3LTZkZWItNDI2NS1iZDc0LWYxNDQ5MDZlNmI2MFIiLCJ1c2VySWQiOiIxODY1ODExMjIiLCJvcHRPdXQiOmZhbHNlLCJzZXNzaW9uSWQiOjE3MzQyNTcyOTk3OTgsImxhc3RFdmVudFRpbWUiOjE3MzQyNTc0MTIxNjMsImV2ZW50SWQiOjE2LCJpZGVudGlmeUlkIjo5LCJzZXF1ZW5jZU51bWJlciI6MjV9");
        headers.set("user-agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/129.0.0.0 Safari/537.36");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();


        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        String responseBody = responseEntity.getBody();
        int statusCode = responseEntity.getStatusCode().value();
        System.out.println("Response Code: " + statusCode);
        System.out.println("Response Body: " + responseBody);

        List<MapMyRunActivity> activities = objectMapper.readValue(responseBody, new TypeReference<List<MapMyRunActivity>>() {
        });
        return activities;
    }

    private String downloadActivity(MapMyRunAuthDetail jwtToken, String activityId, String activityName) throws IOException {
        String url = "https://www.mapmyrun.com/workout/export/" + activityId + "/tcx";

        HttpEntity<String> entity = getHttpEntity(jwtToken, activityId);

        RestTemplate restTemplate = new RestTemplate();


        ResourceHttpMessageConverter resourceHttpMessageConverter = new ResourceHttpMessageConverter();
        resourceHttpMessageConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.parseMediaType("text/plain")));
        resourceHttpMessageConverter.setDefaultCharset(Charset.defaultCharset());//; charset=utf8

        restTemplate.getMessageConverters().add(resourceHttpMessageConverter);


        ResponseEntity<Resource> responseEntity = restTemplate.exchange(url, HttpMethod.GET,
                entity, Resource.class);
        var body = responseEntity.getBody();
        if(body != null) {
            InputStream zis = responseEntity.getBody().getInputStream();
            activityService.saveTcxFile(zis, activityName);
        } else {
            System.out.println("Empty body");
        }

        return "";
    }

    private static HttpEntity<String> getHttpEntity(MapMyRunAuthDetail jwtToken, String activityId) {
        HttpHeaders headers = new HttpHeaders();

        headers.set("authority", "connect.garmin.com");
        headers.set("accept", "*/*");
        headers.set("accept-language", "en-US,en;q=0.9,bg-BG;q=0.8,bg;q=0.7");
        headers.set("di-backend", "connectapi.garmin.com");
        headers.set("Authorization", "Bearer ");// + jwtToken.getJwtToken());
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
        headers.set("cookie", "JWT_FGP=" + jwtToken);//.getJwtFgt());
        HttpEntity<String> entity = new HttpEntity<>(headers);
        return entity;
    }

    private static HttpEntity<String> getStringHttpEntity(MapMyRunAuthDetail jwtToken, String activityId) {
        HttpHeaders headers = new HttpHeaders();

        headers.set("authority", "connect.garmin.com");
        headers.set("accept", "*/*");
        headers.set("Accept-Encoding", "gzip, deflate, br");
        headers.set("accept-language", "en-US,en;q=0.9,bg-BG;q=0.8,bg;q=0.7");
        headers.set("di-backend", "connectapi.garmin.com");
        headers.set("Authorization", "Bearer ");// + jwtToken.getJwtToken());
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
        headers.set("cookie", "JWT_FGP=" + jwtToken);//.getJwtFgt());
        HttpEntity<String> entity = new HttpEntity<>(headers);
        return entity;
    }

    private static MapMyRunActivity parseActivity(JsonParser parser) throws IOException {
        MapMyRunActivity mapMyRunActivity = new MapMyRunActivity();
        while (!JsonToken.END_OBJECT.equals(parser.nextToken())) {
            if (JsonToken.FIELD_NAME.equals(parser.currentToken())) {
                String fieldName = parser.getCurrentName();
                parser.nextToken();

                if ("activityId".equals(fieldName)) {
                    mapMyRunActivity.setId(parser.getText());
                    System.out.println("Activity ID: " + parser.getLongValue());
                } else if ("activityName".equals(fieldName)) {
                    mapMyRunActivity.setName(parser.getText());
                    System.out.println("Activity Name: " + parser.getText());
                }

            }
        }
        return mapMyRunActivity;
    }


}
