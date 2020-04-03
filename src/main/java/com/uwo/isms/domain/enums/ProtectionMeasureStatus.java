package com.uwo.isms.domain.enums;

public enum ProtectionMeasureStatus {
    STAGED("대기중"),
    INITIATED("진행중"),
    HOLD("일시정지"),
    FINISHED("완료"),
    ABORTED("실패");

    private String title;

    ProtectionMeasureStatus(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
