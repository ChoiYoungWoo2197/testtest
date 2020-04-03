package com.uwo.isms.domain;

import com.uwo.isms.domain.enums.ProtectionMeasureStatus;

public class ProtectionMeasureVO {

    private int id;

    private int season;

    private String title;

    private ProtectionMeasureStatus status;

    private String createdAt;

    private String updatedAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ProtectionMeasureStatus getStatus() {
        return status;
    }

    public void setStatus(ProtectionMeasureStatus status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getGuideFileName() {
        return season + "년도 보호대책 수립지침서";
    }
}
