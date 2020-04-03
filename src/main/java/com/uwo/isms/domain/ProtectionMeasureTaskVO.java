package com.uwo.isms.domain;

import com.uwo.isms.domain.enums.ProtectionMeasureTaskNodeType;
import com.uwo.isms.domain.enums.ProtectionMeasureTaskStatus;

import java.util.List;

public class ProtectionMeasureTaskVO {

    private int id;

    private int protectionMeasureId;

    private int parentNodeId;

    private ProtectionMeasureTaskNodeType nodeType;

    private String title;

    private String taskContent;

    private String startedAt;

    private String endedAt;

    private String purpose;

    private String evaluationIndex;

    private String budget;

    private String targetFacility;

    private ProtectionMeasureTaskStatus status;

    private int userKey;

    private String createdAt;

    private String updatedAt;

    private String deletedAt;

    private String category1NodeId;

    private String taskNodeId;

    private String microTaskNodeId;

    private String nodeTreeIds;

    private String parentIds;

    private String childrenIds;

    private String workerKeys;

    private String workerKeysCascade;

    private List<FileVO> files;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProtectionMeasureId() {
        return protectionMeasureId;
    }

    public void setProtectionMeasureId(int protectionMeasureId) {
        this.protectionMeasureId = protectionMeasureId;
    }

    public int getParentNodeId() {
        return parentNodeId;
    }

    public void setParentNodeId(int parentNodeId) {
        this.parentNodeId = parentNodeId;
    }

    public ProtectionMeasureTaskNodeType getNodeType() {
        return nodeType;
    }

    public void setNodeType(ProtectionMeasureTaskNodeType nodeType) {
        this.nodeType = nodeType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTaskContent() {
        return taskContent;
    }

    public void setTaskContent(String taskContent) {
        this.taskContent = taskContent;
    }

    public String getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(String startedAt) {
        this.startedAt = startedAt;
    }

    public String getEndedAt() {
        return endedAt;
    }

    public void setEndedAt(String endedAt) {
        this.endedAt = endedAt;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getEvaluationIndex() {
        return evaluationIndex;
    }

    public void setEvaluationIndex(String evaluationIndex) {
        this.evaluationIndex = evaluationIndex;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getTargetFacility() {
        return targetFacility;
    }

    public void setTargetFacility(String targetFacility) {
        this.targetFacility = targetFacility;
    }

    public ProtectionMeasureTaskStatus getStatus() {
        return status;
    }

    public void setStatus(ProtectionMeasureTaskStatus status) {
        this.status = status;
    }

    public int getUserKey() {
        return userKey;
    }

    public void setUserKey(int userKey) {
        this.userKey = userKey;
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

    public String getCategory1NodeId() {
        return category1NodeId;
    }

    public void setCategory1NodeId(String category1NodeId) {
        this.category1NodeId = category1NodeId;
    }

    public String getTaskNodeId() {
        return taskNodeId;
    }

    public void setTaskNodeId(String taskNodeId) {
        this.taskNodeId = taskNodeId;
    }

    public String getMicroTaskNodeId() {
        return microTaskNodeId;
    }

    public void setMicroTaskNodeId(String microTaskNodeId) {
        this.microTaskNodeId = microTaskNodeId;
    }

    public String getNodeTreeIds() {
        return nodeTreeIds;
    }

    public void setNodeTreeIds(String nodeTreeIds) {
        this.nodeTreeIds = nodeTreeIds;
    }

    public String getWorkerKeys() {
        return workerKeys;
    }

    public void setWorkerKeys(String workerKeys) {
        this.workerKeys = workerKeys;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    public String getChildrenIds() {
        return childrenIds;
    }

    public void setChildrenIds(String childrenIds) {
        this.childrenIds = childrenIds;
    }

    public String getWorkerKeysCascade() {
        return workerKeysCascade;
    }

    public void setWorkerKeysCascade(String workerKeysCascade) {
        this.workerKeysCascade = workerKeysCascade;
    }

    public String getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
    }

    public List<FileVO> getFiles() {
        return files;
    }

    public void setFiles(List<FileVO> files) {
        this.files = files;
    }
}
