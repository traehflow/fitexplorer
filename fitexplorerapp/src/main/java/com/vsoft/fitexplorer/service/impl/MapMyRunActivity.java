package com.vsoft.fitexplorer.service.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class MapMyRunActivity {
    private String name;

    @JsonDeserialize(using = ActivityIdDeserializer.class)
    private String id;

    @JsonProperty("_links")
    private RestrictedLinks links;
    //private Map<String, List<ActivityLink>> links;

    @Data
    public static class RestrictedLinks {
        private List<ActivityLink> self;
    }
}

