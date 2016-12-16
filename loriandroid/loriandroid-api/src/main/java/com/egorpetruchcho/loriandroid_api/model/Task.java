package com.egorpetruchcho.loriandroid_api.model;


public class Task {

    private String id;
    private String name;
    private Project project;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getProjectName() {
        if (project != null) {
            return project.getName();
        }
        return null;
    }
}
