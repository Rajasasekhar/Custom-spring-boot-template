package com.hm.utils;

import lombok.Data;

import java.util.List;

@Data
public class ProjectRequest {
    private String groupId;
    private String artifactId;
    private String name;
    private String description;
    private String packaging;
    private List<String> dependencies;


}
