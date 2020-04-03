package com.uwo.isms.domain.enums;

public enum ProtectionMeasureTaskStatus {
    INITIATED("진행중"),
    FINISHED("완료"),
    ABORTED("실패");

    private String title;

    ProtectionMeasureTaskStatus(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
