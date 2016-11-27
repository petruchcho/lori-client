package com.egorpetruchcho.loriandroid_api.model;

import java.util.List;

public class Project {

    private String id;
    private String name;

    private List<Task> tasks;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Task> getTasks() {
        return tasks;
    }
}
