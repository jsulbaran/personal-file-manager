package com.jsulbaran.apps.personalfilemanager.dto;

public record FileDTO(String path, String name) {
    @Override
    public String toString(){
        return name;
    }
    public String serialize(){
        return "%s,%s".formatted(path,name);
    }
}
